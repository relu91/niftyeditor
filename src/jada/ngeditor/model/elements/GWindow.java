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


import de.lessvoid.nifty.controls.window.WindowControl;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import de.lessvoid.xml.xpp3.Attributes;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 * 
 * @author cris
 */
@ControlBinding(name= XmlTags.WINDOW)
public class GWindow extends GControl {

    public GWindow() {
    }
    
      public GWindow(String id) throws IllegalArgumentException{
      super(id);
      builder = new WindowBuilder(id,"window");
      name = "window";
      
      
    }

     @Override
     public de.lessvoid.nifty.elements.Element getDropContext(){
         return nElement.getControl(WindowControl.class).getContent();
     }
     
    
   
    @Override
    public GElement create(String id) {
        return new GWindow(id);
    }

    @Override
    public void initDefault() {
        attributes.put("width", "50%");
        attributes.put("height", "30%");
        attributes.put("title", "window");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
