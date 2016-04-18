package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import static org.junit.Assert.*;
import org.junit.Test;

public class LargeDataTest {

    @Test
    public void testBigData_1000_10() {
        double delta = 1 / 100000;
        double[][] data = new double[1000][10];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = Math.random();
            }
        }
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addDoubleMatrix("x", data);
        code.addRCode("s <- dim(t(x) %*% x)");
        caller.setRCode(code);

        caller.runAndReturnResult("s");

        double[] result = caller.getParser().getAsDoubleArray("s");

        assertEquals(result[0], 10.0, delta);
        assertEquals(result[1], 10.0, delta);
        caller.deleteTempFiles();
    }

    @Test
    public void testReturnsBigData() {
        double delta = 0.5;
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode("s <- rnorm(1024 * 2)");
        code.addRCode("m <- mean(s)");
        code.addRCode("result <- list(arr=s, mean=m)");
        caller.setRCode(code);
        caller.runAndReturnResult("result");

        double m = caller.getParser().getAsDoubleArray("mean")[0];
        int len = caller.getParser().getAsDoubleArray("arr").length;

        assertEquals(0.0, m, delta);
        assertEquals(2048l,(long) len);
        caller.deleteTempFiles();
    }
}
