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
import jada.ngeditor.model.elements.GImage;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GPanel;
import jada.ngeditor.model.elements.GScreen;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
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
    private final Marshaller m;
    
    public GUIWriter(GUI gui)throws JAXBException{
        this.gui = gui;
        JAXBContext jc = JAXBContext.newInstance(GUI.class,GScreen.class,GLayer.class,GPanel.class,GImage.class);
        m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }
    public void writeGUI(String filename) throws FileNotFoundException, JAXBException 
    {
        File out = new File(filename);
        m.marshal(gui, System.out);
        m.marshal(gui, out);
    }
     public InputStream getDocumentStream() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        m.marshal(gui, buffer);
        return new ByteArrayInputStream(buffer.toByteArray());
    }
}
