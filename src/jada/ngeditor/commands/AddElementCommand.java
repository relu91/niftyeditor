/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.commands;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import java.awt.geom.Point2D;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author cris
 */
public class AddElementCommand extends AbstractUndoableEdit{
    private final GUIEditor editor;
    private final GElement child;
    private final Point2D point;
    
    public AddElementCommand(GUIEditor editor , GElement child,Point2D point){
        this.editor = editor;
        this.child = child;
        this.point = point;
        this.editor.addElement(child,point);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        this.editor.addElement(child, point);
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        this.editor.removeElement(child);
    }
    
    
    
}
