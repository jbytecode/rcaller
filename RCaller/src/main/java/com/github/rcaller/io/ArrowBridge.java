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

    public static synchronized boolean isArrowAvailable() {
        if (arrowAvailable == null) {
            arrowAvailable = isArrowAvailableInJava() && isArrowAvailableInR();
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

    private static synchronized boolean isArrowAvailableInR() {
        if (arrowAvailableInR == null) {
            var rCallerOptions = RCallerOptions.create();
            rCallerOptions.setUseArrowIfAvailable(false);
            RCaller rCaller = RCaller.create(rCallerOptions);
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
        if (!isArrowAvailable()) {
            StringJoiner details = new StringJoiner(", ", "(", ")");
            if (!isArrowAvailableInJava()) {
                details.add("not linked to Java");
            }
            if (!isArrowAvailableInR()) {
                details.add("not installed in R");
            }
            throw new IllegalStateException("Arrow is not available in current context " + details);
        }
        return new com.github.rcaller.io.arrow.ArrowImpl();
    }

    public abstract void loadArrowData(URI ipcResource) throws IOException;

    public abstract List<String> getNames();

    public abstract int[] getDimensions(String name);

    public abstract double[] getAsDoubleArray(String name);

    public abstract double[][] getAsDoubleMatrix(String name);

    //public abstract Object getUnwrappedField(String name);
}
