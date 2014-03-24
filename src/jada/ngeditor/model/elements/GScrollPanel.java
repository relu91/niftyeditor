 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
 @ControlBinding(name= XmlTags.SCROLLPANEL)
public class GScrollPanel extends GControl  {

    public GScrollPanel() {
    }
    
   
    
     public GScrollPanel(String id) throws IllegalArgumentException{
      super(id);
      builder = new ScrollPanelBuilder(id);
      name="scrollPanel";
      
    }

    @Override
    public GElement create(String id) {
       return new GScrollPanel(id);
    }

    @Override
    public void initDefault() {
     attributes.put("width", "50%");
     attributes.put("height", "30%");
     attributes.put("verticalScrollbar", "true");
     attributes.put("horizontalScrollbar", "true");
      
    }
    
     @Override
     protected de.lessvoid.nifty.elements.Element getDropContext(){
         
         return nElement.findElementById("#nifty-scrollpanel-child-root");
     }
     
      public String getAttribute(String key){
         if(key.equals("childLayout"))
             return "absolute";
         return super.getAttribute(key);
    }
      @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
