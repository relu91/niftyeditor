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
import jada.ngeditor.model.utils.ClassUtils;
import jada.ngeditor.model.elements.GButton;
import jada.ngeditor.model.elements.GImage;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GPanel;
import jada.ngeditor.model.elements.GScreen;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cris
 */
public class GUIWriter {
    private final GUI gui;
    private final Marshaller m;
    
    public GUIWriter(GUI gui)throws JAXBException, ClassNotFoundException, IOException{
        this.gui = gui;
        Class[] classes = ClassUtils.getClasses("jada.ngeditor.model", new XmlClassPredicate());
        JAXBContext jc = JAXBContext.newInstance(classes);
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
        m.marshal(gui, System.out);
        m.marshal(gui, buffer);
        return new ByteArrayInputStream(buffer.toByteArray());
    }
     
    public static class XmlClassPredicate implements ClassUtils.Predicate<Class>{

        @Override
        public boolean apply(Class object) {
            Annotation annotation = object.getAnnotation(XmlRootElement.class);
            return annotation != null;
        }
        
    }
}
