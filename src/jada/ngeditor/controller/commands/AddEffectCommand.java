/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.effects.GEffect;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author cris
 */
public class AddEffectCommand extends AbstractUndoableEdit implements Command{
    private final GUIEditor editor;
    private final UndoManager manager;
    private GEffect effectToAdd;
    
    public AddEffectCommand(GUIEditor editor,UndoManager manager){
        this.editor = editor;
        this.manager = manager;
    }
    @Override
    public void perform() throws Exception {
        editor.getSelected().addEffectForThisElement(effectToAdd);
    }
    
    public void setEffectToAdd(GEffect effect){
        effectToAdd = effect;
    }
    

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName() {
        return "AddEffect";
    }
    
}
