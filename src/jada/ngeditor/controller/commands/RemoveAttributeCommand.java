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
public class RemoveAttributeCommand extends AbstractUndoableEdit implements Command{
    private final GUIEditor editor;
    private String key;
    private String value;
    private GElement element;
    
    public RemoveAttributeCommand(GUIEditor editor, UndoManager manger){
        this.editor = editor;
        
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        this.editor.getElementEditor().removeAttribute(key);
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        this.editor.getElementEditor(element).setAttribute(key, value);
    }
    
    @Override
    public void perform() throws Exception {
       if(element == null){
           this.element = this.editor.getSelected();
           value = this.element.getAttribute(key);
           this.editor.getElementEditor().removeAttribute(key);
       }else{
           value = this.element.getAttribute(key);
           this.editor.getElementEditor(element).removeAttribute(key);
       }
    }
    
    public void setAttributeKey(String key){
        this.key = key;
    }
    
    public void setElement(GElement element){
        this.element= element;
    }
    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
        return "Remove attribute";
    }
    
}
