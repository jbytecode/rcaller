package examples;

import rcaller.RCaller;
import rcaller.RCode;

public class StringArrayTest {

  public StringArrayTest() {
    try {
      RCaller caller = new RCaller();
      //caller.setRscriptExecutable("C:\\Program Files\\R\\R-2.12.0\\bin\\i386\\Rscript.exe");
      caller.setRscriptExecutable("/usr/bin/Rscript");
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
      for (int i = 0; i < v.length; i++) {
        System.out.println(v[i]);
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    new StringArrayTest();
  }
}
