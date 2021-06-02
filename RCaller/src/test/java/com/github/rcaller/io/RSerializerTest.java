package com.github.rcaller.io;

import com.github.rcaller.TempFileService;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import java.io.File;
import java.io.IOException;

import com.github.rcaller.util.Globals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RSerializerTest {

    double delta = 1 / 100000;

    @Test
    public void WriteVectorTest() throws IOException {
        RSerializer rs = new RSerializer();
        double[] v = new double[]{1.0, 2.0, 3.0};
        rs.writeVector(v);

        TempFileService tmpServ = new TempFileService();
        File tmpFile = tmpServ.createTempFile("RCaller", "Test");

        rs.save(tmpFile.getCanonicalPath());

        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode("conn <- file(\"" + Globals.getSystemSpecificRPathParameter(tmpFile) + "\" , \"r\")");
        code.addRCode("x <- unserialize(conn)");
        caller.setRCode(code);

        caller.runAndReturnResult("x");

        double[] x = caller.getParser().getAsDoubleArray("x");
        assertEquals(1.0, x[0], delta);
        assertEquals(2.0, x[1], delta);
        assertEquals(3.0, x[2], delta);
    }

    @Test
    public void WriteMatrixTest() throws IOException {
        RSerializer rs = new RSerializer();
        double[][] v = new double[][]{
            {1.0, 2.0},
            {3.0, 4.0},
            {5.0, 6.0}
        };

        rs.writeDoubleMatrix(v);

        TempFileService tmpServ = new TempFileService();
        File tmpFile = tmpServ.createTempFile("RCaller", "Test");

        rs.save(tmpFile.getCanonicalPath());

        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode("conn <- file(\"" + Globals.getSystemSpecificRPathParameter(tmpFile) + "\" , \"r\")");
        code.addRCode("x <- unserialize(conn)");
        caller.setRCode(code);

        caller.runAndReturnResult("x");

        double[][] x = caller.getParser().getAsDoubleMatrix("x");
        assertEquals(1.0, x[0][0], delta);
        assertEquals(2.0, x[1][0], delta);
        assertEquals(3.0, x[2][0], delta);
        assertEquals(4.0, x[0][1], delta);
        assertEquals(5.0, x[1][1], delta);
        assertEquals(6.0, x[2][1], delta);

    }

}
