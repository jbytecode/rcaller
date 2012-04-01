package rcaller;


import org.junit.Test;
import static org.junit.Assert.*;

public class ParserEmptyFileTest {

    @Test(expected = rcaller.exception.RCallerParseException.class)
    public void EmptyOutputInParser(){
    	RCaller caller = new RCaller();
    	caller.setRscriptExecutable(Globals.RScript_Linux);
    	RCode code = new RCode();
    	code.addRCode("Some meaningless code");
    	caller.runAndReturnResult("requestedvar");
    }
}
