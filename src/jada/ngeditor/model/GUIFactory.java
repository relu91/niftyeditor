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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;

/**
 * With this factory you can retrieve gui elements or a new GUI
 * @author cris
 */
public class GUIFactory {
    
    private static GUIFactory instance = null;
    private GUI gui;

    
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
    
    public GElement newGElement(java.lang.Class<? extends GElement> elementClass) throws NoProductException {
        try {
            Constructor<? extends GElement> constructor = elementClass.getConstructor(String.class);
            String id = IDgenerator.getInstance().generate(elementClass);
            GElement result = constructor.newInstance(id);
            result.initDefault();
            return result;
        } catch (Exception ex) {
              ex.printStackTrace();
              throw new NoProductException(elementClass.getName());
        }
          
    }   
}
