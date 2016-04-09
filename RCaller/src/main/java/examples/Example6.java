/*
 * Test for Windows Systems
 */
package examples;

import com.github.rcaller.util.Globals;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.graphics.SkyTheme;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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
      code.addRCode("x<-rnorm(30)");
      code.addRCode("y<-rnorm(30)");
      code.addRCode("ols<-lm(y~x)");

      caller.setGraphicsTheme(new SkyTheme());
      
      File plt = code.startPlot();
      code.addRCode("barplot(x,y)");
      code.addRCode("abline(ols$coefficients[1], ols$coefficients[2])");
      code.addRCode("abline(mean(y),0)");
      code.addRCode("abline(v=mean(x))");
      code.endPlot();

      caller.setRCode(code);
      caller.runAndReturnResult("ols");

      code.showPlot(plt);
    } catch (Exception e) {
      Logger.getLogger(Example6.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }

  public static void main(String[] args) {
    new Example6();
  }
}
