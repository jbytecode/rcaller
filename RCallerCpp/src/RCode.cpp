/* 
 * File:   RCode.cpp
 * Author: hako
 * 
 * Created on November 12, 2011, 12:11 AM
 */

#include "RCode.h"
#include "Globals.h"
#include "CodeUtils.h"


   RCode::RCode(){
    this->code = new string();
    this->clear();
  }
  
  RCode::RCode(string *sb){
      this->code = sb;
  }
  


RCode::~RCode() {
}

void RCode::setCode(string* sb){
    this->code = sb;
}

string *RCode::getCode(){
    return (this->code);
}

  
  
  void RCode::clear(){
     this->code->clear();
     string *s = new string("packageExist<-require(Runiversal)");
     s->append("if(!packageExist){");
     s->append("install.packages(\"Runiversal\", repos=\" " + Globals::cranRepos + "\")");
     s->append("}\n");
     this->addRCode(s);
   }
  
  void RCode::addRCode(string *code) {
      this->code->append(*code).append(string("\n"));
  }
  
  
   void RCode::addStringArray(string* name, string** arr, int length) {
       CodeUtils::addStringArray(code, name, arr, length, false);
  }

   void RCode::addDoubleArray(string* name, double* arr, int length){
       CodeUtils::addDoubleArray(code, name, arr, length, false);
   }

   
   /*
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
*/