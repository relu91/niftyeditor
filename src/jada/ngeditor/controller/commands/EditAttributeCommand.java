/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import com.google.common.base.Strings;
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
public class EditAttributeCommand  extends AbstractUndoableEdit implements Command{
    private final GUIEditor editor;
    private final UndoManager manager;
    private GElement toBeEdited;
    private String attribute;
    private String value;
    private String oldValue;

    public EditAttributeCommand(GUIEditor editor,UndoManager manager) {
        this.editor = editor;
        this.manager = manager;
        
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        if(toBeEdited == null){
            
            this.editor.getElementEditor().setAttribute(attribute, value);
        }else{
            
            this.editor.getElementEditor(toBeEdited).setAttribute(attribute, value);
        }
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        if(toBeEdited == null){
            
            this.editor.getElementEditor().setAttribute(attribute, oldValue);
        }else{
           
            this.editor.getElementEditor(toBeEdited).setAttribute(attribute, oldValue);
        }
        
    }
    
    
    
    @Override
    public void perform() throws Exception {
        if(Strings.isNullOrEmpty(attribute)|| value == null){
            throw new IllegalStateException("You can't perform this command attribute or value is invalid");
        }
        
        if(toBeEdited == null){
            oldValue = this.editor.getSelected().getAttribute(attribute);
            toBeEdited = this.editor.getSelected();
            this.editor.getElementEditor().setAttribute(attribute, value);
        }else{
            oldValue = toBeEdited.getAttribute(attribute);
            this.editor.getElementEditor(toBeEdited).setAttribute(attribute, value);
        }
        
    }

    @Override
    public boolean isActive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        return "Edit Element";
    }

    /**
     * @param toBeEdited the toBeEdited to set
     */
    public void setElement(GElement toBeEdited) {
        this.toBeEdited = toBeEdited;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
