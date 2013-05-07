/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.DND;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.guiviews.palettecomponents.NWidget;
import jada.ngeditor.listeners.NewGuiListener;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
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
               if(ele.getType().equals(""+Types.LAYER))
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
