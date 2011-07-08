
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import rcaller.exception.RCallerExecutionException;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RCaller {

    private String RscriptExecutable;
    private StringBuffer RCode;
    private ROutputParser parser;

    public ROutputParser getParser() {
        return parser;
    }

    public void setParser(ROutputParser parser) {
        this.parser = parser;
    }

    
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

    public RCaller() {
        this.RCode = new StringBuffer();
        this.parser = new ROutputParser();
        cleanRCode();
    }

    public void cleanRCode() {
        this.RCode.setLength(0);
        addRCode("packageExist<-require(Runiversal)");
        addRCode("if(!packageExist){");
        addRCode("install.packages(\"Runiversal\", repos=\"http://cran.r-project.org\")");
        addRCode("}");
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

    public File createRSourceFile() throws rcaller.exception.RCallerExecutionException {
        File f = null;
        BufferedWriter writer = null;

        try {
            f = File.createTempFile("rcaller", "");
        } catch (Exception e) {
            throw new RCallerExecutionException("Can not open a tempopary file for storing the R Code: " + e.toString());
        }

        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(this.RCode.toString());
            writer.flush();
        } catch (Exception e) {
            throw new RCallerExecutionException("Can not write to temporary file for storing the R Code: " + e.toString());
        } finally {
            try {
                writer.close();
            } catch (Exception einner) {
            }
        }

        return (f);
    }

    public void runOnly() throws rcaller.exception.RCallerExecutionException {
        if (this.RscriptExecutable == null) {
            throw new RCallerExecutionException("RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file.");
        }
        RCode.append("q(").append("\"").append("yes").append("\"").append(")\n");
        File rSourceFile = createRSourceFile();
        try {
            Process process = Runtime.getRuntime().exec(RscriptExecutable + " " + rSourceFile.toString());
        } catch (Exception e) {
            throw new RCallerExecutionException("Can not run " + RscriptExecutable + ". Reason: " + e.toString());
        }
    }

    public void runAndReturnResult(String var) throws rcaller.exception.RCallerExecutionException {
        String commandline = null;
        String result = null;
        File rSourceFile, outputFile;
        
        if (this.RscriptExecutable == null) {
            throw new RCallerExecutionException("RscriptExecutable is not defined. Please set this variable to full path of Rscript executable binary file.");
        }
        
        
        try {
            outputFile = File.createTempFile("Routput", "");
        } catch (Exception e) {
            throw new RCallerExecutionException("Can not create a tempopary file for storing the R results: " + e.toString());
        }
        
        RCode.append("cat(makexml(").append(var).append("), file=\"").append(outputFile.toString()).append("\")\n");
        rSourceFile = createRSourceFile();
        System.out.println(rSourceFile);
        
        try {
            commandline = RscriptExecutable + " " + rSourceFile.toString() + " > "+outputFile.toString();
            Process process = Runtime.getRuntime().exec(commandline);
            process.waitFor();
        } catch (Exception e) {
            throw new RCallerExecutionException("Can not run " + RscriptExecutable + ". Reason: " + e.toString());
        }
        
       
        parser.setXMLFile(outputFile);
        try{
        parser.parse();
        }catch (Exception e){
            throw new RCallerExecutionException("Can not handle R results due to : "+e.toString());
        }
    }
}