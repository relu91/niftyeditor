/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.persistence;

import jada.ngeditor.model.GUI;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author cris
 */
public class GUIWriter {
    private final GUI gui;
    
    public GUIWriter(GUI gui){
        this.gui = gui;
    }
    public void writeGUI(File f) throws FileNotFoundException{
        InputStream in = null;
        try{
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            in = this.getDocumentStream();
            for(int i = in.read(); i != -1; i = in.read()){
                out.write(i);
                System.out.write(i);
            }
	    out.flush();
            out.close();
	} 
        catch (Exception ex) {
            Logger.getLogger(GUIWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(GUIWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        
    }
    
    public void writeGUI(String filename) throws FileNotFoundException
    {
        writeGUI(new File(filename));
    }
     public InputStream getDocumentStream() throws Exception {
       
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	DOMSource source = gui.getSource();
      
	
        TransformerFactory fact = TransformerFactory.newInstance();
        fact.setAttribute("indent-number", ""+2);
        Transformer transformer = fact.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //Wrap the out cause a traformer bug
        StreamResult res = new StreamResult(new OutputStreamWriter(buffer, "utf-8"));
        transformer.transform(source, res);
       	return new ByteArrayInputStream(buffer.toByteArray());
    }
}
