package benchmark;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

/**
 * Passing a double[] array (or matrix) to R, calculating a^2 (or transpose) 
 * handling the result in Java.
 * 
 * This benchmark reports the minimum, maximum median, average calculation times
 * with standard deviation and median absolute deviations scale measures.
 */
public class PassingArraysAndMatrices {

    private enum SimType {
        Vector, Matrix
    }

    /**
     * Random vector generator
     * 
     * @param size Size of vector
     * @return Generated vector
     */
    private final static double[] generateRandomVector(int size) {

        double[] d = new double[size];
        for (int i = 0; i < size; i++) {
            d[i] = Math.random();
        }

        return d;
    }

    /**
     * Generate random matrix
     * 
     * @param n Number of rows
     * @param m Number of columns
     * @return Random matrix.
     */
    private final static double[][] generateRandomMatrix(int n, int m) {

        double[][] d = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                d[i][j] = Math.random();
            }
        }

        return d;
    }

    public static void main(String[] args) {

        performSimulation(/* size of vector */ 10, 10 /* times */ , SimType.Vector);

    }

    public static void performSimulation(int VectorSize, int SimCount, SimType type) {

        RCaller caller = RCaller.create();
        RCode code = RCode.create();
        int[] elapsed = new int[SimCount];

        System.out.println("- Simulations started.");

        for (int simulations = 0; simulations < SimCount; simulations++) {

            int timeStart = (int) System.currentTimeMillis();
            code.clear();
            if (type == SimType.Vector) {
                code.addDoubleArray("randomvector", generateRandomVector(VectorSize));
                code.addRCode("result <- randomvector^2.0");
            } else {
                code.addDoubleMatrix("randommatrix", generateRandomMatrix(VectorSize, VectorSize));
                code.addRCode("result <- t(randommatrix)");
            }
            caller.setRCode(code);
            caller.runAndReturnResultOnline("result");
            /* return variable is not handled */ caller.getParser().getAsDoubleArray("result");
            elapsed[simulations] = (int) System.currentTimeMillis() - timeStart;

        }

        System.out.println("- Simulations finished. Calculating statistics:");

        code.clear();
        code.addIntArray("times", elapsed);
        code.addRCode("stats <- c(min(times), max(times), mean(times), sd(times), median(times), mad(times))");
        caller.setRCode(code);
        caller.runAndReturnResultOnline("stats");

        double[] stats = caller.getParser().getAsDoubleArray("stats");

        System.out.printf("%10s %10s %10s %10s %10s %10s\n", "Min", "Max", "Mean", "Std.Dev.", "Median", "Mad");
        System.out.printf("%10f %10f %10f %10f %10f %10f\n", stats[0], stats[1], stats[2], stats[3], stats[4],
                stats[5]);
        caller.stopRCallerOnline();
    }
}
