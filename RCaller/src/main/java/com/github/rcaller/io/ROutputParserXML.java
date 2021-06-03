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
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */
package com.github.rcaller.io;

import com.github.rcaller.exception.ParseException;
import com.github.rcaller.exception.XMLParseException;
import com.github.rcaller.rstuff.ROutputParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class ROutputParserXML implements ROutputParser {

    protected File xmlFile;
    protected DocumentBuilderFactory factory;
    protected DocumentBuilder builder;
    protected Document document;
    final private String variable_tag_name = "variable";

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public File getXMLFile() {
        return xmlFile;
    }

    @Override
    public URI getIPCResource() {
        return xmlFile.toURI();
    }

    @Override
    public void setIPCResource(URI ipcResourceURI) {
        xmlFile = new File(ipcResourceURI);
    }

    @Override
    public String getXMLFileAsString() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
        long filesize = xmlFile.length();
        char[] chars = new char[(int) filesize];
        reader.read(chars);
        return (new String(chars));
    }

    @Override
    public void setXMLFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public void parse() throws ParseException {
        if (this.xmlFile.length() == 0) {
            throw new ParseException("Can not parse output: The generated file " + this.xmlFile.toString() + " is empty");
        }
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ParseException("Can not create parser builder", e);
        }

        try (InputStream in = Channels.newInputStream(FileChannel.open(xmlFile.toPath()))){
            InputSource is = new InputSource(in);
            is.setEncoding("UTF-8");
            document = builder.parse(is);
        } catch (Exception e) {
            StackTraceElement[] frames = e.getStackTrace();
            String msgE = "";
            for (StackTraceElement frame : frames) {
                msgE += frame.getClassName() + "-" + frame.getMethodName() + "-" + String.valueOf(frame.getLineNumber());
            }
            System.out.println(e + msgE);
            throw new XMLParseException("Can not parse the R output: " + e.toString());
        }

        document.getDocumentElement().normalize();
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        NodeList nodes = document.getElementsByTagName(variable_tag_name);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            names.add(node.getAttributes().getNamedItem("name").getNodeValue());
        }
        return (names);
    }

    @Override
    public String getType(String variablename) {
        NodeList nodes = document.getElementsByTagName(variable_tag_name);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getAttributes().getNamedItem("name").getNodeValue().equals(variablename)) {
                return (node.getAttributes().getNamedItem("type").getNodeValue());
            }
        }
        return (null);
    }

    @Override
    public int[] getDimensions(String name) {
        int[] result = new int[2];
        int n = 0, m = 0;
        NodeList nodes = document.getElementsByTagName(variable_tag_name);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getAttributes().getNamedItem("name").getNodeValue().equals(name)) {
                String sn = node.getAttributes().getNamedItem("n").getNodeValue();
                String sm = node.getAttributes().getNamedItem("m").getNodeValue();
                n = Integer.parseInt(sn);
                m = Integer.parseInt(sm);
                break;
            }
        }
        result[0] = n;
        result[1] = m;
        return (result);
    }

    @Override
    public NodeList getValueNodes(String name) {
        NodeList nodes = document.getElementsByTagName(variable_tag_name);
        NodeList content = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getAttributes().getNamedItem("name").getNodeValue().equals(name)) {
                content = node.getChildNodes();
                break;
            }
        }
        return (content);
    }

    @Override
    public String[] getAsStringArray(String name) throws ParseException {
        NodeList nodes = getValueNodes(name);
        if (nodes == null) {
            throw new ParseException("Variable " + name + " not found");
        }
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                values.add(node.getTextContent());
            }
        }
        String[] result = new String[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return (result);
    }

    @Override
    public double[] getAsDoubleArray(String name) throws ParseException {
        String[] strResults = getAsStringArray(name);
        double[] d = new double[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                d[i] = Double.parseDouble(strResults[i]);
            } catch (NumberFormatException e) {
                throw new ParseException("String value '" + strResults[i] + "' can not convert to double");
            }
        }
        return (d);
    }

    @Override
    public float[] getAsFloatArray(String name) throws ParseException {
        String[] strResults = getAsStringArray(name);
        float[] f = new float[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                f[i] = Float.parseFloat(strResults[i]);
            } catch (NumberFormatException e) {
                throw new ParseException("String value '" + strResults[i] + "' can not convert to float");
            }
        }
        return (f);
    }

    @Override
    public int[] getAsIntArray(String name) throws ParseException {
        String[] strResults = getAsStringArray(name);
        int[] ints = new int[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                ints[i] = Integer.parseInt(strResults[i]);
            } catch (NumberFormatException e) {
                throw new ParseException("String value '" + strResults[i] + "' can not convert to int");
            }
        }
        return (ints);
    }

    @Override
    public long[] getAsLongArray(String name) throws ParseException {
        String[] strResults = getAsStringArray(name);
        long[] longs = new long[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                longs[i] = Long.parseLong(strResults[i]);
            } catch (NumberFormatException e) {
                throw new ParseException("String value '" + strResults[i] + "' can not convert to long");
            }
        }
        return (longs);
    }

    @Override
    public double[][] getAsDoubleMatrix(String name, int n, int m) throws ParseException {
        double[][] result = new double[n][m];
        double[] arr = this.getAsDoubleArray(name);
        int c = 0;
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                result[i][j] = arr[c];
                c++;
            }
        }
        return (result);
    }

    @Override
    public double[][] getAsDoubleMatrix(String name) throws ParseException {
        int[] dims = this.getDimensions(name);
        return (this.getAsDoubleMatrix(name, dims[0], dims[1]));
    }

}
