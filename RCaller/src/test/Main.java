/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Main {

    public Main() {
        try {
            RCaller r = new RCaller();
            r.setRscriptExecutable("/usr/bin/Rscript");
            double[] x = new double[]{1, 2, 3, 4, 5};
            double[] y = new double[]{5, 7, 6, 10, 20};
            r.addDoubleArray("x", x);
            r.addDoubleArray("y", y);
            r.addRCode("ols<-lm(y~x)");
            r.addRCode("a<-ols$residuals");
            
            double[] tt= new double[100];
            for (int i=0;i<tt.length;i++)tt[i]=Math.random();
            r.addDoubleArray("tt", tt);
            File f = r.startPlot();
            r.addRCode("pie(tt)");
            r.endPlot();
            
            r.runAndReturnResult("ols");
            
            System.out.println("Names : "+r.getParser().getNames());
            r.showPlot(f);
            double[] v = r.getParser().getAsDoubleArray("fitted_values");
            for (int i = 0; i < v.length; i++) {
                System.out.println(v[i]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
