package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringArrayTest {

  public StringArrayTest() {
    try {
      RCaller caller = RCaller.create();
      RCode code = new RCode();

      String[] str11 = new String[]{"a", "b", "c", "d", "e", "f", "g", "s", "c"};
      String[] str22 = new String[]{"d", "b", "a", "l", "m", "a", "f", "r", "s"};


      code.addStringArray("x", str11);
      code.addStringArray("y", str22);
      code.addRCode("result<-intersect(x, y);");

      caller.setRCode(code);
      caller.runAndReturnResult("result");

      String[] v = caller.getParser().getAsStringArray("result");
      for (String aV : v) {
        System.out.println(aV);
      }

    } catch (Exception e) {
      Logger.getLogger(StringArrayTest.class.getName()).log(Level.SEVERE, e.getMessage());
    }
  }

  public static void main(String[] args) {
    new StringArrayTest();
  }
}
