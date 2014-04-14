/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010,2011  Mehmet Hakan Satman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code projec: http://code.google.com/p/rcaller/
 *
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
 */
public class SimplePlot {

  public static void main(String[] args) {
    new SimplePlot();
  }

  /*
   * Test for simple plots.
   * This class simply plots a time series array using plot.ts()
   * function of R.
   */
  public SimplePlot() {
    try {
      RCaller caller = new RCaller();
      Globals.detect_current_rscript();
      caller.setRscriptExecutable(Globals.Rscript_current);
      
      /*
       * One of the themes.
       */
      //caller.setGraphicsTheme(new BlackTheme());
      
      RCode code = new RCode();
      code.clear();
      

      double[] numbers = new double[]{1, 4, 3, 5, 6, 10};

      code.addDoubleArray("x", numbers);
      File file = code.startPlot();
      System.out.println("Plot will be saved to : " + file);
      code.addRCode("plot(x, pch=19)");
      code.endPlot();
      
      
      caller.setRCode(code);
      System.out.println(code.getCode().toString());
      
//      caller.addEventHandler(new EventHandler() {
//
//        public void messageReceived(String threadName, String msg) {
//          System.out.println(threadName + ": "+ msg);
//        }
//      });
      caller.runOnly();
      code.showPlot(file);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
