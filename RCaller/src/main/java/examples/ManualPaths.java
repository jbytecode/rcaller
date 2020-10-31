package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;

import javax.script.ScriptException;

/**
 * Examples showing how to set the path to the R executable manually
 */
public class ManualPaths {
    public static void main(String[] args) {
        try {
            RCallerScriptEngineExample();
            RCallerExample();
        } catch (ScriptException exception) {
            System.out.println("Error while scripting: " + exception.toString());
        }
    }

    /**
     * Example using the scripting engine
     *
     * @throws ScriptException
     */
    public static void RCallerScriptEngineExample() throws ScriptException {
        //Set the paths manually
        Globals.setRPaths("C:\\Program Files (x86)\\R\\R-2.11.1\\bin\\Rscript.exe", "C:\\Program Files (x86)\\R\\R-2.11.1\\bin\\R.exe");
        //Get the engine
        RCallerScriptEngine engine = new RCallerScriptEngine();
        //Add a variable
        engine.put("mydata", new double[]{1, 2, 3, 4, 5});
        //Evaluate an expression
        engine.eval("mymean <- mean(mydata)");
        //Get the value of a variable
        double[] result = (double[]) engine.get("mymean");
        System.out.println("Mean is " + result[0]);
        engine.close();
    }

    /**
     * Example using the RCaller object
     */
    public static void RCallerExample() {
        //Set the paths manually
        Globals.setRPaths("C:\\Program Files (x86)\\R\\R-2.11.1\\bin\\Rscript.exe", "C:\\Program Files (x86)\\R\\R-2.11.1\\bin\\R.exe");
        //Create a RCaller object
        RCaller rCaller = RCaller.create();
        //Create a code object
        RCode code = RCode.create();
        rCaller.setRCode(code);
        //Add a variable
        code.addDoubleArray("mydata", new double[]{1, 2, 3, 4, 5});
        //Add an expression
        code.addRCode("mymean <- mean(mydata)");
        //Evaluate code
        rCaller.runAndReturnResult("mymean");
        //Parse result
        double[] result = rCaller.getParser().getAsDoubleArray("mymean");
        System.out.println("Mean is " + result[0]);
    }
}
