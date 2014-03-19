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
import jada.ngeditor.guiviews.palettecomponents.NWidget;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GLayer;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
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
public class TrasferHandling extends TransferHandler implements Observer{
    
    private GUIEditor gui;
    
    @Override
    public  Transferable createTransferable(JComponent c) {
        if(c instanceof NWidget){
            NWidget comp = (NWidget)c;
            return comp.getData();
        } else{
             Rectangle rec = gui.getSelected().getBounds();
             int a = (int)rec.getCenterX() - gui.getSelected().getNiftyElement().getX();
             int b = (int)rec.getCenterY() - gui.getSelected().getNiftyElement().getY();
             return new WidgetData(gui.getSelected(),a,b);
         
        }
        
    }
    
    /**
     *
     * @return
     */
    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action)
    {
        super.exportAsDrag(comp, e, action);
       
    }
    
   
    @Override
    public int getSourceActions(JComponent c) {
        if(c instanceof NWidget)
            return COPY;
        else
            return MOVE;
    }
    
    @Override
    public boolean canImport(TransferSupport support){
       if(support.getComponent() instanceof NWidget ) {
            return false;
        } 
       else {
            try {
               GElement ele = (GElement) support.getTransferable().getTransferData(WidgetData.FLAVOR);
               if(ele instanceof GLayer)
                        return false;
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(TrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       return true;
    }


  

    @Override
    public void update(Observable o, Object arg) {
        if(((Action)arg).getType() == Action.NEW)
            this.gui = ((GUIEditor)o);
    }
    
    
    
    
    
}
