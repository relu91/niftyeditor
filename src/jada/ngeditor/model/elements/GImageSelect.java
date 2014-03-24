/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.imageselect.builder.ImageSelectBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.IMAGESELECT)
public class GImageSelect extends GControl {
 
    public GImageSelect() {
        super();
    }

    public GImageSelect(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new ImageSelectBuilder(id);
        this.name="imageSelect";
    }

  

    @Override
    public GElement create(String id) {
        return new GImageSelect(id);
    }

    @Override
    public void initDefault() {
       attributes.put("name", XmlTags.IMAGESELECT.toString());
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
