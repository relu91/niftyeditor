/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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
