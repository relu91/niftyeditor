 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
public class GScrollPanel extends GElement {
    
     static{
        GUIFactory.registerProduct(new GScrollPanel());
    }
    
    /**
     * only for Factory use
     */
    private GScrollPanel(){
        super();
    }
    
     public GScrollPanel(String id) throws IllegalArgumentException{
      super(id);
      builder = new ScrollPanelBuilder(id);
      name="scrollPanel";
      
    }
    @Override
    public Types getType() {
        return Types.SCROLLPANEL;
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
