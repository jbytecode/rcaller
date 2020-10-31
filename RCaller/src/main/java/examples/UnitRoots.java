/*
 * This example simply demonstrates calling S4 methods 
 * using RCaller.
 */
package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finding unit roots using a package 
 * which is not pre-installed.
 */
public class UnitRoots {

  public static void main(String[] args) {
    new UnitRoots();
  }

  public UnitRoots() {
    try {
      /**
       * Creating a Random object
       */
      Random random = new Random(12345);

      /**
       * Generating artificial data from
       * y[t] = 0.5 + 1* y[t-1] + epsilon
       * where epsilon ~ N(0,1)
       */
      double[] stockClosePrices = new double[100];
      stockClosePrices[0] = 0;
      for (int i = 1; i < stockClosePrices.length; i++) {
        stockClosePrices[i] = 0.5 + 1 * stockClosePrices[0] + random.nextGaussian();
      }
      RCaller caller = RCaller.create();

      RCode code = RCode.create();

      code.addDoubleArray("x", stockClosePrices);

      code.R_require("fUnitRoots");

      code.addRCode("ww <- diff(x, differences = 1)");

      code.addRCode("tt <- unitrootTest(ww, lags=0)");

      caller.setRCode(code);
      caller.runAndReturnResult("tt@test");

      double[] res = caller.getParser().getAsDoubleArray("statistic");
      System.out.println(res[0]);
      System.out.println("success");
    } catch (Exception e) {
      Logger.getLogger(UnitRoots.class.getName()).log(Level.SEVERE, e.getMessage());
    }

  }
}
