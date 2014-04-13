package rcaller;

import org.junit.Assert;
import org.junit.Test;

public class TestBugFixesStackOverflow {

    @Test
    public void testStack1() {
        RCaller caller = new RCaller();
        RCode code = new RCode();
        caller.setRscriptExecutable("/usr/bin/Rscript");
        caller.cleanRCode();
        
        String x = "is.installed <- function(mypkg){ \n"
                + "is.element(mypkg, installed.packages()[,1])\n"
                + "}\n"
                + "result <- is.installed(\"bbmle\")\n";
        StringBuffer s = new StringBuffer(x);
        code.setCode(s);

        System.out.println(x);
        caller.setRCode(code);
        //caller.redirectROutputToConsole();
        //caller.runOnly();
        caller.runAndReturnResult("result");
        boolean result = caller.getParser().getAsLogicalArray("result")[0];
        Assert.assertFalse(result);
    }
}
