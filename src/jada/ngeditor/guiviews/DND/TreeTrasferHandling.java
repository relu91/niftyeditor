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
package jada.ngeditor.guiviews.DND;

import jada.ngeditor.controller.CommandProcessor;
import jada.ngeditor.controller.commands.AddElementCommand;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GuiEditorModel;
import jada.ngeditor.model.elements.GElement;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author cris
 */
//TODO: make aviable also the drag&drop for the tree
public class TreeTrasferHandling extends TransferHandler implements Observer {

    private GUI gui;
    private boolean coping = false;
    private GElement copyTemplate;

    public TreeTrasferHandling() {
        CommandProcessor.getInstance().getObservable().addObserver(TreeTrasferHandling.this);
    }

    @Override
    public Transferable createTransferable(JComponent c) {
        if (copyTemplate != null) {
            return new WidgetData(copyTemplate, 0, 0);
        } else {
            return null;
        }
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return false;
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
        coping = true;
        copyTemplate = gui.getSelection().getFirst();
        if (action == MOVE) {
            gui.removeElement(gui.getSelection().getFirst());
        }
        super.exportToClipboard(comp, clip, action);
    }

    @Override
    public boolean importData(TransferSupport support) {
        boolean res = super.importData(support);
        if (!res) {
            try {
                GElement from = (GElement) support.getTransferable().getTransferData(WidgetData.FLAVOR);
                GElement cloned = from.clone();
                AddElementCommand command = CommandProcessor.getInstance().getCommand(AddElementCommand.class);
                command.setChild(cloned);
                command.setParent(this.gui.getSelection().getFirst());
                CommandProcessor.getInstance().excuteCommand(command);
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(TreeTrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TreeTrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TreeTrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GuiEditorModel) {
            this.gui = ((GuiEditorModel) o).getCurrent();
        }
    }

}
