/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class RedoCommand implements Command{
    
     private final GUIEditor editor;
    private final UndoManager manager;
    
    public RedoCommand (GUIEditor editor, UndoManager manager){
        this.editor = editor;
        this.manager = manager;
        
    }

    @Override
    public boolean isActive() {
        return manager.canRedo();
    }
    
    
    @Override
    public void perform() throws Exception {
        try{
            manager.redo();
        }catch(CannotUndoException ex){
            ex.printStackTrace(); 
        }
    }

    @Override
    public String getName() {
        return "Redo";
    }
    
    
}
