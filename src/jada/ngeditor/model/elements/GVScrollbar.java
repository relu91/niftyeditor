/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.VERTICALSCROLLBAR)
public class GVScrollbar extends GScrollbar{

    public GVScrollbar() {
    }
  
  
    
    public GVScrollbar(String id) throws IllegalArgumentException {
        super(id,  true);
        name="verticalScrollbar";
    }
 
    @Override
    public GElement create(String id) {
        return new GVScrollbar(id);
    }

    @Override
    public void initDefault() {
       attributes.put("height", "50%");
    }
    
     @Override
    public de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a scrollbar");
    }
    
      @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
