
package rcaller;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RunOnlineTest {
  
  public RunOnlineTest(){
    
  }
  
  @Test
  public void onlineCalculationTest() {
    RCaller rcaller = new RCaller();
    rcaller.setRExecutable("/usr/bin/R");
    rcaller.cleanRCode();
    rcaller.addRCode("a<-1:10");
    rcaller.runAndReturnResultOnline("a");
    assertEquals(rcaller.getParser().getAsIntArray("a")[0], 1);

    rcaller.cleanRCode();
    rcaller.addRCode("b<-1:10");
    rcaller.addRCode("m<-mean(b)");
    rcaller.runAndReturnResultOnline("m");
    assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);

    rcaller.cleanRCode();
    rcaller.addRCode("a<-1:99");
    rcaller.addRCode("k<-median(a)");
    rcaller.runAndReturnResultOnline("k");
    assertEquals(rcaller.getParser().getAsDoubleArray("k")[0], 50.0, 0.000001);
  }
}
