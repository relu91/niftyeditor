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
package jada.ngeditor.model;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.window.WindowControl;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.model.elements.GWindow;
import jada.ngeditor.model.elements.specials.GUseControls;
import jada.ngeditor.model.elements.specials.GUseStyle;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Main model class it is a container for all GUI elements
 * @author cris
 */
@XmlRootElement(name="nifty")
public class GUI extends Observable{
    private static int GUIID = 0;
    private final Nifty manager;
    @XmlElementRef
    private LinkedList<GScreen> screens;
    private LinkedList<GLayer> currentlayers;
    
    private GScreen currentS;
    @XmlElement
    private GUseControls useControls = new GUseControls();
    @XmlElement
    private GUseStyle  useStyles = new GUseStyle();
    
    public GUI(){
        manager=null;
    }
    /**
     * Creates a new gui
     * @param nifty 
     */
    protected GUI(Nifty nifty) {
       this.manager = nifty;
       this.screens = new LinkedList<GScreen> ();
       this.currentlayers = new LinkedList<GLayer> ();
       this.currentS = null;  
       this.useControls.setFilename("nifty-default-controls.xml");
       this.useStyles.setFilename("nifty-default-styles.xml");
       this.GUIID++;
       
    }
    
    public void addScreen(GScreen screen){
       this.screens.add(screen);
       screen.createNiftyElement(manager);
       manager.gotoScreen(screen.getID());
    }
 
    public LinkedList<GScreen> getScreens(){
        return this.screens;
    }
    public void addElementToParent(GElement child,GElement parent){
        if (parent == null) {
            GScreen screen = (GScreen) child;
            this.screens.add(screen);
            this.currentS = screen;
        } else if (parent instanceof GScreen) {
            GLayer temp = (GLayer) child;
            this.currentlayers.add(temp);
            if (this.currentS != null) {
                parent.addChild(child, false);
            }
        } else {
            parent.addChild(child, false);
        }
        this.setChanged();
        this.notifyObservers(new Action(Action.ADD, child));
        this.clearChanged();
            
    }
    public boolean addElement(GElement child,GElement parent){
        try{
         parent.addChild(child, true);
         child.createNiftyElement(manager);
         return true;
        }catch(IllegalDropException e){
            child.removeFromParent();
            throw e;
        }
    }
    
   
    
    public void move(Point2D to,GElement toEle, GElement from){
        if(!toEle.equals(from)){
          de.lessvoid.nifty.elements.Element nTo ;
           if(toEle instanceof GWindow){
                nTo = toEle.getNiftyElement().getNiftyControl(WindowControl.class).getContent();
           }else
               nTo = toEle.getNiftyElement();
        if(toEle.getAttribute("childLayout").equals("absolute")){
            int parentX = toEle.getNiftyElement().getX();
                int parentY = toEle.getNiftyElement().getY();
                       
                from.addAttribute("x",""+ (int)(to.getX()-parentX)); 
                from.addAttribute("y",""+ (int)(to.getY()-parentY));
            if(!from.getParent().equals(toEle)){
                this.manager.moveElement(this.manager.getCurrentScreen(), from.getNiftyElement(), nTo, null);
            }
            from.lightRefresh();
        }else 
            this.manager.moveElement(this.manager.getCurrentScreen(), from.getNiftyElement(), nTo, null);
            from.removeFromParent();
            toEle.addChild(from, true);
            
        }
      }

   
    public void removeElement(GElement e){
        if(e instanceof GScreen){
            this.screens.remove(e);
            manager.removeScreen(e.getID());
        }
        else if(e instanceof GLayer){
            this.currentlayers.remove(e);
            manager.removeElement(manager.getCurrentScreen(), e.getNiftyElement());
        }
        else
            manager.removeElement(manager.getCurrentScreen(), e.getNiftyElement());
        e.removeFromParent();
    }
    
   public void reloadAfterFresh(){
        for(GScreen sel : this.screens){
            sel.reloadElement(manager);
        for(GElement lay : sel.getElements()){
            lay.reloadElement(manager);
            for(GElement ele : this.getAllChild(lay))
                ele.reloadElement(manager);
        }
        }
    }
    public LinkedList<GElement> getAllChild(GElement element){
         LinkedList<GElement> res = new LinkedList<GElement>();
         if(element == null)
             return res;
         for(GElement ele : element.getElements()){
               res.add(ele);
               res.addAll(getAllChild(ele));
          }
         return res;
    }
    
    public GScreen gettopScreen(){
        return this.screens.peekLast();
    }
    
  
    public void goTo(GScreen screen){
        this.manager.gotoScreen(screen.getID());
        manager.scheduleEndOfFrameElementAction(new de.lessvoid.nifty.elements.Action() {

            @Override
            public void perform() {
               manager.getCurrentScreen().getFocusHandler().resetFocusElements(); 
            }
        }
                , null);
              
    }
    @Override
    public String toString(){
        return "GUI: "+this.GUIID;
    }
    
    public GLayer getTopLayer(){
        return this.currentlayers.peekLast();
    }
    
    public Collection<GLayer> getLayers(){
     
        return this.currentlayers;
    }
    
    
    public void accept(Visitor visit){
        visit.visit(this); 
    }

    
}