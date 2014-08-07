/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.controller.MainCrontroller;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GuiEditorModel;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

/**
 *
 * @author cris
 */
//TODO: make this class context aware like the popup menu of netbeans(i.e. providing
// an action map for an element.
public class EditingPopUp extends JPopupMenu implements Observer{
    private GUI editor;

    public EditingPopUp() {
        super("Edit");
        MainCrontroller.getInstance().getObservable().addObserver(EditingPopUp.this);
        
        JMenuItem menuItem = new JMenuItem(TransferHandler.getCopyAction());
         JMenuItem menuItemPaste = new JMenuItem(TransferHandler.getPasteAction());
         JMenuItem menuItemCut = new JMenuItem(TransferHandler.getCutAction());
        menuItem.setActionCommand((String)TransferHandler.getCopyAction().
             getValue(javax.swing.Action.NAME));
       menuItem.addActionListener(new EditingPopUp.TransferActionListener());
        menuItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_T);
        
        menuItemPaste.setActionCommand((String)TransferHandler.getPasteAction().
             getValue(javax.swing.Action.NAME));
      menuItemPaste.addActionListener(new EditingPopUp.TransferActionListener());
       menuItemPaste.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItemPaste.setMnemonic(KeyEvent.VK_V);
        
         menuItemCut.setActionCommand((String)TransferHandler.getCutAction().
             getValue(javax.swing.Action.NAME));
      menuItemCut.addActionListener(new EditingPopUp.TransferActionListener());
       menuItemCut.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItemCut.setMnemonic(KeyEvent.VK_X);
        this.add(menuItemPaste);
        this.add(menuItem);
        this.add(menuItemCut);
        this.add(new Separator());
        this.add(new JMenuItem(new Show()));
        this.add(new JMenuItem(new Hide()));
        this.add(new JMenuItem(new Delete()));
        this.add(new JMenuItem(new FillAction()));
        JMenu menu = new JMenu("Normalize");
        menu.add(new JMenuItem(new Normalize("Normalize Size")));
        menu.add(new JMenuItem(new Normalize("Normalize Postion")));
        menu.add(new JMenuItem(new Normalize("Normalize")));
        this.add(menu);
    }

     @Override
    public void update(Observable o, Object arg) {
       if(o instanceof GuiEditorModel){
           this.editor = ((GuiEditorModel)o).getCurrent();
       }
    }
    
    
    private class FillAction extends AbstractAction{

        public FillAction() {
            super("Fill");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
           // editor.getElementEditor().fill();
        }
        
    }
    
    private class Normalize extends AbstractAction{
        private final int action;

        public Normalize(String name) {
            super(name);
            //Just to not make 3 inner class
            if(name.equalsIgnoreCase("normalize position")){
                action = 0;
            }else if(name.equalsIgnoreCase("normalize size")){
                action = 1;
            }else
                action = 2;
            
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(action){
                case 0 :
                   // editor.getElementEditor().normalizeSize();
                    break;
                case 1 :
                  //  editor.getElementEditor().normalizePosition();
                    break;
                default: 
                   // editor.getElementEditor().normalize();
            }
        }
        
    }
    
    private class Hide extends AbstractAction{

        public Hide() {
            super("Hide");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          // editor.getElementEditor().setVisibileSelected(false);
        }
        
        
    }
    
    private class Show extends AbstractAction{

        public Show() {
            super("Show");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          // editor.getElementEditor().setVisibileSelected(true);
        }
        
        
    }
    
     private class Delete extends AbstractAction{

        public Delete() {
            super("Delete");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
          // editor.removeSelected();
        }
        
        
    }
    
      
    private class TransferActionListener implements ActionListener,
                                              PropertyChangeListener {
    private JComponent focusOwner = null;

    public TransferActionListener() {
        KeyboardFocusManager manager = KeyboardFocusManager.
           getCurrentKeyboardFocusManager();
        manager.addPropertyChangeListener("permanentFocusOwner", this);
    }

    public void propertyChange(PropertyChangeEvent e) {
        Object o = e.getNewValue();
        if (o instanceof JComponent) {
            focusOwner = (JComponent)o;
        } else {
            focusOwner = null;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (focusOwner == null)
            return;
        String action = (String)e.getActionCommand();
        javax.swing.Action a = focusOwner.getActionMap().get(action);
        if (a != null) {
            a.actionPerformed(new ActionEvent(focusOwner,
                                              ActionEvent.ACTION_PERFORMED,
                                              null));
        }
    }
}
}
