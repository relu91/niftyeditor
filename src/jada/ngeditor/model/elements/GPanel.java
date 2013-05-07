/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GPanel extends GElement{
    static{
         GUIFactory.registerProduct(new GPanel());
    }
  
    
    private GPanel(){
        super();
    }
    public GPanel(String id,org.w3c.dom.Element docElement){
      super(id,docElement);
      if(!docElement.getTagName().equals("panel"))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new PanelBuilder(id);
     
    }
    
    @Override
    public Types getType() {
        return Types.PANEL;
    }

    

    

    @Override
    public void initDefault() {
      element.setAttribute("width", "50%");
      element.setAttribute("height", "30%");
      element.setAttribute("childLayout", "absolute");
      element.setAttribute("style", "nifty-panel-simple");
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GPanel(id,ele);
    }
    
}
