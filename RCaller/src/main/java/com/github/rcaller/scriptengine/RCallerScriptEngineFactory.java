/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2016  Mehmet Hakan Satman

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
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package com.github.rcaller.scriptengine;

import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class RCallerScriptEngineFactory implements ScriptEngineFactory {

    @Override
    public String getEngineName() {
        System.out.println("getEngineName() called");
        return ("RCaller");
    }

    @Override
    public String getEngineVersion() {
        return ("0.1");
    }

    @Override
    public List<String> getExtensions() {
        ArrayList<String> ext = new ArrayList<String>();
        ext.add("R");
        return (ext);
    }

    @Override
    public List<String> getMimeTypes() {
        ArrayList<String> mimes = new ArrayList<String>();
        mimes.add("text/R");
        return (mimes);
    }

    @Override
    public List<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("RCallerScriptEngine");
        names.add("RCaller");
        names.add("R");
        return (names);
    }

    @Override
    public String getLanguageName() {
        return ("R");
    }

    @Override
    public String getLanguageVersion() {
        return ("3.2.4");
    }

    @Override
    public Object getParameter(String key) {
        if (null != key) {
            switch (key) {
            case ScriptEngine.ENGINE:
                return this.getEngineName();
            case ScriptEngine.ENGINE_VERSION:
                return this.getEngineVersion();
            case ScriptEngine.NAME:
                return this.getNames();
            case ScriptEngine.LANGUAGE:
                return this.getLanguageName();
            case ScriptEngine.LANGUAGE_VERSION:
                return this.getLanguageVersion();
            default:
                return null;
           }
        }
        return("!");
    }

    @Override
    public String getMethodCallSyntax(String string, String string1, String... strings) {
        StringBuffer buf = new StringBuffer();
        buf.append(string);
        buf.append("(");
        buf.append(string1);
        if (strings.length == 0) {
            buf.append(")");
            return (buf.toString());
        }
        for (int i = 0; i < strings.length; i++) {
            buf.append(strings[i]);
            if (i < strings.length) {
                buf.append(",");
            }
        }
        buf.append(")");
        return (buf.toString());
    }

    @Override
    public String getOutputStatement(String string) {
        return ("print(" + string + ")");
    }

    @Override
    public String getProgram(String... strings) {
        return ("Program");
    }

    @Override
    public ScriptEngine getScriptEngine() {
        //System.out.println("getScriptEngine() called");
        RCallerScriptEngine engine = new RCallerScriptEngine();
        return (engine);
    }

}
