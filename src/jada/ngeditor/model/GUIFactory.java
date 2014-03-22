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
package jada.ngeditor.model;

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.model.utils.ClassUtils;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * With this factory you can retrieve gui elements or a new GUI
 * @author cris
 */
public class GUIFactory {
    
    private static HashMap<String,GElement> products;
    private static GUIFactory instance = null;
    private GUI gui;

    public GUIFactory(){
        try {
            Class [] res = ClassUtils.getClasses("jada.ngeditor.model.elements");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUIFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void registerProduct(GElement ele){
        if(products == null) {
            products = new HashMap<String,GElement>();
        }
        products.put(ele.getType().toString(), ele);
    }
    
    public static GUIFactory getInstance(){
        if(instance == null) {
            instance= new GUIFactory();
        }
        return instance;
    }
    
    public GUI createGUI(Nifty manager) throws ParserConfigurationException{
        gui = new GUI(manager);
        return gui;
    }
    
    public GElement newGElement(Types type){
        return this.newGElement(type.toString());
    }
    
    public GElement newGElement(String tag){
        if(!products.containsKey(tag))
            throw new IllegalArgumentException("No product for tag : "+tag);
        //Fixme remove element
        Element temp = null ;
        
        Types type = Types.valueOf(Types.convert(tag));
        String id = IDgenerator.getInstance().generate(type);
        GElement result = products.get(tag).create(id);
        result.initDefault();
        return result;
    }
    
    public GElement createGElement(Element ele) throws NoProductException{
        String key;
        String tag = ele.getTagName();
        key=tag;
        if(tag.equals(Types.CONTROL_TAG)) {
            key = ele.getAttribute("name");
        }
        if(!products.containsKey(key)) {
            throw new NoProductException(tag);
        }
        String id = ele.getAttribute("id");
        GElement temp = products.get(key);
        
        Types t = temp.getType();
        if(IDgenerator.getInstance().isUnique(t, id)) {
            IDgenerator.getInstance().addID(id, t);
            return products.get(key).create(id);
        }
        else {
            return products.get(key).create(IDgenerator.getInstance().generate(t));
        }
    }
    
}
