/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.screen.Screen;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import org.w3c.dom.Node;


/**
 *
 * @author cris
 */
public class GScreen extends GElement{
    
    static {
        
         GUIFactory.registerProduct(new GScreen());
    }
    private static int ids=0;
    
    private GScreen() {
        super();
    }
    public GScreen(String id,org.w3c.dom.Element docElement){
      super(id,docElement);
      if(!docElement.getTagName().equals("screen"))
          throw new IllegalArgumentException("Illegal tag name");
      ids++; 
    }
    
   
   
    public org.w3c.dom.Element toXml() {
        return element;
    }

    @Override
    public Types getType() {
        return Types.SCREEN;
    }

  

    @Override
    public void initDefault() {
        
    }

    

    @Override
    public void createNiftyElement(Nifty nifty) {
        final HashMap<String,String> attributes = new HashMap<String,String>();
        for(int i =0;i<element.getAttributes().getLength();i++){
            Node n = element.getAttributes().item(i);
            attributes.put(n.getNodeName(),n.getNodeValue());
        }
        Screen screen = new ScreenBuilder(id){{
             
        }}.build(nifty);
        nElement = screen.getRootElement();
         for(String sel : attributes.keySet()){
               nElement.getElementType().getAttributes().set(sel, attributes.get(sel));
         }
        
    }

    @Override
    public void reloadElement(Nifty manger) {
         Nifty nif = manger;
        if(nElement != null)
            nif = nElement.getNifty();
        Collection<String> pe = nif.getAllScreensName();
        
        nElement = nif.getScreen(id).getRootElement();
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        
        GScreen te =new  GScreen(id,ele);
       
        return te;
    }
    
}
