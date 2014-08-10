/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.ElementEditor;
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
public class VisibilityCommand extends AbstractUndoableEdit implements Command{
    private boolean visibility;
    private final GUIEditor editor;
    private GElement element;
    
    public VisibilityCommand(GUIEditor editor , UndoManager manager){
        this.editor = editor;
        
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        ElementEditor elementEditor = editor.getElementEditor(element);
        elementEditor.setVisibileSelected(visibility);
    }
    
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        ElementEditor elementEditor = editor.getElementEditor(element);
        elementEditor.setVisibileSelected(!visibility);
    }
    
    
    @Override
    public void perform() throws Exception {
        if(element == null){
            element = editor.getSelected();
        }
        ElementEditor elementEditor = editor.getElementEditor(element);
        elementEditor.setVisibileSelected(visibility);
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
       return "Visibility";
    }
    
}
