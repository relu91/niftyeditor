/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Main model class where you can retrieve all the current {@code GUI}.
 * @author cris
 */
public class GuiEditorModel  {
    public static final String PROP_CURRENT_GUI = "PROP_CURRENT_GUI";
    private ArrayList<GUI> guis = new ArrayList<GUI>();
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private static GuiEditorModel instance;
    private GUI current;
    public static GuiEditorModel getInstance(){
        if(instance == null) {
            instance = new GuiEditorModel();
        }
        return instance;
    }
  
    
    public Collection<GUI> getGUIs(){
        return Collections.unmodifiableCollection(guis);
    }
    
    public GUI getGUI(int uid){
        for(GUI g : guis){
            if(g.getGUIid() == uid)
                return g;
        }
        throw new IllegalArgumentException("No gui for your id");
    }
 
    public void addGUI(GUI g){
        this.guis.add(g);
    }
    
    public void removeGUI(GUI g){
        this.guis.remove(g);
    }

    /**
     * @return the current gui
     */
    public GUI getCurrent() {
        return current;
    }

    /**
     * @param current the current GUI to set
     */
    public void setCurentGUI(GUI current) {
        jada.ngeditor.model.GUI oldCurrent = current;
        if(!guis.contains(current)){
            this.addGUI(current);
        }
        this.current = current;
        propertyChangeSupport.firePropertyChange(PROP_CURRENT_GUI, oldCurrent, current);
    }
    
     public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        this. propertyChangeSupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener )
    {
        this. propertyChangeSupport.removePropertyChangeListener( listener );
    }
}
