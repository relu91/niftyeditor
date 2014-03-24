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
public class GHorizontalScrollbar extends GScrollbar{

    
    public GHorizontalScrollbar(String id){
        super(id,false);
        this.name="horizontalScrollbar";
    }

    private GHorizontalScrollbar() {
        super();
    }
  

    @Override
    public GElement create(String id) {
        return new GHorizontalScrollbar(id);
    }

    @Override
    public void initDefault() {
         attributes.put("width", "50%");
    }
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a scroolbar");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
