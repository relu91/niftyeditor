/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class GImage extends GElement{
      static{
        GUIFactory.registerProduct(new GImage());
    }
    
    /**
     * only for Factory use
     */
    private GImage(){
        super();
    }
    public GImage(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals(Types.IMAGE.toString()))
          throw new IllegalArgumentException("Illegal tag name");
       super.builder = new ImageBuilder(id);
      
    }
    @Override
    public Types getType() {
        return Types.IMAGE;
    }
    public Map<String,String> getAttributes(){
       Map<String,String> res = super.getAttributes();
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve("imageType")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
       return res;
    }
    
    
    
    @Override
    public GElement create(String id, Element ele) {
        return new GImage(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("filename", "jada/ngeditor/resources/noImage.png");
        element.setAttribute("width", "31%");
        element.setAttribute("height", "30%");
    }
    
}
