package rcaller;

import org.junit.Test;
import static org.junit.Assert.*;

public class RunOnlineTest {

  @Test
  public void onlineCalculationTest() {
    RCaller rcaller = new RCaller();
    RCode code = new RCode();

    Globals.detect_current_rscript();
    rcaller.setRscriptExecutable(Globals.Rscript_current);
    rcaller.setRExecutable(Globals.R_current);

    code.clear();
    code.addRCode("a<-1:10");

    rcaller.setRCode(code);
    rcaller.runAndReturnResultOnline("a");
    assertEquals(rcaller.getParser().getAsIntArray("a")[0], 1);

    code.clear();
    code.addRCode("b<-1:10");
    code.addRCode("m<-mean(b)");

    rcaller.runAndReturnResultOnline("m");
    assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);

    code.clear();
    code.addRCode("a<-1:99");
    code.addRCode("k<-median(a)");
    
    rcaller.runAndReturnResultOnline("k");
    assertEquals(rcaller.getParser().getAsDoubleArray("k")[0], 50.0, 0.000001);

    //Test mean of b is still alive
    rcaller.runAndReturnResultOnline("m");
    assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);
  }
  
}
