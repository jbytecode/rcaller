/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2016  Mehmet Hakan Satman

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package com.github.rcaller;

import com.github.rcaller.scriptengine.RCallerScriptEngine;
import static com.github.rcaller.scriptengine.NamedArgument.*;
import com.github.rcaller.util.Globals;
import javax.script.ScriptException;
import javax.script.Invocable;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

public class RCallerScriptEngineTest {

    static RCallerScriptEngine engine = null;
    double delta = 1 / 100000;

    public static void message(String text) {
        System.out.println("* " + text);
    }

    @Before
    @Test
    public void init() {
        if (engine == null) {
            message("Init...");
            Globals.detect_current_rscript();
            engine = new RCallerScriptEngine();
        }
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
        assertEquals(2, result[0][1], delta);
        assertEquals(3, result[0][2], delta);
        assertEquals(4, result[1][0], delta);
        assertEquals(5, result[1][1], delta);
        assertEquals(6, result[1][2], delta);
        assertEquals(7, result[2][0], delta);
        assertEquals(8, result[2][1], delta);
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
    public void PutDobleArrayTest() throws ScriptException {
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
        message("Pass & Retreive integer array...");
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
        message("Pass & Retreive long array...");
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
        message("Pass & Retreive short array...");
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
        message("Pass & Retreive String array...");
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
    public void InvokeRunifTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'runif' ...");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("runif",
                Named("n", 5),
                Named("min", 0),
                Named("max", 100)
        );
        double[] dresult = (double[]) result;
        assertEquals(5, dresult.length);
        assertTrue(dresult[0] > 0 && dresult[0] < 100);
        assertTrue(dresult[1] > 0 && dresult[0] < 100);
        assertTrue(dresult[2] > 0 && dresult[0] < 100);
        assertTrue(dresult[3] > 0 && dresult[0] < 100);
        assertTrue(dresult[4] > 0 && dresult[0] < 100);
    }

    @Test
    public void InvokeSqrtTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'sqrt' ...");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("sqrt", Named("", 25.0));
        double[] dresult = (double[]) result;
        assertEquals(5.0, dresult[0], delta);
    }

    @Test
    public void InvokeRNormWithoutNamesTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'rnorm' without argument names");
        Invocable invocable = (Invocable) engine;
        Object result
                = invocable.invokeFunction(
                        "rnorm", // function name
                        Named("", 100), // for n
                        Named("", 0), // for mean
                        Named("", 2));  // for standard deviation
        double[] dresult = (double[]) result;
        assertEquals(100, dresult.length);
        assertTrue(dresult[0] < 0 + 2 * 5 && dresult[0] > 0 - 2 * 5);
        assertTrue(dresult[1] < 0 + 2 * 5 && dresult[1] > 0 - 2 * 5);
    }

    @Test
    public void InvokePassDoubleArrayToRFunctionTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'mean' on a Java double[] without argument names");
        Invocable invocable = (Invocable) engine;
        double[] x = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        Object result
                = invocable.invokeFunction(
                        "mean", // function name
                        Named("", x)
                );
        double[] dresult = (double[]) result;
        assertEquals(1, dresult.length);
        assertEquals(5.0, dresult[0], delta);
    }

    @Test
    public void InvokeUserDefinedFunctionOnAVectorTest() throws ScriptException, NoSuchMethodException {
        message("Invoke user defined R function on a Java double[] array");
        Invocable invocable = (Invocable) engine;
        double[] x = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        engine.put("x", x);
        engine.eval("f <- function(a){return(a^2)}");
        Object result = invocable.invokeFunction("f", Named("a", x));
        double[] dresult = (double[]) result;
        assertEquals(9, dresult.length);
        assertEquals(1.0, dresult[0], delta);
        assertEquals(4.0, dresult[1], delta);
        assertEquals(9.0, dresult[2], delta);
        assertEquals(16.0, dresult[3], delta);
        assertEquals(25.0, dresult[4], delta);
        assertEquals(36.0, dresult[5], delta);
        assertEquals(49.0, dresult[6], delta);
        assertEquals(64.0, dresult[7], delta);
        assertEquals(81.0, dresult[8], delta);
    }

}
