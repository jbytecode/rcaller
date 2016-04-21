/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaObjectTest {

    double delta = 0.0000001;

    public JavaObjectTest() {
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
    public void simpleJavaObjectTest() throws IllegalAccessException {
        TestClass tc = new TestClass();
        JavaObject t = new JavaObject("myObj", tc);
        assertEquals("myObj <- list(i=9, f=10.0, d=3.14, b=TRUE, l=567, s=\"test\")\n", t.produceRCode(false));
    }

    @Test
    public void passJavaObjectToR() throws IllegalAccessException {
        TestClass tc = new TestClass();
        tc.i = 999;
        tc.f = 2.71828f;
        tc.d = Math.PI;
        JavaObject jo = new JavaObject("myObj", tc);

        RCaller rcaller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode(jo.produceRCode(false));

        code.addRCode("myObj$i <- myObj$i + 1");

        rcaller.setRCode(code);
        rcaller.runAndReturnResult("myObj");

        int[] result = rcaller.getParser().getAsIntArray("i");

        assertEquals(1000, result[0]);
        rcaller.deleteTempFiles();
    }

    @Test
    public void TestClassWithArrays() throws IllegalAccessException, IOException {
        TestClassWithArrays tcwa = new TestClassWithArrays();
        JavaObject jo = new JavaObject("tcwa", tcwa);

        RCaller rcaller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode(jo.produceRCode(false));

        rcaller.setRCode(code);
        rcaller.runAndReturnResult("tcwa");

        int[] expectedIntArray = rcaller.getParser().getAsIntArray("ia");
        for (int i = 0; i < tcwa.ia.length; i++) {
            assertEquals(expectedIntArray[i], tcwa.ia[i]);
        }

        double[] expectedDoubleArray = rcaller.getParser().getAsDoubleArray("da");
        for (int i = 0; i < tcwa.da.length; i++) {
            assertEquals(expectedDoubleArray[i], tcwa.da[i], delta);
        }

        String[] expectedStringArray = rcaller.getParser().getAsStringArray("sa");
        for (int i = 0; i < tcwa.sa.length; i++) {
            assertEquals(expectedStringArray[i], tcwa.sa[i]);
        }

        rcaller.deleteTempFiles();
    }

    @Test
    public void TestClassWithArraysCalculations() throws IllegalAccessException, IOException {
        TestClassWithArrays tcwa = new TestClassWithArrays();
        JavaObject jo = new JavaObject("tcwa", tcwa);

        RCaller rcaller = RCaller.create();
        RCode code = RCode.create();

        code.addRCode(jo.produceRCode(false));
        code.addRCode("result <- quantile(tcwa$da, 0.95)");
        rcaller.setRCode(code);

        rcaller.runAndReturnResult("result");

        double mean = rcaller.getParser().getAsDoubleArray("result")[0];
        assertEquals(mean, 10.05, delta);
        rcaller.deleteTempFiles();
    }

    @Test
    public void ConvertJavaPlainObjectToRCode() throws IllegalAccessException {
        TestClass tc = new TestClass();
        tc.b = true;
        tc.d = 6.789;
        tc.f = 4.5f;
        tc.i = 3;
        tc.l = 7L;
        tc.s = "s1";
        String code = JavaObject.ConvertToRCode("Test", tc, true, true);
        assertEquals("Test = list(i=3, f=4.5, d=6.789, b=TRUE, l=7, s=\"s1\")\n", code);
    }

    @Test
    public void ConvertJavaPlainObjectToRCodeWithArrays() throws IllegalAccessException {
        TestClassWithArrays tc = new TestClassWithArrays();
        tc.b = true;
        tc.ba = new boolean[]{true,false,true,true};
        tc.d = 3.4;
        tc.da = new double[]{1.0,10.0,100.0};
        tc.f = 5.6f;
        tc.i = 9;
        tc.ia = new int[]{1,2,3,4,5};
        tc.l = 999L;
        tc.s = "H...";
        tc.sa = new String[]{"One", "Two", "Three"};
        String code = JavaObject.ConvertToRCode("Test", tc, true, true);
        assertEquals(
                "Test = list(ia=c(1, 2, 3, 4, 5), da=c(1.0, 10.0, 100.0), sa=c(\"One\", \"Two\", \"Three\"), ba=c(TRUE, FALSE, TRUE, TRUE), i=9, f=5.6, d=3.4, b=TRUE, l=999, s=\"H...\")\n", 
                code);
    }

}/* end of test class */


/**
 *
 * Simple Test Class Used for tests in passing simple Java objects to R as R
 * lists
 *
 */
class TestClass {

    public int i = 9;
    public float f = 10.0f;
    public double d = 3.14;
    public boolean b = true;
    public long l = 567;
    public String s = "test";
}

class TestClassWithArrays extends TestClass {

    public int[] ia = new int[]{1, 2, 3, 4, 5};
    public double[] da = new double[]{1.0, 2.0, 3.0, 4.0, 9.9, 10.1};
    public String[] sa = new String[]{"One", "Two", "Three"};
    public boolean[] ba = new boolean[]{true, true, false};
}

class OtherClass {

    public String s = "This is a string in OtherClass";
}

class TestClassWithObject {

    public JavaObject InnerObject = new JavaObject("anObject", new OtherClass());
    public int[] ia = new int[]{1, 2, 3, 4, 5};
    public int i = 90;
}
