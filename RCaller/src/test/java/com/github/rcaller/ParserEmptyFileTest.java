package com.github.rcaller;


import com.github.rcaller.rStuff.RCaller;
import com.github.rcaller.rStuff.RCode;
import com.github.rcaller.util.Globals;
import org.junit.Test;

public class ParserEmptyFileTest {

    @Test(expected = Exception.class)
    public void EmptyOutputInParser(){
    	RCaller caller = new RCaller();
        Globals.detect_current_rscript();
    	caller.setRscriptExecutable(Globals.Rscript_current);
    	RCode code = new RCode();
    	code.addRCode("Some meaningless code");
    	caller.runAndReturnResult("requestedvar");
        caller.deleteTempFiles();
    }
}
