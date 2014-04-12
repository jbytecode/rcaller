
package rcaller;

import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class LargeDataTest {

    double delta = 1/100000;
    
    @Test
    public void testBigData500(){
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
        
        

    }
}
