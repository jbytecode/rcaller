package rcaller;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatrixTest {

  double delta = 0.0001;
  
  @Test
  public void simpleMatrixTest() {

    RCaller caller = new RCaller();
    Globals.detect_current_rscript();
    caller.setRscriptExecutable(Globals.Rscript_current);

    RCode code = new RCode();

    double[][] matrix = new double[][]{{6, 4}, {9, 8}};

    code.addDoubleMatrix("x", matrix);
    code.addRCode("s<-solve(x)");

    caller.setRCode(code);

    caller.runAndReturnResult("s");

    double[][] inverse = caller.getParser().getAsDoubleMatrix("s", matrix.length, matrix[0].length);

    double[][] expected = new double[][]{{0.6666667, -0.7500000}, {-0.3333333, 0.5000000}};

    assertEquals(expected.length, inverse.length);
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j], inverse[i][j], delta);
      }
    }
    
    caller.deleteTempFiles();
  }
}
