/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.renderUtil;

import jada.ngeditor.model.elements.GElement;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author cris
 */
public class NiftyTreeRender extends DefaultTreeCellRenderer{
    
    
    
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {
        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        if(row!=0){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            GElement element = (GElement)node.getUserObject();
             Image image =null;
             try {
                    BufferedImage tempimage = ImageIO.read(getClass().getResourceAsStream("/jada/ngeditor/resources/"+element.getType()+".png"));
                    image = tempimage.getScaledInstance(16, 16, Image.SCALE_FAST);
                 } catch (Exception ex) {
                     
                try {
                     BufferedImage tempimage = ImageIO.read(getClass().getResourceAsStream("/jada/ngeditor/resources/NoElement.png"));
                     image = tempimage.getScaledInstance(16, 16, Image.SCALE_FAST);
                } catch (IOException ex1) {
                    Logger.getLogger(NiftyTreeRender.class.getName()).log(Level.SEVERE, null, ex1);
                }
                    
               }
            this.setIcon(new ImageIcon(image));
        }
        
        return this;
    
    
    }
    
}
