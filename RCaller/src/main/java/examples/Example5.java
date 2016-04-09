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
 * Google code projec: https://github.com/jbytecode/rcaller
 *
 */

/*
 * Test for RandomNumberGenerator
 */
package examples;

import com.github.rcaller.util.Globals;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.statistics.RandomNumberGenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Example5 {

    public static void main(String[] args) {
        new Example5();
    }

    public Example5() {
        try {
            /**
             * RCaller instant
             */
            RCaller caller = new RCaller();

            /**
             * Rscript executable
             */
            Globals.detect_current_rscript();
            caller.setRscriptExecutable(Globals.Rscript_current);

            /**
             * Random Number Generator
             */
            RandomNumberGenerator rng = new RandomNumberGenerator(caller);
            
            /**
             * Generating random numbers from a normal distribution with 
             * zero mean and unit standard deviation
             */
            double[] d = rng.randomNormal(10, 0, 1);
            
            /**
             * Printing generated content
             */
            for (double aD : d) {
                System.out.println(aD);
            }
        } catch (Exception e) {
            Logger.getLogger(Example5.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
