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
import jada.ngeditor.model.visitor.Visitor;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cris
 */
@XmlRootElement(name="image")
public class GImage extends GElement{
  
    
    /**
     * only for Factory use
     */
    private GImage(){
        super();
    }
    public GImage(String id) throws IllegalArgumentException{
      super(id);
       super.builder = new ImageBuilder(id);
      
    }
    @Override
    public Map<String,String> listAttributes(){
       Map<String,String> res = super.listAttributes();
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve("imageType")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
       return res;
    }

    @Override
    public GElement create(String id) {
        return new GImage(id);
    }

    @Override
    public void initDefault() {
        attributes.put("filename", "jada/ngeditor/resources/noImage.png");
        attributes.put("width", "31%");
        attributes.put("height", "30%");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
