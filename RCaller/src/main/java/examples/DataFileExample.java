
package examples;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import rcaller.Globals;
import rcaller.RCaller;
import rcaller.RCode;


public class DataFileExample {
  
  public DataFileExample(){
    
    /* Creating a RCaller */
    RCaller caller = new RCaller();
    caller.setRscriptExecutable("/usr/bin/Rscript");
    
    /* Creating a source code */
    RCode code = new RCode();
    code.clear();
    
    /* Creating an external data file 
     * Suppose that the data file is like
     * X Y Z
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * 10 11 12
     */
    File f = null;
    try{
      f = File.createTempFile("rcallerexmp", "");
      FileWriter writer = new FileWriter (f);
      PrintWriter pwriter = new PrintWriter(writer);
      pwriter.println("X Y Z");
      pwriter.println("1 2 3");
      pwriter.println("4 5 6");
      pwriter.println("7 8 9");
      pwriter.println("10 11 12");
      pwriter.flush();
      pwriter.close();
    }catch(Exception e){
      System.out.println("Error while writing to external data file");
    }
    
    /* Now, writing some R Code */
    code.addRCode("data<-read.table(\""+f.getAbsoluteFile()+"\", header=TRUE)");
    
    /* Running the Code */
    caller.setRCode(code);
    caller.runAndReturnResult("data");
    
    /* Getting Results */
    double[] Z = caller.getParser().getAsDoubleArray("Z");
    
    /* Printing Z */
    for (int i=0;i<Z.length;i++){
      System.out.println(Z[i]);
    }
  }
  
  public static void main(String[] args){
    new DataFileExample();
  }
}
