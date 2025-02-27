package com.github.rcaller.io;

import com.github.rcaller.util.Globals;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class RSerializer {

    ByteArrayOutputStream byteOut;
    DataOutputStream dataOut;

    public RSerializer() {
        byteOut = new ByteArrayOutputStream();
        dataOut = new DataOutputStream(byteOut);
    }

    public void writeVector(double[] v) throws IOException {
        dataOut.write(getBytes("A"));
        /* version */
        dataOut.write(getBytes("2"));
        dataOut.write(getBytes("197214"));
        dataOut.write(getBytes("131840"));
        dataOut.write(getBytes("14"));
        /* Vector Type */
        dataOut.write(getBytes(v.length));
        for (int i = 0; i < v.length; i++) {
            dataOut.write(getBytes(v[i]));
        }
        dataOut.flush();
        byteOut.flush();
        dataOut.close();
        byteOut.close();
    }

    public void writeDoubleMatrix(double[][] d) throws IOException {
        dataOut.write(getBytes("A"));
        dataOut.write(getBytes("2"));
        dataOut.write(getBytes("197214"));
        dataOut.write(getBytes("131840"));
        dataOut.write(getBytes("525"));
        dataOut.write(getBytes(d.length * d[0].length));
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                dataOut.write(getBytes(String.valueOf(d[i][j])));
            }
        }
        dataOut.write(getBytes("1026"));
        dataOut.write(getBytes("1"));
        dataOut.write(getBytes("262153"));
        dataOut.write(getBytes("3"));
        dataOut.write(getBytes("dim"));
        dataOut.write(getBytes("13"));
        dataOut.write(getBytes("2"));
        dataOut.write(getBytes(d.length));
        dataOut.write(getBytes(d[0].length));
        dataOut.write(getBytes("254"));
        dataOut.flush();
        byteOut.flush();
        dataOut.close();
        byteOut.close();
    }

    byte[] getBytes(String s) {
        return ((s + "\n").getBytes(Globals.standardCharset));
    }

    byte[] getBytes(double d) {
        return ((String.valueOf(d) + "\n").getBytes(Globals.standardCharset));
    }

    public void save(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(byteOut.toString());
        writer.close();
    }
}
