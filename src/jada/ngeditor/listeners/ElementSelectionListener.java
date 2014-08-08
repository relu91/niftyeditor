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

import jada.ngeditor.controller.CommandProcessor;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.controller.commands.SelectCommand;
import jada.ngeditor.model.elements.GElement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author cris
 */
public class ElementSelectionListener implements TreeSelectionListener {

   

    public ElementSelectionListener() {
       
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getNewLeadSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (!node.isRoot()) {
                try {
                    SelectCommand command = CommandProcessor.getInstance().getCommand(SelectCommand.class);
                    command.setElement((GElement) node.getUserObject());
                    CommandProcessor.getInstance().excuteCommand(command);
                } catch (Exception ex) {
                    Logger.getLogger(ElementSelectionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            final JTree temp = (JTree) e.getSource();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    temp.updateUI();
                }
            });
        }
    }
}
