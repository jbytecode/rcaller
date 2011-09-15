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

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Example1 {

    public static void main(String[] args) {
        new Example1();
    }

    /*
     * Test for simple plots.
     * This class simply plots a time series array using plot.ts()
     * function of R.
     */
    public Example1() {
        try {
            RCaller caller = new RCaller();
            caller.setRscriptExecutable("/usr/bin/Rscript");
            caller.cleanRCode();

            double[] numbers = new double[]{1, 4, 3, 5, 6, 10};

            caller.addDoubleArray("x", numbers);
            File file = caller.startPlot();
            System.out.println("Plot will be saved to : " + file);
            caller.addRCode("plot.ts(x)");
            caller.endPlot();
            caller.runOnly();
            caller.showPlot(file);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
