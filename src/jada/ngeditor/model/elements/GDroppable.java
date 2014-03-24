/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.DROPPABLE)
public class GDroppable extends GControl  {

    

    public GDroppable(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new DroppableBuilder(id);
        this.name = "droppable";
    }

    private GDroppable() {
        super();
    }
   

    @Override
    public GElement create(String id) {
        return new GDroppable(id);
    }

    @Override
    public void initDefault() {
       
       attributes.put("width", "100px");
       attributes.put("height", "100px");
       attributes.put("childLayout", "center");
       attributes.put("backgroundImage", "jada/ngeditor/resources/drop.png");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
