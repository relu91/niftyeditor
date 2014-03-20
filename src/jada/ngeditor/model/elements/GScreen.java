/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.screen.Screen;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Node;


/**
 *
 * @author cris
 */
@XmlRootElement(name="screen")
public class GScreen extends GElement{
    
    static {
        
         GUIFactory.registerProduct(new GScreen());
    }
    private static int ids=0;
    
    private GScreen() {
        super();
    }
    public GScreen(String id,org.w3c.dom.Element docElement){
      super(id,docElement);
      if(!docElement.getTagName().equals("screen"))
          throw new IllegalArgumentException("Illegal tag name");
      ids++; 
    }
    
   
   
    public org.w3c.dom.Element toXml() {
        return element;
    }

    @Override
    public Types getType() {
        return Types.SCREEN;
    }

  

    @Override
    public void initDefault() {
        
    }

    

    @Override
    public void createNiftyElement(Nifty nifty) {
        final HashMap<String,String> attributes = new HashMap<String,String>();
        for(int i =0;i<element.getAttributes().getLength();i++){
            Node n = element.getAttributes().item(i);
            attributes.put(n.getNodeName(),n.getNodeValue());
        }
        Screen screen = new ScreenBuilder(id){{
             
        }}.build(nifty);
        nElement = screen.getRootElement();
         for(String sel : attributes.keySet()){
               nElement.getElementType().getAttributes().set(sel, attributes.get(sel));
         }
        
    }

    @Override
    public void reloadElement(Nifty manger) {
         Nifty nif = manger;
        if(nElement != null)
            nif = nElement.getNifty();
        Collection<String> pe = nif.getAllScreensName();
        
        nElement = nif.getScreen(id).getRootElement();
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        
        GScreen te =new  GScreen(id,ele);
       
        return te;
    }

    /**
     *
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
    
    
    
}
