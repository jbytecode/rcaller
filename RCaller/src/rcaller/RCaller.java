
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import rcaller.exception.RCallerExecutionException;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RCaller {

    private String RscriptExecutable;
    private StringBuffer RCode;

    public StringBuffer getRCode() {
        return RCode;
    }

    public void setRCode(StringBuffer RCode) {
        this.RCode = RCode;
    }

    public String getRscriptExecutable() {
        return RscriptExecutable;
    }

    public void setRscriptExecutable(String RscriptExecutable) {
        this.RscriptExecutable = RscriptExecutable;
    }

    public void cleanRCode() {
        this.RCode.setLength(0);
    }

    public void addRCode(String code) {
        this.RCode.append(code).append("\n");
    }

    public void addStringArray(String name, String[] arr) {
        this.RCode.append(name).append("<-").append("c(");
        for (int i = 0; i < arr.length; i++) {
            this.RCode.append(arr[i]);
            if (i < arr.length - 1) {
                this.RCode.append(", ");
            }
        }
        this.RCode.append(");").append("\n");
    }

    public void addDoubleArray(String name, double[] arr) {
        String[] s = new String[arr.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(arr[i]);
        }
        addStringArray(name, s);
    }

    public void addFloatArray(String name, float[] arr) {
        String[] s = new String[arr.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(arr[i]);
        }
        addStringArray(name, s);
    }

    public void addIntArray(String name, int[] arr) {
        String[] s = new String[arr.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(arr[i]);
        }
        addStringArray(name, s);
    }
    
    public File createRSourceFile() throws rcaller.exception.RCallerExecutionException{
        File f =null ;
        try{
            f = File.createTempFile("rcaller", "");
        }catch (Exception e){
            throw new RCallerExecutionException("Can not open a tempopary file for storing the R Code: "+e.toString());
        }
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(this.RCode.toString());
            writer.flush();
            writer.close();
        }catch (Exception e){
            throw new RCallerExecutionException("Can not write to temporary file for storing the R Code: "+e.toString());
        }
        
        return(f);
    }
    
    public void run() throws rcaller.exception.RCallerExecutionException{
        RCode.append("q(").append("\"").append("yes").append("\"").append(")\n");
        File rSourceFile = createRSourceFile();
    }
    
    

    public RCaller() {
    }
}