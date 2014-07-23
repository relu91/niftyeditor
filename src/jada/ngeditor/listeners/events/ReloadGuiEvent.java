/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners.events;

import jada.ngeditor.model.GUI;

/**
 *
 * @author cris
 */
public class ReloadGuiEvent {
    private final GUI newGui;
    
    public ReloadGuiEvent(GUI newGui){
        this.newGui = newGui;
    }

    /**
     * @return the newGui
     */
    public GUI getNewGui() {
        return newGui;
    }
}
