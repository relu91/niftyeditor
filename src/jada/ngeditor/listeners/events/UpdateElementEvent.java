/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners.events;

import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class UpdateElementEvent implements ElementEvent{
    private final GElement element;
    
    public UpdateElementEvent(GElement element){
        this.element = element;
        
    }

    /**
     * @return the element
     */
    public GElement getElement() {
        return element;
    }
    
}
