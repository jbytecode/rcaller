/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010,2016  Mehmet Hakan Satman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code projec: https://github.com/jbytecode/rcaller
 *
 */
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

public class RCallerScriptEngineExample3 {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException{
        new RCallerScriptEngineExample3();
    }
    
    public RCallerScriptEngineExample3() throws ScriptException, NoSuchMethodException{
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
