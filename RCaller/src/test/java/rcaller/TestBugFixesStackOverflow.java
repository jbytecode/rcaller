package rcaller;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class TestBugFixesStackOverflow {

   
    @Test
    public void testStack1() {
        RCaller caller = new RCaller();
        RCode code = new RCode();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
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
        caller.deleteTempFiles();
    }

    @Test
    public void testStack2() {
        double[][] data = new double[][]{
            {197.136, 124.32, 63.492, 59.052},
            {124.32, 78.4, 40.04, 37.24},
            {63.492, 40.04, 20.449, 19.019},
            {59.052, 37.24, 19.019, 17.689}
        };
        RCaller caller = new RCaller();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
        RCode code = new RCode();

        code.addDoubleMatrix("mydata", data);
        code.addRCode("result <- chisq.test(mydata)");
        code.addRCode("mylist <- list(pval = result$p.value, df=result$parameter)");

        caller.setRCode(code);
        caller.runAndReturnResult("mylist");

        double pvalue = caller.getParser().getAsDoubleArray("pval")[0];
        double df = caller.getParser().getAsDoubleArray("df")[0];
        System.out.println("Pvalue is : " + pvalue);
        System.out.println("Df is : " + df);
        caller.deleteTempFiles();
    }
}
