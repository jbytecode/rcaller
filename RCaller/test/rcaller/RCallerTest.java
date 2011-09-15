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
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    rcaller.addIntArray("x_i", new int[]{1, 2, 3, 4, 5, 6});
    rcaller.addRCode("x_i <- x_i * 2");
    rcaller.runAndReturnResult("x_i");
    int[] actual = rcaller.getParser().getAsIntArray("x_i");
    int[] expected = new int[]{2, 4, 6, 8, 10, 12};
    assertEquals(expected.length, actual.length);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i]);
    }
  }

  @Test
  public void testDoubleArrays() {
    double delta = 0.0000001;
    RCaller rcaller = new RCaller();
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    rcaller.addDoubleArray("x_d", new double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6});
    rcaller.addRCode("x_d <- x_d * 2");
    rcaller.runAndReturnResult("x_d");
    double[] actual = rcaller.getParser().getAsDoubleArray("x_d");
    double[] expected = new double[]{2.2, 4.4, 6.6, 8.8, 11, 13.2};
    assertEquals(expected.length, actual.length, delta);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i], delta);
    }
  }
  
  @Test
  public void testFloatArrays() {
    double delta = 0.0000001;
    RCaller rcaller = new RCaller();
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    rcaller.addFloatArray("x_f", new float[]{1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f});
    rcaller.addRCode("x_f <- x_f * 2");
    rcaller.runAndReturnResult("x_f");
    float[] actual = rcaller.getParser().getAsFloatArray("x_f");
    float[] expected = new float[]{2.2f, 4.4f, 6.6f, 8.8f, 11.0f, 13.2f};
    assertEquals(expected.length, actual.length, delta);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i], delta);
    }
  }

  @Test
  public void testStringArrays() {
    RCaller rcaller = new RCaller();
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    rcaller.addStringArray("x_s1", new String[]{"a", "b", "c", "d", "e", "f", "g"});
    rcaller.addStringArray("x_s2", new String[]{"g", "f", "z", "q", "W", "Z", "%"});
    rcaller.addRCode("result <- intersect(x_s1, x_s2)");
    rcaller.runAndReturnResult("result");
    String[] actual = rcaller.getParser().getAsStringArray("result");
    String[] expected = new String[]{"f", "g"};
    assertEquals(expected.length, actual.length);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i]);
    }
  }
  
  @Test
  public void testLists(){
    RCaller rcaller = new RCaller();
    rcaller.setRscriptExecutable("/usr/bin/Rscript");
    rcaller.cleanRCode();
    rcaller.addRCode("alist <- list(x=c(1,2,3), y=c('a','b','c'))");
    rcaller.runAndReturnResult("alist");
    
    String[] actual = rcaller.getParser().getAsStringArray("y");
    String[] expected = new String[]{"a", "b","c"};
    assertEquals(expected.length, actual.length);
    for (int i = 0; i < actual.length; i++) {
      assertEquals(actual[i], expected[i]);
    }
    
    int[] actual_i = rcaller.getParser().getAsIntArray("x");
    int[] expected_i = new int[]{1,2,3};
    assertEquals(expected_i.length, actual_i.length);
    for (int i = 0; i < actual_i.length; i++) {
      assertEquals(actual_i[i], expected_i[i]);
    }
    
  }
}
