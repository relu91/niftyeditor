/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.scrollbar.builder.ScrollbarBuilder;
import jada.ngeditor.model.Types;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
public abstract class GScrollbar extends GElement{
    
    protected GScrollbar(){
        super();
    }
    protected GScrollbar(String id,org.w3c.dom.Element docElement,boolean vertical) throws IllegalArgumentException{
      super(id,docElement);
      builder = new ScrollbarBuilder(id, vertical);
    }
    
}
