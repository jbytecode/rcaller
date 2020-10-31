package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RegressionWithRandomData {
  
  public static void main(String[] args) {
    new RegressionWithRandomData();
  }
  
  public RegressionWithRandomData() {
    try {
      /**
       * Creating an instance of RCaller
       */
      RCaller caller = RCaller.create();
      RCode code = RCode.create();

      /**
       * Generating random model with X ~ Normal(0,1),
       * e ~ Normal(0,1) and y = 5.0 + 5.0x + e
       */
      code.addRCode("set.seed(123)");
      code.addRCode("x <- rnorm(10)");
      code.addRCode("e <- rnorm(10)");
      code.addRCode("y <- 5.0 + 5.0 * x + e");

      /**
       * Regression using simulated data
       */
      code.addRCode("ols <- lm(y~x)");

      /**
       * We want to handle the object 'ols'
       */
      caller.setRCode(code);
      caller.runAndReturnResult("ols");

      /**
       * Getting R results as XML
       * for debugging issues.
       */
      System.out.println(caller.getParser().getXMLFileAsString());
    } catch (Exception e) {
      Logger.getLogger(RegressionWithRandomData.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
}
