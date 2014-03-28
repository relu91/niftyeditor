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
import jada.ngeditor.guiviews.J2DNiftyView;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.elements.GElement;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author cris
 */
public class PaletteDropTarget extends DropTarget implements Observer {
    
    private GUIEditor obj;
    
    public PaletteDropTarget(){
        
    }
    
    @Override
    public synchronized void dragOver(DropTargetDragEvent dtde){
        super.dragOver(dtde);
        J2DNiftyView comp = (J2DNiftyView) this.getComponent();
        if(dtde.getDropAction() == DnDConstants.ACTION_MOVE ){
            comp.moveRect(dtde.getLocation().x, dtde.getLocation().y);
            this.obj.getDragDropSupport().dragAround(dtde.getLocation().x, dtde.getLocation().y);
        }
        
    }
    @Override
    public void drop(DropTargetDropEvent dtde) {
        if(dtde.isDataFlavorSupported(WidgetData.FLAVOR)){
         try {
          if(dtde.getDropAction() == DnDConstants.ACTION_COPY){
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
            GElement res  =  (GElement) dtde.getTransferable().getTransferData(WidgetData.FLAVOR);
            obj.addElement(res,dtde.getLocation());
            dtde.dropComplete(true);
          } 
          else if(dtde.getDropAction() == DnDConstants.ACTION_MOVE){
               dtde.acceptDrop(DnDConstants.ACTION_MOVE);
               GElement from  =  (GElement) dtde.getTransferable().getTransferData(WidgetData.FLAVOR); 
                if(dtde.getTransferable().isDataFlavorSupported(WidgetData.POINTFLAVOR)){
                    
                    Point2D point = (Point2D) dtde.getTransferable().getTransferData(WidgetData.POINTFLAVOR);
                    dtde.getLocation().x= (int) (dtde.getLocation().x - point.getX());
                    dtde.getLocation().y= (int) (dtde.getLocation().y - point.getY());
                }
               obj.move(dtde.getLocation(), from);
               obj.getDragDropSupport().endDrag();
               dtde.dropComplete(true);
          }
        }catch (IllegalDropException ex){
            JOptionPane.showMessageDialog(dtde.getDropTargetContext().getComponent(), ex.getMessage());
            obj.getDragDropSupport().reverDrag();
        } catch (UnsupportedFlavorException ex) {
             obj.getDragDropSupport().reverDrag();
            Logger.getLogger(PaletteDropTarget.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             obj.getDragDropSupport().reverDrag();
            Logger.getLogger(PaletteDropTarget.class.getName()).log(Level.SEVERE, null, ex);
        }   
        } else {
            dtde.rejectDrop();
        }
        
    }
 @Override
    public void update(Observable o, Object arg) {
        if(((Action)arg).getType() == Action.NEW) {
            this.obj = ((GUIEditor)o);
        }
    }
    
    
}
