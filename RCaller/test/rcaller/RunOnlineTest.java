
package rcaller;

import org.junit.Test;
import static org.junit.Assert.*;

public class RunOnlineTest {
    
  String RFileWindows = "C:\\Program Files\\R\\R-2.13.0\\bin\\R.exe";
  String RFileLinux = "/usr/bin/R";
  String currentRFile = null;
  
  public RunOnlineTest(){
    String osname = System.getProperty("os.name");
    if(osname.contains("Windows")){
        currentRFile = RFileWindows;
    }else{
        currentRFile = RFileLinux;
    }
  }
  
  @Test
  public void onlineCalculationTest(){
    RCaller rcaller = new RCaller();
    rcaller.setRExecutable(currentRFile);
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
    
    //Test mean of b is still alive
    rcaller.runAndReturnResultOnline("m");
    assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);
  }
}
