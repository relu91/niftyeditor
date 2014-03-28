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
import de.lessvoid.nifty.controls.Console;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name=XmlTags.NIFTYCONSOLE)
public class GConsole extends GControl {
   
    
    
    private GConsole(){
        super();
    }
    public GConsole(String id) throws IllegalArgumentException{
      super(id);
      super.builder = new de.lessvoid.nifty.controls.console.builder.ConsoleBuilder(id);
      this.name="nifty-console";
    }
   

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        nElement.disable();
        nElement.getNiftyControl(Console.class).getTextField().getElement().disable();
    }
    
    @Override
    public GElement create(String id) {
       return new GConsole(id);
    }

    @Override
    public void initDefault() { 
        attributes.put("width", "50%");
    }
    @Override
    public de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a console");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
