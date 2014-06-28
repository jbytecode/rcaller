/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hako
 */
public class RCallerTest {

    public RCallerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIntArrays() {
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);
        RCode code = new RCode();

        code.clear();
        code.addIntArray("x_i", new int[]{1, 2, 3, 4, 5, 6});
        code.addRCode("x_i <- x_i * 2");
        rcaller.setRCode(code);
        rcaller.runAndReturnResult("x_i");
        int[] actual = rcaller.getParser().getAsIntArray("x_i");
        int[] expected = new int[]{2, 4, 6, 8, 10, 12};
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i]);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void testDoubleArrays() {
        double delta = 0.0000001;
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addDoubleArray("x_d", new double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6});
        code.addRCode("x_d <- x_d * 2");
        rcaller.setRCode(code);

        rcaller.runAndReturnResult("x_d");
        double[] actual = rcaller.getParser().getAsDoubleArray("x_d");
        double[] expected = new double[]{2.2, 4.4, 6.6, 8.8, 11, 13.2};
        assertEquals(expected.length, actual.length, delta);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i], delta);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void testFloatArrays() {
        double delta = 0.0000001;
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addFloatArray("x_f", new float[]{1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f});
        code.addRCode("x_f <- x_f * 2");
        rcaller.setRCode(code);

        rcaller.runAndReturnResult("x_f");
        float[] actual = rcaller.getParser().getAsFloatArray("x_f");
        float[] expected = new float[]{2.2f, 4.4f, 6.6f, 8.8f, 11.0f, 13.2f};
        assertEquals(expected.length, actual.length, delta);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i], delta);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void testStringArrays() {
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addStringArray("x_s1", new String[]{"a", "b", "c", "d", "e", "f", "g"});
        code.addStringArray("x_s2", new String[]{"g", "f", "z", "q", "W", "Z", "%"});
        code.addRCode("result <- intersect(x_s1, x_s2)");
        rcaller.setRCode(code);

        rcaller.runAndReturnResult("result");
        String[] actual = rcaller.getParser().getAsStringArray("result");
        String[] expected = new String[]{"f", "g"};
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i]);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void testLogicalArrays() {
        boolean[] boolarr = new boolean[]{true, true, false, true, true, true, false};
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addLogicalArray("b", boolarr);
        code.addRCode("result<-xor(b,b)");

        rcaller.setRCode(code);

        rcaller.runAndReturnResult("result");
        boolean[] actual = rcaller.getParser().getAsLogicalArray("result");
        boolean[] expected = new boolean[]{false, false, false, false, false, false, false};
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i]);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void testLists() {
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addRCode("alist <- list(x=c(1,2,3), y=c('a','b','c'))");

        rcaller.setRCode(code);
        rcaller.runAndReturnResult("alist");

        String[] actual = rcaller.getParser().getAsStringArray("y");
        String[] expected = new String[]{"a", "b", "c"};
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected[i]);
        }

        int[] actual_i = rcaller.getParser().getAsIntArray("x");
        int[] expected_i = new int[]{1, 2, 3};
        assertEquals(expected_i.length, actual_i.length);
        for (int i = 0; i < actual_i.length; i++) {
            assertEquals(actual_i[i], expected_i[i]);
        }
        rcaller.deleteTempFiles();
    }

    @Test
    public void TestLists2() throws Exception {
        double delta = 0.0000001;
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addRCode("x <- c(6 ,8, 3.4, 1, 2)");
        code.addRCode("med1 <- median(x)");

        code.addRCode("y <- c(16 ,18, 13.4, 11,12)");
        code.addRCode("med2 <- median(y)");

        code.addRCode("z <- c(116 ,118, 113.4,111,112)");
        code.addRCode("med3 <- median(z)");

        code.addRCode("results <- list(m1 = med1, m2 = med2, m3 = med3)");

        rcaller.setRCode(code);

        rcaller.runAndReturnResult("results");

        double[] result = rcaller.getParser().getAsDoubleArray("m1");
        assertEquals(result[0], 3.4, delta);

        result = rcaller.getParser().getAsDoubleArray("m2");
        assertEquals(result[0], 13.4, delta);

        result = rcaller.getParser().getAsDoubleArray("m3");
        assertEquals(result[0], 113.4, delta);
        rcaller.deleteTempFiles();
    }

    @Test
    public void testPlot() {
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);
        RCode code = new RCode();
        code.clear();

        File plot = null;

        try {
            plot = code.startPlot();
        } catch (Exception e) {
            fail(e.toString());
        }
        code.addRCode("hist(rnorm(1000))");
        code.endPlot();

        rcaller.setRCode(code);

        rcaller.runOnly();
        assertFalse(rcaller.getRCode().getPlot(plot) == null);
        rcaller.deleteTempFiles();
        plot.deleteOnExit();
    }

    @Test
    public void singleResultTest() {
        double delta = 0.0000001;
        RCaller rcaller = new RCaller();
        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);

        RCode code = new RCode();
        code.clear();
        code.addRCode("x <- c(6 ,8, 3.4, 1, 2)");
        code.addRCode("med <- median(x)");
        rcaller.setRCode(code);
        rcaller.runAndReturnResult("med");

        double[] result = rcaller.getParser().getAsDoubleArray("med");

        assertEquals(result[0], 3.4, delta);
        rcaller.deleteTempFiles();
    }
}
