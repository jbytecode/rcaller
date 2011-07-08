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
public class Main {
    
    public Main(){
        RCaller r = new RCaller();
        r.setRscriptExecutable("/usr/bin/Rscript");
        double[] x = new double[]{1,2,3,4,5};
        double[] y = new double[]{5,7,6,10,20};
        r.addDoubleArray("x", x);
        r.addDoubleArray("y", y);
        r.addRCode("ols<-lm(y~x)");
        r.addRCode("a<-list(val1=c(1,2.718,3.141), b=9)");
        try{
            r.runAndReturnResult("a");
            double[] v=r.getParser().getAsDoubleArray("val1");
            for (int i=0;i<v.length;i++) System.out.println(v[i]);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String[] args){
        new Main();
    }
}
