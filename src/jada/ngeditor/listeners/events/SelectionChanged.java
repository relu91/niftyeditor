/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners.events;

import jada.ngeditor.model.Selection;
import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class SelectionChanged implements ElementEvent{
    private final Selection newSelection;
    
    public SelectionChanged(Selection newSelection){
        this.newSelection = newSelection;
        
    }

    /**
     * @return the newSelection
     */
    public Selection getNewSelection() {
        return newSelection;
    }

    @Override
    public GElement getElement() {
        return newSelection.getFirst();
    }
    
}
