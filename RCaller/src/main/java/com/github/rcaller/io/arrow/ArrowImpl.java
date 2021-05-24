package com.github.rcaller.io.arrow;

import com.github.rcaller.io.ArrowBridge;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.ArrayList;

public class ArrowImpl extends ArrowBridge {

    @Override
    public void invokeArrow() {
        try {
            org.apache.arrow.memory.RootAllocator rootAllocator = new org.apache.arrow.memory.RootAllocator();
            org.apache.arrow.vector.FieldVector fieldVector = new org.apache.arrow.vector.IntVector("iv", rootAllocator);
            org.apache.arrow.vector.VectorSchemaRoot vectorSchemaRoot = new org.apache.arrow.vector.VectorSchemaRoot(fieldVector);
            vectorSchemaRoot.close();
            rootAllocator.close();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }

    }
}
