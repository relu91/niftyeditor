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


import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;


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
