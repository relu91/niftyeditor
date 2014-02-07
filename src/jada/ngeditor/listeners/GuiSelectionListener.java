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
package jada.ngeditor.listeners;

import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.layout.align.HorizontalAlign;
import de.lessvoid.nifty.layout.align.VerticalAlign;
import de.lessvoid.nifty.tools.SizeValue;
import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.guiviews.J2DNiftyView;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.TransferHandler;

/**
 *
 * @author cris
 */
public class GuiSelectionListener extends MouseAdapter implements ActionListener,Observer,KeyListener{
    private final byte DIR_N=0;
    private final byte DIR_E=1;
    private final byte DIR_S=2;
    private final byte DIR_W=3;
    private final byte DIR_SE=4;
    private final byte NOP=-1;
    private byte curDir;
    private GUIEditor gui;
    private Timer hold;
    private MouseEvent toServe;
    private  JPopupMenu p;
    private boolean enable;
    private Rectangle selected;
    private boolean selecting;
    private boolean dragging;
    private J2DNiftyView v;
    
    

    public GuiSelectionListener(GUIEditor currentGUI) {
        this.gui = currentGUI;
        enable =true;
        
    }

    public GuiSelectionListener(GUIEditor toChange, JPopupMenu po,J2DNiftyView view) {
        this.gui=toChange;
        this.p=po;
        this.v=view;
        enable =true;
        this.selected=new Rectangle();
    }
    
   
    public final void startDrag(MouseEvent e) {
        if(enable){
        JPanel c = (JPanel) e.getComponent().getParent();
       // this.gui.selectElement(e.getX(), e.getY());
        TransferHandler handler = c.getTransferHandler();
        handler.exportAsDrag(c, e, TransferHandler.MOVE);
        
        }
      
    }
    public GElement getSelected(){
        return gui.getSelected();
    }
     @Override
    public void mousePressed(MouseEvent e) {
         if(enable){
           toServe = e;
        //   this.gui.selectElement(e.getX(), e.getY());
           hold = new Timer(225, this);
           hold.setRepeats(false);
           hold.start();
           
         }
          if(e.isPopupTrigger()){
              this.gui.selectElement(e.getX(), e.getY());
                this.p.show(e.getComponent(), e.getX(), e.getY());
          }
        
     }

        public void mouseReleased(MouseEvent e) {
            if(enable){
            if (hold != null) {
                hold.stop();
            }
          
           
           }
             if(e.isPopupTrigger()){
                 this.gui.selectElement(e.getX(), e.getY());
                this.p.show(e.getComponent(), e.getX(), e.getY()); 
             }
        if(dragging){
            GElement sel = this.gui.getSelected();
         if(sel!=null && this.selected!=null){
            sel.lightRefresh();
            this.selected.setRect(sel.getBounds());
            this.enable();
            this.gui.selectElement(this.getSelected());
          }
         }
        }
     @Override
     public void mouseClicked(MouseEvent e) {
           this.gui.selectElement(e.getX(), e.getY());
           
     }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.toServe!=null)
           this.startDrag(toServe);
        toServe=null;
        
    }
    
    public void disable(){
        this.enable=false;
    }
    
    public void enable(){
        this.enable=true;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        
       if(this.selecting){
           this.disable();
           if(e.getX()>selected.getMaxX()-5 && e.getX()<selected.getMaxX()+5 
                   && e.getY()>selected.getMaxY()-5
                   && e.getY()<selected.getMaxY()+5
                   ){
               e.getComponent().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
               curDir=DIR_SE;
               
           }else if(e.getX()==selected.getMinX() && (e.getY()<selected.getMaxY() && e.getY()>selected.getMinY() )){
               e.getComponent().setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
               curDir=DIR_W;
           }else if(e.getX()==selected.getMaxX() && (e.getY()<selected.getMaxY() && e.getY()>selected.getMinY() )){
              e.getComponent().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
              curDir=DIR_E;
           }
           else if(e.getY()<selected.getMaxY()+5 && e.getY()>selected.getMaxY()-5 
                   && (e.getX()<selected.getMaxX() && e.getX()>selected.getMinX() )){
                e.getComponent().setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); 
                curDir=DIR_S;
           }
            else if(e.getY()==selected.getMinY() && (e.getX()<selected.getMaxX() && e.getX()>selected.getMinX() )){
                curDir=DIR_N;
                e.getComponent().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); 
       }else if(e.getY()<selected.getCenterY()+10 &&
               e.getY()>selected.getCenterY()-10 && (e.getX()<(selected.getCenterX()+10) && e.getX()>selected.getCenterX()-10 )){
                 e.getComponent().setCursor(new Cursor(Cursor.MOVE_CURSOR)); 
                 this.enable();
                 
                 curDir = NOP;
            }
            else{
                e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                curDir=NOP;
            }
          }else{
            e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
           curDir=NOP;
           
       }
        
    }
    
        @Override
    public void mouseDragged(MouseEvent e) {
        if(this.selecting){
            int to ;
            this.dragging=true;
            switch (curDir){
                case DIR_E:
                    to=(int) (e.getX() - this.selected.getMaxX());
                    if((this.selected.width+to)>0){
                    this.selected.width+=to;
                    this.gui.getSelected().getNiftyElement().setWidth(selected.width);
                    this.gui.getSelected().addAttribute("width",""+selected.width+"px" );
                    }
                    break;
                case DIR_W:
                    to=(int) (this.selected.getMinX()-e.getX());
                     if((this.selected.width+to)>0){
                    this.selected.x=e.getX();
                    this.selected.width+=to;
                     if(this.gui.getSelected().getParent().getAttribute("childLayout").equals("absolute")){
                        int x = gui.getSelected().getParent().getNiftyElement().getX();
                        this.gui.getSelected().addAttribute("x",""+(e.getX()-x)+"px" );
                        this.gui.getSelected().getNiftyElement().setConstraintX(SizeValue.px(e.getX()-x));
                        this.gui.getSelected().getParent().getNiftyElement().layoutElements();
                          this.gui.getSelected().getNiftyElement().setWidth(selected.width);
                    }
                    this.gui.getSelected().addAttribute("width",""+selected.width+"px" );
                   
                    }
                    
                    break;
                 case DIR_S:
                    to=(int) (e.getY()-this.selected.getMaxY());
                     if((this.selected.height+to)>0){
                    this.selected.height+=to;
                    
                    this.gui.getSelected().getNiftyElement().setHeight(selected.height);
                    this.gui.getSelected().addAttribute("height",""+selected.height+"px" );
                     }
                    break;
                  case DIR_SE:
                    to=(int) (e.getX() - this.selected.getMaxX());
                    int toy=(int) (e.getY() - this.selected.getMaxY());
                    if(((this.selected.width+to)>0) &&  (this.selected.height+to)>0){
                    if(e.isControlDown())
                         this.selected.height+=to;
                    else
                         this.selected.height+=toy;
                  
                    this.gui.getSelected().getNiftyElement().setHeight(selected.height);
                    this.gui.getSelected().addAttribute("height",""+selected.height+"px" );
                   
                    this.selected.width+=to;
                   
                    this.gui.getSelected().getNiftyElement().setWidth(selected.width);
                    this.gui.getSelected().addAttribute("width",""+selected.width+"px" );
                    
                    if(e.isControlDown()){
                        Point gtry = new Point((int)selected.getMaxX(),(int)selected.getMaxY());
                      //  SwingUtilities.convertPointToScreen(gtry, this);
                       // mouseBot.mouseMove(gtry.x,gtry.y);
                    }
                
                    }
                    break;
                   case DIR_N:
                    to=(int) (this.selected.getMinY()-e.getY());
                     if((this.selected.height+to)>0){
                    this.selected.height+=to;
                    this.selected.y=e.getY();
                    
                    this.gui.getSelected().addAttribute("height",""+selected.height+"px" );
                    if(this.gui.getSelected().getParent().getAttribute("childLayout").equals("absolute")){
                        int y = gui.getSelected().getParent().getNiftyElement().getY();
                        gui.getSelected().getNiftyElement().setConstraintY(SizeValue.px(e.getY()-y));
                          this.gui.getSelected().getParent().getNiftyElement().layoutElements();
                         this.gui.getSelected().getNiftyElement().setHeight(selected.height);
                        this.gui.getSelected().addAttribute("y",""+(e.getY()-y)+"px" );
                    }
                    
                    }
                    break;
                   default:
                       dragging=false;
                 
                
            }
       v.displayRect(selected.x, selected.y, selected.height, selected.width);
        this.gui.getSelected().getNiftyElement().layoutElements();
       
        
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Action act = (Action) arg;
        if(act.getType()== Action.SEL && !act.getGUIElement().getType().equals(Types.LAYER)){
            this.selected.setBounds( act.getGUIElement().getBounds());
            this.selecting=true;
        }else if(act.getType()== Action.NEW){
            this.gui = (((GUIEditor)o));
            this.selecting=false;
           
        }else{
            this.selecting=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
            GElement sel = gui.getSelected();
            String layout = sel.getParent().getAttribute("childLayout");
            if(layout.equals("horizontal"))
                horizontalBeahvior(sel,e.getKeyCode());
            else if(layout.equals("vertical"))
                verticalBeahvior(sel,e.getKeyCode());
            else
                absoluteBehavior(sel,e.getKeyCode());
        this.gui.fireUpdate(sel);
     
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void absoluteBehavior(GElement sel,int key){
        Element parent = sel.getParent().getNiftyElement();
        int xp = parent.getX();
        int yp = parent.getY();
        int totalPaddingHorz = parent.getPaddingLeft().getValueAsInt(parent.getWidth());
        int totalPaddingVert = parent.getPaddingTop().getValueAsInt(parent.getHeight());
        int x = sel.getNiftyElement().getX()-(xp+totalPaddingHorz);
        int y = sel.getNiftyElement().getY()-(yp+totalPaddingVert);
        if(key==KeyEvent.VK_DOWN){
            y++;
            sel.addAttribute("y", ""+y);
            sel.lightRefresh();
        }else  if(key==KeyEvent.VK_UP){
            y--;
            sel.addAttribute("y", ""+y);
            sel.lightRefresh();
        }else  if(key==KeyEvent.VK_LEFT){
            x--;
            sel.addAttribute("x", ""+x);
            sel.lightRefresh();
        }else  if(key==KeyEvent.VK_RIGHT){
            x++;
            sel.addAttribute("x", ""+x);
            sel.lightRefresh();
        }
        v.displayRect(x, y,sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth() );
      this.selected.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(),sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight() );
    }
    
    private void horizontalBeahvior(GElement sel,int key){
         VerticalAlign current=null;
         int index =0;
        try{
         current =  VerticalAlign.valueOf(sel.getAttribute("valign"));
         index = current.ordinal();
        }catch(IllegalArgumentException e){
            current =  VerticalAlign.verticalDefault;
            index=1;
        }
       
        if(key==KeyEvent.VK_UP && index>1){
          index--;
        VerticalAlign  newAlign= VerticalAlign.values()[index];
          sel.addAttribute("valign", newAlign.name());
          sel.lightRefresh();
        }else  if(key==KeyEvent.VK_DOWN && index < VerticalAlign.values().length){
          index++;
         VerticalAlign  newAlign= VerticalAlign.values()[index];
          sel.addAttribute("valign", newAlign.name());
          sel.lightRefresh();
        }else {
         index = sel.getParent().getNiftyElement().getElements().indexOf(sel.getNiftyElement());
         if(key==KeyEvent.VK_RIGHT && ++index<sel.getParent().getNiftyElement().getElements().size()){
            
            sel.setIndex(index);
            sel.lightRefresh();
        }else  if(key==KeyEvent.VK_LEFT && --index>=0){
            sel.setIndex(index);
            sel.lightRefresh();
        }
        }
        v.displayRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(),sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth() );
        this.selected.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(),sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight() );
    }
    
    private void verticalBeahvior(GElement sel,int key){
         HorizontalAlign current=null;
         int index =0;
        try{
         current = HorizontalAlign .valueOf(sel.getAttribute("align"));
         index = current.ordinal();
        }catch(IllegalArgumentException e){
            current = HorizontalAlign.horizontalDefault;
            index=1;
        }
       
        if(key==KeyEvent.VK_LEFT && index>1){
          index--;
         HorizontalAlign  newAlign= HorizontalAlign.values()[index];
          sel.addAttribute("align", newAlign.name());
          sel.lightRefresh();
        }else  if(key==KeyEvent.VK_RIGHT && index < HorizontalAlign.values().length){
          index++;
         HorizontalAlign  newAlign= HorizontalAlign.values()[index];
          sel.addAttribute("align", newAlign.name());
          sel.lightRefresh();
        }else {
            index = sel.getParent().getNiftyElement().getElements().indexOf(sel.getNiftyElement());
            if(key==KeyEvent.VK_DOWN && ++index<sel.getParent().getNiftyElement().getElements().size()){
            sel.setIndex(index);
            sel.lightRefresh();
        }else  if(key==KeyEvent.VK_UP && --index>=0){
            sel.setIndex(index);
            sel.lightRefresh();
        }
        }
        v.displayRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(),sel.getNiftyElement().getHeight(), sel.getNiftyElement().getWidth() );
        this.selected.setRect(sel.getNiftyElement().getX(), sel.getNiftyElement().getY(),sel.getNiftyElement().getWidth(), sel.getNiftyElement().getHeight() );
    }
}
