/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

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
public class GVerticalScrollbar extends GScrollbar{
    static{
         GUIFactory.registerProduct(new GVerticalScrollbar());
    }
    
    public GVerticalScrollbar(){
        super();
    }
    
    public GVerticalScrollbar(String id, Element docElement) throws IllegalArgumentException {
        super(id, docElement, true);
        name="verticalScrollbar";
    }
    @Override
    public Types getType() {
        return Types.VERTICALSCROLLBAR;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GVerticalScrollbar(id,ele);
    }

    @Override
    public void initDefault() {
       attributes.put("height", "50%");
    }
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a scrollbar");
    }
    
      @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
