package examples;

import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;
import javax.script.ScriptException;

public class RCallerScriptEngineExample {

    public static void main(String[] args) {
        try {
            RCallerScriptEngineExample e = new RCallerScriptEngineExample();
        } catch (ScriptException exception) {
            System.out.println("Error while scripting: " + exception.toString());
        }
    }

    public RCallerScriptEngineExample() throws ScriptException {
        Globals.R_current = "/usr/bin/R";
        RCallerScriptEngine engine = new RCallerScriptEngine();
        engine.put("mydata", new double[]{1, 2, 3, 4, 5});
        engine.eval("mymean <- mean(mydata)");
        double[] result = (double[]) engine.get("mymean");
        System.out.println("Mean is " + result[0]);
        engine.close();
    }
}
