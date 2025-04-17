
package com.github.rcaller.scriptengine;

import com.github.rcaller.datatypes.DataFrame;
import com.github.rcaller.util.Globals;
import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScriptEngineBasicsTest {

    static RCallerScriptEngine engine = null;
    double delta = 1 / 100000;

    public static void message(String text) {
        System.out.println("* " + text);
    }

    @BeforeClass
    public static void init() {
        if (engine == null) {
            message("Init...");
            Globals.detect_current_rscript();
            engine = new RCallerScriptEngine();
        }
    }

    @Test
    public void ScriptEngineManagerTest() {
        //this test will always fail to produce a RCallerScriptEngine
        //because the file in META-INF/services will be loaded when
        //the library is packaged in a jar
        message("Creating an engine using ScriptEngineManager");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine en = manager.getEngineByName("RCaller");
        assertNotNull(en);

        Bindings bindings = en.getBindings(0);
        assertNotNull(bindings);

        ScriptContext context = en.getContext();
        assertNotNull(bindings);
    }

    @Test
    public void sendCommandBasicTest() throws ScriptException {
        message("sendCommandBasic...");
        engine.eval("a <- 5");
        engine.eval("b <- 3");
        engine.eval("d <- a+b");
        double[] result = (double[]) engine.get("d");
        assertEquals(1, result.length);
        assertEquals(8.0, result[0], delta);
    }

    @Test
    public void sendReceiveMatrixTest() throws ScriptException {
        message("sendReceive 3x3 Matrix...");
        engine.eval("m <- matrix(1:9, nrow = 3, ncol = 3)");
        double[][] result = (double[][]) engine.get("m");
        assertEquals(1, result[0][0], delta);
        assertEquals(2, result[1][0], delta);
        assertEquals(3, result[2][0], delta);
        assertEquals(4, result[0][1], delta);
        assertEquals(5, result[1][1], delta);
        assertEquals(6, result[2][1], delta);
        assertEquals(7, result[0][2], delta);
        assertEquals(8, result[1][2], delta);
        assertEquals(9, result[2][2], delta);
    }

    @Test
    public void longTimeProcessTest() throws ScriptException {
        message("LongTimeProcess...");
        engine.eval("a <- 1:100");
        engine.eval("s <- sum(a)");
        engine.eval("Sys.sleep(1)");
        double[] result = (double[]) engine.get("s");
        assertEquals((100.0 * 101.0) / 2, result[0], delta);
    }

    @Test
    public void longVectorResultTest() throws ScriptException {
        message("Long vector(size of 10000)...");
        engine.eval("a <- 1:10000");
        double[] result = (double[]) engine.get("a");
        assertEquals(10000, result.length);
        assertEquals(10000.0, result[result.length - 1], delta);
    }

    @Test
    public void PutDoubleArrayTest() throws ScriptException {
        message("Passing and retrieving double array to R...");
        double[] a = new double[]{19.0, 17.0, 23.0};
        engine.put("a", a);
        engine.eval("a <- sort(a)");
        double[] result = (double[]) engine.get("a");
        assertEquals(result[0], 17.0, delta);
        assertEquals(result[1], 19.0, delta);
        assertEquals(result[2], 23.0, delta);
    }

    @Test
    public void PutIntArrayTest() throws ScriptException {
        message("Pass & Retrieve integer array...");
        int[] a = new int[]{19, 17, 23};
        engine.put("a", a);
        engine.eval("a <- sort(a)");
        double[] result = (double[]) engine.get("a");
        assertEquals(result[0], 17.0, delta);
        assertEquals(result[1], 19.0, delta);
        assertEquals(result[2], 23.0, delta);
    }

    @Test
    public void PutLongArrayTest() throws ScriptException {
        message("Pass & Retrieve long array...");
        long[] a = new long[]{19L, 17L, 23L};
        engine.put("a", a);
        engine.eval("a <- sort(a)");
        double[] result = (double[]) engine.get("a");
        assertEquals(result[0], 17.0, delta);
        assertEquals(result[1], 19.0, delta);
        assertEquals(result[2], 23.0, delta);
    }

    @Test
    public void PutShortArrayTest() throws ScriptException {
        message("Pass & Retrieve short array...");
        short[] a = new short[]{19, 17, 23};
        engine.put("a", a);
        engine.eval("a <- sort(a)");
        double[] result = (double[]) engine.get("a");
        assertEquals(result[0], 17.0, delta);
        assertEquals(result[1], 19.0, delta);
        assertEquals(result[2], 23.0, delta);
    }

    @Test
    public void PutStringArrayTest() throws ScriptException {
        message("Pass & Retrieve String array...");
        String[] a = new String[]{"19", "17", "23"};
        engine.put("a", a);
        String[] result = (String[]) engine.get("a");
        assertEquals(result[0], "19");
        assertEquals(result[1], "17");
        assertEquals(result[2], "23");
    }

    @Test
    public void PutDoubleMatrix() throws ScriptException {
        message("Send and retrieve double matrix...");
        double[][] mat = new double[][]{{1, 2, 3}, {4, 5, 6}};
        engine.put("a", mat);
        double[][] result = (double[][]) engine.get("a");
        assertEquals(2, result.length);
        assertEquals(3, result[0].length);
        assertEquals(1.0, mat[0][0], delta);
        assertEquals(2.0, mat[0][1], delta);
        assertEquals(3.0, mat[0][2], delta);
        assertEquals(4.0, mat[1][0], delta);
        assertEquals(5.0, mat[1][1], delta);
        assertEquals(6.0, mat[1][2], delta);
    }

    @Test
    public void setRefClassTest() throws ScriptException, NoSuchMethodException {
        message("setRefClass test...");
        engine.eval("Person <- setRefClass(\"Person\", \n"
                + "                      fields = c(name=\"character\", surname=\"character\", age=\"numeric\"),\n"
                + "                      methods = list(\n"
                + "                        initialize = function(name, surname, age){\n"
                + "                          .self$name = name\n"
                + "                          .self$surname = surname\n"
                + "                          .self$age = age\n"
                + "                        },\n"
                + "                        \n"
                + "                        toString = function(){\n"
                + "                          cat(.self$name, \" \",.self$surname, \" \", .self$age)\n"
                + "                        },\n"
                + "                        \n"
                + "                        getName = function(){\n"
                + "                          return(.self$name)\n"
                + "                        },\n"
                + "                        \n"
                + "                        setName = function(name){\n"
                + "                          .self$name = name\n"
                + "                        },\n"
                + "                        \n"
                + "                        getSurname = function(){\n"
                + "                          return(.self$surname)\n"
                + "                        },\n"
                + "                        \n"
                + "                        setSurname = function(surname){\n"
                + "                          .self$surname = surname\n"
                + "                        },\n"
                + "                        \n"
                + "                        getAge = function(){\n"
                + "                          return(.self$age)\n"
                + "                        },\n"
                + "                        \n"
                + "                        setAge = function(age){\n"
                + "                          .self$age = age\n"
                + "                        }\n"
                + "                    )\n)");

        engine.eval("p <- Person$new('NAME','SURNAME',100)");
        assertEquals("NAME", ((String[]) engine.get("p$name"))[0]);
        assertEquals("SURNAME", ((String[]) engine.get("p$surname"))[0]);
        assertEquals(100.0, ((double[]) engine.get("p$age"))[0], delta);

        engine.eval("temp <- p$getName()");
        assertEquals("NAME", ((String[]) engine.get("temp"))[0]);
        engine.eval("temp <- p$getSurname()");
        assertEquals("SURNAME", ((String[]) engine.get("temp"))[0]);
        engine.eval("temp <- p$getAge()");
        assertEquals(100.0, ((double[]) engine.get("temp"))[0], delta);
    }

    @Test
    public void GetSetDataFrameTest() throws ScriptException, NoSuchMethodException {
        message("get/set data.frame ...");
        String[] names = new String[]{"x", "y"};
        Object[][] data = new Object[][]{{1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 2.0, 3.0, 4.0, 5.0}};
        DataFrame dataFrame = DataFrame.create(data, names);
        engine.put("mydf", dataFrame);

        double[] x = (double[]) engine.get("mydf$x");
        for (int i = 0; i < data[0].length; i++) {
            assertEquals((double) data[0][i], x[i], delta);
        }

        double[] y = (double[]) engine.get("mydf$y");
        for (int i = 0; i < data[0].length; i++) {
            assertEquals((double) data[1][i], y[i], delta);
        }
    }

}
