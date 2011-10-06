/*
 * Test for Windows Systems
 */
package examples;

import java.io.File;
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
      caller.setRscriptExecutable("C:\\Program Files\\R\\R-2.13.0\\bin\\Rscript.exe");
      RCode code = new RCode();
      code.clear();
      code.addRCode("x<-rnorm(10)");
      code.addRCode("y<-rnorm(10)");
      code.addRCode("ols<-lm(y~x)");

      File plt = code.startPlot();
      code.addRCode("plot(ols$residuals, ols$fitted.values)");
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
