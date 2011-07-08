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
public class Test1 {
    
    public static void main(String[] args){
        new Test1();
    }
    
    /*
     * Test for simple plots
     */
    public Test1(){
        try{
            RCaller caller = new RCaller();
            caller.setRscriptExecutable("/usr/bin/Rscript");
            caller.cleanRCode();
            
            double[] numbers = new double[] {1,4,3,5,6,10};
            
            caller.addDoubleArray("x", numbers);
            File file = caller.startPlot();
            System.out.println(file);
            caller.addRCode("plot.ts(x)");
            caller.endPlot();
            caller.runOnly();
            caller.showPlot(file);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
