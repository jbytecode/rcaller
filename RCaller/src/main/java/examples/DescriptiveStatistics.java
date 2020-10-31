package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 * @since 2.0
 * @version 2.0
 */
public class DescriptiveStatistics {

  /**
   * Standalone test application.
   * Calculates descriptive statistics of a random array using R
   * from Java.
   *
   */
  public DescriptiveStatistics() {
    try {

      /**
       * Creating Java's random number generator
       */
      Random random = new Random();

      /**
       * Creating RCaller
       */
      RCaller caller = RCaller.create();
      RCode code = RCode.create();

      /**
       *  We are creating a random data from a normal distribution
       * with zero mean and unit variance with size of 100
       */
      double[] data = new double[100];
      
      for (int i = 0; i < data.length; i++) {
        data[i] = random.nextGaussian();
      }

      /**
       * We are transferring the double array to R
       */
      code.addDoubleArray("x", data);

      /**
       * Adding R Code
       */
      code.addRCode("my.mean<-mean(x)");
      code.addRCode("my.var<-var(x)");
      code.addRCode("my.sd<-sd(x)");
      code.addRCode("my.min<-min(x)");
      code.addRCode("my.max<-max(x)");
      code.addRCode("my.standardized<-scale(x)");

      /**
       * Combining all of them in a single list() object
       */
      code.addRCode(
              "my.all<-list(mean=my.mean, variance=my.var, sd=my.sd, min=my.min, max=my.max, std=my.standardized)");

      /**
       * We want to handle the list 'my.all'
       */
      caller.setRCode(code);
      caller.runAndReturnResult("my.all");
      
      double[] results;

      /**
       * Retrieving the 'mean' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("mean");
      System.out.println("Mean is " + results[0]);

      /**
       * Retrieving the 'variance' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("variance");
      System.out.println("Variance is " + results[0]);

      /**
       * Retrieving the 'sd' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("sd");
      System.out.println("Standard deviation is " + results[0]);

      /**
       * Retrieving the 'min' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("min");
      System.out.println("Minimum is " + results[0]);

      /**
       * Retrieving the 'max' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("max");
      System.out.println("Maximum is " + results[0]);

      /**
       * Retrieving the 'std' element of list 'my.all'
       */
      results = caller.getParser().getAsDoubleArray("std");

      /**
       * Now we are retrieving the standardized form of vector x
       */
      System.out.println("Standardized x is ");

      for (double result : results) {
        System.out.print(result + ", ");
      }
    } catch (Exception e) {
      Logger.getLogger(DescriptiveStatistics.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }
  
  public static void main(String[] args) {
    new DescriptiveStatistics();
  }
}

