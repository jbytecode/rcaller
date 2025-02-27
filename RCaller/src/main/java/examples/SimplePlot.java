package examples;

import com.github.rcaller.util.Globals;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class SimplePlot {

  public static void main(String[] args) {
    new SimplePlot();
  }

  /**
   * Test for simple plots.
   * This class simply plots a time series array using plot.ts()
   * function of R.
   */
  public SimplePlot() {
    try {
      RCaller caller = RCaller.create();

      /**
       * One of the themes.
       */

      RCode code = RCode.create();


      double[] numbers = new double[]{1, 4, 3, 5, 6, 10};

      code.addDoubleArray("x", numbers);
      File file = code.startPlot();
      System.out.println("Plot will be saved to : " + file);
      code.addRCode("plot(x, pch=19)");
      code.endPlot();
      
      
      caller.setRCode(code);
      System.out.println(code.getCode().toString());
      
      caller.runOnly();
      code.showPlot(file);
    } catch (Exception e) {
      Logger.getLogger(SimplePlot.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
}
