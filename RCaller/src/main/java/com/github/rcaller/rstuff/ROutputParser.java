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
package com.github.rcaller.rstuff;

import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.exception.ParseException;
import com.github.rcaller.exception.XMLParseException;
import com.github.rcaller.io.ArrowBridge;
import com.github.rcaller.io.ROutputParserArrow;
import com.github.rcaller.io.ROutputParserXML;
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
 * API for reading R IPC output.
 * @author Mehmet Hakan Satman
 */
public interface ROutputParser {

    /**
     * Static factory implementation creator with given options.
     * Returned implementation uses XML or Arrow IPC format.
     * @param rCallerOptions given options
     * @return ROutputParser implementing object
     */
    static ROutputParser create(RCallerOptions rCallerOptions) {
        if (rCallerOptions.useArrowIfAvailable()) {
            if (ArrowBridge.isArrowAvailable(rCallerOptions)) {
                //Use Arrow by default if enabled
                return new ROutputParserArrow(rCallerOptions);
            } else {
                if (rCallerOptions.failIfArrowNotAvailable()) {
                    throw new ExecutionException("Arrow is enabled but not available");
                } else {
                    //Use XML if Arrow is enabled but not available and we should not fail
                    return new ROutputParserXML();
                }
            }
        } else {
            //Use XML if Arrow is disabled
            return new ROutputParserXML();
        }
    }

    /**
     * Returns parsed XML document.
     * @deprecated Do not use if you are not using XML consciously
     * @return parsed XML document
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    Document getDocument();

    /**
     * Inits parsed XML document.
     * @deprecated Do not use if you are not using XML consciously
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    void setDocument(Document document);

    /**
     * Returns XML file.
     * @deprecated Do not use if you are not using XML consciously. Can be replaced with {@link #getIPCResource}
     * @return XML file
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    File getXMLFile();

    URI getIPCResource();

    /**
     * Returns raw XML document.
     * @deprecated Do not use if you are not using XML consciously
     * @return raw XML document
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    String getXMLFileAsString() throws IOException;

    /**
     * Inits XML file.
     * @deprecated Do not use if you are not using XML consciously. Can be replaced with {@link #setIPCResource}
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    void setXMLFile(File xmlFile);

    void setIPCResource(URI ipcResourceURI);

    void parse() throws ParseException;

    ArrayList<String> getNames();

    String getType(String variablename);

    int[] getDimensions(String name);

    /**
     * Returns nodes from parsed XML document.
     * @deprecated Do not use if you are not using XML consciously
     * @return nodes from parsed XML document
     * @throw NotImplementedException if Arrow implementation is used
     */
    @Deprecated
    NodeList getValueNodes(String name);

    String[] getAsStringArray(String name) throws ParseException;

    double[] getAsDoubleArray(String name) throws ParseException;

    float[] getAsFloatArray(String name) throws ParseException;

    int[] getAsIntArray(String name) throws ParseException;

    long[] getAsLongArray(String name) throws ParseException;

    default boolean[] getAsLogicalArray(String name) throws ParseException {
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

    double[][] getAsDoubleMatrix(String name, int n, int m) throws ParseException;

    double[][] getAsDoubleMatrix(String name) throws ParseException;

}
