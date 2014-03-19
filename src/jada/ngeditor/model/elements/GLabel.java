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


import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;

/**
 *
 * @author cris
 */
public class GLabel extends GElement{
    static{
        GUIFactory.registerProduct(new GLabel());
    }
    
    /**
     * only for Factory use
     */
    private GLabel(){
        super();
    }
    public GLabel(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals("control"))
          throw new IllegalArgumentException("Illegal tag name");
      builder =  new LabelBuilder(id);
      
    }
    @Override
    public Types getType() {
        return Types.LABEL;
    }

    

    @Override
    public void initDefault() {
        element.setAttribute("name",""+Types.LABEL);
	element.setAttribute("text", element.getAttribute("id"));
	element.setAttribute("width", "100px");
	element.setAttribute("height", "50px");
        element.setAttribute("font","aurulent-sans-16.fnt");
    }

    @Override
    public GElement create(String id,org.w3c.dom.Element ele) {
        return new GLabel(id,ele);
    }
    
     @Override
    protected Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a label");
    }
    
      @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
