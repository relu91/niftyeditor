/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller.commands;

import jada.ngeditor.controller.Command;
import de.lessvoid.nifty.Nifty;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.GuiEditorModel;
import jada.ngeditor.model.exception.NoProductException;
import java.io.IOException;
import javax.swing.undo.UndoableEdit;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author cris
 */
public class NewGuiCommand implements Command{
    private final Nifty nifty;
    private final GUIEditor editor;
    public NewGuiCommand(Nifty gui){
        this.nifty = gui;
        editor = new GUIEditor();
    }
    @Override
    public void perform() throws ParserConfigurationException, JAXBException, ClassNotFoundException, IOException, NoProductException {
         editor.createNewGui(nifty);
         GuiEditorModel.getInstance().addGUI(editor.getGui());
    }

    @Override
    public boolean isActive() {
       return true;
    }
    
     @Override
    public String getName() {
        return "New Gui";
    }
    
}
