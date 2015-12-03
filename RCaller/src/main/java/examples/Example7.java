package examples;

import org.expr.rcaller.Globals;
import org.expr.rcaller.RCaller;
import org.expr.rcaller.RCode;
import org.expr.rcaller.graphics.BlackTheme;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hako
 */
public class Example7 {

  public static void main(String[] args) {
    new Example7();
  }

  public Example7() {
    try {

      RCaller caller = new RCaller();
      /**
       * This must be caller.setRScriptExecutable() instead.
       */
      Globals.detect_current_rscript();
      caller.setRscriptExecutable(Globals.Rscript_current);

      /**
       * After version 2.0.6
       * Graphics themes change the theme for plots.
       * Themes:
       * BlackTheme
       * SkyTheme
       * DefaultTheme
       */
      caller.setGraphicsTheme(new BlackTheme());

      /**
       * After version 2.0.6
       * We build the code using RCode class. Older 
       * methods are deprecated and will no longer will be
       * available in RCaller 3.0
       */
      RCode code = new RCode();
      code.clear();


      double[] numbers = new double[]{1, 4, 3, 5, 6, 10};

      code.addDoubleArray("x", numbers);

      File file = code.startPlot();
      code.addRCode("plot.ts(x)");
      code.endPlot();

      /**
       * After version 2.0.6
       * We set the code.
       */
      caller.setRCode(code);
      
      caller.runOnly();
      
      ImageIcon ii = code.getPlot(file);
      code.showPlot(file);
      
      System.out.println("success");
    } catch (Exception e) {
      Logger.getLogger(Example7.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
}
