package examples;

import com.github.rcaller.scriptengine.NamedArgument;
import static com.github.rcaller.scriptengine.NamedArgument.*;
import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;
import java.util.ArrayList;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineInvocable {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException{
        new ScriptEngineInvocable();
    }
    
    public ScriptEngineInvocable() throws ScriptException, NoSuchMethodException{
        Globals.detect_current_rscript();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("RCaller");
        
        ArrayList<NamedArgument> results = (ArrayList<NamedArgument>)
            ((Invocable)engine).invokeFunction("sqrt", Named("",25.0));
        
        double[] val = (double[])results.get(0).getObj();
        System.out.println("Result is "+ val[0]);
        
        ((RCallerScriptEngine)engine).close();
    }
}
