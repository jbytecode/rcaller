
package test;

import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */

public class Test4 {
    
    public static void main (String[] args){
        new Test4();
    }
    
    public Test4(){
        try{
            /*
             * Creating an instance of RCaller
             */
            RCaller caller = new RCaller();
            
            /*
             * Defining the Rscript executable
             */
            caller.setRscriptExecutable("/usr/bin/Rscript");
            
            /*
             * Some R Stuff
             */
            caller.addRCode("set.seed(123)");
            caller.addRCode("x<-rnorm(10)");
            caller.addRCode("y<-rnorm(10)");
            caller.addRCode("ols<-lm(y~x)");
            
            /*
             * We want to handle the object 'ols'
             */
            caller.runAndReturnResult("ols");
            
            /*
             * Getting R results as XML
             * for debugging issues.
             */
            System.out.println(caller.getParser().getXMLFileAsString());
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
