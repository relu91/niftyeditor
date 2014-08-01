/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import java.util.logging.Logger;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class UndoCommand implements Command{
    private final GUIEditor editor;
    private final UndoManager manager;
    
    public UndoCommand(GUIEditor editor, UndoManager manager){
        this.editor = editor;
        this.manager = manager;
        
    }

    @Override
    public void perform() throws Exception {
        try{
            manager.undo();
        }catch(CannotUndoException ex){
            ex.printStackTrace(); 
        }
    }
    
    @Override
    public boolean isActive() {
        return manager.canRedo();
    }
    
     @Override
    public String getName() {
        return "Undo";
    }
    
}
