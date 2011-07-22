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
            RCaller caller = new RCaller();
            caller.setRscriptExecutable("/usr/bin/Rscript");
            RandomNumberGenerator rng = new RandomNumberGenerator(caller);
            double[] d = rng.randomNormal(5, 0, 1);
            for (int i = 0; i < d.length; i++) {
                System.out.println(d[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
