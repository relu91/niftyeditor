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
import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cris
 */
public class J2DNiftyView extends javax.swing.JPanel implements GraphicsWrapper,Observer{
    private final byte DIR_N=0;
    private final byte DIR_E=1;
    private final byte DIR_S=2;
    private final byte DIR_W=3;
    private final byte DIR_SE=4;
    private final byte NOP=-1;
    protected Nifty nifty;
    private  Canvas canvas;
    private  long fps = 0;
    private boolean dragging,selecting;
    private Graphics2D graphics2D;
    private GuiSelectionListener previous;
    private byte curDir;
    private Rectangle selected;
    AffineTransform transformer = new AffineTransform();
    private GUIEditor manager;
    private Robot mouseBot;
    /**
     * Creates new form J2DNiftyView
     */
      
    public J2DNiftyView(int width , int height) {
        initComponents();
        this.setSize(new Dimension(width,height));
        previous=null;
        dragging=false;
        selecting=false;
        curDir=-1;
        
        this.selected= new Rectangle();
        try {
            mouseBot =new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(J2DNiftyView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollBar2 = new javax.swing.JScrollBar();

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

        setLayout(new java.awt.BorderLayout());

        jScrollBar1.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        add(jScrollBar1, java.awt.BorderLayout.PAGE_END);
        add(jScrollBar2, java.awt.BorderLayout.LINE_END);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DeleteButton;
    private javax.swing.JMenuItem HideButton;
    private javax.swing.JMenuItem NormalizeButton;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JPopupMenu popUp;
    // End of variables declaration//GEN-END:variables

    @Override
    public Graphics2D getGraphics2d() {
        return this.graphics2D;
    }

    private void registerFonts(FontProviderJava2dImpl fontProvider) {
        fontProvider.addFont("aurulent-sans-16.fnt", new Font("aurulent-sans-16",
	                                Font.ROMAN_BASELINE, 16));
        
    }

    public void init() {
        InputSystemAwtImpl inputSystem = new InputSystemAwtImpl();
	canvas = new Canvas(){
            public void addNotify() {
    super.addNotify();
    createBufferStrategy(2);
}
        };
        canvas.setSize(this.getSize());
        //canvas.addMouseMotionListener(inputSystem);
       
	//canvas.addMouseListener(inputSystem);
	//canvas.addKeyListener(inputSystem);
        this.add(canvas,BorderLayout.EAST);
       // this.setMaximumSize(new Dimension(this.getWidth(),this.getHeight()));
        this.setIgnoreRepaint(true);
	canvas.setIgnoreRepaint(true);
        this.jScrollBar1.setVisibleAmount(60);
       // canvas.createBufferStrategy(2);
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
        nifty.resolutionChanged();
     //   canvas.addMouseMotionListener(this);
      //  canvas.addMouseListener(this);
        
    }
   
     public void start() {
         BasicStroke stroke = new BasicStroke(2,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,30,new float[] { 10.0f, 8.0f },0);
	
         boolean done = false;
	 long time = System.currentTimeMillis();
	 long frames = 0;
        // this.canvas.setSize((int)(this.getHeight()*1), (int)(this.getWidth()*1));
         Font fpsFont =new Font("arial",Font.BOLD, 14);
         String fps="Fps: 0";
	       while (!done) {
                    try{
	            BufferStrategy bufferStrategy = canvas.getBufferStrategy();
	           graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
                               graphics2D.setBackground(Color.BLACK);
                               graphics2D.scale(1, 1);
                               
                               done = nifty.update();
                               nifty.render(true);
                               nifty.getRenderEngine().renderQuad(0, 0, 0, 0);
                              if(nifty.isDebugOptionPanelColors()){
                                graphics2D.setColor(java.awt.Color.red);
                                graphics2D.setFont(fpsFont);
                                graphics2D.drawString(fps, 0, fpsFont.getSize());
                              }
                              if(selecting){
                                graphics2D.drawLine((int)selected.getCenterX()-10, (int)selected.getCenterY(), (int)selected.getCenterX()+10,(int) selected.getCenterY());
                                graphics2D.drawLine((int)selected.getCenterX(), (int)selected.getCenterY()-10, (int)selected.getCenterX(),(int) selected.getCenterY()+10);
                                
                                
                                graphics2D.setColor(java.awt.Color.red);
                                graphics2D.setStroke(stroke);
                                graphics2D.draw(selected);
                                graphics2D.setColor(java.awt.Color.black);
                                graphics2D.setStroke(new BasicStroke());
                                graphics2D.drawRect((int)selected.getMaxX()-6,(int)selected.getMaxY()-6, 11, 11);
                                nifty.getRenderEngine().renderQuad((int)selected.getMaxX()-5,(int)selected.getMaxY()-5, 10, 10);
                                
                                
                                
                                
                              }
                              graphics2D.setTransform(transformer);
                              bufferStrategy.show();
                               
	                       graphics2D.dispose();
	                       graphics2D = null;
	                        frames++;
	
	                        long diff = System.currentTimeMillis() - time;
	                        if (diff >= 1000) {
	                              
	                                time += diff;
	                                fps="Fps: "+frames;
	                                frames = 0;
	                        }
                               
             try {
                 Thread.sleep(15);
             } catch (InterruptedException ex) {
                 Logger.getLogger(J2DNiftyView.class.getName()).log(Level.SEVERE, null, ex);
             }
             
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
                                
	    }
     }
    @Override
     public int getHeight(){
         return this.canvas.getHeight();
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
       
        this.canvas.removeMouseListener(previous);
        this.canvas.removeMouseMotionListener(previous);
        this.canvas.addMouseListener(list);
        this.canvas.addMouseMotionListener(list);
        this.canvas.addKeyListener(list);
        
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
        if(act.getType()== Action.SEL && !act.getGUIElement().getType().equals(Types.LAYER)){
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
    
    
}
