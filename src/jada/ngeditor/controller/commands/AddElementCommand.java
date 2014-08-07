/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import java.awt.geom.Point2D;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class AddElementCommand extends AbstractUndoableEdit implements Command{
    private final GUIEditor editor;
    private GElement child;
    private Point2D point;
    private final UndoManager manager;
    private GElement parent;
    private boolean pointMode = false;
    
    public AddElementCommand(GUIEditor editor,UndoManager manager){
        this.editor = editor;
        this.manager = manager;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        if(pointMode){
            this.editor.addElement(child, point);
        }else{
            this.editor.addElement(child, parent);
        }
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        this.editor.removeElement(child);
    }

    @Override
    public void perform() throws Exception {
        if(child == null || point == null){
            throw new IllegalStateException("No child or point to add");
        }
        if(pointMode){
            this.editor.addElement(child, point);
        }else{
            this.editor.addElement(child, parent);
        }
    }

    /**
     * @param child the child to set
     */
    public void setChild(GElement child) {
        this.child = child;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point2D point) {
        pointMode = true;
        this.point = point;
    }
    
    public void setParent(GElement parent){
        pointMode= false;
        this.parent = parent;
    }

    @Override
    public boolean isActive() {
        return !(child == null || point == null);
    }

    @Override
    public String getName() {
        return "Add Element";
    }

    
    
    
    
    
    
}
