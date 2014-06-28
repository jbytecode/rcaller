package rcaller;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EventHandlerTest {

    public EventHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("************* Cleaning oneCopy.txt ****************");
        File f = new File("oneCopy.txt");
        boolean result = f.delete();
        System.out.println("Deletion Result: "+result);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEventHandler1() throws FileNotFoundException {
        RCaller caller = new RCaller();
        RCode code = new RCode();

        Globals.detect_current_rscript();
        caller.setRExecutable(Globals.R_current);

        //this file must contain one copy of R's starup statement
        //try running with append=false first, then with append=true
        //the file must now contain two copies
        //TODO: I cannot think of an easy way to test that automatically
        caller.redirectROutputToFile("oneCopy.txt", false);
        
        code.clear();
        code.addRCode("x<-1:1000");
        code.addRCode("m<-median(x)");
        caller.setRCode(code);
        System.out.println("running code");
        caller.runAndReturnResultOnline("m");

        code.clear();
        code.addRCode("cat(\"This message might be catched by the event handler\")");
        caller.runAndReturnResultOnline("m");
        System.out.println("stopping consumers");
        caller.stopStreamConsumers();
        
        caller.deleteTempFiles();
        caller.StopRCallerOnline();
    }
}
