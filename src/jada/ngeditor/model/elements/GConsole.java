/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.console.ConsoleControl;
import de.lessvoid.nifty.controls.console.builder.ConsoleBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GConsole extends GElement{
    static{
        GUIFactory.registerProduct(new GConsole());
    }
    
    
    private GConsole(){
        super();
    }
    public GConsole(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      super.builder = new de.lessvoid.nifty.controls.console.builder.ConsoleBuilder(id);
    
    }
    @Override
    public Types getType() {
        return Types.NIFTYCONSOLE;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        nElement.disable();
       
    }
    
  
    @Override
     public void reloadElement(Nifty manager){
         super.reloadElement(manager);
         nElement.disable();
         nElement.layoutElements();
         
     }
    @Override
    public GElement create(String id,Element ele) {
       return new GConsole(id,ele);
    }

    @Override
    public void initDefault() { 
    element.setAttribute("name", ""+Types.NIFTYCONSOLE);
     element.setAttribute("width", "50%");
      
    }
    
}
