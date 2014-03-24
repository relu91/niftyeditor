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


import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.CHECKBOX)
public class GCheckbox extends GControl {
  
    
    
    private GCheckbox(){
        super();
    }
    public GCheckbox(String id){
        super(id);
        super.builder = new CheckboxBuilder(id);
        this.name="checkbox";
    }
   

    @Override
    protected Element getDropContext() {
        throw new IllegalDropException("You can't add elements to a checkbox");
    }

    @Override
    public GElement create(String id) {
        return new GCheckbox(id);
    }

     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }

    @Override
    public void initDefault() {
       
    }
   
    
}
