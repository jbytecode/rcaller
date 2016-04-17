
package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.Globals;
import org.junit.Assert;
import org.junit.Test;

public class GetMatrixDimensionsTest {
   
    @Test
    public void testMatrixGetDimensions(){
        int n = 21;
        int m = 23;
        double[][] data = new double[n][m];
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data[0].length;j++){
                data[i][j] = Math.random();
            }
        }
        RCaller caller = RCaller.create();

        RCode code = new RCode();
        code.addDoubleMatrix("x", data);
        caller.setRCode(code);
        
        caller.runAndReturnResult("x");
        
        int[] mydim = caller.getParser().getDimensions("x");
        
        Assert.assertEquals(n, mydim[0]);
        Assert.assertEquals(m, mydim[1]);
        caller.deleteTempFiles();
    }

    @Test
    public void testVectorGetDimensions(){
        int n = 25;
        int m = 1;
        double[] data = new double[n];
        for (int i=0;i<data.length;i++){
            data[i] = Math.random();
        }
        RCaller caller = RCaller.create();

        RCode code = new RCode();
        code.addDoubleArray("x", data);
        caller.setRCode(code);
        
        caller.runAndReturnResult("x");
        
        int[] mydim = caller.getParser().getDimensions("x");
        
        Assert.assertEquals(n, mydim[0]);
        Assert.assertEquals(m, mydim[1]);
        
        caller.deleteTempFiles();
    }

}
