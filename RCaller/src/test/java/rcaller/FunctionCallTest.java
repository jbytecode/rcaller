
package rcaller;

import org.junit.Test;
import org.junit.Assert;


public class FunctionCallTest {
    
    private final static double delta = 1/10000;
    
    @Test
    public void GridCapTest(){
        RCaller caller = new RCaller();
        RCode code = new RCode();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
        
        code.addDoubleArray("x", new double[]{1,2,3,4,5});
        code.addDoubleArray("y", new double[]{2,4,6,8,10});
        code.addRCode("mydata <- as.data.frame(cbind(x,y))");
        
        
        FunctionCall fc = new FunctionCall();
        fc.setFunctionName("lm");
        fc.addParameter(new FunctionParameter("formula", "y~x", FunctionParameter.PARAM_OBJECT));
        fc.addParameter(new FunctionParameter("data", "mydata", FunctionParameter.PARAM_OBJECT));
        
        code.addFunctionCall("RegressResult", fc);
        caller.setRCode(code);
        
        caller.runAndReturnResult("RegressResult");
        double[] coefs = caller.getParser().getAsDoubleArray("coefficients");

        Assert.assertEquals(0.0, coefs[0],delta);
        Assert.assertEquals(2.0, coefs[1],delta);
    }
}
