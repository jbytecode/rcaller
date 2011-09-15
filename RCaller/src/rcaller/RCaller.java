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
import javax.swing.ImageIcon;
import rcaller.exception.RCallerExecutionException;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RCaller {

  private String RscriptExecutable;
  private StringBuffer RCode;
  private ROutputParser parser;
  private String cranRepos = "http://cran.r-project.org";
  public final static String version = "RCaller 2.0";
  public final static String about = "Author: Mehmet Hakan Satman - mhsatman@yahoo.com";
  public final static String licence = "LGPL v3.0";

  public String getCranRepos() {
    return cranRepos;
  }

  public void setCranRepos(String cranRepos) {
    this.cranRepos = cranRepos;
  }

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
    addRCode("install.packages(\"Runiversal\", repos=\" " + cranRepos + "\")");
    addRCode("}\n");
  }

  public void addRCode(String code) {
    this.RCode.append(code).append("\n");
  }

  public void addStringArray(String name, String[] arr) {
    this.RCode.append(name).append("<-").append("c(");
    for (int i = 0; i < arr.length; i++) {
      this.RCode.append("\"").append(arr[i]).append("\"");
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

  public File startPlot() throws IOException {
    File f = File.createTempFile("RPlot", ".png");
    addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    return (f);
  }

  public void endPlot() {
    addRCode("dev.off()");
  }

  public ImageIcon getPlot(File f) {
    ImageIcon img = new ImageIcon(f.toString());
    return (img);
  }

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
      process.waitFor();
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

    RCode.append("cat(makexml(obj=").append(var).append(", name=\"").append(var).append("\"), file=\"").append(outputFile.toString().replace("\\", "/")).append("\")\n");
    rSourceFile = createRSourceFile();
    try {
      //commandline = RscriptExecutable + " " + rSourceFile.toString() + " > " + outputFile.toString();
      commandline = RscriptExecutable + " " + rSourceFile.toString();
      Process process = Runtime.getRuntime().exec(commandline);
      process.waitFor();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not run " + RscriptExecutable + ". Reason: " + e.toString());
    }


    parser.setXMLFile(outputFile);
    try {
      parser.parse();
    } catch (Exception e) {
      throw new RCallerExecutionException("Can not handle R results due to : " + e.toString());
    }
  }

  public void R_require(String pkg) {
    this.RCode = this.RCode.insert(0, "require(" + pkg + ")\n");
  }

  public void R_source(String sourceFile) {
    addRCode("source(\"" + sourceFile + "\")\n");
  }
}