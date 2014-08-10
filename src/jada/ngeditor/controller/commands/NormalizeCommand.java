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
public class NormalizeCommand extends AbstractUndoableEdit implements Command{
    public static final byte ALL = 0;
    public static final byte SIZE = 1;
    public static final byte POSITION = 2;
    private byte command;
    private final GUIEditor editor;
    private GElement element;
    private String oldX="";
    private String oldY="";
    private String oldW="";
    private String oldH="";
    public NormalizeCommand(GUIEditor editor , UndoManager manager){
        this.editor = editor;
        
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
         ElementEditor elementEditor = editor.getElementEditor(element);
        switch(command){
            case ALL :
                elementEditor.normalize();
                break;
            case SIZE :
                elementEditor.normalizeSize();
                break;
            case POSITION :
                elementEditor.normalizePosition();
                break;
             
        }
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo(); 
         ElementEditor elementEditor = editor.getElementEditor(element);
         switch(command){
            case ALL :
                elementEditor.setAttribute("x", oldX);
                elementEditor.setAttribute("y", oldY);
                elementEditor.setAttribute("width", oldW);
                elementEditor.setAttribute("height", oldH);
                break;
            case SIZE :
                 elementEditor.setAttribute("width", oldW);
                elementEditor.setAttribute("height", oldH);
                break;
            case POSITION :
                elementEditor.setAttribute("x", oldX);
                elementEditor.setAttribute("y", oldY);
                break;
         }
    }
    
    @Override
    public void perform() throws Exception {
        if(element == null){
            element = editor.getSelected();
        }
        ElementEditor elementEditor = editor.getElementEditor(element);
        switch(command){
            case ALL :
                oldX = element.getAttribute("x");
                oldY = element.getAttribute("y");
                oldW = element.getAttribute("width");
                oldH = element.getAttribute("height");
                elementEditor.normalize();
                break;
            case SIZE :
                 oldW = element.getAttribute("width");
                oldH = element.getAttribute("height");
                elementEditor.normalizeSize();
                break;
            case POSITION :
                oldX = element.getAttribute("x");
                oldY = element.getAttribute("y");
                elementEditor.normalizePosition();
                break;
             
        }
    }

    @Override
    public boolean isActive() {
       return true;
    }

    public void setCommand(byte command) {
        this.command = command;
    }
    
    
    @Override
    public String getName() {
       return "Normalize";
    }
    
}
