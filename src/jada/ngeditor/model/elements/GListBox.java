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

import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
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
      name="listBox";
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
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a list use your code");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
