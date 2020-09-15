package com.github.rcaller;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.Globals;
import java.io.BufferedReader;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EventHandlerTest {

    private RCaller caller;
    private RCode code;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Globals.detect_current_rscript();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        File f = new File("oneCopy.txt");
        boolean result = f.delete();
    }

    @Before
    public void setUp() {
        caller = RCaller.create();
        code = RCode.create();
    }

    @After
    public void tearDown() {
        caller = null;
        code = null;
    }

    @Test
    public void testEventHandler1() throws FileNotFoundException, IOException {
        caller.redirectROutputToFile("oneCopy.txt", false);

        code.clear();
        code.addRCode("x<-1:1000");
        code.addRCode("m<-median(x)");
        caller.setRCode(code);

        caller.runAndReturnResultOnline("m");

        code.clear();
        code.addRCode("cat(\"This message might be catched by the event handler\")");
        caller.runAndReturnResultOnline("m");

        caller.stopStreamConsumers();

        caller.deleteTempFiles();
        caller.stopRCallerOnline();

        BufferedReader reader = new BufferedReader(new FileReader("oneCopy.txt"));
        String s;
        boolean resultFound = false;

        while (true) {
            s = reader.readLine();
            if (s == null) {
                break;
            }
            if (s.contains("This message might be catched by the event handler")) {
                resultFound = true;
                break;
            }
        }

        assertTrue(resultFound);

        reader.close();

        File f = new File("oneCopy.txt");
        f.delete();

    }
}
