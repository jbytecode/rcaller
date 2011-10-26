package examples;

import graphics.BlackTheme;
import java.io.File;
import javax.swing.ImageIcon;
import rcaller.RCaller;
import rcaller.RCode;

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
      /*
       * This must be caller.setRScriptExecutable() instead.
       */
      caller.setRscriptExecutable("C:/Program Files/R/R-2.13.2/bin/x64/Rscript");

      /*
       * After version 2.0.6
       * Graphics themes change the theme for plots.
       * Themes:
       * BlackTheme
       * SkyTheme
       * DefaultTheme
       */
      caller.setGraphicsTheme(new BlackTheme());

      /*
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

      /*
       * After version 2.0.6
       * We set the code.
       */
      caller.setRCode(code);
      
      caller.runOnly();
      
      ImageIcon ii = code.getPlot(file);
      code.showPlot(file);
      
      System.out.println("success");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
