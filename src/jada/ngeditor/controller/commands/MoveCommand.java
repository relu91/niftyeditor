/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.utils.NiftyDDManager;
import jada.ngeditor.model.utils.NiftyDDManager.ElementState;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author cris
 */
public class MoveCommand extends AbstractUndoableEdit implements Command{
    private final GUIEditor editor;
    private Point2D to;
    private GElement element;
    private ElementState state;
    
    public MoveCommand(GUIEditor editor, UndoManager manager) {
       
        this.editor = editor;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
         this.editor.move(this.to, this.element);
    }

    @Override
    public boolean canUndo() {
        return super.canUndo() && this.state != null;
    }
    
    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        this.editor.move(new Point(state.getX(), state.getY()), this.element);
    }
    
    
    @Override
    public void perform() throws Exception {
        
        this.editor.move(this.to, this.element);
    }

    @Override
    public boolean isActive() {
       return false;
    }

    @Override
    public String getName() {
        return "Move";
    }

    /**
     * @param element the element to set
     */
    public void setElement(GElement element) {
        this.element = element;
    }

    /**
     * @param to the to to set
     */
    public void setTo(Point2D to) {
        this.to = to;
    }
    /**
     * Set the state of the element before this command. if it's null this command
     * can't be reverted.
     * @param state 
     */
    public void setElementState(NiftyDDManager.ElementState state){
        this.state = state;
    }
    
}
