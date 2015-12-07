

package examples;

import com.github.rcaller.util.Globals;
import com.github.rcaller.rStuff.RCaller;
import com.github.rcaller.rStuff.RCode;


public class GridCapExample {
    
    public static void main(String[] args){
        RCaller caller = new RCaller();
        RCode code = new RCode();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
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
