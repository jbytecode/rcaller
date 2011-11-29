
package rcaller;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;


public class RCode {
  
  StringBuffer code;
  
  public void setCode(StringBuffer sb){
    this.code = sb;
  }
  
  public StringBuffer getCode(){
    return(this.code);
  }
  
  public RCode(){
    this.code = new StringBuffer();
    clear();
  }
  
  public RCode(StringBuffer sb){
    this.code = sb;
  }
  
  
  
  public final void clear(){
    code.setLength(0);
    addRCode("packageExist<-require(Runiversal)");
    addRCode("if(!packageExist){");
    addRCode("install.packages(\"Runiversal\", repos=\" " + Globals.cranRepos + "\")");
    addRCode("}\n");
  }
  
  public void addRCode(String code) {
    this.code.append(code).append("\n");
  }
  
  public void addStringArray(String name, String[] arr) {
    CodeUtils.addStringArray(code, name, arr, false);
  }

  public void addDoubleArray(String name, double[] arr) {
    CodeUtils.addDoubleArray(code, name, arr, false);
  }

  public void addFloatArray(String name, float[] arr) {
    CodeUtils.addFloatArray(code, name, arr, false);
  }

  public void addIntArray(String name, int[] arr) {
    CodeUtils.addIntArray(code, name, arr, false);
  }

  public void addShortArray(String name, short[] arr) {
    CodeUtils.addShortArray(code, name, arr, false);
  }

  public void addLogicalArray(String name, boolean[] arr) {
    CodeUtils.addLogicalArray(code, name, arr, false);
  }

  public void addJavaObject(String name, Object o) throws IllegalAccessException {
    CodeUtils.addJavaObject(code, name, o, false);
  }
  
  public void addDoubleMatrix(String name, double[][] matrix){
    CodeUtils.addDoubleMatrix(code, name, matrix, false);
  }

  public File startPlot() throws IOException {
    File f = File.createTempFile("RPlot", ".png");
    addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
    addRCode(Globals.theme.generateRCode());
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
  
  public void R_require(String pkg) {
    code = code.insert(0, "require(" + pkg + ")\n");
  }

  public void R_source(String sourceFile) {
    addRCode("source(\"" + sourceFile + "\")\n");
  }
  
  @Override
  public String toString() {
    return this.code.toString();
  }  
}
