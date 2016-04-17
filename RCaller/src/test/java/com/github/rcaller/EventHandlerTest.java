package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.Globals;
import org.junit.*;

import java.io.File;
import java.io.FileNotFoundException;

public class EventHandlerTest {

    private RCaller caller;
    private RCode code;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Globals.detect_current_rscript();
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
        caller = RCaller.create();
        code = new RCode();
    }

    @After
    public void tearDown() {
        caller = null;
        code = null;
    }

    @Test
    public void testEventHandler1() throws FileNotFoundException {
        caller.redirectROutputToFile("oneCopy.txt", false);

        code.clear();
        code.addRCode("x<-1:1000");
        code.addRCode("m<-median(x)");
        caller.setRCode(code);
        //System.out.println("running code");
        //System.out.println(code);
        caller.runAndReturnResultOnline("m");

        code.clear();
        code.addRCode("cat(\"This message might be catched by the event handler\")");
        caller.runAndReturnResultOnline("m");
        //System.out.println("stopping consumers");
        caller.stopStreamConsumers();

        caller.deleteTempFiles();
        caller.StopRCallerOnline();
    }
}
