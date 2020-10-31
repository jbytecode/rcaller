
package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

/**
 * This example installs the package grid 
 * in R and produces some graphics.
 */
public class GridCap {
    
    public static void main(String[] args){

        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.R_require("grid");

        code.addRCode("dev.new(width = 0.5, height = 0.5)");
        code.addRCode("grid.rect()");
        code.addRCode("grid.text(\"hi\")");
        code.addRCode("cap <- grid.cap()");
        code.addRCode("dev.off()");

        caller.setRCode(code);
        caller.runAndReturnResult("cap");


        int[] dims = caller.getParser().getDimensions("cap");
        System.out.println("Returned matrix dimensions: "+dims[0]+" - "+dims[1]);

    }
}
