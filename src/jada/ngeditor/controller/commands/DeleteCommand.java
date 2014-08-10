/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class DeleteCommand extends AbstractUndoableEdit implements Command{
     private final GUIEditor editor;
    private GElement child;
    
    private final UndoManager manager;
    private GElement parent;
    private boolean pointMode = false;
    
    public  DeleteCommand(GUIEditor editor,UndoManager manager){
        this.editor = editor;
        this.manager = manager;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        this.editor.removeElement(child);
        
        
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        this.editor.addElement(child, parent);
    }

    @Override
    public void perform() throws Exception {
        if(child == null){
            child = editor.getSelected();
        }
        parent = child.getParent();
        this.editor.removeElement(child);
        
    }

    /**
     * @param child the child to set
     */
    public void setChild(GElement child) {
        this.child = child;
    }

    
   

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
        return "Remove Element";
    }
}
