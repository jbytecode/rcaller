package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.Globals;
import org.junit.Test;
import org.junit.Assert;

public class FunctionCallTest {

    private final static double delta = 1.0 / 1000.0;

    @Test
    public void LmCall() {
        RCaller caller = RCaller.create();
        RCode code = new RCode();

        code.addDoubleArray("x", new double[]{1, 2, 3, 4, 5});
        code.addDoubleArray("y", new double[]{2, 4, 6, 8, 10});
        code.addRCode("mydata <- as.data.frame(cbind(x,y))");

        FunctionCall fc = new FunctionCall();
        fc.setFunctionName("lm");
        fc.addParameter(new FunctionParameter("formula", "y~x", FunctionParameter.PARAM_OBJECT));
        fc.addParameter(new FunctionParameter("data", "mydata", FunctionParameter.PARAM_OBJECT));

        code.addFunctionCall("RegressResult", fc);
        caller.setRCode(code);

        caller.runAndReturnResult("RegressResult");
        double[] coefs = caller.getParser().getAsDoubleArray("coefficients");

        Assert.assertEquals(0.0, coefs[0], delta);
        Assert.assertEquals(2.0, coefs[1], delta);
    }
}
