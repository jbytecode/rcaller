package org.expr.rcaller;


import org.expr.rcaller.Globals;
import org.expr.rcaller.RCaller;
import org.expr.rcaller.RCode;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserEmptyFileTest {

    @Test(expected = org.expr.rcaller.exception.ParseException.class)
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
