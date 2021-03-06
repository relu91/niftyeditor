/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author cris
 */
public class ImageModeCanvas extends javax.swing.JPanel {
    private ImageModeModel model;
    private final Image image;
    private ImageModePresenter painter;
    /**
     * Creates new form ImageModeCanvas
     */
    public ImageModeCanvas(ImageModeModel model) {
        initComponents();
        this.model = model;
        MediaTracker tracker = new MediaTracker(this);
        this.image = Toolkit.getDefaultToolkit().createImage(this.model.getImage());
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(ImageModeCanvas.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.painter = new ResizePainter((ResizeImageMode)model, image.getWidth(null), image.getHeight(null));
        this.addMouseListener(painter);
        this.addMouseMotionListener(painter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.image.getWidth(null), this.image.getHeight(null));
        g.drawImage(this.image, 0, 0, null);
        g.clipRect(0, 0, this.image.getWidth(null), this.image.getHeight(null));
        painter.paint(g);
       
    }
    
    public static void main(String[] args) {
        JFrame pr = new JFrame("BALDFA");
        pr.getContentPane().add(new ImageModeCanvas(new ResizeImageMode("C:/Users/cris/Documents/JMonkeyProject/BasicGame/assets/Interface/Leaf.png","","resize:15,2,15,15,15,2,15,2,15,2,15,15")));
        pr.setVisible(true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private static interface ImageModePresenter extends MouseListener,MouseMotionListener {

        public abstract void paint(Graphics g);
    }
    
    private  class SubImagePainter extends MouseAdapter implements ImageModePresenter{
        private final SubImageModel model;
        private final byte DIR_N=0;
        private final byte DIR_E=1;
        private final byte DIR_S=2;
        private final byte DIR_W=3;
        private final byte DIR_SE=4;
        private final byte MOV=5;
        private final byte NOP=-1;
         private byte curDir;
         
        public SubImagePainter(SubImageModel model) {
            this.model = model;
        }
        
        
        @Override
        public void paint(Graphics g) {
            Rectangle temp = model.getRectangle();
            g.setColor(new Color(255, 0, 0, 70));
            g.fillRect(temp.x, temp.y, temp.width, temp.height);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Rectangle selected = this.model.getRectangle();
            int to;
            switch (curDir) {
                case DIR_E:
                    to = (int) (e.getX() - selected.getMaxX());
                    if ((selected.width + to) > 0) {
                        selected.width += to;
                    }
                    break;
                case DIR_W:
                    to = (int) (selected.getMinX() - e.getX());
                    if ((selected.width + to) > 0) {
                        selected.x = e.getX();
                        selected.width += to;

                    }

                    break;
                case DIR_S:
                    to = (int) (e.getY() - selected.getMaxY());
                    if ((selected.height + to) > 0) {
                        selected.height += to;
                    }
                    break;
                case DIR_SE:
                    to = (int) (e.getX() - selected.getMaxX());
                    int toy = (int) (e.getY() - selected.getMaxY());
                    if (((selected.width + to) > 0) && (selected.height + to) > 0) {
                        if (e.isControlDown()) {
                            selected.height += to;
                        } else {
                            selected.height += toy;
                        }
                        selected.width += to;

                    }
                    break;
                case DIR_N:
                    to = (int) (selected.getMinY() - e.getY());
                    if ((selected.height + to) > 0) {
                        selected.height += to;
                        selected.y = e.getY();
                    }
                    break;
                case MOV:
                    selected.x = e.getX() - selected.width/2;
                    selected.y = e.getY() - selected.height/2;
                    break;
                default:



            }
            updateUI();
        }  
        @Override
        public void mouseMoved(MouseEvent e) {
            
            Rectangle selected = this.model.getRectangle();
            if(e.getX()>selected.getMaxX()-5 && e.getX()<selected.getMaxX()+5 
                   && e.getY()>selected.getMaxY()-5
                   && e.getY()<selected.getMaxY()+5
                   ){
               
               e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
               curDir=DIR_SE;
           }else if(e.getX()==selected.getMinX() && (e.getY()<selected.getMaxY() && e.getY()>selected.getMinY() )){
               e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
               curDir=DIR_W;
           }else if(e.getX()==selected.getMaxX() && (e.getY()<selected.getMaxY() && e.getY()>selected.getMinY() )){
              e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
              curDir=DIR_E;
           }
           else if(e.getY()<selected.getMaxY()+5 && e.getY()>selected.getMaxY()-5 
                   && (e.getX()<selected.getMaxX() && e.getX()>selected.getMinX() )){
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR)); 
                curDir=DIR_S;
           }
            else if(e.getY()==selected.getMinY() && (e.getX()<selected.getMaxX() && e.getX()>selected.getMinX() )){
                curDir=DIR_N;
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR)); 
       }else if(e.getY()<selected.getCenterY()+10 &&
               e.getY()>selected.getCenterY()-10 && (e.getX()<(selected.getCenterX()+10) && e.getX()>selected.getCenterX()-10 )){
                 e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); 
                 curDir = MOV;
            }
            else{
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
                curDir=NOP;
            }
          }
         }
    
    private  class SpritePainter extends MouseAdapter implements ImageModePresenter{
        private final SpriteImageMode mode;
        private final int imgWidth;
        private final int imgHeight;
        public SpritePainter(SpriteImageMode mode,int imgWidth,int imgHeight) {
            this.mode = mode;
            this.imgWidth = imgWidth;
            this.imgHeight = imgHeight;
            
        }
        
        @Override
        public void paint(Graphics g) {
            for(int i=0;i<imgHeight;i+=mode.getHeigth()){
                g.drawLine(0, i, this.imgWidth, i);
            }
            for(int i=0;i<imgWidth;i+=mode.getWidth()){
                g.drawLine(i, 0, i, this.imgHeight);
            }
            int ind = mode.getIndex();
            int max_index = this.imgWidth/mode.getWidth();
            int x = (ind%max_index)*mode.getWidth();
            int y = (ind/max_index)*mode.getHeigth();
            g.setColor(new Color(255, 0, 0, 70));
            g.fillRect(x, y, mode.getWidth(),mode.getHeigth());
        }

     
        
    }
    
     private  class ResizePainter extends MouseAdapter implements ImageModePresenter{
        private final ResizeImageMode mode;
        private final int imgWidth;
        private final int imgHeight;

        public ResizePainter(ResizeImageMode mode,int imgWidth,int imgHeight) {
            this.mode = mode;
            this.imgWidth = imgWidth;
            this.imgHeight = imgHeight;
        }
         
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLUE);
            g.drawLine(mode.getX0(), 0,mode.getX0(),imgHeight );
            g.fillPolygon(new int[]{mode.getX0()+5,mode.getX0()-5,mode.getX0()}, new int[]{0,0,5}, 3);
            
            g.setColor(Color.GREEN);
             int x = this.imgWidth-mode.getX2();
             g.fillPolygon(new int[]{x+5,x-5,x}, new int[]{0,0,5}, 3);
            g.drawLine(x, 0, x,imgHeight );
            
            g.setColor(Color.RED);
            g.drawLine(0, mode.getY0(),imgWidth, mode.getY0() );
            g.fillPolygon( new int[]{0,0,5},new int[]{mode.getY0()+5,mode.getY0()-5,mode.getY0()}, 3);
            
            g.setColor(Color.PINK);
            g.drawLine(0, mode.getY2(),imgWidth, mode.getY2() );
            g.fillPolygon( new int[]{0,0,5},new int[]{mode.getY2()+5,mode.getY2()-5,mode.getY2()}, 3);
            
            g.setColor(Color.YELLOW);
            g.drawLine(mode.getX6(), 0,mode.getX6(),imgHeight );
            g.fillPolygon(new int[]{mode.getX6()+5,mode.getX6()-5,mode.getX6()}, new int[]{0,0,5}, 3);
            
            g.setColor(Color.BLACK);
            int x1 = this.imgWidth-mode.getX8();
            g.fillPolygon(new int[]{x1+5,x1-5,x1}, new int[]{0,0,5}, 3);
            g.drawLine(x1, 0, x1,imgHeight );
           
            
        }
         
     }
}
