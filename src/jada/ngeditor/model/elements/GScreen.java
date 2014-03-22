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
import javax.xml.bind.annotation.XmlRootElement;


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
    public GScreen(String id){
      super(id);
      ids++; 
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
         for(String sel : attributes.keySet()){
               nElement.getElementType().getAttributes().set(sel, attributes.get(sel));
         }
    }

    @Override
    public GElement create(String id) {
        
        GScreen te =new  GScreen(id);
       
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
