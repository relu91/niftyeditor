/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.persistence;

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.guiviews.MainView;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
               GUI result = GUIFactory.getInstance().createGUI(loader,document);
            Element root = (Element) document.getElementsByTagName("nifty").item(0);
            NodeList screens = root.getElementsByTagName(Types.SCREEN.toString());
            for(int i =0;i<screens.getLength();i++){
                Element screen = (Element) screens.item(i);
            try {
                this.addRecursiveChild(screen, null, result);
            } catch (NoProductException ex) {
                this.errors.add(ex.getProduct());
                continue;
            }
            }
        return result;
    }
    
    
    private void addRecursiveChild(Element element,GElement parent,GUI gui) throws NoProductException{
            
            GElement Gchild =GUIFactory.getInstance().createGElement(element);
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
    
    public String getTagNotLoaded(){
        String res="";
        for(String sel : this.errors){
            res+=sel+", ";
        }
        return res.substring(0, res.length()>0 ? res.length()-1 :0);
    }
    
}
