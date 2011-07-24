/*
 * Test for RandomNumberGenerator
 */
package test;

import rcaller.RCaller;
import rcaller.statistics.RandomNumberGenerator;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Test5 {

    public static void main(String[] args) {
        new Test5();
    }

    public Test5() {
        try {
            /*
             * RCaller instant
             */
            RCaller caller = new RCaller();
            /*
             * Rscript executable
             */
            caller.setRscriptExecutable("/usr/bin/Rscript");
            /*
             * Random Number Generator
             */
            RandomNumberGenerator rng = new RandomNumberGenerator(caller);
            
            /*
             * Generating random numbers from a normal distribution with 
             * zero mean and unit standard deviation
             */
            double[] d = rng.randomNormal(10, 0, 1);
            
            /*
             * Printing generated content
             */
            for (int i = 0; i < d.length; i++) {
                System.out.println(d[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
