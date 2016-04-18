
package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class GridCapTest {
    
    @Test
    public void GridCapTest() throws IOException{
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.R_require("grid");
        code.addRCode("dev.new(width=.5, height=.5)");
        code.addRCode("grid.rect()");
        code.addRCode("grid.text(\"hi\")");
        code.addRCode("cap <- grid.cap()");
        code.addRCode("returns <- cap");
        code.addRCode("dev.off()");
        caller.setRCode(code);
        caller.runAndReturnResult("returns");
        
        File f = new File("Rplots.pdf");

        assertEquals(-1397462731, f.hashCode());
        
        f.delete();
    }
}
