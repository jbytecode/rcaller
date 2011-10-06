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
 */
public class Example4 {
  
  public static void main(String[] args) {
    new Example4();
  }
  
  public Example4() {
    try {
      /*
       * Creating an instance of RCaller
       */
      RCaller caller = new RCaller();
      RCode code = new RCode();
      /*
       * Defining the Rscript executable
       */
      caller.setRscriptExecutable("/usr/bin/Rscript");

      /*
       * Some R Stuff
       */
      code.addRCode("set.seed(123)");
      code.addRCode("x<-rnorm(10)");
      code.addRCode("y<-rnorm(10)");
      code.addRCode("ols<-lm(y~x)");

      /*
       * We want to handle the object 'ols'
       */
      caller.setRCode(code);
      caller.runAndReturnResult("ols");

      /*
       * Getting R results as XML
       * for debugging issues.
       */
      System.out.println(caller.getParser().getXMLFileAsString());
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
