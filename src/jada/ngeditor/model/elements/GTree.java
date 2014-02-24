/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.treebox.builder.TreeBoxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.exception.IllegalDropException;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public class GTree extends GElement{
static{
         GUIFactory.registerProduct(new GTree());
    }

    public GTree(String id, Element docElement) throws IllegalArgumentException {
        super(id, docElement);
        this.builder = new TreeBoxBuilder(id);
    }

    public GTree() {
        super();
    }
    @Override
    public Types getType() {
        return Types.NIFTYTREEBOX;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GTree(id,ele);
    }

    @Override
    public void initDefault() {
      element.setAttribute("name", Types.NIFTYTREEBOX.toString());
      element.setAttribute("width", "30%");
      element.setAttribute("height", "50%");
      element.setAttribute("childLayout", "vertical");
    }
    
     @Override
    protected de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a tree,only from your code");
    }
    
}
