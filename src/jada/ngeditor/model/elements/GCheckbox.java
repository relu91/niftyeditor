/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GCheckbox extends GElement{
     static{
        GUIFactory.registerProduct(new GCheckbox());
    }
    
    
    private GCheckbox(){
        super();
    }
    public GCheckbox(String id,org.w3c.dom.Element docElement){
        super(id,docElement);
         if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
         super.builder = new CheckboxBuilder(id);
        
    }
    @Override
    public Types getType() {
        return Types.CHECKBOX;
    }

    

    @Override
    public void initDefault() {
         element.setAttribute("name",Types.CHECKBOX.toString());
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GCheckbox(id,ele);
    }

   
    
}
