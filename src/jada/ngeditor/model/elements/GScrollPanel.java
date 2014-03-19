 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
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
    
     public GScrollPanel(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new ScrollPanelBuilder(id);
      
      
    }
    @Override
    public Types getType() {
        return Types.SCROLLPANEL;
    }

    @Override
    public GElement create(String id, Element ele) {
       return new GScrollPanel(id,ele);
    }

    @Override
    public void initDefault() {
      element.setAttribute("width", "50%");
      element.setAttribute("name", ""+Types.SCROLLPANEL);
      element.setAttribute("height", "30%");
      element.setAttribute("verticalScrollbar", "true");
      element.setAttribute("horizontalScrollbar", "true");
      
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
