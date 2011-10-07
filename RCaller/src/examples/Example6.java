/*
 * Test for Windows Systems
 */
package examples;

import graphics.BlackTheme;
import java.io.File;
import rcaller.Globals;
import rcaller.RCaller;
import rcaller.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 * 
 */
public class Example6 {

  public Example6() {
    try {
      RCaller caller = new RCaller();
      Globals.detect_current_rscript();
      caller.setRscriptExecutable(Globals.Rscript_current);
      RCode code = new RCode();
      code.clear();
      code.addRCode("x<-rnorm(10)");
      code.addRCode("y<-rnorm(10)");
      code.addRCode("ols<-lm(y~x)");

      caller.setGraphicsTheme(new BlackTheme());
      
      File plt = code.startPlot();
      code.addRCode("plot(ols$residuals, ols$fitted.values)");
      code.addRCode("abline(ols$coefficients)");
      code.endPlot();

      caller.setRCode(code);
      caller.runAndReturnResult("ols");

      code.showPlot(plt);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public static void main(String[] args) {
    new Example6();
  }
}
