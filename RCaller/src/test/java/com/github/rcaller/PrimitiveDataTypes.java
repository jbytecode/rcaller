
package com.github.rcaller;


import com.github.rcaller.rstuff.RCode;
import org.junit.Test;
import static org.junit.Assert.*;

public class PrimitiveDataTypes {
    
    @Test
    public void testDouble(){
        RCode code = RCode.create();
        code.addDouble("d", 3.141592);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3.141592\n"));
    }
    
    @Test
    public void testInt(){
        RCode code = RCode.create();
        code.addInt("d", 3);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testLong(){
        RCode code = RCode.create();
        code.addLong("d", 3);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testFloat(){
        RCode code = RCode.create();
        code.addFloat("d", 3.141592f);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3.141592\n"));
    }

    @Test
    public void testShort(){
        RCode code = RCode.create();
        code.addShort("d", (short)3);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testBoolean(){
        RCode code = RCode.create();
        code.addBoolean("d", true);
        //System.out.println(code);
        assertEquals(true, code.toString().contains("d<-TRUE\n"));
    }
    
    @Test
    public void testString(){
        String msg = "Hello R!";
        RCode code = RCode.create();
        code.addString("s", msg);
        assertEquals(true, code.toString().contains("s<-\"Hello R!\"\n"));
    }
    
}
