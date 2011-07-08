/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.io.File;
import java.util.ArrayList;
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

    public void setXMLFile(File XMLFile) {
        this.XMLFile = XMLFile;
    }
    
    public void parse() throws RCallerParseException {
        factory = DocumentBuilderFactory.newInstance();
        try{
            builder = factory.newDocumentBuilder();
        }catch (Exception e){
            throw new RCallerParseException("Can not create parser builder: "+e.toString());
        }
        
        try{
        document = builder.parse(XMLFile);
        }catch (Exception e){
            throw new RCallerParseException("Can not parse the R output: "+e.toString());
        }
        
        document.getDocumentElement().normalize();
    }
    
    
    public ROutputParser(File XMLFile){
        this.XMLFile = XMLFile;
    }
    
    public ROutputParser(){
        
    }
    
    public NodeList getValueNodes(String name){
        NodeList nodes = document.getElementsByTagName("variable");
        NodeList content = null;
        for (int i=0;i<nodes.getLength();i++){
            Node node = nodes.item(i);
            if(node.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
               content = node.getChildNodes(); 
               break;
            }
        }
        return(content);
    }
    
    
    public double[] getAsDoubleArray(String name){
        System.out.println(getValueNodes(name));        
        return(null);
    }
    
    
}
