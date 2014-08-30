package jada.ngeditor.model.elements;

import de.lessvoid.nifty.builder.ControlBuilder;

public class GCostumControl extends GControl{

    public GCostumControl(String name,String id) throws IllegalArgumentException {
        super(id);
        this.builder = new ControlBuilder(id,name);
        this.name = name;
        
    }
    
    
    
} 