package com.github.rcaller.io.arrow;

import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.exception.ParseException;
import com.github.rcaller.io.ArrowBridge;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.BaseIntVector;
import org.apache.arrow.vector.FloatingPointVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.complex.ListVector;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Field;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArrowImpl extends ArrowBridge {
    private RootAllocator memoryAllocator;
    private final List<VectorSchemaRoot> vectors = new ArrayList<>();
    private final List<VectorSchemaRoot> cleaning = new ArrayList<>();

    @Override
    public void loadArrowData(URI ipcResource) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(new File(ipcResource).toPath())) {
            Supplier<Boolean> channelOver = () -> {
                try {
                    return fileChannel.position() == fileChannel.size();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            free();
            memoryAllocator = new RootAllocator();
            //Read entire channel (can contain several frames)
            while (!channelOver.get()) {
                var arrowStreamReader = new ArrowStreamReader(fileChannel, memoryAllocator);

                //Expect that each frame has one batch, load it
                if (!arrowStreamReader.loadNextBatch()) {
                    throw new IOException("Can not load data batch");
                }
                VectorSchemaRoot vectorSchemaRoot = arrowStreamReader.getVectorSchemaRoot();
                cleaning.add(vectorSchemaRoot);
                //Copy loaded data (original will be lost on final loadNextBatch invocation)
                var buffer = new ByteArrayOutputStream();
                var bufferWriter = new ArrowStreamWriter(vectorSchemaRoot, null, buffer);
                bufferWriter.writeBatch();
                bufferWriter.end();

                var bufferReader = new ArrowStreamReader(new ByteArrayInputStream(buffer.toByteArray()), memoryAllocator);
                bufferReader.loadNextBatch();
                vectors.add(bufferReader.getVectorSchemaRoot());
                //Invoke loadNextBatch() again for correct stepping along data channel (in case if we have next frame)
                //Assert that current frame is ended
                if (arrowStreamReader.loadNextBatch()) {
                    throw new IOException("Multiple batches are not supported in current version");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getNames() {
        return vectors.stream()
                .flatMap((VectorSchemaRoot vectorSchemaRoot) -> vectorSchemaRoot.getSchema().getFields().stream())
                .map(Field::getName).collect(Collectors.toList());
    }

    private FieldVector findVector(String vectorName) {
        var vectorIfPresent =  vectors.stream()
                .flatMap((VectorSchemaRoot vectorSchemaRoot) -> vectorSchemaRoot.getFieldVectors().stream())
                .filter((FieldVector vector) -> vector.getName().equals(vectorName)).findFirst();
        if (vectorIfPresent.isPresent()) {
            return vectorIfPresent.get();
        } else {
            throw new ExecutionException("Vector " + vectorName + " is not found");
        }
    }

    @Override
    public int[] getDimensions(String name) {
        var vector = findVector(name);
        int[] result = new int[2];
        result[0] = vector.getValueCount();
        if (vector instanceof FixedSizeListVector) {
            result[1] = ((FixedSizeListVector) vector).getListSize();
        } else {
            result[1] = 1;
        }
        return result;
    }

    @Override
    public double[] getAsDoubleArray(String name) {
        var vector = findVector(name);
        if (vector instanceof FloatingPointVector) {
            var result = new double[vector.getValueCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = ((FloatingPointVector)vector).getValueAsDouble(i);
            }
            return result;
        } else if (vector instanceof BaseIntVector) {
            var result = new double[vector.getValueCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = ((BaseIntVector)vector).getValueAsLong(i);
            }
            return result;
        } else if (vector instanceof VarCharVector) {
            var result = new double[vector.getValueCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = Double.parseDouble(((VarCharVector)vector).getObject(i).toString());
            }
            return result;
        } else if (vector instanceof FixedSizeListVector || vector instanceof ListVector) {
            var buffer = new ArrayList<Double>();
            for (int i = 0; i < vector.getValueCount(); i++) {
                List row = (List) vector.getObject(i);
                buffer.addAll(row);
            }
            var result = new double[buffer.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = buffer.get(i);
            }
            return result;
        } else {
            throw new ParseException("Can not read from this vector");
        }
    }

    public double[][] getAsDoubleMatrix(String name) {
        var vector = findVector(name);
        if (vector instanceof FixedSizeListVector || vector instanceof ListVector) {
            var result = new double[vector.getValueCount()][];
            for (int i = 0; i < vector.getValueCount(); i++) {
                List row = (List) vector.getObject(i);
                //var rowOut = new ArrayList<Double>();
                result[i] = new double[row.size()];
                for (int j = 0; j < row.size(); j++) {
                    result[i][j] = (Double)row.get(j);
                }
            }
            return result;
        } else {
            throw new ParseException("Can not read from this vector " + vector.getClass().getCanonicalName());
        }
    }

    private void free() {
        cleaning.addAll(vectors);
        for (VectorSchemaRoot vectorSchemaRoot: cleaning) {
            if (vectorSchemaRoot != null) {
                vectorSchemaRoot.close();
            }
        }
        cleaning.clear();
        vectors.clear();

        if (memoryAllocator != null) {
            memoryAllocator.close();
            memoryAllocator = null;
        }
    }

    @Override
    public void close() throws Exception {
        free();
    }
}
