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

import java.io.File;
import rcaller.RCaller;
import rcaller.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Main {

  public Main() {
    try {
      RCaller r = new RCaller();
      r.setRscriptExecutable("/usr/bin/Rscript");
      RCode code = new RCode();
      double[] x = new double[]{1, 2, 3, 4, 5};
      double[] y = new double[]{5, 7, 6, 10, 20};
      code.addDoubleArray("x", x);
      code.addDoubleArray("y", y);
      code.addRCode("ols<-lm(y~x)");
      code.addRCode("a<-ols$residuals");

      double[] tt = new double[100];
      for (int i = 0; i < tt.length; i++) {
        tt[i] = Math.random();
      }
      code.addDoubleArray("tt", tt);
      File f = code.startPlot();
      code.addRCode("pie(tt)");
      code.endPlot();

      r.setRCode(code);
      r.runAndReturnResult("ols");

      System.out.println("Names : " + r.getParser().getNames());
      code.showPlot(f);
      double[] v = r.getParser().getAsDoubleArray("fitted_values");
      for (int i = 0; i < v.length; i++) {
        System.out.println(v[i]);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    new Main();
  }
}
