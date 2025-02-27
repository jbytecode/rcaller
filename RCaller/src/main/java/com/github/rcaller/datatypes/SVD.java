package com.github.rcaller.datatypes;

public class SVD {

    private double[] d;
    private RealMatrix u;
    private RealMatrix v;

    public double[] getD() {
        return d;
    }

    public void setD(double[] d) {
        this.d = d;
    }

    public RealMatrix getU() {
        return u;
    }

    public void setU(RealMatrix u) {
        this.u = u;
    }

    public RealMatrix getV() {
        return v;
    }

    public void setV(RealMatrix v) {
        this.v = v;
    }

}
