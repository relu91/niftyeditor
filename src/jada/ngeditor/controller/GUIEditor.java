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
package jada.ngeditor.controller;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.listeners.actions.Action;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.exception.NoProductException;
import jada.ngeditor.persistence.GUIReader;
import jada.ngeditor.persistence.GUIWriter;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;



/**
 * Editor controller provides a list of method to interact with
 * @see GUI and its elements.
 * @author Cris
 */

public class GUIEditor extends Observable{
    private  GUI gui;
    private  GElement selected;
    private LinkedList<GLayer> currentlayers;
    private GScreen currentS;
    private GLayer  currentL;
    private GUIWriter writer ;
    private final ElementEditor eEditor;
    
    
    public GUIEditor(){
        currentlayers= new LinkedList<GLayer>();
        eEditor=new ElementEditor(this);
    }
    /**
     * Create a new empty gui with one screen
     * @param nifty a valid Nifty instace @see Nifty
     * @throws ParserConfigurationException if controller failed to create
     * a valid document instance
     */
    public void createNewGui(Nifty nifty) throws ParserConfigurationException{
        gui= GUIFactory.getInstance().createGUI(nifty);
        GScreen screen = (GScreen) GUIFactory.getInstance().newGElement(""+Types.SCREEN);
        getGui().addScreen(screen);
        this.currentS = screen;
        this.currentL = null;
        this.currentlayers.clear();
        this.setChanged();
        this.notifyObservers(new Action(Action.NEW,screen));
        this.clearChanged();
        writer = new GUIWriter(gui);
    }
    /**
     * Create a new gui from a file
     * @param nifty a valid Nifty instace @see Nifty
     * @throws ParserConfigurationException if controller failed to create
     * a valid document instance
     * @throws IOException
     * @throws  SAXException
     * @throws Exception if nifty can't create the gui
     * @return A string with the elements that weren't loaded
     * 
     */
    public String createNewGui(Nifty nifty ,File filename) throws ParserConfigurationException, IOException, SAXException, NoProductException, Exception{
       GUIReader reader = new GUIReader(nifty);
       String res = "";
       this.gui = reader.readGUI(filename);
       res = reader.getTagNotLoaded();
       final GScreen screen =this.getGui().gettopScreen();
       for(String sel : nifty.getAllScreensName()){
                    nifty.removeScreen(sel);
       }
      nifty.scheduleEndOfFrameElementAction(new Reload(nifty, screen.getID()), new EndNotify() {

            @Override
            public void perform() {
               setChanged();
               notifyObservers(new Action(Action.NEW,screen));
               clearChanged();
            }
        });
       currentL=gui.getTopLayer();
       currentS=gui.gettopScreen();
       currentlayers.addAll(gui.getLayers());
       return res;
            
    }
    
    
    public void saveGui(String filename) throws FileNotFoundException{
        writer.writeGUI(filename);
    }
    /**
     * Refresh the current gui . It causes a call to nifty.fromXml method. 
     * @param nifty
     * @throws Exception if nifty can't load the old gui
     */
    public void refresh(Nifty nifty) throws Exception{
        if(getGui() != null){
            String screenID =this.currentS.getID();
               for(String sel : nifty.getAllScreensName()){
                    nifty.removeScreen(sel);
               }
                nifty.scheduleEndOfFrameElementAction(new Reload(nifty, screenID), null);
                
                
        }
    }
    /**
     * 
     * @return true if there's no gui in the editor
     */
    public boolean isfirstGui(){
        return getGui()==null;
    }
    
    /**
     * Select an element in x and y screen coordinates
     * @param x
     * @param y 
     */
    public void selectElement(int x,int y){
        this.selected = findElement(new Point(x,y));
        this.setChanged();
        this.notifyObservers(new Action(Action.SEL,this.selected));
        this.clearChanged();
    }
    
    /**
     * Select a specific element
     * @param UID GElement to select
     */
    public void selectElement(GElement UID){
        if(UID.getType().equals(Types.SCREEN)){
            this.currentS = (GScreen) UID;
            this.currentlayers.clear();
            for(GElement lay : currentS.getElements()){
                this.currentlayers.add((GLayer)lay);
            }
            this.currentL = this.currentlayers.peekLast();
            this.getGui().goTo(currentS);
        }else if(UID.getType().equals(Types.LAYER)){
            this.currentL = (GLayer)UID;
        }
        this.selected=UID;
        this.setChanged();
        this.notifyObservers(new Action(Action.SEL,UID));
        this.clearChanged();
    }
    
    /**
     * Find the first GUI element by its id
     * @param id id attribute
     * @return null if there's no Element with that id
     */
    public GElement findElement(String id){
        GElement res = null;
        for(GScreen screen : this.gui.getScreens()){
            if(screen.getID().equals(id))
                return screen;
            for(GElement layer : screen.getElements()){
                if(layer.getID().equals(id))
                    return layer;
                for(GElement ele : this.gui.getAllChild(layer))
                    if(ele.getID().equals(id))
                        return ele;
            }
        }
        return res;
    }
    /**
     * Add element to selected or to last element added (not tested)
     * @param child
     * @return This editor useful for chains: editor.addElement(one,two).addElement(three);
     */
    public GUIEditor addElement(GElement child){
        return this.addElement(child, this.selected);
    }
    /**
     * Add Element to parent (not tested)
     * @param child
     * @param parent
     * @return This editor useful for chains: editor.addElement(one,two).addElement(two,three);
     */
    public GUIEditor addElement(GElement child,GElement parent){
        if(child.getType().equals(Types.SCREEN)){
            this.currentS = (GScreen) child;
            this.currentlayers.clear();
            for(GElement lay : currentS.getElements()){
                this.currentlayers.add((GLayer)lay);
            }
            this.getGui().addScreen(currentS);
            this.getGui().goTo(currentS);
        } else if(child.getType().equals(Types.LAYER)){
            if(parent.getType().equals(Types.SCREEN)){
                this.getGui().addElement(child,parent);
            }else
                throw new IllegalDropException("Can't add a layer to a simple element");
            GLayer temp =(GLayer) child;
            this.currentL=temp;
            this.currentlayers.add(temp);
        }
        else{
            if(findElement(parent.getID())==null){
                throw new IllegalDropException("Parent is not in the GUI");
            }
            this.getGui().addElement(child, parent);
        }
        this.setChanged();
        this.notifyObservers(new Action(Action.ADD,child));
        parent.getNiftyElement().layoutElements();
        this.selectElement(child);
        return this;
    }
    /**
     * Add an Element in a specific position
     * @param e element to add
     * @param mouse mouse position or screen position
     * 
     */
    public void addElement(GElement e,Point2D mouse){
        if(e.getType().equals(Types.SCREEN)){
            this.currentS = (GScreen) e;
            this.currentlayers.clear();
            for(GElement lay : currentS.getElements()){
                this.currentlayers.add((GLayer)lay);
            }
            this.getGui().addScreen(currentS);
            this.getGui().goTo(currentS);
        } else if(e.getType().equals(Types.LAYER)){
            if(this.currentS != null){
                this.getGui().addElement(e,this.currentS);
            }else
                throw new IllegalDropException("No screen!");
            GLayer temp =(GLayer) e;
            this.currentL=temp;
            this.currentlayers.add(temp);
            e.getParent().getNiftyElement().layoutElements();
        }
        else{
            if(this.currentlayers.isEmpty()){
                throw new IllegalDropException("No layer to drop in");
            }
            if(currentL.contains(mouse)){
            GElement result = findElement(mouse);
                this.getGui().addElement(e, result);
            String layout= result.getAttribute("childLayout");
            
            if(layout.equals("absolute")){
                int parentX = result.getNiftyElement().getX();
                int parentY = result.getNiftyElement().getY();    
                e.addAttribute("x",""+ (int)(mouse.getX()-parentX)); 
                e.addAttribute("y",""+ (int)(mouse.getY()-parentY));
                e.refresh();
            }
             e.getParent().getNiftyElement().layoutElements();
          }
        }
        this.setChanged();
        this.notifyObservers(new Action(Action.ADD,e));
        this.clearChanged();
       
    }
    
    public GScreen getCurrentScreen(){
        return this.currentS;
    }
    public GElement getSelected(){
        return this.selected;
    }
    
    
    
     public void removeSelected(){
        this.getGui().removeElement(selected);
        if(selected.getType().equals(Types.LAYER)){
            this.currentlayers.remove(selected);
            if(this.currentlayers.size() > 0){
                this.currentL = this.currentlayers.getLast();
               
            }else
                 this.currentL = null;
        }
        this.setChanged();
        this.notifyObservers(new Action(Action.DEL,selected));
        this.notifyObservers(new Action(Action.SEL,this.currentL));
        this.clearChanged();
        
    }
     
    public void reloadAfterFresh(){
        this.getGui().reloadAfterFresh();
    }
    public ElementEditor getElementEditor(){
        eEditor.setEdited(selected);
        return eEditor;
    }
    
    public ElementEditor getElementEditor(GElement toEdit){
        eEditor.setEdited(toEdit);
        return eEditor;
    }
    
    /**
     * move an element in points coordinates. 
     * @param to
     * @param from 
     */
    public void move(Point2D to,GElement from){
        if(from.getType().equals(""+Types.LAYER))
            return;
        GElement ele = findElement(to);
        if(ele.equals(from))
           getGui().move(to,ele.getParent(), from);
        else
          getGui().move(to,ele,from);
        this.setChanged();
        this.notifyObservers(new Action(Action.MOV,from));
        this.clearChanged();
    }

    /**
     * @return the gui
     */
    public GUI getGui() {
        return gui;
    }
    /**
     * Find the upper visible element in screen coordinates
     * @param point screen coordinate 
     * @return the upper element or if there's no one the upper layer visible
     */
    public GElement findElement(Point2D point){
            LinkedList<GElement> res = new LinkedList<GElement>();
                for(GElement ele : this.gui.getAllChild(currentL)){
                    if(ele.contains(point))
                        res.add(ele); 
                }

            GElement result = currentL;
            Rectangle minArea = currentS.getBounds();
            while(!res.isEmpty()){
                GElement temp = res.pop();
                Rectangle area = temp.getBounds();
                if(area.width <= minArea.width && area.height <= minArea.height) {
		    result = temp;
		    minArea = area;
		}
            }  
        return result;
    }
    /**
     * 
     * Get Nifty element from a GElement
     * @param id
     * @return Nifty element inside the GElement
     */
    public Element getNiftyElement(String id){
        return this.findElement(id).getNiftyElement();
    }
    
    /**
     * Util method that wraps Element.findNiftyControl
     * @see Element#findNiftyControl(java.lang.String, java.lang.Class) 
     * @param <T>
     * @param id
     * @param request
     * @return 
     */
    public <T extends NiftyControl> T getNiftyControl(String id , Class<T> request){
        return this.findElement(id).getNiftyElement().findNiftyControl(id, request);
    }
    
    /**
     * Notify all observers that an element it's been updated
     * @param sel updated gui element
     */
    public void fireUpdate(GElement sel){
        this.setChanged();
        this.notifyObservers(new Action(Action.UPDATE,sel));
        this.clearChanged();
    }
    
    public GLayer getCurrentLayer(){
        return this.currentL;
    }
    @Override
    public String toString(){
        return this.gui.toString();
    }
    
    private class Reload implements de.lessvoid.nifty.elements.Action{
        private final Nifty nifty;
        private final String screen;
        
        private Reload(Nifty nifty,String screen){
            this.nifty = nifty;
            this.screen = screen;
            
        }

        @Override
        public void perform() {
            try {
                nifty.fromXml(""+getGui(),writer.getDocumentStream() ,
                             this.screen);
                reloadAfterFresh();
                nifty.getCurrentScreen().getFocusHandler().resetFocusElements();
            } catch (Exception ex) {
                Logger.getLogger(GUIEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
