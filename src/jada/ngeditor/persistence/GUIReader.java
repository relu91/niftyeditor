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

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.IDgenerator;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author cris
 */
public class GUIReader {
    private Nifty loader;
    private Set<String> errors;
    
    public GUIReader (Nifty loader){
        this.loader=loader;
        this.errors=new HashSet<String>();
    }
    
    public GUI readGUI(File f) throws ParserConfigurationException, IOException, SAXException, NoProductException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(f);
            document.getDocumentElement().normalize();
            GUI result = GUIFactory.getInstance().createGUI(loader,document);
            IDgenerator.getInstance().invalidate();
            Element root = (Element) document.getElementsByTagName("nifty").item(0);
            NodeList screens = document.getElementsByTagName("screen");
            for (int i = 0; i < screens.getLength(); i++) {
            if (screens.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element screen = (Element) screens.item(i);
                System.out.println("id "+screen.getAttribute("id"));
                try {
                    this.addRecursiveChild(screen, null, result);
                } catch (NoProductException ex) {
                    this.errors.add(ex.getProduct());
                    continue;
                }
            }
        }
        return result;
    }
    
    
    private void addRecursiveChild(Element element,GElement parent,GUI gui) throws NoProductException{
            String id = element.getAttribute("id");
            System.out.print("before "+id);
            GElement Gchild =GUIFactory.getInstance().createGElement(element);
            id= element.getAttribute("id");
            System.out.println(" after "+id);
            gui.addElementToParent(Gchild, parent);
            NodeList child = element.getChildNodes();
            
            for(int i=0;i<child.getLength();i++){
               if(child.item(i).getNodeType() == Node.ELEMENT_NODE){
               Element cur = (Element) child.item(i);
               try{
                addRecursiveChild(cur,Gchild,gui);
               }catch(NoProductException e){
                   this.errors.add(e.getProduct());
                   continue;
               }
               }
            }
            
        
    }
    /**
     * Provide all tag not loaded by the editor
     * @return a string with all the tag
     */
    public String getTagNotLoaded(){
        String res="";
        for(String sel : this.errors){
            res+=sel+", ";
        }
        return res.substring(0, res.length()>0 ? res.length()-1 :0);
    }
    
}
