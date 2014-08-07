/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;

/**
 * Main model class where you can retrieve all the current {@code GUI}.
 * Fire changes when the current gui is changed.
 * @author cris
 */
public class GuiEditorModel extends Observable {
    private ArrayList<GUI> guis = new ArrayList<GUI>();
    private GUI current;
    
   
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
    
    public void removeGUI(int uid){
        this.removeGUI(this.getGUI(uid));
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
        this.current = current;
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * 
     * @param uid the id of GUI to set
     */
    public void setCurrentGUI(int uid){
        this.setCurentGUI(this.getGUI(uid));
    }
}
