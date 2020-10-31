
package examples;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * This example shows how RCaller
 * handles data sent from R. The 
 * data used is imported using 
 * read.table() in R side.
 */
public class WorkingWithData {

  public WorkingWithData(){

    /**
     *  Creating an RCaller instance
     */
    RCaller caller = RCaller.create();

    /**
     * Creating a source code
     */
    RCode code = RCode.create();

    /**
     *  Creating an external data file
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
    
    /**
     * Now, writing some R Code
     */
    if (f != null) {
      code.addRCode("data <- read.table (\""+f.getAbsoluteFile()+"\", header=TRUE)");
    }

    /**
     *  Running the Code
     */
    caller.setRCode(code);
    caller.runAndReturnResult("data");
    
    /**
     * Getting Results
     */
    double[] Z = caller.getParser().getAsDoubleArray("Z");
    
    /**
     * Printing Z
     */
    for (double aZ : Z) {
      System.out.println(aZ);
    }
  }

  public static void main(String[] args){
    new WorkingWithData();
  }
}
