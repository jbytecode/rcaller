package com.github.rcaller.io;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.ArrayList;
import java.util.StringJoiner;

public abstract class ArrowBridge {

    public static boolean isArrowAvailable() {
        return isArrowAvailableInJava() && isArrowAvailableInR();
    }

    private static boolean isArrowAvailableInJava() {
        try {
            org.apache.arrow.memory.RootAllocator rootAllocator = new org.apache.arrow.memory.RootAllocator();
            org.apache.arrow.vector.VectorSchemaRoot vectorSchemaRoot = new org.apache.arrow.vector.VectorSchemaRoot(new ArrayList<>());
            vectorSchemaRoot.close();
            rootAllocator.close();
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    private static boolean isArrowAvailableInR() {
        RCaller rCaller = RCaller.create();
        RCode rCode = rCaller.getRCode();
        rCode.addRCode("available_arrow <- require(\"arrow\")");
        rCaller.runAndReturnResult("available_arrow");
        boolean[] availableArrow = rCaller.getParser().getAsLogicalArray("available_arrow");
        rCaller.deleteTempFiles();
        return availableArrow.length == 1 && availableArrow[0];
        //
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

    public abstract void invokeArrow();

}
