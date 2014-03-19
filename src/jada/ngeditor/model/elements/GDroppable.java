/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GDroppable extends GElement {
static{
        GUIFactory.registerProduct(new GDroppable());
    }
    

    public GDroppable(String id, Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        this.builder = new DroppableBuilder(id);
    }

    private GDroppable() {
        super();
    }
    @Override
    public Types getType() {
        return Types.DROPPABLE;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GDroppable(id,ele);
    }

    @Override
    public void initDefault() {
        element.setAttribute("name", ""+Types.DROPPABLE);
       element.setAttribute("width", "100px");
       element.setAttribute("height", "100px");
       element.setAttribute("childLayout", "center");
        element.setAttribute("backgroundImage", "jada/ngeditor/resources/drop.png");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
