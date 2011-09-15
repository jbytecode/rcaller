/*
 * Test for Windows Systems
 */

package examples;

import java.io.File;
import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 * 
 */

public class Test6 {
    
    public Test6(){
        try{
            RCaller caller = new RCaller();
            caller.setRscriptExecutable("C:\\Program Files\\R\\R-2.13.0\\bin\\Rscript.exe");
            caller.cleanRCode();
            caller.addRCode("x<-rnorm(10)");
            caller.addRCode("y<-rnorm(10)");
            caller.addRCode("ols<-lm(y~x)");
            
            File plt = caller.startPlot();
            caller.addRCode("plot(ols$residuals, ols$fitted.values)");
            caller.endPlot();
            
            caller.runAndReturnResult("ols");
            
            caller.showPlot(plt);
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public static void main(String[] args){
        new Test6();
    }
}
