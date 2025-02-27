package com.github.rcaller.datatypes;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.rstuff.RService;
import com.github.rcaller.exception.ExecutionException;

public class RealMatrix {

    private RService service;
    private RCaller rcaller;
    private RCode code;
    private String name;
    private String HashString;

    public RCaller getRCaller(){
        return(this.rcaller);
    }
    
    public RealMatrix(RService service) {
        this.service = service;
        this.rcaller = service.getRCaller();
        code = this.service.getRCode();
        this.name = "MAT" + String.valueOf(Math.round(Math.random() * 100000));
        this.HashString = "RCALLER" + String.valueOf(this.hashCode());
        code.addRCode(this.HashString + " <- 3");
        rcaller.runAndReturnResultOnline(this.HashString);
    }

    public RealMatrix(String pathToR, String name) {
        service = new RService();
        rcaller = service.getRCaller();
        code = service.getRCode();
        this.name = name;
        this.HashString = "RCALLER" + String.valueOf(this.hashCode());
        code.addRCode(this.HashString + " <- 3");
        rcaller.runAndReturnResultOnline(this.HashString);
    }

    public void setData(double[][] data) {
        code.clearOnline();
        code.addDoubleMatrix(name, data);
        rcaller.runAndReturnResultOnline(this.HashString);
    }

    public int[] getDimensions() {
        code.clearOnline();
        rcaller.runAndReturnResultOnline(this.name);
        return (rcaller.getParser().getDimensions(this.name));
    }

    public double[][] getData() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- " + this.name);
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        return (rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]));
    }

    public double[] getColumn(int column) {
        if (column == 0) {
            throw new ExecutionException("getColumn: Column number can not be zero. R indices start from 1");
        }
        code.clearOnline();
        code.addRCode(this.HashString + "<- " + this.name + "[," + column + "]");
        rcaller.runAndReturnResultOnline(this.HashString);
        return (rcaller.getParser().getAsDoubleArray(this.HashString));
    }

    public double[][] getColumns(int[] columns) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] == 0) {
                throw new ExecutionException("getColumns: Column number can not be zero. R indices start from 1");
            }
        }
        code.clearOnline();
        code.addRCode(this.HashString + "<- t(" + this.name + "[,c(");
        for (int i = 0; i < columns.length; i++) {
            code.addRCode(String.valueOf(columns[i]));
            if (i != columns.length - 1) {
                code.addRCode(",");
            }
        }
        code.addRCode(")])");
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        return (rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]));
    }

    public double[] getRow(int row) {
        if (row == 0) {
            throw new ExecutionException("getRow: Row number can not be zero. R indices start from 1");
        }
        code.clearOnline();
        code.addRCode(this.HashString + "<- " + this.name + "[" + row + ",]");
        rcaller.runAndReturnResultOnline(this.HashString);
        return (rcaller.getParser().getAsDoubleArray(this.HashString));
    }

    public double[][] getRows(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            if (rows[i] == 0) {
                throw new ExecutionException("getRows: Row number can not be zero. R indices start from 1");
            }
        }
        code.clearOnline();
        code.addRCode(this.HashString + "<- " + this.name + "[c(");
        for (int i = 0; i < rows.length; i++) {
            code.addRCode(String.valueOf(rows[i]));
            if (i != rows.length - 1) {
                code.addRCode(",");
            }
        }
        code.addRCode("),]");
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        return (rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]));
    }

    public double getDeterminant() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- det(" + this.name + ")");
        rcaller.runAndReturnResultOnline(this.HashString);
        double det = rcaller.getParser().getAsDoubleArray(this.HashString)[0];
        return (det);
    }

    public RealMatrix getInverse() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- solve(" + this.name + ")");
        rcaller.runAndReturnResultOnline(this.HashString);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.HashString, 2, 2);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public double[] getDiagonal() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- diag(" + this.name + ")");
        rcaller.runAndReturnResultOnline(this.HashString);
        return (rcaller.getParser().getAsDoubleArray(this.HashString));
    }

    public double getTrace() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- sum(diag(" + this.name + "))");
        rcaller.runAndReturnResultOnline(this.HashString);
        return (rcaller.getParser().getAsDoubleArray(this.HashString)[0]);
    }

    public RealMatrix getTranspose() {
        code.clearOnline();
        rcaller.runAndReturnResultOnline(this.name);
        int[] dims = rcaller.getParser().getDimensions(this.name);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.name, dims[0], dims[1]);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public RealMatrix product(double[][] another) {
        String anotherHashString = "RCALLER" + String.valueOf(another.hashCode());
        code.clearOnline();
        code.addDoubleMatrix(anotherHashString, another);
        code.addRCode(this.HashString + " <- " + this.name + " %*% " + anotherHashString);
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public RealMatrix product(RealMatrix another) {
        return (this.product(another.getData()));
    }

    public RealMatrix sum(double[][] another) {
        String anotherHashString = "RCALLER" + String.valueOf(another.hashCode());
        code.clearOnline();
        code.addDoubleMatrix(anotherHashString, another);
        code.addRCode(this.HashString + " <- " + this.name + " + " + anotherHashString);
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public RealMatrix sum(RealMatrix another) {
        return (this.sum(another.getData()));
    }

    public RealMatrix productWithScaler(double scaler) {
        code.clearOnline();
        code.addRCode(this.HashString + " <- " + this.name + " * " + String.valueOf(scaler));
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public RealMatrix subtract(double[][] another) {
        String anotherHashString = "RCALLER" + String.valueOf(another.hashCode());
        code.clearOnline();
        code.addDoubleMatrix(anotherHashString, another);
        code.addRCode(this.HashString + " <- " + this.name + " - " + anotherHashString);
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        double[][] mat = rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]);
        RealMatrix matnew = new RealMatrix(service);
        matnew.setData(mat);
        return (matnew);
    }

    public RealMatrix subtract(RealMatrix another) {
        return (this.subtract(another.getData()));
    }

    public double[] getEigenValues() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- eigen(" + this.name + ")$values");
        rcaller.runAndReturnResultOnline(this.HashString);
        return (rcaller.getParser().getAsDoubleArray(this.HashString));
    }

    public RealMatrix getEigenVectors() {
        code.clearOnline();
        code.addRCode(this.HashString + " <- eigen(" + this.name + ")$vectors");
        rcaller.runAndReturnResultOnline(this.HashString);
        int[] dims = rcaller.getParser().getDimensions(this.HashString);
        double[][] result = rcaller.getParser().getAsDoubleMatrix(this.HashString, dims[0], dims[1]);
        RealMatrix newmat = new RealMatrix(service);
        newmat.setData(result);
        return (newmat);
    }
    
    public SVD getSVD(){
        SVD svd = new SVD();
        code.clearOnline();
        code.addRCode(this.HashString + " <- svd(" + this.name + ")");
        rcaller.runAndReturnResultOnline(this.HashString);
        double[] d = rcaller.getParser().getAsDoubleArray("d");
        double[][] u = rcaller.getParser().getAsDoubleMatrix("u");
        double[][] v = rcaller.getParser().getAsDoubleMatrix("v");
        
        RealMatrix uMatrix = new RealMatrix(service);
        RealMatrix vMatrix = new RealMatrix(service);
        
        svd.setD(d);
        
        uMatrix.setData(u);
        svd.setU(uMatrix);
        
        vMatrix.setData(v);
        svd.setV(vMatrix);
        
        return(svd);
    }

}
