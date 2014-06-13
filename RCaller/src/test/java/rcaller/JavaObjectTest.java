/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.IOException;
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
    assertEquals("myObj <- list(i=9, f=10.0, d=3.14, b=TRUE, s=\"test\")\n", t.produceRCode(false));
  }

  @Test
  public void passJavaObjectToR() throws IllegalAccessException {
    TestClass tc = new TestClass();
    tc.i = 999;
    tc.f = 2.71828f;
    tc.d = Math.PI;
    JavaObject jo = new JavaObject("myObj", tc);


    RCaller rcaller = new RCaller();
    RCode code = new RCode();

    Globals.detect_current_rscript();
    rcaller.setRscriptExecutable(Globals.Rscript_current);
    code.clear();

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

    RCaller rcaller = new RCaller();
    RCode code = new RCode();

    Globals.detect_current_rscript();
    rcaller.setRscriptExecutable(Globals.Rscript_current);
    code.clear();

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

    RCaller rcaller = new RCaller();
    RCode code = new RCode();

    Globals.detect_current_rscript();
    rcaller.setRscriptExecutable(Globals.Rscript_current);
    code.clear();

    code.addRCode(jo.produceRCode(false));
    code.addRCode("result <- quantile(tcwa$da, 0.95)");
    rcaller.setRCode(code);
    
    rcaller.runAndReturnResult("result");
    
    double mean = rcaller.getParser().getAsDoubleArray("result")[0];
    assertEquals(mean, 10.05, delta);
    rcaller.deleteTempFiles();
  }

}/* end of test class */


/**
 * 
 * Simple Test Class 
 * Used for tests in passing simple Java objects to R as R lists
 * 
 */
class TestClass {

  public int i = 9;
  public float f = 10.0f;
  public double d = 3.14;
  public boolean b = true;
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
