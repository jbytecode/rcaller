package com.github.rcaller;


import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import org.junit.Test;

public class ParserEmptyFileTest {

    @Test(expected = Exception.class)
    public void EmptyOutputInParser(){
    	RCaller caller = RCaller.create();
    	RCode code = new RCode();

    	code.addRCode("Some meaningless code");
    	caller.runAndReturnResult("requestedvar");
        caller.deleteTempFiles();
    }
}
