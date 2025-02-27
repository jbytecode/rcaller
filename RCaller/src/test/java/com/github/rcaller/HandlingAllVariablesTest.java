

package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class HandlingAllVariablesTest {

    private final static double delta = 1.0 / 1000.0;

    @Test
    public void GetAllVariablesInEnvironmentTest() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addDouble("x", 5.65);
        code.addDouble("y", 8.96);
        code.addRCode("result <- as.list(.GlobalEnv)");

        caller.setRCode(code);

        caller.runAndReturnResult("result");

        ArrayList<String> names = caller.getParser().getNames();

        assertEquals(caller.getParser().getAsDoubleArray("x")[0], 5.65, delta);
        assertEquals(caller.getParser().getAsDoubleArray("y")[0], 8.96, delta);
    }
    
    @Test
    public void GetAllVectorsInEnvironmentTest() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addDoubleArray("x", new double[]{1,2,3,4,5});
        code.addDoubleArray("y", new double[]{2,4,6,8,10});
        code.addRCode("result <- as.list(.GlobalEnv)");

        caller.setRCode(code);

        caller.runAndReturnResult("result");

        ArrayList<String> names = caller.getParser().getNames();
        //System.out.println("Names : " + names);

        //System.out.println("x[4] is " + caller.getParser().getAsDoubleArray("x")[4]);
        //System.out.println("y[4] is " + caller.getParser().getAsDoubleArray("y")[4]);

        assertEquals(caller.getParser().getAsDoubleArray("x")[4], 5, delta);
        assertEquals(caller.getParser().getAsDoubleArray("y")[4], 10, delta);
    }
}
