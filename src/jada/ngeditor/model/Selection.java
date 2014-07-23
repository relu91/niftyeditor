/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

import jada.ngeditor.listeners.events.SelectionChanged;
import jada.ngeditor.model.elements.GElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;

/**
 *
 * @author cris
 */
public class Selection extends Observable{
    private ArrayList<GElement> selected = new ArrayList<GElement>();
    
    public boolean isEmpty(){
        return selected.isEmpty();
    }
    
    public void addSelection(GElement element){
        selected.add(element);
        this.setChanged();
        this.notifyObservers(new SelectionChanged(this));
    }
    
    public void removeSelection(GElement element){
        selected.remove(element);
        this.setChanged();
        this.notifyObservers(new SelectionChanged(this));
    }
    
    public void setSelection(GElement element){
        this.clearSelection();
        selected.add(element);
        this.setChanged();
        this.notifyObservers(new SelectionChanged(this));
    }
    
    public void clearSelection(){
        selected.clear();
        this.setChanged();
        this.notifyObservers(new SelectionChanged(this));
    }

    public GElement getFirst(){
        return selected.get(0);
    }
    
    public Collection<GElement> getAll(){
        return Collections.unmodifiableCollection(this.selected);
    }
    
}
