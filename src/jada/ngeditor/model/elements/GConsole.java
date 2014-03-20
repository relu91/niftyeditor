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
import de.lessvoid.nifty.controls.console.ConsoleControl;
import de.lessvoid.nifty.elements.Action;
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
public class GConsole extends GElement{
    static{
        GUIFactory.registerProduct(new GConsole());
    }
    
    
    private GConsole(){
        super();
    }
    public GConsole(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      super.builder = new de.lessvoid.nifty.controls.console.builder.ConsoleBuilder(id);
      this.name="nifty-console";
    }
    @Override
    public Types getType() {
        return Types.NIFTYCONSOLE;
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        super.createNiftyElement(nifty);
        nElement.disable();
        nElement.getNiftyControl(Console.class).getTextField().getElement().disable();
    }
    
    @Override
    public GElement create(String id,Element ele) {
       return new GConsole(id,ele);
    }

    @Override
    public void initDefault() { 
    element.setAttribute("name", ""+Types.NIFTYCONSOLE);
     element.setAttribute("width", "50%");
      
    }
    @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a console");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
