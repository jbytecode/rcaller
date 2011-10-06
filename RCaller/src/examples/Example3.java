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
import java.util.Random;
import rcaller.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 * @since 2.0
 * @version 2.0
 */
public class Example3 {

  /**
   * Standalone test application.
   * Calculates descriptive statistics of a random array using R
   * from Java.
   *
   */
  public Example3() {
    try {

      /*
       * Creating Java's random number generator
       */
      Random random = new Random();

      /*
       * Creating RCaller
       */
      RCaller caller = new RCaller();
      RCode code = new RCode();
      /*
       * Full path of the Rscript. Rscript is an executable file shipped with R.
       * It is something like C:\\Program File\\R\\bin.... in Windows
       */
      caller.setRscriptExecutable("/usr/bin/Rscript");

      /*
       *  We are creating a random data from a normal distribution
       * with zero mean and unit variance with size of 100
       */
      double[] data = new double[100];
      
      for (int i = 0; i < data.length; i++) {
        data[i] = random.nextGaussian();
      }

      /*
       * We are transferring the double array to R
       */
      code.addDoubleArray("x", data);

      /*
       * Adding R Code
       */
      code.addRCode("my.mean<-mean(x)");
      code.addRCode("my.var<-var(x)");
      code.addRCode("my.sd<-sd(x)");
      code.addRCode("my.min<-min(x)");
      code.addRCode("my.max<-max(x)");
      code.addRCode("my.standardized<-scale(x)");

      /*
       * Combining all of them in a single list() object
       */
      code.addRCode(
              "my.all<-list(mean=my.mean, variance=my.var, sd=my.sd, min=my.min, max=my.max, std=my.standardized)");

      /*
       * We want to handle the list 'my.all'
       */
      caller.setRCode(code);
      caller.runAndReturnResult("my.all");
      
      double[] results;

      /*
       * Retrieving the 'mean' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("mean");
      System.out.println("Mean is " + results[0]);

      /*
       * Retrieving the 'variance' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("variance");
      System.out.println("Variance is " + results[0]);

      /*
       * Retrieving the 'sd' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("sd");
      System.out.println("Standard deviation is " + results[0]);

      /*
       * Retrieving the 'min' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("min");
      System.out.println("Minimum is " + results[0]);

      /*
       * Retrieving the 'max' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("max");
      System.out.println("Maximum is " + results[0]);

      /*
       * Retrieving the 'std' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("std");

      /*
       * Now we are retrieving the standardized form of vector x
       */
      System.out.println("Standardized x is ");
      
      for (int i = 0; i < results.length; i++) {
        System.out.print(results[i] + ", ");
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
  
  public static void main(String[] args) {
    new Example3();
  }
}


//~ Formatted by Jindent --- http://www.jindent.com

