/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import java.awt.geom.Point2D;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class MoveCommand implements Command{
    private final GUIEditor editor;
    private Point2D to;
    private GElement element;
    public MoveCommand(GUIEditor editor, UndoManager manager) {
        this.editor = editor;
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
    
}
