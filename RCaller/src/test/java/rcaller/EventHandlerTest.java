package rcaller;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventHandlerTest {

  public EventHandlerTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testEventHandler1() {
    RCaller caller = new RCaller();
    RCode code = new RCode();

    Globals.detect_current_rscript();
    caller.setRExecutable(Globals.R_current);

    caller.addEventHandler(new EventHandler() {
      public void MessageReceived(String threadName, String msg) {
        System.out.println("** Event Handled ("+threadName+"): " + msg);
      }
    });
    
    code.clear();
    code.addRCode("x<-1:1000");
    code.addRCode("m<-median(x)");
    caller.setRCode(code);
    caller.runAndReturnResultOnline("m");
    
    code.clear();
    code.addRCode("cat(\"This message might be catched by the event handler\")");
    caller.runAndReturnResultOnline("m");

  }
}
