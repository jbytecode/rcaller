
package rcaller;

import org.junit.Assert;
import org.junit.Test;


public class LargeDataTest {

    
   
    @Test
    public void testBigData_1000_10(){
        double delta = 1/100000;
        double[][] data = new double[1000][10];
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data[0].length;j++){
                data[i][j] = Math.random();
            }
        }
        RCaller caller = new RCaller();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
        
        RCode code = new RCode();
        code.addDoubleMatrix("x", data);
        code.addRCode("s <- dim(t(x) %*% x)");
        caller.setRCode(code);
        
        caller.runAndReturnResult("s");
        
        double[] result = caller.getParser().getAsDoubleArray("s");
        
        Assert.assertEquals(result[0], 10.0, delta);
        Assert.assertEquals(result[1], 10.0, delta);
        caller.deleteTempFiles();
    }
    
    @Test
    public void testReturnsBigData (){
        double delta = 0.5;
        RCaller caller = new RCaller();
        Globals.detect_current_rscript();
        caller.setRscriptExecutable(Globals.Rscript_current);
        
        RCode code = new RCode();
        code.addRCode("s <- rnorm(1024 * 2)");
        code.addRCode("m <- mean(s)");
        code.addRCode("result <- list(arr=s, mean=m)");
        caller.setRCode(code);
        caller.runAndReturnResult("result");
        
        double m = caller.getParser().getAsDoubleArray("mean")[0];
        int len = caller.getParser().getAsDoubleArray("arr").length;
        
        Assert.assertEquals(0.0, m, delta);
        Assert.assertEquals((long)len, 2048l);
        caller.deleteTempFiles();
    }
}
