/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GLabel extends GElement{
    static{
        GUIFactory.registerProduct(new GLabel());
    }
    
    /**
     * only for Factory use
     */
    private GLabel(){
        super();
    }
    public GLabel(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      builder =  new LabelBuilder(id);
      
    }
    @Override
    public Types getType() {
        return Types.LABEL;
    }

    

    @Override
    public void initDefault() {
        element.setAttribute("name",""+Types.LABEL);
	element.setAttribute("text", element.getAttribute("id"));
	element.setAttribute("width", "100px");
	element.setAttribute("height", "50px");
        element.setAttribute("font","aurulent-sans-16.fnt");
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GLabel(id,ele);
    }
    
}
