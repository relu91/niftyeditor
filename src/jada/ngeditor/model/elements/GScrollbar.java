/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.scrollbar.builder.ScrollbarBuilder;

/**
 *
 * @author cris
 */

public abstract class GScrollbar extends GControl {
    
    protected GScrollbar(){
        super();
    }
    protected GScrollbar(String id,boolean vertical) throws IllegalArgumentException{
      super(id);
      builder = new ScrollbarBuilder(id, vertical);
    }
    
}
