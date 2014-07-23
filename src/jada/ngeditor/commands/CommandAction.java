/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.commands;

import com.google.common.annotations.Beta;
import com.sun.istack.internal.Nullable;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.undo.UndoManager;



/**
 *
 * @author cris
 */
public abstract class CommandAction extends AbstractAction{
    private final Command com;
    @Beta
    @Nullable
    private UndoManager manager; 
    
    public CommandAction(String name,Command com){
        super(name);
        this.com = com;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       try{
       this.com.perform();
       if(manager != null){
           manager.addEdit(this.com.getEdit());
       }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"Command "+e.getActionCommand()+" failed with this message:"+ex.getMessage(),"Nifty-Editor error",JOptionPane.ERROR_MESSAGE);
       }
    }
    
}
