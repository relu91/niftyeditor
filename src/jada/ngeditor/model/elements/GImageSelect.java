/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.imageselect.builder.ImageSelectBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
public class GImageSelect extends GElement{
    static{
         GUIFactory.registerProduct(new GImageSelect());
    }
    public GImageSelect() {
        super();
    }

    public GImageSelect(String id, Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        this.builder = new ImageSelectBuilder(id);
        this.name="imageSelect";
    }

    @Override
    public Types getType() {
        return Types.IMAGESELECT;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GImageSelect(id,ele);
    }

    @Override
    public void initDefault() {
       attributes.put("name", Types.IMAGESELECT.toString());
      attributes.put("width", "100px");
      attributes.put("height", "100px");
    }
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a imageselect");
    }
    
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
    
}
