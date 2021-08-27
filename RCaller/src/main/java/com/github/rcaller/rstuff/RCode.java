/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2015  Mehmet Hakan Satman

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

package com.github.rcaller.rstuff;

import com.github.rcaller.FunctionCall;
import com.github.rcaller.TempFileService;
import com.github.rcaller.datatypes.DataFrame;
import com.github.rcaller.graphics.GraphicsType;
import com.github.rcaller.io.RCodeIO;
import com.github.rcaller.util.RCodeUtils;
import com.github.rcaller.util.Globals;

import javax.swing.*;
import java.io.*;
import java.net.URI;

public class RCode {

    private StringBuilder code;
    private TempFileService tempFileService = null;
    private final RCallerOptions rCallerOptions;


    private RCode() {
        this.code = new StringBuilder();
        rCallerOptions = RCallerOptions.create();
    }

    private RCode(RCallerOptions rCallerOptions) {
        this.code = new StringBuilder();
        this.rCallerOptions = rCallerOptions;
    }

    public static RCode create() {
        RCode rCode = new RCode();
        rCode.clear();
        return rCode;
    }

    public static RCode create(StringBuffer stringBuffer) {
        RCode rCode = RCode.create();
        rCode.getCode().append(stringBuffer.toString());
        return rCode;
    }

    public static RCode create(RCallerOptions rCallerOptions) {
        RCode rCode = new RCode(rCallerOptions);
        rCode.clear();
        return rCode;
    }

    public static RCode create(StringBuffer stringBuffer, RCallerOptions rCallerOptions) {
        RCode rCode = RCode.create(rCallerOptions);
        rCode.getCode().append(stringBuffer.toString());
        return rCode;
    }

    public void setCode(StringBuffer sb) {
        this.code = new StringBuilder();
        clear();
        this.code.append(sb.toString());
    }

    public void setCode(StringBuilder sb) {
        this.code = new StringBuilder();
        clear();
        this.code.append(sb);
    }

    public StringBuilder getCode() {
        return (this.code);
    }

    public final void clear() {
        this.code.setLength(0);
        addRCode(RCodeIO.getInterprocessDependencies(rCallerOptions));
    }

    /**
     * Adding R code that exports toplevel var to outputFile for reading by Java. Internal use only.
     * @param outputFile file to be created
     * @param var name of variable to be exported
     */
    public void appendStandardCodeToAppend(File outputFile, String var) {
        addRCode(RCodeIO.getVariableExporting(rCallerOptions, var, outputFile.toURI()));
    }

    static String createEndSignalCode(File outputFile) {
        return ("cat(1, file=\"" + outputFile.getPath().replace("\\", "/") + "\")\n");
    }

    public void appendEndSignalCode(File outputFile) {
        addRCode(createEndSignalCode(outputFile));
    }

    public void clearOnline(){
        code.setLength(0);
    }

    public void addRCode(String code) {
        this.code.append(code).append("\n");
    }

    public void addStringArray(String name, String[] arr) {
        RCodeUtils.addStringArray(code, name, arr, false);
    }

    public void addDoubleArray(String name, double[] arr) {
        RCodeUtils.addDoubleArray(code, name, arr, false);
    }

    public void addLongArray(String name, long[] arr) {
        RCodeUtils.addLongArray(code, name, arr, false);
    }
    
    public void addFloatArray(String name, float[] arr) {
        RCodeUtils.addFloatArray(code, name, arr, false);
    }

    public void addIntArray(String name, int[] arr) {
        RCodeUtils.addIntArray(code, name, arr, false);
    }

    public void addShortArray(String name, short[] arr) {
        RCodeUtils.addShortArray(code, name, arr, false);
    }

    public void addLogicalArray(String name, boolean[] arr) {
        RCodeUtils.addLogicalArray(code, name, arr, false);
    }

    public void addJavaObject(Object o) throws IllegalAccessException {
        RCodeUtils.addJavaObject(code, o, false);
    }

    public void addDoubleMatrix(String name, double[][] matrix) {
        RCodeUtils.addDoubleMatrix(code, name, matrix, false);
    }
    
    public void addFunctionCall(String returnVarName, FunctionCall fc){
        this.code.append(fc.generateCode(returnVarName));
    }

    public void addDouble(String name, double d){
        RCodeUtils.addDouble(code, name, d, false);
    }
    
    public void addInt(String name, int i){
        RCodeUtils.addInt(code, name, i, false);
    }
    
    public void addLong(String name, long l){
        RCodeUtils.addLong(code, name, l, false);
    }
    
    public void addFloat(String name, float f){
        RCodeUtils.addFloat(code, name, f, false);
    }
    
    public void addShort(String name, short s){
        RCodeUtils.addShort(code, name, s, false);
    }
    
    public void addBoolean(String name, boolean b){
        RCodeUtils.addBoolean(code, name, b, false);
    }
    
    public void addLogical(String name, boolean b){
        addBoolean(name, b);
    }
    
    public void addString(String name, String value){
        RCodeUtils.addString(code, name, value, false);
    }

    public void addDataFrame(String name, DataFrame dataFrame) {
        RCodeUtils.addDataFrame(code, name, dataFrame);
    }
    
    public File startPlot() throws IOException {
        return (startPlot(GraphicsType.png));
    }

    public File startPlot(GraphicsType type) throws IOException {
        if(tempFileService == null){
            tempFileService = new TempFileService();
        }
        //File f = File.createTempFile("RPlot", "." + type.name());
        File f = tempFileService.createTempFile("RPlot", "." + type.name());
        switch (type) {
            case png:
                addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
                break;
            case jpeg:
                addRCode("jpeg(\"" + f.toString().replace("\\", "/") + "\")");
                break;
            case tiff:
                addRCode("tiff(\"" + f.toString().replace("\\", "/") + "\")");
                break;
            case bmp:
                addRCode("bmp(\"" + f.toString().replace("\\", "/") + "\")");
                break;
            default:
                addRCode("png(\"" + f.toString().replace("\\", "/") + "\")");
        }
        addRCode(Globals.theme.generateRCode());
        return (f);
    }

    public void endPlot() {
        addRCode("dev.off()");
    }

    public ImageIcon getPlot(File f) {
        return (new ImageIcon(f.toString()));
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
    
    public void deleteTempFiles(){
        if (tempFileService != null){
            tempFileService.deleteRCallerTempFiles();   
        }
    }

    @Override
    public String toString() {
        return this.code.toString();
    }

    /**
     * Wrap current code to standard tryCatch function.
     * Error handler saves details to errorOutputFile if the error occurs.
     * @param errorOutputFile file to save error if it occurs
     * @return generated script to be evaluated
     */
    String toTryCatchScript(File errorOutputFile) {
        //Using code snippet "An improved “error handler”" with withCallingHandlers nested in tryCatch
        //from https://cran.r-project.org/web/packages/tryCatchLog/vignettes/tryCatchLog-intro.html

        var script = new StringBuilder("tryCatch(  withCallingHandlers({\n");
        script.append(this.code);

        script.append("}, error=function(e) {\n" +
                "  stacktrace <- as.character(sys.calls())\n" +
                "  exception <- as.character(e)\n" +
                "  caught <- list()\n" +
                "  caught[[1]] <- exception\n" +
                "  caught[[2]] <- stacktrace[6:(length(stacktrace)-2)]\n" + //remove wrapping steps
                "  names(caught) <- c(\"exception\", \"stacktrace\")\n");
        script.append(RCodeIO.getVariableExporting(rCallerOptions, "caught", errorOutputFile.toURI())).append("\n");
        script.append("}), error = function(e) {  })\n");
        return script.toString();
    }
}
