/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.commands;

import javax.swing.undo.UndoableEdit;

/**
 * 
 * @author cris
 */
public interface Command {
    
    public void perform() throws Exception;
    /**
     * UndoableEdit associated with this command. 
     * @see UndoableEdit
     * @return 
     */
    public UndoableEdit getEdit();
    
}
