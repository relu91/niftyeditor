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

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author cris
 */
public class TreeTrasferHandling extends TransferHandler {
    
    private GUIEditor gui;
    private boolean coping=false;
    private GElement copyTemplate;

    public TreeTrasferHandling(GUIEditor gui) {
      
        this.gui = gui;
      
    }
    
    
    @Override
    public  Transferable createTransferable(JComponent c) {
       
                if(copyTemplate!=null){
                   
                   
                   return new WidgetData(copyTemplate,0,0);
                
                }else
                    return null;
             
        
        
        
    }
    
    
    
   
    @Override
    public int getSourceActions(JComponent c) {
       return COPY_OR_MOVE;
    }
    
    @Override
    public boolean canImport(TransferSupport support){
       return false;
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
        coping = true;
        copyTemplate = gui.getSelected();
        if(action == MOVE){
            gui.removeElement(gui.getSelected());
        }
        super.exportToClipboard(comp, clip, action);
    }  

    @Override
    public boolean importData(TransferSupport support) {
        boolean res = super.importData(support);
        if(!res){
        try {
            GElement from  =  (GElement) support.getTransferable().getTransferData(WidgetData.FLAVOR);
            GElement cloned = from.clone();
            
            this.gui.addElement(cloned,this.gui.getSelected());
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(TreeTrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TreeTrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return res;
    }
 
    
    
    
}
