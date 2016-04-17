

package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;


public class GridCapExample {
    
    public static void main(String[] args){
        RCaller caller = RCaller.create();
        RCode code = new RCode();
        code.R_require("grid");
        code.addRCode("dev.new(width=.5, height=.5)");
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
