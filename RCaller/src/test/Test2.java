/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Test2 {
    
    public static void main(String[] args){
        new Test2();
    }
    
    public Test2(){
        try{
            RCaller caller = new RCaller();
            caller.setRscriptExecutable("/usr/bin/Rscript");
            
            double[] x = new double[]{1,2,3,4,5,6,7,8,9,10};
            double[] y = new double[]{2,4,6,8,10,12,14,16,18,30};
            
            caller.addDoubleArray("x", x);
            caller.addDoubleArray("y", y);
            caller.addRCode("ols<-lm(y~x)");
            
            caller.runAndReturnResult("ols");
            
            System.out.println("Available results from lm() object:");
            System.out.println(caller.getParser().getNames());
            
            double[] residuals = caller.getParser().getAsDoubleArray("residuals");
            double[] coefficients = caller.getParser().getAsDoubleArray("coefficients");
            double[] fitteds = caller.getParser().getAsDoubleArray("fitted_values");
            
            System.out.println("Coefficients:");
            for (int i=0;i<coefficients.length;i++){
                System.out.println("Beta "+i+" = "+coefficients[i]);
            }
            
            System.out.println("Residuals:");
            for (int i=0;i<residuals.length;i++){
                System.out.println(i+" = "+residuals[i]);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    
}
