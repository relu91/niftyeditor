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
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.DragHandler;
import jada.ngeditor.guiviews.DND.TrasferHandling;
import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GButton;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author cris
 */
public class NWidget extends javax.swing.JPanel {
    private final Class dataClass;
    
    /**
     * Creates new form NWidget
     */
    public NWidget(Class wrappedClass) {
        this.dataClass = wrappedClass;
        try {
            initComponents();
            this.addMouseListener(new DragHandler());
            this.setTransferHandler(new TrasferHandling());
            String name = wrappedClass.getSimpleName();
            this.text.setText(name);
            BufferedImage image = ImageIO.read(getIcon(name+".png"));
            Image scaledInstance = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            this.text.setIcon(new ImageIcon(scaledInstance ));
        } catch (Exception ex) {
            BufferedImage image;
            try {
                image = ImageIO.read(getIcon("NoElement.png"));
                Image scaledInstance = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                this.text.setIcon(new ImageIcon(scaledInstance ));
            } catch (IOException ex1) {
                Logger.getLogger(NWidget.class.getName()).log(Level.SEVERE, "No default image", ex1);
            }
            Logger.getLogger(NWidget.class.getName()).log(Level.SEVERE,"No Image for "+wrappedClass);
        }
    }
    
    public WidgetData getData() {
        try {
            GElement e = GUIFactory.getInstance().newGElement(dataClass);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
           return new WidgetData();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(194, 70));

        text.setFont(new java.awt.Font("Simplified Arabic", 0, 14)); // NOI18N
        text.setForeground(new java.awt.Color(0, 153, 204));
        text.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jada/ngeditor/resources/NoElement.png"))); // NOI18N
        text.setText("Screen");
        text.setIconTextGap(10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables
    
    protected java.io.InputStream getIcon(String icon){
       return getClass().getResourceAsStream("/jada/ngeditor/resources/"+icon);
    }
}

