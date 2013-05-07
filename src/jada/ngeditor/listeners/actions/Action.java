/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners.actions;

import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class Action {
    
    public static final int ADD = 0;
    public static final int SEL = 1;
    public static final int DEL = 2;
    public static final int MOV = 3;
    public static final int UPDATE = 4;
    public static final int NEW = 5;
    
    private int type;
    private GElement gele;
    
    public Action(int type,GElement throwed){
        this.type=type;
        this.gele=throwed;
    }
    
    public int getType(){
        return this.type;
    }
    
    public GElement getGUIElement(){
        return this.gele;
    }
}
