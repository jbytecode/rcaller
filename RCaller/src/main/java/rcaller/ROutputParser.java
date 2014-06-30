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

package rcaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rcaller.exception.ParseException;
import rcaller.exception.XMLParseException;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class ROutputParser {

  protected File XMLFile;
  protected DocumentBuilderFactory factory;
  protected DocumentBuilder builder;
  protected Document document;
  final private String variable_tag_name = "variable";

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  public File getXMLFile() {
    return XMLFile;
  }

  public String getXMLFileAsString() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(XMLFile));
    long filesize = XMLFile.length();
    char[] chars = new char[(int) filesize];
    reader.read(chars);
    String result = new String(chars);
    return (result);
  }

  public void setXMLFile(File XMLFile) {
    this.XMLFile = XMLFile;
  }

  public void parse() throws ParseException {
	if(this.XMLFile.length() == 0){
		throw new ParseException("Can not parse output: The generated file "+this.XMLFile.toString()+" is empty");
	}
    factory = DocumentBuilderFactory.newInstance();
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new ParseException("Can not create parser builder: " + e.toString());
    }

    try {
      document = builder.parse(XMLFile);
    } catch (Exception e) {
      throw new XMLParseException("Can not parse the R output: " + e.toString());
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
    NodeList nodes = document.getElementsByTagName(variable_tag_name);
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      names.add(node.getAttributes().getNamedItem("name").getNodeValue());
    }
    return (names);
  }
  
  public int[] getDimensions(String name) {
    int[] result = new int[2];
    int n =0, m=0;
    NodeList nodes = document.getElementsByTagName(variable_tag_name);
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
        String sn = node.getAttributes().getNamedItem("n").getNodeValue();
        String sm = node.getAttributes().getNamedItem("m").getNodeValue();
        n = Integer.parseInt(sn);
        m = Integer.parseInt(sm);
        break;
      }
    }
    result[0] = n; result[1] = m;
    return (result);
  }

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

  public String[] getAsStringArray(String name) throws ParseException {
    NodeList nodes = getValueNodes(name);
    if (nodes == null) {
      throw new ParseException("Variable " + name + " not found");
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

  public boolean[] getAsLogicalArray(String name) throws ParseException {
    String[] strResults = getAsStringArray(name);
    boolean[] bools = new boolean[strResults.length];
    for (int i = 0; i < strResults.length; i++) {
      try {
        bools[i] = Boolean.parseBoolean(strResults[i]);
      } catch (Exception e) {
        throw new ParseException("String value '" + strResults[i] + "' can not convert to boolean");
      }
    }
    return (bools);
  }
  
  public double[][] getAsDoubleMatrix(String name, int n, int m) throws ParseException {
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
  
  public double[][] getAsDoubleMatrix(String name) throws ParseException {
    int[] dims = this.getDimensions(name);
    return (this.getAsDoubleMatrix(name, dims[0], dims[1]));
  }
  
}
