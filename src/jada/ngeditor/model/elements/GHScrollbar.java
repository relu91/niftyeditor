/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.HORIZONTALSCROLLBAR)
public class GHScrollbar extends GScrollbar{

    
    public GHScrollbar(String id){
        super(id,false);
        this.name="horizontalScrollbar";
    }

    private GHScrollbar() {
        super();
    }
  

    @Override
    public GElement create(String id) {
        return new GHScrollbar(id);
    }

    @Override
    public void initDefault() {
         attributes.put("width", "50%");
    }
    
     @Override
    public de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a scroolbar");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
