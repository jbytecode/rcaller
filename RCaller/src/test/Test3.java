package test;

//~--- non-JDK imports --------------------------------------------------------
import rcaller.RCaller;

//~--- JDK imports ------------------------------------------------------------

import java.util.Random;

/**
 *
 * @author Mehmet Hakan Satman
 * @since 2.0
 * @version 2.0
 */
public class Test3 {

    /**
     * Standalone test application.
     * Calculates descriptive statistics of a random array using R
     * from Java.
     *
     */
    public Test3() {
        try {

            /*
             * Creating Java's random number generator
             */
            Random random = new Random();

            /*
             * Creating RCaller
             */
            RCaller caller = new RCaller();

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
            caller.addDoubleArray("x", data);

            /*
             * Adding R Code
             */
            caller.addRCode("my.mean<-mean(x)");
            caller.addRCode("my.var<-var(x)");
            caller.addRCode("my.sd<-sd(x)");
            caller.addRCode("my.min<-min(x)");
            caller.addRCode("my.max<-max(x)");
            caller.addRCode("my.standardized<-scale(x)");

            /*
             * Combining all of them in a single list() object
             */
            caller.addRCode(
                    "my.all<-list(mean=my.mean, variance=my.var, sd=my.sd, min=my.min, max=my.max, std=my.standardized)");

            /*
             * We want to handle the list 'my.all'
             */
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
        new Test3();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
