/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.IIOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rcaller.exception.RCallerParseException;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class ROutputParser {

    File XMLFile;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public File getXMLFile() {
        return XMLFile;
    }
    
    public String getXMLFileAsString() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(XMLFile));
        long filesize = XMLFile.length();
        char[] chars = new char[(int)filesize];
        reader.read(chars);
        String result = new String(chars);
        return(result);
    }

    public void setXMLFile(File XMLFile) {
        this.XMLFile = XMLFile;
    }

    public void parse() throws RCallerParseException {
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            throw new RCallerParseException("Can not create parser builder: " + e.toString());
        }

        try {
            document = builder.parse(XMLFile);
        } catch (Exception e) {
            throw new RCallerParseException("Can not parse the R output: " + e.toString());
        }

        document.getDocumentElement().normalize();
    }

    public ROutputParser(File XMLFile) {
        this.XMLFile = XMLFile;
    }

    public ROutputParser() {
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        NodeList nodes = document.getElementsByTagName("variable");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            names.add(node.getAttributes().getNamedItem("name").getNodeValue());
        }
        return (names);
    }

    public NodeList getValueNodes(String name) {
        NodeList nodes = document.getElementsByTagName("variable");
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

    public String[] getAsStringArray(String name) throws RCallerParseException {
        NodeList nodes = getValueNodes(name);
        if (nodes == null) {
            throw new RCallerParseException("Variable " + name + " not found");
        }
        ArrayList<String> values = new ArrayList<String>();
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

    public double[] getAsDoubleArray(String name) throws RCallerParseException {
        String[] strResults = getAsStringArray(name);
        double[] d = new double[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                d[i] = Double.parseDouble(strResults[i]);
            } catch (Exception e) {
                throw new RCallerParseException("String value '" + strResults[i] + "' can not convert to double");
            }
        }
        return (d);
    }

    public float[] getAsFloatArray(String name) throws RCallerParseException {
        String[] strResults = getAsStringArray(name);
        float[] f = new float[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                f[i] = Float.parseFloat(strResults[i]);
            } catch (Exception e) {
                throw new RCallerParseException("String value '" + strResults[i] + "' can not convert to float");
            }
        }
        return (f);
    }

    public int[] getAsIntArray(String name) throws RCallerParseException {
        String[] strResults = getAsStringArray(name);
        int[] ints = new int[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                ints[i] = Integer.parseInt(strResults[i]);
            } catch (Exception e) {
                throw new RCallerParseException("String value '" + strResults[i] + "' can not convert to int");
            }
        }
        return (ints);
    }

    public long[] getAsLongArray(String name) throws RCallerParseException {
        String[] strResults = getAsStringArray(name);
        long[] longs = new long[strResults.length];
        for (int i = 0; i < strResults.length; i++) {
            try {
                longs[i] = Long.parseLong(strResults[i]);
            } catch (Exception e) {
                throw new RCallerParseException("String value '" + strResults[i] + "' can not convert to long");
            }
        }
        return (longs);
    }
}
