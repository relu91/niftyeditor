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

import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GDropDown extends GElement{
    static{
        GUIFactory.registerProduct(new GDropDown());
    }
    
    public GDropDown(){
        super();
    }
    
    private GDropDown(String id,org.w3c.dom.Element docElement){
        super(id,docElement);
        super.builder = new DropDownBuilder(id);
    }
    
    @Override
    public Types getType() {
        return Types.DROPDOWN;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GDropDown(id,ele);
    }

    @Override
    public void initDefault() {
      element.setAttribute("width", "100px");
      element.setAttribute("name", ""+Types.DROPDOWN);
    }
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a dropdown control");
    }
    
}
