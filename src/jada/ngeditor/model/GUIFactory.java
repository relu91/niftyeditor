/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GUIFactory {
    
    private static HashMap<String,GElement> products;
    private static GUIFactory instance = null;
    private GUI gui;

    
    public static void registerProduct(GElement ele){
        if(products == null)
            products = new HashMap<String,GElement>();
        products.put(ele.getType().toString(), ele);
    }
    
    public static GUIFactory getInstance(){
        if(instance == null)
            instance= new GUIFactory();
        return instance;
    }
    
    public GUI createGUI(Nifty manager) throws ParserConfigurationException{
        gui = new GUI(manager);
        return gui;
    }
    
    public GUI createGUI(Nifty manager,Document doc){
        gui = new GUI(manager,doc);
        return gui;
    }
    
    public GElement newGElement(String tag){
        if(!products.containsKey(tag))
            throw new IllegalArgumentException("No product for tag : "+tag);
        
        Element temp ;
        
        Types type = Types.valueOf(Types.convert(tag));
        if(type.isControl())
            temp = gui.elementFactory(Types.CONTROL_TAG);
        else
            temp = gui.elementFactory(tag);
        String id = IDgenerator.getInstance().generate(type);
        GElement result = products.get(tag).create(id,temp);
        result.initDefault();
        return result;
    }
    
    public GElement createGElement(Element ele) throws NoProductException{
        String key;
        String tag = ele.getTagName();
        key=tag;
        if(tag.equals(Types.CONTROL_TAG))
            key = ele.getAttribute("name");
        if(!products.containsKey(key))
            throw new NoProductException(tag);
        String id = ele.getAttribute("id");
        GElement temp = products.get(key);
        
        Types t = temp.getType();
        if(IDgenerator.getInstance().isUnique(t, id))
            return products.get(key).create(id,ele);
        else
            return products.get(key).create(IDgenerator.getInstance().generate(t),ele);
    }
    
}
