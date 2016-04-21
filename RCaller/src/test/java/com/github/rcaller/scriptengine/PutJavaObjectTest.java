package com.github.rcaller.scriptengine;

import com.github.rcaller.util.Globals;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PutJavaObjectTest {

    static ScriptEngine engine = null;
    double delta = 1 / 100000;

    public static void message(String text) {
        System.out.println("* " + text);
    }

    @BeforeClass
    public static void setUpClass() {
        Globals.detect_current_rscript();
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("RCaller");
    }

    @AfterClass
    public static void tearDownClass() {
        ((RCallerScriptEngine) engine).close();
    }

    @Test
    public void passAsPlainJavaObjectToEngine() {
        TestClass1 tc1 = new TestClass1();
        engine.put("tc1", tc1);
        double[] d1 = (double[]) engine.get("d1");
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, d1, delta);
        double[] d2 = (double[]) engine.get("d2");
        assertArrayEquals(new double[]{4.0, 5.0, 6.0}, d2, delta);
    }

    @Test
    public void passRichPlainJavaObjectToEngine() {
        TestClass2 tc2 = new TestClass2();
        engine.put("tc2", tc2);
        double[] v1 = (double[]) engine.get("int_array");
        assertArrayEquals(new double[]{1, 2, 3, 4, 5}, v1, delta);
        double[] v2 = (double[]) engine.get("double_array");
        assertArrayEquals(new double[]{1.0, 2.0, 9.0}, v2, delta);
        String[] v3 = (String[]) engine.get("string_array");
        assertArrayEquals(new String[]{"A", "Z", "X", "H", "h"}, v3);
    }

}
