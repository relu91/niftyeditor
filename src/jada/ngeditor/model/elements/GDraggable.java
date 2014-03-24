/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.DRAGGABLE)
public class GDraggable extends GControl{

   
  
    

    public GDraggable(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new DraggableBuilder(id);
        this.name="draggable";
    }

    public GDraggable() {
        super();
    }
    

    @Override
    public GElement create(String id) {
        return new GDraggable(id);
    }

    @Override
    public void initDefault() {
       attributes.put("width", "100px");
       attributes.put("height", "100px");
       attributes.put("childLayout", "center");
       attributes.put("backgroundImage", "jada/ngeditor/resources/drag.png");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
