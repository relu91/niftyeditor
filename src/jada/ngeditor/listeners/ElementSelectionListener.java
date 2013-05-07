/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author cris
 */
public class ElementSelectionListener implements TreeSelectionListener{
    private final GUIEditor gui;
    
    public ElementSelectionListener(GUIEditor gui){
        this.gui = gui;
    }
    @Override
    public void valueChanged(TreeSelectionEvent e) {
       TreePath path =  e.getNewLeadSelectionPath();
       if(path!=null){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if(!node.isRoot())
            this.gui.selectElement((GElement)node.getUserObject());
       }
    }
    
}
