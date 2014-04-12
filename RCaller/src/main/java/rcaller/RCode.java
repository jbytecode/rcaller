package rcaller;

import graphics.GraphicsType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import rcaller.exception.ExecutionException;

public class RCode {

    protected StringBuffer code;

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
            InputStream is = this.getClass().getResourceAsStream("runiversal.r");
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
        } catch (Exception e) {
            throw new ExecutionException("runiversal.R in package: " + e.toString());
        }
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

    public void addDoubleMatrix(String name, double[][] matrix) {
        CodeUtils.addDoubleMatrix(code, name, matrix, false);
    }

    public File startPlot() throws IOException {
        return (startPlot(GraphicsType.png));
    }

    public File startPlot(GraphicsType type) throws IOException {
        File f = File.createTempFile("RPlot", "." + type.name());
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
