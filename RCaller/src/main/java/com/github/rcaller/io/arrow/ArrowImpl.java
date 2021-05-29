package com.github.rcaller.io.arrow;

import com.github.rcaller.exception.ParseException;
import com.github.rcaller.io.ArrowBridge;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.*;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.complex.ListVector;
import org.apache.arrow.vector.ipc.ArrowFileReader;
import org.apache.arrow.vector.types.pojo.Field;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ArrowImpl extends ArrowBridge {
    private RootAllocator memoryAllocator;
    private VectorSchemaRoot vectorSchemaRoot;

    @Override
    public void loadArrowData(URI ipcResource) throws IOException {
        FileChannel fileChannel = FileChannel.open(new File(ipcResource).toPath());
        memoryAllocator = new RootAllocator();
        ArrowFileReader arrowFileReader = new ArrowFileReader(fileChannel, memoryAllocator);
        arrowFileReader.loadNextBatch();
        vectorSchemaRoot = arrowFileReader.getVectorSchemaRoot();
    }

    @Override
    public ArrayList<String> getNames() {
        var result = new ArrayList<String>();
        for (Field field: vectorSchemaRoot.getSchema().getFields()) {
            result.add(field.getName());
        }
        return result;
    }

    @Override
    public int[] getDimensions(String name) {
        var vector = vectorSchemaRoot.getVector(name);
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
        var vector = vectorSchemaRoot.getVector(name);
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
        var vector = vectorSchemaRoot.getVector(name);
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
            throw new ParseException("Can not read from this vector");
        }
    }

    @Override
    public void close() throws Exception {
        if (vectorSchemaRoot != null) {
            vectorSchemaRoot.close();
            vectorSchemaRoot = null;
        }
        if (memoryAllocator != null) {
            memoryAllocator.close();
            memoryAllocator = null;
        }
    }
}
