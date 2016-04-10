/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2016  Mehmet Hakan Satman

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

import com.github.rcaller.TempFileService;
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.exception.RExecutableNotFoundException;
import com.github.rcaller.rstuff.ROutputParser;
import com.github.rcaller.rstuff.RStreamHandler;
import com.github.rcaller.util.CodeUtils;
import com.github.rcaller.util.Globals;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class RCallerScriptEngine implements ScriptEngine {

    Process rprocess = null;
    boolean needXmlUpdate = false;
    TempFileService tempFileService = null;
    OutputStream rout = null;
    Thread processThread = null;
    public RStreamHandler inputStreamHandler = null;
    RStreamHandler errorStreamHandler = null;

    private void createProcess() throws ScriptException {
        final String currentR = Globals.R_current;
        final ProcessBuilder builder = new ProcessBuilder(currentR);
        StringBuilder basicCode = new StringBuilder();
        tempFileService = new TempFileService();

        try {
            rprocess = Runtime.getRuntime().exec(currentR + " --vanilla");
            inputStreamHandler = new RStreamHandler(rprocess.getInputStream(), "R: ");
            inputStreamHandler.start();
            rout = rprocess.getOutputStream();
            
            errorStreamHandler = new RStreamHandler(rprocess.getErrorStream(), "R(err): ");
            errorStreamHandler.start();
            
            Thread.sleep(200);
        } catch (InterruptedException inte) {
            System.out.println("rprocess create error " + inte.toString());
        } catch (IOException ioe) {
            System.out.println("rprocess create error " + ioe.toString());
        }

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("runiversal.r");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader breader = new BufferedReader(reader);
            while (true) {
                String s = breader.readLine();
                if (s == null) {
                    break;
                }
                basicCode.append(s);
                basicCode.append("\n");
            }
            basicCode.append("\n");
            breader.close();
            reader.close();
            is.close();
        } catch (IOException e) {
            throw new ScriptException("runiversal.R in package: " + e.toString());
        }

        try {
            this.rout.write(basicCode.toString().getBytes());
            this.rout.flush();
        } catch (IOException e) {
            throw new ScriptException("Cannot run RCaller basic code in R : " + e.toString());
        }
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            
        }

    }

    private void evalwrapper(String code) throws ScriptException {
        if (rprocess == null) {
            createProcess();
        }
        try {
            this.rout.write((code + "\n").getBytes());
            this.rout.flush();
            this.needXmlUpdate = true;
        } catch (IOException ioe) {
            throw new ScriptException("Error sending command (" + code + "): " + ioe.toString());
        }
    }

    @Override
    public Object eval(String string, ScriptContext sc) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object eval(Reader reader, ScriptContext sc) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object eval(String code) throws ScriptException {
        this.evalwrapper(code);
        System.out.println("Sent code: " + code);
        return (true);
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object eval(String string, Bindings bndngs) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object eval(Reader reader, Bindings bndngs) throws ScriptException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void put(String string, Object o) {

    }

    @Override
    public Object get(String var) {
        StringBuilder rcode = new StringBuilder();
        File tmpfile = null;
        try {
            tmpfile = tempFileService.createTempFile("RCallerEngine", "xml");
        } catch (Exception e) {
            throw new ExecutionException("Cannot create temp file while getting variable " + var + ": " + e.toString());
        }
        rcode.append("cat(makexml(obj=").append(var).append(", name=\"").append(var).
                append("\"), file=\"").append(tmpfile.toString().replace("\\", "/")).append("\")\n");

        try {
            this.rout.write(rcode.toString().getBytes());
            this.rout.flush();
        } catch (IOException ioe) {
            throw new ExecutionException("Cannot send makexml command to R while getting variable " + var + " : " + ioe.toString());
        }

        ROutputParser parser = new ROutputParser(tmpfile);
        parser.parse();
        switch(parser.getType(var)){
            case "character":
                return(parser.getAsStringArray(var));
            case "numeric":
                return(parser.getAsDoubleArray(var));
        }
        return (parser.getAsStringArray(var));
    }

    @Override
    public Bindings getBindings(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBindings(Bindings bndngs, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bindings createBindings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScriptContext getContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setContext(ScriptContext sc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ScriptEngineFactory getFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
