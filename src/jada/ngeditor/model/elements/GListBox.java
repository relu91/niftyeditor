/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GListBox extends GElement{
     static{
         GUIFactory.registerProduct(new GListBox());
     }
    public GListBox(){
        super();
    }
    public GListBox(String id,org.w3c.dom.Element docElement){
      super(id,docElement);
      if(!docElement.getTagName().equals(Types.CONTROL_TAG))
          throw new IllegalArgumentException("Illegal tag name");
      builder = new ListBoxBuilder(id);
      
    }
    @Override
    public Types getType() {
       return Types.LISTBOX;
    }

   

    @Override
    public GElement create(String id, Element ele) {
        return new GListBox(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", "listBox");
        element.setAttribute("width", "50%");
    }
    
}
