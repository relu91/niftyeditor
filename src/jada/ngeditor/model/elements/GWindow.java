/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.window.WindowControl;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import de.lessvoid.xml.xpp3.Attributes;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author cris
 */
public class GWindow extends GElement{
      static{
         GUIFactory.registerProduct(new GWindow());
    }
      
      private GWindow(){
          super();
      }
      public GWindow(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new WindowBuilder();
      
      
    }
    @Override
    public Types getType() {
        return Types.WINDOW;
    }

    
     @Override
     protected de.lessvoid.nifty.elements.Element getDropContext(){
         return nElement.getControl(WindowControl.class).getContent();
     }
     
     @Override
     public void addAttribute(String key , String val){
        if(key.equals("id")){
            this.id = val;
        }
        if(key.equals("childLayout")){
            Attributes att = getDropContext().getElementType().getAttributes();
            att.set(key, val);
        }else{
        this.element.setAttribute(key, val);
        Attributes att = this.nElement.getElementType().getAttributes();
        att.set(key, val);
        }
    }
   
    @Override
    public GElement create(String id, Element ele) {
        return new GWindow(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", Types.WINDOW.toString());
        element.setAttribute("width", "50%");
        element.setAttribute("height", "30%");
        element.setAttribute("title", "window");
        
       
    }
    
}
