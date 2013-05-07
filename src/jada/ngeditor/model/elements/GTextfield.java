/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GTextfield extends GElement{
    static{
         GUIFactory.registerProduct(new GTextfield());
    }

    
    
    private GTextfield() {
        super();
    }
    public GTextfield(String id,org.w3c.dom.Element docElement){
        super(id,docElement);
         if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
        builder = new TextFieldBuilder(id);
    }
    @Override
    public Types getType() {
        return Types.TEXTFIELD;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        nElement.disable();
       
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", ""+Types.TEXTFIELD);
        element.setAttribute("width", "50%");
       
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GTextfield(id,ele);
    }

    
}
