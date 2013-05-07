/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;

/**
 *
 * @author cris
 */
public class PopUpShowListener extends MouseAdapter{
     private final javax.swing.JPopupMenu popUp;
   
     
     public PopUpShowListener(javax.swing.JPopupMenu pop){
         popUp=pop;
     }
     @Override
     public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
             javax.swing.JTree tree = (javax.swing.JTree) e.getComponent();
             TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		    if(path != null) {
                        tree.setSelectionPath(path);
			this.popUp.show(tree, e.getX(), e.getY());
                        this.popUp.setLightWeightPopupEnabled(false);
		    }
		}
        }
 }
    

