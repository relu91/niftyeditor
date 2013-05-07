/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.HashMap;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GLayer extends GElement{
    static{
         GUIFactory.registerProduct(new GLayer());
    }
    
    
   
    
    private GLayer(){
        super();
    }
    public GLayer(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals(Types.LAYER.toString()))
          throw new IllegalArgumentException("Illegal tag name");
      builder= new LayerBuilder(id);
      
    }
    
    @Override
    public Types getType() {
        return Types.LAYER;
    }


    

    @Override
    public void initDefault() {
       element.setAttribute("childLayout", "absolute");
       
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
       return new GLayer(id,ele);
    }

   
    
}
