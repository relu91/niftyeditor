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
package jada.ngeditor.guiviews;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.java2d.input.InputSystemAwtImpl;
import de.lessvoid.nifty.java2d.renderer.FontProviderJava2dImpl;
import de.lessvoid.nifty.java2d.renderer.GraphicsWrapper;
import de.lessvoid.nifty.java2d.renderer.RenderDeviceJava2dImpl;
import de.lessvoid.nifty.tools.TimeProvider;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.listeners.GuiSelectionListener;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.Types;
import jada.ngeditor.renderUtil.SoudDevicenull;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author cris
 */
public class J2DNiftyView extends javax.swing.JPanel implements GraphicsWrapper,Observer,ActionListener{
    protected Nifty nifty;
    private  long fps = 0;
    private boolean selecting;
    private Graphics2D graphics2D;
    private GuiSelectionListener previous;
    private Rectangle selected;
    AffineTransform transformer = new AffineTransform();
    private GUIEditor manager;
    /**
     * Creates new form J2DNiftyView
     */
      
    public J2DNiftyView(int width , int height) {
        initComponents();
        this.setSize(new Dimension(width,height));
        previous=null;
        
        selecting=false;
        this.selected= new Rectangle();
        
    }
    
    public void createBuffer(){
       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUp = new javax.swing.JPopupMenu();
        DeleteButton = new javax.swing.JMenuItem();
        HideButton = new javax.swing.JMenuItem();
        NormalizeButton = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        popUp.add(DeleteButton);

        HideButton.setText("Hide");
        HideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HideButtonActionPerformed(evt);
            }
        });
        popUp.add(HideButton);

        NormalizeButton.setText("NormalizeSize");
        NormalizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalizeButtonActionPerformed(evt);
            }
        });
        popUp.add(NormalizeButton);

        jMenuItem1.setText("fill");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        popUp.add(jMenuItem1);

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void HideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HideButtonActionPerformed
        this.manager.getElementEditor().setVisibile(false);
        this.selecting=false; //clear the screen from selection
    }//GEN-LAST:event_HideButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
       this.manager.removeSelected();
       this.selecting=false;
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void NormalizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalizeButtonActionPerformed
        this.manager.getElementEditor().normalizeSize();
    }//GEN-LAST:event_NormalizeButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.manager.getElementEditor().fill();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DeleteButton;
    private javax.swing.JMenuItem HideButton;
    private javax.swing.JMenuItem NormalizeButton;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu popUp;
    // End of variables declaration//GEN-END:variables

    @Override
    public Graphics2D getGraphics2d() {
        return graphics2D;
    }

    private void registerFonts(FontProviderJava2dImpl fontProvider) {
        fontProvider.addFont("aurulent-sans-16.fnt", new Font("aurulent-sans-16",
	                                Font.ROMAN_BASELINE, 16));
        
    }

    public void init() {
        InputSystemAwtImpl inputSystem = new InputSystemAwtImpl();
        FontProviderJava2dImpl fontProvider = new FontProviderJava2dImpl();
	registerFonts(fontProvider);
        RenderDeviceJava2dImpl renderDevice = new RenderDeviceJava2dImpl(this);
	renderDevice.setFontProvider(fontProvider);
	nifty = new Nifty(renderDevice,  new SoudDevicenull(), inputSystem,new TimeProvider());
       
        java.net.URL empty = getClass().getResource("/jada/ngeditor/resources/empty.xml");
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        try {
            nifty.fromXml(empty.getFile(),empty.openStream(), "screen1");
        } catch (IOException ex) {
            Logger.getLogger(J2DNiftyView.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timer t = new Timer(60,this); //This supplies your timing for your loop... this is 100 fps
        t.start();
    }
   private static java.awt.Color line = new java.awt.Color(17,229,229);
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        BasicStroke stroke = new BasicStroke(1.5f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,30,new float[] { 10.0f, 4.0f },0);
         boolean done = false;
	 long time = System.currentTimeMillis();
	 long frames = 0;
         Font fpsFont =new Font("arial",Font.BOLD, 14);
         String fps="Fps: 0";
         int h = this.getSize().height > 600 ? 600 : this.getSize().height;
         int w = this.getSize().width > 800 ? 800 : this.getSize().width;
         nifty.getRenderEngine().getRenderDevice().enableClip(0, 0,w, h);
         graphics2D = (Graphics2D) g;
         
                               graphics2D.setBackground(Color.darkGray);
                               
                               done = nifty.update();
                                nifty.getRenderEngine().saveStates();
                               nifty.render(true);
                              nifty.getRenderEngine().restoreStates();
                              if(nifty.isDebugOptionPanelColors()){
                                graphics2D.setColor(java.awt.Color.red);
                                graphics2D.setFont(fpsFont);
                                graphics2D.drawString(fps, 0, fpsFont.getSize());
                              }
                              if(selecting){
                               
                                
                                
                                graphics2D.setColor(line);
                                graphics2D.drawLine((int)selected.getCenterX()-10, (int)selected.getCenterY(), (int)selected.getCenterX()+10,(int) selected.getCenterY());
                                graphics2D.drawLine((int)selected.getCenterX(), (int)selected.getCenterY()-10, (int)selected.getCenterX(),(int) selected.getCenterY()+10);
                                graphics2D.setStroke(stroke);
                                graphics2D.draw(selected);
                                graphics2D.setColor(java.awt.Color.black);
                                graphics2D.setStroke(new BasicStroke());
                                graphics2D.drawRect((int)selected.getMaxX()-6,(int)selected.getMaxY()-6, 11, 11);
                                nifty.getRenderEngine().renderQuad((int)selected.getMaxX()-5,(int)selected.getMaxY()-5, 10, 10);
                                graphics2D.setColor(Color.WHITE);
                                graphics2D.fillOval(selected.x-4, selected.y-4,8, 8);
                                graphics2D.setColor(Color.BLACK);
                                graphics2D.drawOval(selected.x-4, selected.y-4,8, 8);
                              }
                              graphics2D.setColor(Color.BLACK);
                              graphics2D.drawRect(0, 0, 800,600);
                              Toolkit.getDefaultToolkit().sync();
                              graphics2D.setTransform(transformer);
	                       graphics2D.dispose();
	                        frames++;
	
	                        long diff = System.currentTimeMillis() - time;
	                        if (diff >= 1000) {
	                              
	                                time += diff;
	                                fps="Fps: "+frames;
	                                frames = 0;
	                        }
    }
     public Nifty getNifty(){
         return nifty;
     }
     
     public void clearNifty(){
         Nifty temp = this.getNifty();
        for(String sel : temp.getAllScreensName()){
            temp.removeScreen(sel);
        }
     }
    
    protected void setClickListener(GuiSelectionListener list){
       
        this.removeMouseListener(previous);
        this.removeMouseMotionListener(previous);
        this.addMouseListener(list);
        this.addMouseMotionListener(list);
        this.addKeyListener(list);
        
        previous=list;
    }

    
    public void newGui(GUIEditor toChange) {
       this.manager = toChange;
       this.setClickListener( new GuiSelectionListener(toChange,popUp,this));
       this.selecting=false;
    }

    @Override
    public void update(Observable o, Object arg) {
        
        Action act = (Action) arg;
        if((act.getType()== Action.SEL || act.getType()== Action.UPDATE) && !act.getGUIElement().getType().equals(Types.LAYER)){
            this.selected.setBounds( act.getGUIElement().getBounds());
            this.selecting=true;
        }else if(act.getType()== Action.NEW){
            this.newGui(((GUIEditor)o));
            o.addObserver(this.previous);
        }else
            this.selecting=false;
    }
    
    public void moveRect(int x,int y){
        if(selected!=null){
            int dx=(int)(x-this.selected.getCenterX());
            int dy=(int)(y-this.selected.getCenterY());
            this.selected.translate(dx, dy);
        }
    }
    
    public void displayRect(int x,int y,int h,int w){
        this.selected.setBounds(x, y, w, h);
     
        this.selecting=true;
    }
    
    public void cancelRect(){
        this.selecting=false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
    
    
}
