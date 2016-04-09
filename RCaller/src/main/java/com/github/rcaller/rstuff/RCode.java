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
import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.graphics.GraphicsType;
import com.github.rcaller.util.CodeUtils;
import com.github.rcaller.util.Globals;

import javax.swing.*;
import java.io.*;

public class RCode {

    protected StringBuffer code;
    protected TempFileService tempFileService = null;
    
    public void setCode(StringBuffer sb) {
        this.code = new StringBuffer();
        clear();
        this.code.append(sb);
    }

    public StringBuffer getCode() {
        return (this.code);
    }

    public RCode() {
        this.code = new StringBuffer();
        clear();
    }

    public RCode(StringBuffer sb) {
        this.code = new StringBuffer();
        clear();
        this.code.append(sb);
    }

    public final void clear() {
        code.setLength(0);
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("runiversal.r");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader breader = new BufferedReader(reader);
            while (true) {
                String s = breader.readLine();
                if (s == null) {
                    break;
                }
                addRCode(s);
            }
            addRCode("\n");
        } catch (IOException e) {
            throw new ExecutionException("runiversal.R in package: " + e.toString());
        }
    }
    
    public void clearOnline(){
        code.setLength(0);
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

    public void addJavaObject(Object o) throws IllegalAccessException {
        CodeUtils.addJavaObject(code, o, false);
    }

    public void addDoubleMatrix(String name, double[][] matrix) {
        CodeUtils.addDoubleMatrix(code, name, matrix, false);
    }
    
    public void addFunctionCall(String returnVarName, FunctionCall fc){
        this.code.append(fc.generateCode(returnVarName));
    }

    public void addDouble(String name, double d){
        CodeUtils.addDouble(code, name, d, false);
    }
    
    public void addInt(String name, int i){
        CodeUtils.addInt(code, name, i, false);
    }
    
    public void addLong(String name, long l){
        CodeUtils.addLong(code, name, l, false);
    }
    
    public void addFloat(String name, float f){
        CodeUtils.addFloat(code, name, f, false);
    }
    
    public void addShort(String name, short s){
        CodeUtils.addShort(code, name, s, false);
    }
    
    public void addBoolean(String name, boolean b){
        CodeUtils.addBoolean(code, name, b, false);
    }
    
    public void addLogical(String name, boolean b){
        addBoolean(name, b);
    }
    
    public void addString(String name, String value){
        CodeUtils.addString(code, name, value, false);
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
        if(tempFileService != null){
            tempFileService.deleteRCallerTempFiles();   
        }
    }

    @Override
    public String toString() {
        return this.code.toString();
    }
}
