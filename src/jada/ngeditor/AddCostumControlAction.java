package jada.ngeditor;

import jada.ngeditor.controller.CommandProcessor;
import jada.ngeditor.controller.commands.AddElementCommand;
import jada.ngeditor.model.IDgenerator;
import jada.ngeditor.model.elements.GCostumControl;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AddCostumControlAction extends AbstractAction{

    public AddCostumControlAction() {
        super("Add Control");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String name ="";
        name = JOptionPane.showInputDialog(null, "Insert the control name");
        if(!name.equals("")){
            AddElementCommand command = CommandProcessor.getInstance().getCommand(AddElementCommand.class);
            command.setParentSelected();
            String id = IDgenerator.getInstance().generate(GCostumControl.class);
            GCostumControl control = new GCostumControl(name, id);
            command.setChild(control);
            try {
                CommandProcessor.getInstance().excuteCommand(command);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Can't add the control");
            }
        }
        
    }

}