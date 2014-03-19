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
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;

/**
 *
 * @author cris
 */
public class GTextfield extends GElement{
    static{
         GUIFactory.registerProduct(new GTextfield());
    }

    
    
    private GTextfield() {
        super();
    }
    public GTextfield(String id,org.w3c.dom.Element docElement){
        super(id,docElement);
         if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
        builder = new TextFieldBuilder(id);
    }
    @Override
    public Types getType() {
        return Types.TEXTFIELD;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        nElement.disable();
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", ""+Types.TEXTFIELD);
        element.setAttribute("width", "50%");
       
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GTextfield(id,ele);
    }
    
     @Override
    protected Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a textfield");
    }
     
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
    
}
