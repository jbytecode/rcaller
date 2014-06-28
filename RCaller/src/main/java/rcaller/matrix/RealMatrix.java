/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2014  Mehmet Hakan Satman

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
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */

package rcaller.matrix;

import rcaller.RCaller;
import rcaller.RCode;
import rcaller.RService;

public class RealMatrix {
    RService service;
    RCaller rcaller;
    RCode code;
    String name;
    String HashString;
    
    public RealMatrix(String pathToR, String name){
        service = new RService(pathToR);
        rcaller = service.getRCaller();
        code = service.getRCode();
        this.name = name;
        this.HashString = "RCALLER" + String.valueOf(this.hashCode());
        code.addRCode(this.HashString + " <- 3" );
        rcaller.runAndReturnResultOnline(this.HashString);
    }
    
    public void setData(double[][] data){
        code.clearOnline();
        code.addDoubleMatrix(name, data);
        rcaller.runAndReturnResultOnline(this.HashString);
    }
    
    public int[] getDimensions(){
        code.clearOnline();
        rcaller.runAndReturnResultOnline(this.name);
        int[] dims = rcaller.getParser().getDimensions(this.name);
        return(dims);
    }
    
    public double[][] getData(){
        code.clearOnline();
        code.addRCode(this.HashString + "<- t(" + this.name +")");
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        return(rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]));
    }
    
    public double getDeterminant(){
        code.clearOnline();
        code.addRCode(this.HashString + " <- det("+ this.name +")");
        rcaller.runAndReturnResultOnline(this.HashString);
        double det = rcaller.getParser().getAsDoubleArray(this.HashString)[0];
        return(det);
    }
    
    public double[][] getInverse(){
        code.clearOnline();
        code.addRCode(this.HashString + " <- t(solve("+this.name+"))");
        rcaller.runAndReturnResultOnline(this.HashString);
        return(rcaller.getParser().getAsDoubleMatrix(this.HashString, 2, 2));
    }
    
    public double[] getDiagonal(){
        code.clearOnline();
        code.addRCode(this.HashString + " <- diag("+this.name+")");
        rcaller.runAndReturnResultOnline(this.HashString);
        return(rcaller.getParser().getAsDoubleArray(this.HashString));
    }
    
    public double getTrace(){
        code.clearOnline();
        code.addRCode(this.HashString + " <- sum(diag("+this.name+"))");
        rcaller.runAndReturnResultOnline(this.HashString);
        return(rcaller.getParser().getAsDoubleArray(this.HashString)[0]);
    }
    
    public double[][] getTranspose(){
        code.clearOnline();
        rcaller.runAndReturnResultOnline(this.name);
        int[] dims = rcaller.getParser().getDimensions(this.name);
        return(rcaller.getParser().getAsDoubleMatrix(this.name, dims[0], dims[1]));
    }
    
    public RealMatrix product(RealMatrix another){
        throw new RuntimeException("product not implemented yet");
    }
    
    public RealMatrix sum(RealMatrix another){
        throw new RuntimeException("sum not implemented yet");
    }
    
    public RealMatrix productWithScaler(double scaler){
        throw new RuntimeException("productWithScaler not implemented yet");
    }
    
    public RealMatrix subtract(RealMatrix another){
        throw new RuntimeException("subtract not implemented yet");
    }
    
}
