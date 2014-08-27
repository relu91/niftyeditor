package jada.ngeditor.persistence;

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.model.elements.GElement;
import java.awt.Rectangle;
import java.util.HashMap;

public class UnknowElement extends GElement{

    
    public UnknowElement(String id){
        super(id);
        
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle();
    }

    @Override
    public void createNiftyElement(Nifty nifty) {
        
    }

    public HashMap<String, String> getAttributes() {
        return new HashMap<String, String>();
    }

    
    
    @Override
    public void initDefault() {
        
    }




}