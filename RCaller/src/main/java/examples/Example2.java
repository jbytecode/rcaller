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

import rcaller.RCaller;
import rcaller.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 * @since 2.0
 * @version 2.0
 * 
 */
public class Example2 {
  
  public static void main(String[] args) {
    new Example2();
  }

  /**
   *
   * Ordinary Least Squares with RCaller.
   * This class runs the lm() function of R
   * for regression user defined vector y on vector x from java
   */
  public Example2() {
    
    try {
      /*
       * Creating an instance of RCaller class
       */
      RCaller caller = new RCaller();
      RCode code = new RCode();

      /*
       * Where is the Rscript binary file (Rscript.exe in Windows) ?
       * We must do this!
       * 
       */
      caller.setRscriptExecutable("/usr/bin/Rscript");

      /*
       * Creating vectors x and y
       */
      double[] x = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
      double[] y = new double[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 30};

      /*
       * Converting Java arrays to R arrays
       */
      code.addDoubleArray("x", x);
      code.addDoubleArray("y", y);

      /*
       * R code for regression of y on x
       */
      code.addRCode("ols<-lm(y~x)");

      /*
       * Run all! Our regression code returns someting
       * and we want to handle the variable 'ols'
       */
      caller.setRCode(code);
      caller.runAndReturnResult("ols");

      /*
       * Getting names of components of the variable 'ols'
       */
      System.out.println("Available results from lm() object:");
      System.out.println(caller.getParser().getNames());


      /*
       * Getting ols$residulas, ols$coefficients and ols$fitted.values
       * The names after '$' are components of the lm() object in R language
       */
      double[] residuals = caller.getParser().getAsDoubleArray("residuals");
      double[] coefficients = caller.getParser().getAsDoubleArray("coefficients");
      double[] fitteds = caller.getParser().getAsDoubleArray("fitted_values");

      /*
       * Printing results
       */
      System.out.println("Coefficients:");
      for (int i = 0; i < coefficients.length; i++) {
        System.out.println("Beta " + i + " = " + coefficients[i]);
      }
      
      System.out.println("Residuals:");
      for (int i = 0; i < residuals.length; i++) {
        System.out.println(i + " = " + residuals[i]);
      }
      
    } catch (Exception e) {
      /*
       * Note that, RCaller does some OS based works such as creating an external process and
       * reading files from temporary directories or creating images for plots. Those operations
       * may cause exceptions for those that user must handle the potential errors. 
       */
      System.out.println(e.toString());
    }
  }
}
