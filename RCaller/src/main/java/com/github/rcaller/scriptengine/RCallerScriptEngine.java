package com.github.rcaller.scriptengine;

import com.github.rcaller.EventHandler;
import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.rstuff.ROutputParser;
import com.github.rcaller.util.CodeUtils;
import com.github.rcaller.util.Globals;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

public class RCallerScriptEngine implements ScriptEngine, EventHandler {

    RCaller rcaller;
    RCode rcode;
    ROutputParser parser;

    public RCallerScriptEngine() {
        rcaller = new RCaller();
        rcode = new RCode();

        rcaller.setRExecutable(Globals.R_current);

        rcode.addRCode("result <- list(a=0)");
        rcaller.setRCode(rcode);
        rcaller.runAndReturnResultOnline("result");
    }

    @Override
    public Object eval(String code, ScriptContext sc) throws ScriptException {
        return (this.eval(code));
    }

    @Override
    public Object eval(Reader reader, ScriptContext sc) throws ScriptException {
        return(this.eval(reader));
    }

    @Override
    public Object eval(String code) throws ScriptException {
        rcode.clearOnline();
        rcode.addRCode(code);
        rcode.addRCode("result <- list(a=0)");
        rcaller.setRCode(rcode);
        rcaller.runAndReturnResultOnline("result");
        return (null);
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        BufferedReader breader = null;

        breader = new BufferedReader(reader);

        String line = null;
        StringBuilder sbuilder = new StringBuilder();

        try {
            while (true) {
                line = breader.readLine();
                if (line == null) {
                    break;
                }
                sbuilder.append(line);
            }
        } catch (IOException ioe) {
            throw new ScriptException("Error while reading from reader: " + ioe.toString());
        }
        return (this.eval(sbuilder.toString()));
    }

    @Override
    public Object eval(String code, Bindings bndngs) throws ScriptException {
        return(this.eval(code));
    }

    @Override
    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        return(this.eval(reader));
    }

    @Override
    public void put(String name, Object o) {
        rcode.clearOnline();
        StringBuffer code = new StringBuffer();
        if (o instanceof double[]) {
            CodeUtils.addDoubleArray(code, name, (double[]) o, false);
        } else if (o instanceof int[]) {
            CodeUtils.addIntArray(code, name, (int[]) o, false);
        } else if (o instanceof float[]) {
            CodeUtils.addFloatArray(code, name, (float[]) o, false);
        } else if (o instanceof boolean[]) {
            CodeUtils.addLogicalArray(code, name, (boolean[]) o, false);
        } else if (o instanceof long[]) {
            CodeUtils.addLongArray(code, name, (long[]) o, false);
        } else if (o instanceof String[]) {
            CodeUtils.addStringArray(code, name, (String[]) o, false);
        } else if (o instanceof short[]) {
            CodeUtils.addShortArray(code, name, (short[]) o, false);
        } else if (o instanceof Double) {
            CodeUtils.addDouble(code, name, (Double) o, false);
        } else if (o instanceof Integer) {
            CodeUtils.addInt(code, name, (Integer) o, false);
        } else if (o instanceof Long) {
            CodeUtils.addLong(code, name, (Long) o, false);
        } else if (o instanceof Short) {
            CodeUtils.addShort(code, name, (Short) o, false);
        } else if (o instanceof String) {
            CodeUtils.addString(code, name, (String) o, false);
        } else if (o instanceof double[][]) {
            CodeUtils.addDoubleMatrix(code, name, (double[][]) o, false);
        }

        rcode.addRCode("result <- list(a=0)");
        rcode.setCode(code);

        rcaller.setRCode(rcode);
        rcaller.runAndReturnResultOnline("result");
    }

    @Override
    public Object get(String var) {
        int[] dimension;
        rcode.clearOnline();
        rcode.addRCode("result <- ls()");
        rcaller.runAndReturnResultOnline(var);
        parser = rcaller.getParser();
        parser.parse();
        try{
            dimension = parser.getDimensions(var);
        }catch (Exception e){
            return(parser.getAsStringArray(var));
        }
        String vartype = parser.getType(var);
        if (dimension[0] > 1 && dimension[1] > 1) {
            return (parser.getAsDoubleMatrix(var));
        } else if (vartype.equals("numeric")) {
            return (parser.getAsDoubleArray(var));
        } else if (vartype.equals("character")) {
            return (parser.getAsStringArray(var));
        } else {
            return (parser.getAsStringArray(var)); // :o
        }
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

    @Override
    public void messageReceived(String senderName, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void close(){
        this.rcaller.StopRCallerOnline();
    }

}
