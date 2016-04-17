// Fixes the bug reported in
// http://stackoverflow.com/questions/24167677/rcaller-2-2-and-2-3-do-not-stop-in-sequential-command-mode-with-runandreturnresu/24170648#24170648
// by the RCaller version 2.4


package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

public class Stackoverflow1 {

    public static void main(String[] args) {
        RCaller rcaller = RCaller.create();
        RCode code = new RCode();

        code.addDoubleArray("x", new double[]{1.0, 2.0, 3.0, 4.0, 50.0});
        code.addRCode("result <- mean(x)");
        rcaller.setRCode(code);
        System.out.println("Running R");

        rcaller.runAndReturnResultOnline("result");

        double mean = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("mean: " + mean);

        code.clear();
        code.addRCode("result <- sd(x)");
        rcaller.runAndReturnResultOnline("result");

        double sd = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("sd: " + sd);

        code.clear();
        code.addRCode("result <- mad(x)");
        rcaller.runAndReturnResultOnline("result");

        double mad = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("mad: " + mad);
        rcaller.stopStreamConsumers();
        rcaller.StopRCallerOnline();
    }

}
