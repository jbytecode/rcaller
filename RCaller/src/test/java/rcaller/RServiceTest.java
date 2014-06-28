package rcaller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RServiceTest {

    static RService service;
    
    public RServiceTest(){
        Globals.detect_current_rscript();
        if(service == null){
            service = new RService(Globals.R_current);
        }
    }
    
    
    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestVersion() {
        String version = service.version();
        Assert.assertTrue(version.contains("R version"));
    }
    
    @Test
    public void TestMajorVersion(){
        String major = service.major();
        Assert.assertTrue(Integer.parseInt(major) >= 3);
    }
    
    
}
