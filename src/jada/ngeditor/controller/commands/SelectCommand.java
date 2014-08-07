/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class SelectCommand implements Command{
    private final GUIEditor editor;
    private GElement toSelect;
    private boolean point = false;
    private int x;
    private int y;
    
    public SelectCommand(GUIEditor editor , UndoManager mana){
        this.editor = editor;
        
    }
    @Override
    public void perform() throws Exception {
       if(!point)
        editor.selectElement(this.toSelect);
       else
           editor.selectElement(x, y);
    }
    
    public void setElement(GElement toselect){
        point = false;
        this.toSelect = toselect;
    }
    
    public void selectByPoint(int x,int y){
        point = true;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
       return "Select";
    }
    
}
