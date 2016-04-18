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

import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RCallerScriptEngineExample2 {

    public static void main(String[] args) throws ScriptException {
        Globals.detect_current_rscript();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("RCaller");

        engine.put("a", 25);

        engine.eval("b <- sqrt(a)");

        double[] result = (double[]) engine.get("b");

        System.out.println("b is " + result[0]);

        ((RCallerScriptEngine) engine).close();
    }
}
