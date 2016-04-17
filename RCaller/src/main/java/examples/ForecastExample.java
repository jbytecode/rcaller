package examples;

import com.github.rcaller.util.Globals;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForecastExample {

  public static void main(String[] args) {
    new ForecastExample();
  }

  public ForecastExample() {
    Random random = new Random(12345);
    double[] stockClosePrices = new double[100];
    stockClosePrices[0] = 0;
    for (int i = 1; i < stockClosePrices.length; i++) {
      stockClosePrices[i] = 0.5 + 1 * stockClosePrices[i - 1] + random.nextGaussian();
    }
    RunRScript(stockClosePrices);
  }

  public void RunRScript(double[] stockClosePrices) {
    try {

      RCaller caller = RCaller.create();

      RCode code = RCode.create();

      code.addDoubleArray("x", stockClosePrices);
      code.R_require("forecast");
      code.addRCode("ww<-auto.arima(x)");
      code.addRCode("tt<-forecast(ww,h=20)");

      code.addRCode("myResult <- list(upper=tt$upper, lower=tt$lower, fitted = as.double(tt$fitted))");

      caller.setRCode(code);
      caller.runAndReturnResult("myResult");

      /**
       * It is good to have a look at the XML file
       * for having info about which variables are passed to result
       */
      System.out.println(caller.getParser().getXMLFileAsString());

      double[] upValues = caller.getParser().getAsDoubleArray("upper");
      double[] loValues = caller.getParser().getAsDoubleArray("lower");
      double[] fitted = caller.getParser().getAsDoubleArray("fitted");

      for (int i = 0; i < upValues.length; i++) {
        System.out.println(i + ": upValues: " + upValues[i] + ", fitted: " + fitted[i] + ", loValues: " + loValues[i]);
      }

    } catch (Exception e) {
      Logger.getLogger(Example7.class.getName()).log(Level.SEVERE, e.getMessage());
    }

  }
}
