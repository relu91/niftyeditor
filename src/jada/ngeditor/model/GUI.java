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


import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Action;
import de.lessvoid.nifty.tools.resourceloader.FileSystemLocation;
import jada.ngeditor.listeners.events.AddElementEvent;
import jada.ngeditor.listeners.events.RemoveElementEvent;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.model.elements.specials.GUseControls;
import jada.ngeditor.model.elements.specials.GUseStyle;
import jada.ngeditor.model.exception.IllegalDropException;
import jada.ngeditor.model.visitor.Visitor;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Main model class it is a container for all GUI elements
 *
 * @author cris
 */
@XmlRootElement(namespace = "", name = "nifty")
public class GUI extends Observable {

    private static int GUIID = 0;
    private final Nifty manager;
    private FileSystemLocation assets;
    @XmlTransient
    private File assetsFile;
    @XmlAttribute
    private final String xmlns = "http://nifty-gui.lessvoid.com/nifty-gui";

    public Nifty getNifty() {
        return manager;
    }
    @XmlElement
    private ArrayList<GUseControls> useControls = new ArrayList<GUseControls>();
    @XmlElement
    private ArrayList<GUseStyle> useStyles = new ArrayList<GUseStyle>();
    @XmlElementRef
    private LinkedList<GScreen> screens;
    private GScreen currentS;
    private final Selection selection;
    private GLayer currentL;
    private int ID;
    public GUI() {
        manager = null;
        selection = null;
        ID = GUI.GUIID++;
    }

    /**
     * Creates a new gui
     *
     * @param nifty
     */
    protected GUI(Nifty nifty) {
        this.manager = nifty;
        this.screens = new LinkedList<GScreen>();
        this.currentS = null;
        ID = GUI.GUIID++;
        this.assetsFile = new File(".");
        this.selection = new Selection();
    }

    public void addScreen(final GScreen screen) {
        this.screens.add(screen);
        screen.createNiftyElement(manager);
        this.goTo(screen);
        this.setChanged();
        this.notifyObservers();
        //Needed to add other children after nifty has changed the screen.
        if (!screen.getElements().isEmpty()) {
            manager.scheduleEndOfFrameElementAction(new Action() {
                @Override
                public void perform() {
                    for (GElement child : screen.getElements()) {
                        recorsiveCreateNiftyElement(child);
                    }
                }
            }, null);
        }
    }

    public LinkedList<GScreen> getScreens() {
        return this.screens;
    }

    public void addElementToParent(GElement child, GElement parent) {
        if (parent == null) {
            GScreen screen = (GScreen) child;
            this.screens.add(screen);
            this.currentS = screen;
        } else if (parent instanceof GScreen) {
            GLayer temp = (GLayer) child;
            if (this.currentS != null) {
                parent.addChild(child, false);
            }
        } else {
            parent.addChild(child, false);
        }
        this.setChanged();
        this.notifyObservers(new AddElementEvent(child));

    }

    public boolean addElement(GElement child, GElement parent) {
        try {
            parent.addChild(child, true);
            this.recorsiveCreateNiftyElement(child);
            this.setChanged();
            this.notifyObservers(new AddElementEvent(child));
            return true;
        } catch (IllegalDropException e) {
            child.removeFromParent();
            throw e;
        }
    }

    private void recorsiveCreateNiftyElement(GElement element) {
        element.createNiftyElement(manager);
        for (GElement child : element.getElements()) {
            this.recorsiveCreateNiftyElement(child);
        }
    }

    public void move(Point2D to, GElement toEle, GElement from, EndNotify callback) {
        if (!toEle.equals(from)) {
            de.lessvoid.nifty.elements.Element nTo = toEle.getDropContext();
            if (toEle.getAttribute("childLayout").equals("absolute")) {
                int parentX = toEle.getNiftyElement().getX();
                int parentY = toEle.getNiftyElement().getY();
                from.addAttribute("x", "" + (int) (to.getX() - parentX));
                from.addAttribute("y", "" + (int) (to.getY() - parentY));
                this.manager.moveElement(this.manager.getCurrentScreen(), from.getNiftyElement(), nTo, callback);
                from.lightRefresh();
            } else {
                this.manager.moveElement(this.manager.getCurrentScreen(), from.getNiftyElement(), nTo, callback);
            }
            from.removeFromParent();
            toEle.addChild(from, true);
            this.setChanged();
            this.notifyObservers(new RemoveElementEvent(from));
            this.setChanged();
            this.notifyObservers(new AddElementEvent(from));

        }
    }

    public void removeElement(GElement e) {
        if (e instanceof GScreen) {
            this.screens.remove(e);
            manager.removeScreen(e.getID());
        } else if (e instanceof GLayer) {
            manager.removeElement(manager.getCurrentScreen(), e.getNiftyElement());
        } else {
            manager.removeElement(manager.getCurrentScreen(), e.getNiftyElement());
        }
        e.removeFromParent();
        this.setChanged();
        this.notifyObservers(new RemoveElementEvent(e));
    }

    public void reloadAfterFresh() {
        for (GScreen sel : this.screens) {
            sel.reloadElement(manager);
            for (GElement lay : sel.getElements()) {
                lay.reloadElement(manager);
                for (GElement ele : this.getAllChild(lay)) {
                    ele.reloadElement(manager);
                }
            }
        }
    }

    public LinkedList<GElement> getAllChild(GElement element) {
        LinkedList<GElement> res = new LinkedList<GElement>();
        if (element == null) {
            return res;
        }
        for (GElement ele : element.getElements()) {
            res.add(ele);
            res.addAll(getAllChild(ele));
        }
        return res;
    }

    public GScreen gettopScreen() {
        return this.screens.peekLast();
    }

    public void goTo(GScreen screen) {
        this.manager.gotoScreen(screen.getID());
        manager.scheduleEndOfFrameElementAction(new de.lessvoid.nifty.elements.Action() {
            @Override
            public void perform() {
                manager.getCurrentScreen().getFocusHandler().resetFocusElements();
            }
        }, null);
        this.currentS = screen;

    }

    @Override
    public String toString() {
        return "GUI: " + this.ID;
    }

    public int getGUIid() {
        return this.ID;
    }

    public GLayer getTopLayer() {
        int last = this.currentS.getLayers().size() - 1;
        return this.currentS.getLayers().get(last);
    }

    public Collection<GLayer> getLayers() {
        return this.currentS.getLayers();
    }

    public void accept(Visitor visit) {
        visit.visit(this);
    }

    /**
     * Set asset folder for this gui . All the resources of this gui should be
     * inside this particular folder.
     *
     * @param f
     */
    public void setAssetFolder(File f) {
        //remove previous assets
        manager.getResourceLoader().removeResourceLocation(assets);
        assets = new FileSystemLocation(f);
        this.assetsFile = f;
        manager.getResourceLoader().addResourceLocation(assets);
    }

    @XmlTransient
    public File getAssetFolder() {
        return assetsFile;
    }
    /**
     * Simple add UseControls in the gui hiearchy
     * @param controls 
     */
    public void addUseControls(GUseControls controls) {
        this.useControls.add(controls);
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * Simple add and load UseControls.
     * @param controls 
     */
    public void addLoadUseControls(GUseControls controls) {
        controls.createInNifty(manager);
        this.addUseControls(controls);
    }
    /**
     * Simple add UseStyles in the gui hiearchy
     * @param controls 
     */
    public void addUseStyles(GUseStyle styles) {
        this.useStyles.add(styles);
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * Simple add and load UseStyles in the gui hiearchy
     * @param controls 
     */
    public void addLoadUseStyle(GUseStyle styles){
        //Nifty adds default styles by default no needs to add again.
        if(!styles.getFilename().equals("nifty-default-styles")){
            styles.createInNifty(manager);
        }
        this.addUseStyles(styles);
    }
    
    public Collection<GUseStyle> getUseStyles(){
        return Collections.unmodifiableCollection(this.useStyles);
    }
    
    public Collection<GUseControls> getUseControls(){
        return Collections.unmodifiableCollection(this.useControls);
    }
    public void reoloadStyles(String filename){
        for(GUseStyle s : this.useStyles){
            if(s.getFilename().endsWith(filename)){
                s.createInNifty(manager);
            }
        }
    }
    public void reloadStyles(InputStream input,String filename){
        for(GUseStyle s : this.useStyles){
            if(s.getFilename().endsWith(filename)){
                manager.loadStyleFileFromStream(input);
            }
        }
    }
    /**
     *
     * @return the selection
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * @return the currentLayer
     */
    public GLayer getCurrentLayer() {
        return currentL;
    }

    /**
     * @param currentL the currentLayer to set
     */
    public void setCurrentLayer(GLayer currentL) {
        this.currentL = currentL;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GUI)){
            return false;
        }
        GUI object = (GUI) obj;
        return ID == object.ID; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return ID;
    }
    
}
