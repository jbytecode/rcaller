package com.github.rcaller.io;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.rstuff.RCode;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class ArrowBridge implements AutoCloseable {
    private static Boolean arrowAvailable = null;

    public static synchronized boolean isArrowAvailable(RCallerOptions rCallerOptions) {
        if (arrowAvailable == null) {
            arrowAvailable = isArrowAvailableInJava() && isArrowAvailableInR(rCallerOptions);
        }
        return arrowAvailable;
    }

    private static Boolean arrowAvailableInJava = null;

    private static synchronized boolean isArrowAvailableInJava() {
        if (arrowAvailableInJava == null) {
            try {
                org.apache.arrow.memory.RootAllocator rootAllocator = new org.apache.arrow.memory.RootAllocator();
                org.apache.arrow.vector.VectorSchemaRoot vectorSchemaRoot = new org.apache.arrow.vector.VectorSchemaRoot(new ArrayList<>());
                vectorSchemaRoot.close();
                rootAllocator.close();
                arrowAvailableInJava = true;
            } catch (NoClassDefFoundError e) {
                arrowAvailableInJava = false;
            }
        }
        return arrowAvailableInJava;
    }

    private static Boolean arrowAvailableInR = null;

    private static synchronized boolean isArrowAvailableInR(RCallerOptions rCallerOptions) {
        if (arrowAvailableInR == null) {
            var rCallerOptionsTemp = new RCallerOptions(rCallerOptions);
            rCallerOptionsTemp.setUseArrowIfAvailable(false);
            RCaller rCaller = RCaller.create(rCallerOptionsTemp);
            RCode rCode = rCaller.getRCode();
            rCode.addRCode("available_arrow <- require(\"arrow\")");
            rCaller.runAndReturnResult("available_arrow");
            boolean[] availableArrow = rCaller.getParser().getAsLogicalArray("available_arrow");
            rCaller.deleteTempFiles();
            arrowAvailableInR = availableArrow.length == 1 && availableArrow[0];
        }
        return arrowAvailableInR;
    }

    public static ArrowBridge newInstance() {
        return newInstance(RCallerOptions.create());
    }

    public static ArrowBridge newInstance(RCallerOptions rCallerOptions) {
        if (!isArrowAvailable(rCallerOptions)) {
            StringJoiner details = new StringJoiner(", ", "(", ")");
            if (!isArrowAvailableInJava()) {
                details.add("not linked to Java");
            }
            if (!isArrowAvailableInR(rCallerOptions)) {
                details.add("not installed in R");
            }
            throw new IllegalStateException("Arrow is not available in current context " + details);
        }
        return new com.github.rcaller.io.arrow.ArrowImpl();
    }

    public abstract void loadArrowData(URI ipcResource) throws IOException;

    public abstract List<String> getNames();

    public abstract String getType(String name);

    public abstract int[] getDimensions(String name);

    public abstract String[] getAsStringArray(String name);

    public abstract double[] getAsDoubleArray(String name);

    public abstract float[] getAsFloatArray(String name);

    public abstract int[] getAsIntArray(String name);

    public abstract long[] getAsLongArray(String name);

    public abstract double[][] getAsDoubleMatrix(String name);

    //public abstract Object getUnwrappedField(String name);
}
