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
}
