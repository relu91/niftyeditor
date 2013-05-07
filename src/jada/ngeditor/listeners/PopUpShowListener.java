/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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
    

