/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

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
    assertEquals("myObj <- list(i=9, f=10.0, d=3.14, b=TRUE, s=\"test\")\n", t.produceRCode());
  }

  @Test
  public void passJavaObjectToR() throws IllegalAccessException{
    TestClass tc = new TestClass();
    tc.i = 999;
    tc.f = 2.71828f;
    tc.d = Math.PI;
    JavaObject jo = new JavaObject("myObj", tc);
    
    double delta = 0.0000001;
    RCaller rcaller = new RCaller();
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    
    rcaller.addRCode(jo.produceRCode());
    
    rcaller.addRCode("myObj$i <- myObj$i + 1");
    
    rcaller.runAndReturnResult("myObj");
    
    int[] result = rcaller.getParser().getAsIntArray("i");
    
    assertEquals(1000, result[0]);
  }
}

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
