/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.treebox.builder.TreeBoxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.NIFTYTREEBOX)
public class GTree extends GControl {

   

    public GTree(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new TreeBoxBuilder(id);
        this.name="nifty-tree-box";
    }

    public GTree() {
        super();
    }

    @Override
    public GElement create(String id) {
        return new GTree(id);
    }

    @Override
    public void initDefault() {
      
      attributes.put("width", "30%");
      attributes.put("height", "50%");
      attributes.put("childLayout", "vertical");
    }
    
     @Override
    public de.lessvoid.nifty.elements.Element getDropContext() {
        throw new IllegalDropException("You can not add elements to a tree,only from your code");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
