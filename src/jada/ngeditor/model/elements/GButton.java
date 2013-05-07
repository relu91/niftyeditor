/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.ButtonControl;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GButton extends GElement{
    static{
        GUIFactory.registerProduct(new GButton());
    }
   
    
    private GButton(){
        super();
    }
    public GButton(String id ,org.w3c.dom.Element docElement){
        super(id,docElement);
         if(!docElement.getTagName().equals(Types.CONTROL_TAG))
          throw new IllegalArgumentException("Illegal tag name");
         this.builder = new ButtonBuilder(this.getID());
        
    }
    @Override
    public Types getType() {
        return Types.BUTTON;
    }
    
    @Override
    public void initDefault() {
        element.setAttribute("name", Types.BUTTON.toString());
        element.setAttribute("childLayout", "center");
        element.setAttribute("label", this.getID());
        
    }

    @Override
    public GElement create(String id, org.w3c.dom.Element ele) {
        return new GButton(id,ele);
    }
    
}
