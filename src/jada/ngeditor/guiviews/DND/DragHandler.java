/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.DND;

import jada.ngeditor.guiviews.palettecomponents.NWidget;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.TransferHandler;

/**
 *
 * @author cris
 */
public class DragHandler extends MouseAdapter{
    
    @Override
    public void mousePressed(MouseEvent e) {
       
            NWidget c = (NWidget) e.getSource();
            TransferHandler handler = c.getTransferHandler();
            Image prova = ((ImageIcon) c.getIcon().getIcon()).getImage();
            prova = prova.getScaledInstance(32,32, Image.SCALE_FAST);
           // handler.setDragImage(prova);
            handler.exportAsDrag(c, e, TransferHandler.COPY); 
           
    }
}
