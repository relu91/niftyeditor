/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioButtonBuilder;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GRadioButton extends GElement {
    static{
         GUIFactory.registerProduct(new GRadioButton());
    }
     private GRadioButton(){
          super();
      }
      public GRadioButton(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new RadioButtonBuilder();
      
      
    }
    @Override
    public Types getType() {
        return Types.RADIOBUTTON;
    }

   
        
    

    @Override
    public GElement create(String id, org.w3c.dom.Element ele) {
        return new GRadioButton(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", ""+Types.RADIOBUTTON);
    }

    
    
}
