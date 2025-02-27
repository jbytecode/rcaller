package com.github.rcaller.io;

import com.github.rcaller.exception.ParseException;
import com.github.rcaller.exception.XMLParseException;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.rstuff.ROutputParser;
import org.apache.commons.lang3.NotImplementedException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;

/**
 *
 * @author Kopilov Aleksandr
 */
public class ROutputParserArrow implements ROutputParser {
    ArrowBridge bridge;
    URI ipcResourceURI;

    public ROutputParserArrow() {
        this.bridge = ArrowBridge.newInstance();
    }

    public ROutputParserArrow(RCallerOptions rCallerOptions) {
        this.bridge = ArrowBridge.newInstance(rCallerOptions);
    }

    @Override
    public Document getDocument() {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public void setDocument(Document document) {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public File getXMLFile() {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public URI getIPCResource() {
        return ipcResourceURI;
    }

    @Override
    public void setIPCResource(URI ipcResourceURI) {
        this.ipcResourceURI = ipcResourceURI;
    }

    @Override
    public String getXMLFileAsString() throws IOException {
        throw new IOException(new NotImplementedException("Not accessible in Arrow implementation"));
    }

    @Override
    public void setXMLFile(File xmlFile) {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public void parse() throws ParseException {
        try {
            bridge.loadArrowData(ipcResourceURI);
        } catch (IOException e) {
            throw new ParseException("Could not load Arrow data", e);
        }
    }

    @Override
    public ArrayList<String> getNames() {
        var names = bridge.getNames();
        if (names instanceof ArrayList) {
            return (ArrayList<String>) names;
        }
        var castedNames = new ArrayList<String>(names);
        return castedNames;
    }

    @Override
    public String getType(String variablename) {
        return bridge.getType(variablename);
    }

    @Override
    public int[] getDimensions(String name) {
        return bridge.getDimensions(name);
    }

    @Override
    public NodeList getValueNodes(String name) {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public String[] getAsStringArray(String name) throws ParseException {
        return bridge.getAsStringArray(name);
    }

    @Override
    public double[] getAsDoubleArray(String name) throws ParseException {
        return bridge.getAsDoubleArray(name);
    }

    @Override
    public float[] getAsFloatArray(String name) throws ParseException {
        return bridge.getAsFloatArray(name);
    }

    @Override
    public int[] getAsIntArray(String name) throws ParseException {
        return bridge.getAsIntArray(name);
    }

    @Override
    public long[] getAsLongArray(String name) throws ParseException {
        return bridge.getAsLongArray(name);
    }

    @Override
    public double[][] getAsDoubleMatrix(String name, int n, int m) throws ParseException {
        int[] dimensions = getDimensions(name);
        if (dimensions[0] == n && dimensions[1] == m) {
            return bridge.getAsDoubleMatrix(name);
        } else {
            double[][] result = new double[n][m];
            double[] arr = this.getAsDoubleArray(name);
            int c = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result[i][j] = arr[c];
                    c++;
                }
            }
            return (result);
        }
    }

    @Override
    public double[][] getAsDoubleMatrix(String name) throws ParseException {
        return bridge.getAsDoubleMatrix(name);
    }

}
