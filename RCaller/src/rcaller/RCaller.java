/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010,2011  Mehmet Hakan Satman

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
 * Google code projec: http://code.google.com/p/rcaller/
 *
 */
package rcaller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;
import rcaller.exception.RCallerExecutionException;

/**
 *
 * @author Mehmet Hakan Satman
 * mhsatman@yahoo.com
 * http://stdioe.blogspot.com
 * http://www.mhsatman.com
 * http://code.google.com/p/rcaller
 * 
 */
public class RCaller {

  private String RscriptExecutable;
  private String RExecutable;
  private RCode rcode;
  private ROutputParser parser;
  private Process process;
  private InputStream inputStreamToR = null;
  private OutputStream outputStreamToR = null;
  private InputStream errorStreamToR = null;

  public InputStream getErrorStreamToR() {
    return errorStreamToR;
  }

  public void setErrorStreamToR(InputStream errorStreamToR) {
    this.errorStreamToR = errorStreamToR;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }

  public InputStream getInputStreamToR() {
    return inputStreamToR;
  }

  public void setInputStreamToR(InputStream inputStreamToR) {
    this.inputStreamToR = inputStreamToR;
  }

  public OutputStream getOutputStreamToR() {
    return outputStreamToR;
  }

  public void setOutputStreamToR(OutputStream outputStreamToR) {
    this.outputStreamToR = outputStreamToR;
  }

  public String getRExecutable() {
    return RExecutable;
  }

  public void setRExecutable(String RExecutable) {
    this.RExecutable = RExecutable;
  }

  public String getCranRepos() {
    return Globals.cranRepos;
  }

  public void setCranRepos(String cranRepos) {
    Globals.cranRepos = cranRepos;
  }

  public ROutputParser getParser() {
    return parser;
  }

  public void setParser(ROutputParser parser) {
    this.parser = parser;
  }

  public RCode getRCode() {
    return rcode;
  }

  public void setRCode(RCode rcode) {
    this.rcode = rcode;
  }

  public String getRscriptExecutable() {
    return RscriptExecutable;
  }

  public void setRscriptExecutable(String RscriptExecutable) {
    this.RscriptExecutable = RscriptExecutable;
  }

  public RCaller() {
    this.rcode = new RCode();
    this.parser = new ROutputParser();
    cleanRCode();
  }

  public void cleanRCode() {
    rcode.clear();
    rcode.addRCode("packageExist<-require(Runiversal)");
    rcode.addRCode("if(!packageExist){");
    rcode.addRCode("install.packages(\"Runiversal\", repos=\" " + this.getCranRepos() + "\")");
    rcode.addRCode("}\n");
  }

  /**
   * @deprecated Use RCode.addRCode instead
   * @param code 
   */
  public void addRCode(String code) {
    this.rcode.getCode().append(code).append("\n");
  }

  /**
   * @deprecated Use RCode.addStringArray instead
   * @param name
   * @param arr 
   */
  public void addStringArray(String name, String[] arr) {
    CodeUtils.addStringArray(rcode.getCode(), name, arr, false);
  }

  /**
   * @deprecated Use RCode.addDoubleArray
   * @param name
   * @param arr 
   */
  public void addDoubleArray(String name, double[] arr) {
    CodeUtils.addDoubleArray(rcode.getCode(), name, arr, false);
  }

  /**
   * @deprecated Use RCode.addFloatArray
   * @param name
   * @param arr 
   */
  public void addFloatArray(String name, float[] arr) {
    CodeUtils.addFloatArray(rcode.getCode(), name, arr, false);
  }

    /**
   * @deprecated Use RCode.addIntArray
   * @param name
   * @param arr 
   */
  public void addIntArray(String name, int[] arr) {
    CodeUtils.addIntArray(rcode.getCode(), name, arr, false);
  }

    /**
   * @deprecated Use RCode.addShortArray
   * @param name
   * @param arr 
   */
  public void addShortArray(String name, short[] arr) {
    CodeUtils.addShortArray(rcode.getCode(), name, arr, false);
  }

    /**
   * @deprecated Use RCode.addLogicalArray
   * @param name
   * @param arr 
   */
  public void addLogicalArray(String name, boolean[] arr) {
    CodeUtils.addLogicalArray(rcode.getCode(), name, arr, false);
  }

    /**
   * @deprecated Use RCode.addJavaObject
   * @param name
   * @param arr 
   */
  public void addJavaObject(String name, Object o) throws IllegalAccessException {
    CodeUtils.addJavaObject(rcode.getCode(), name, o, false);
  }

    /**
   * @deprecated Use RCode.startPlot
   * @param name
   * @param arr 
   */
  public File startPlot() throws IOException {
    File f = File.createTempFile("RPlot", ".png");
    rcode.addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    return (f);
  }

    /**
   * @deprecated Use RCode.endPlot
   * @param name
   * @param arr 
   */
  public void endPlot() {
    rcode.addRCode("dev.off()");
  }

    /**
   * @deprecated Use RCode.getPlot
   * @param name
   * @param arr 
   */
  public ImageIcon getPlot(File f) {
    ImageIcon img = new ImageIcon(f.toString());
    return (img);
  }

    /**
   * @deprecated Use RCode.showPlot
   * @param name
   * @param arr 
   */
  public void showPlot(File f) {
    ImageIcon plot = getPlot(f);
    RPlotViewer plotter = new RPlotViewer(plot);
    plotter.setVisible(true);
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
      writer.write(this.rcode.toString());
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
    this.rcode.getCode().append("q(").append("\"").append("yes").append("\"").append(")\n");
    File rSourceFile = createRSourceFile();
    try {
      //this Process object is local to this method. Do not use the public one.
      Process process = Runtime.getRuntime().exec(RscriptExecutable + " " + rSourceFile.toString());
      process.waitFor();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not run " + RscriptExecutable + ". Reason: " + e.toString());
    }
  }

  public void runAndReturnResultOnline(String var) throws rcaller.exception.RCallerExecutionException {
    String commandline = null;
    String result = null;
    File rSourceFile;
    final File outputFile;

    if (this.RExecutable == null) {
      throw new RCallerExecutionException("RExecutable is not defined. Please set this variable to full path of R executable binary file.");
    }


    try {
      outputFile = File.createTempFile("Routput", "");
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not create a tempopary file for storing the R results: " + e.toString());
    }

    this.rcode.getCode().append("cat(makexml(obj=").append(var).append(", name=\"").append(var).append("\"), file=\"").append(outputFile.toString().replace("\\", "/")).append("\")\n");

    if (outputStreamToR == null || inputStreamToR == null || errorStreamToR == null || process == null) {
      try {
        commandline = RExecutable + " --vanilla";
        process = Runtime.getRuntime().exec(commandline);
        outputStreamToR = process.getOutputStream();
        inputStreamToR = process.getInputStream();
        errorStreamToR = process.getErrorStream();
      } catch (Exception e) {
        throw new RCallerExecutionException("Can not run " + RExecutable + ". Reason: " + e.toString());
      }
    }

    InputStreamConsumer isConsumer = new InputStreamConsumer(inputStreamToR);
    InputStreamConsumer errConsumer = new InputStreamConsumer(errorStreamToR);
    isConsumer.getConsumerThread().start();
    errConsumer.getConsumerThread().start();

    try {
      outputStreamToR.write(rcode.toString().getBytes());
      outputStreamToR.flush();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not send the source code to R file due to: " + e.toString());
    }

    Thread calcThread = new Thread(new Runnable() {

      @Override
      public void run() {
        while (outputFile.length() < 1) {
          try {
            Thread.sleep(1);
          } catch (Exception e) {
          }
        }
      }
    });
    calcThread.start();
    try {
      calcThread.join();
    } catch (Exception e) {
      //Do nothing here
    }

    isConsumer.setCloseSignal(true);
    errConsumer.setCloseSignal(true);

    parser.setXMLFile(outputFile);

    try {
      parser.parse();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not handle R results due to : " + e.toString());
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

    rcode.getCode().append("cat(makexml(obj=").append(var).append(", name=\"").append(var).append("\"), file=\"").append(outputFile.toString().replace("\\", "/")).append("\")\n");
    rSourceFile = createRSourceFile();
    try {
      commandline = RscriptExecutable + " " + rSourceFile.toString();
      //this Process object is local to this method. Do not use the public one.
      Process process = Runtime.getRuntime().exec(commandline);
      process.waitFor();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not run " + RscriptExecutable + ". Reason: " + e.toString());
    }


    parser.setXMLFile(outputFile);
    try {
      parser.parse();
    } catch (Exception e) {
      System.out.println(rcode.toString());
      throw new RCallerExecutionException("Can not handle R results due to : " + e.toString());
    }
  }

  /**
   * @deprecated Use RCode.R_require instead
   * @param pkg 
   */
  public void R_require(String pkg) {
    this.rcode.code = this.rcode.getCode().insert(0, "require(" + pkg + ")\n");
  }

  /**
   * @deprecated Use RCode.R_source instead
   * @param sourceFile 
   */
  public void R_source(String sourceFile) {
    rcode.addRCode("source(\"" + sourceFile + "\")\n");
  }
}