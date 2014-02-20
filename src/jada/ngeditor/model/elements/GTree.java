/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.treebox.builder.TreeBoxBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
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
    
}
