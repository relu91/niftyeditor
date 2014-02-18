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


import de.lessvoid.nifty.builder.ImageBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import java.util.Map;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GImage extends GElement{
      static{
        GUIFactory.registerProduct(new GImage());
    }
    
    /**
     * only for Factory use
     */
    private GImage(){
        super();
    }
    public GImage(String id,org.w3c.dom.Element docElement) throws IllegalArgumentException{
      super(id,docElement);
      if(!docElement.getTagName().equals(Types.IMAGE.toString()))
          throw new IllegalArgumentException("Illegal tag name");
       super.builder = new ImageBuilder(id);
      
    }
    @Override
    public Types getType() {
        return Types.IMAGE;
    }
    public Map<String,String> getAttributes(){
       Map<String,String> res = super.getAttributes();
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve("imageType")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
       return res;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GImage(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("filename", "jada/ngeditor/resources/noImage.png");
        element.setAttribute("width", "31%");
        element.setAttribute("height", "30%");
    }
    
}
