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
    URI ipcResourceURI;

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
        return null;
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
    public void setXMLFile(File XMLFile) {
        throw new NotImplementedException("Not accessible in Arrow implementation");
    }

    @Override
    public void parse() throws ParseException {

    }

    public ROutputParserArrow() {
    }

    @Override
    public ArrayList<String> getNames() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public String getType(String variablename) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int[] getDimensions(String name) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public NodeList getValueNodes(String name) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public String[] getAsStringArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public double[] getAsDoubleArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public float[] getAsFloatArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int[] getAsIntArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    public long[] getAsLongArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    public boolean[] getAsLogicalArray(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    public double[][] getAsDoubleMatrix(String name, int n, int m) throws ParseException {
        throw new NotImplementedException("TODO");
    }

    public double[][] getAsDoubleMatrix(String name) throws ParseException {
        throw new NotImplementedException("TODO");
    }

}
