package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.statistics.RandomNumberGenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class PRandomNumberGenerator {

    public static void main(String[] args) {
        new PRandomNumberGenerator();
    }

    public PRandomNumberGenerator() {
        try {
            /**
             * RCaller instant
             */
            RCaller caller = RCaller.create();

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
            Logger.getLogger(PRandomNumberGenerator.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
