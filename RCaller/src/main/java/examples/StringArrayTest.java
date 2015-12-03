package examples;

import org.expr.rcaller.Globals;
import org.expr.rcaller.RCaller;
import org.expr.rcaller.RCode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringArrayTest {

  public StringArrayTest() {
    try {
      RCaller caller = new RCaller();
      Globals.detect_current_rscript();
      caller.setRscriptExecutable(Globals.Rscript_current);
      RCode code = new RCode();
      code.clear();

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
