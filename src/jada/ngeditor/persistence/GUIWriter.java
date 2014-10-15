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
import jada.ngeditor.model.elements.GElement;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;

/**
 *
 * @author cris
 */
public class GUIWriter {
    private final GUI gui;
    private final Marshaller m;
    
    public GUIWriter(GUI gui)throws JAXBException, ClassNotFoundException, IOException{
        this.gui = gui;
        Reflections reflections = new Reflections ("jada.ngeditor.model",ClasspathHelper.forClass(GElement.class), 
                             new SubTypesScanner(false), new TypeAnnotationsScanner());
        Set<Class<?>> setXmlRoot = reflections.getTypesAnnotatedWith(XmlRootElement.class);
        Class[] classes = setXmlRoot.toArray(new Class[0]);
        
        JAXBContext jc = JAXBContext.newInstance("jada.ngeditor.model:jada.ngeditor.model.elements:jada.ngeditor.model.elements.specials:jada.ngeditor.model.elements.effects",this.getClass().getClassLoader());       
       
        m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://raw.githubusercontent.com/void256/nifty-gui/1.4/nifty-core/src/main/resources/nifty.xsd https://raw.githubusercontent.com/void256/nifty-gui/1.4/nifty-core/src/main/resources/nifty.xsd");
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
