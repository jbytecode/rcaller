package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.graphics.BlackTheme;

import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class GraphicsWithThemes {

  public static void main(String[] args) {
    new GraphicsWithThemes();
  }

  public GraphicsWithThemes() {
    try {

      RCaller caller = RCaller.create();

      /**
       * After version 2.0.6
       * Graphics themes change the theme for plots.
       * Themes:
       * BlackTheme
       * SkyTheme
       * DefaultTheme
       */
      caller.setGraphicsTheme(new BlackTheme());


      RCode code = RCode.create();


      double[] numbers = new double[]{1, 4, 3, 5, 6, 10};

      code.addDoubleArray("x", numbers);

      File file = code.startPlot();
      code.addRCode("plot.ts(x)");
      code.endPlot();

      caller.setRCode(code);
      
      caller.runOnly();
      
      /** 
       * Handling generated plot as an ImageIcon 
      */
      ImageIcon ii = code.getPlot(file);
      
      code.showPlot(file);
      
      System.out.println("success");
    } catch (Exception e) {
      Logger.getLogger(GraphicsWithThemes.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
}
