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
import jada.ngeditor.guiviews.palettecomponents.NWidget;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GuiEditorModel;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GLayer;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
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
    
    private GUI gui;
    private boolean coping=false;
    private GElement copyTemplate;

    public TrasferHandling() {
        super();
        CommandProcessor.getInstance().getObservable().addObserver(this);
    }
    
    @Override
    public  Transferable createTransferable(JComponent c) {
        if(c instanceof NWidget){
            NWidget comp = (NWidget)c;
            return comp.getData();
        } else{
             if(!coping){
             Rectangle rec = gui.getSelection().getFirst().getBounds();
             int a = (int)rec.getCenterX() - gui.getSelection().getFirst().getNiftyElement().getX();
             int b = (int)rec.getCenterY() - gui.getSelection().getFirst().getNiftyElement().getY();
             return new WidgetData(gui.getSelection().getFirst(),a,b);
             }
             else{
                if(copyTemplate!=null){
                   
                   
                   return new WidgetData(copyTemplate,0,0);
                
                }else
                    return null;
             }
        }
        
        
    }
    
    
    
   
    @Override
    public int getSourceActions(JComponent c) {
        if(c instanceof NWidget)
            return COPY;
        else
            return COPY_OR_MOVE;
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
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        coping = false;
        super.exportAsDrag(comp, e, action);
    }


    

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof GuiEditorModel){
            this.gui = ((GuiEditorModel)o).getCurrent();
        }
        
    }

    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException {
        coping = true;
        copyTemplate = gui.getSelection().getFirst();
        if(action == MOVE){
            gui.removeElement(gui.getSelection().getFirst());
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
            Point mousePosition = support.getComponent().getMousePosition();
             AddElementCommand command = CommandProcessor.getInstance().getCommand(AddElementCommand.class);
                  command.setChild(cloned);
                  command.setPoint(mousePosition);
           CommandProcessor.getInstance().excuteCommand(command);
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(TrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
           Logger.getLogger(TrasferHandling.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return res;
    }
 
    
    
    
}
