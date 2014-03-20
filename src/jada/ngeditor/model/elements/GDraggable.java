/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
public class GDraggable extends GElement{
    static{
        GUIFactory.registerProduct(new GDraggable());
    }
    

    public GDraggable(String id, Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        this.builder = new DraggableBuilder(id);
        this.name="draggable";
    }

    public GDraggable() {
        super();
    }
    @Override
    public Types getType() {
        return Types.DRAGGABLE;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GDraggable(id,ele);
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
