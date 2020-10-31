package examples;

import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class BasicScriptEngine {

    public static void main(String[] args) {
        try {
            new BasicScriptEngine();
        } catch (ScriptException exception) {
            System.out.println("Error while scripting: " + exception.toString());
        }
    }

    public BasicScriptEngine() throws ScriptException{
        Example1();
        Example2();
    }

    public void Example1() throws ScriptException {

        Globals.R_current = "/usr/bin/R";
        RCallerScriptEngine engine = new RCallerScriptEngine();
        
        engine.put("mydata", new double[]{1, 2, 3, 4, 5});
        engine.eval("mymean <- mean(mydata)");
        
        double[] result = (double[]) engine.get("mymean");
        System.out.println("Mean is " + result[0]);
        engine.close();

    }



     public void Example2() throws ScriptException {
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("RCaller");

        engine.put("a", 25);

        engine.eval("b <- sqrt(a)");

        double[] result = (double[]) engine.get("b");

        System.out.println("b is " + result[0]);

        ((RCallerScriptEngine) engine).close();
    }
}
