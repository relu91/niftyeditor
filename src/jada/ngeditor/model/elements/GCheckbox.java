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
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
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
        this.name="checkbox";
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

     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
   
    
}
