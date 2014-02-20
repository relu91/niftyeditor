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
package jada.ngeditor.renderUtil;

import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.model.elements.GElement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author cris
 */
public class NiftyTreeRender implements TreeCellRenderer{
    private Color bla = new Color(119,136,136);
    public NiftyTreeRender(){
        super();
    }
    
    @Override
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {
       DefaultTreeCellRenderer c =  new DefaultTreeCellRenderer();
       c.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
       if(row!=0){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
            GUIEditor edit = (GUIEditor) root.getUserObject();
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
            c.setIcon(new ImageIcon(image));
            c.setText(value.toString());
            if(element.equals(edit.getCurrentLayer())){
                c.setBackgroundSelectionColor(Color.CYAN);
                c.setBackgroundNonSelectionColor(bla);
            }
        }
        
        return c;
    
    
    }
    
}
