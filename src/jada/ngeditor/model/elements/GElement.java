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
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import jada.ngeditor.model.visitor.Visitor;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 *
 * @author cris
 */
public abstract class GElement {

    static private int UID = 0;
    @XmlElementRef
    private LinkedList<GElement> children;
    private int UniID;
    @XmlTransient
    protected GElement parent;
    @XmlID
    @XmlAttribute(name = "id")
    protected String id;
    protected Element nElement;
    protected ElementBuilder builder;
    private String oldStyle;
    private ArrayList<String> toBeRemoved = new ArrayList<String>();
    protected HashMap<String, String> attributes = new HashMap<String, String>();

    protected GElement() {
    
    }

    protected GElement(String id) throws IllegalArgumentException {
        this.id = id;

        this.parent = null;
        this.children = new LinkedList<GElement>();
        this.UniID = UID;
        UID++;
        attributes = new HashMap<String, String>();
        attributes.put("id", this.id);


    }

    public int getUniID() {

        return this.UniID;
    }

    /**
     * 
     *
     * @param index
     */
    @Deprecated
    public void setIndex(int index) {
        nElement.setIndex(index);
        parent.children.remove(this);
        parent.children.add(index, this);
    }

    public void removeFromParent() {
        if (parent != null) {
            this.parent.children.remove(this);
            this.parent = null;
            
        }
    }

    public void addChild(GElement toAdd, boolean xml) {
        this.children.add(toAdd);
        toAdd.parent = this;

    }

    @XmlTransient
    public String getID() {
        return id;
    }

    public void setParent(GElement parent) {
        this.parent = parent;
    }

    public java.awt.Rectangle getBounds() {
        int ex = nElement.getX();
        int ey = nElement.getY();
        int ew = nElement.getWidth();
        int eh = nElement.getHeight();
        return new java.awt.Rectangle(ex, ey, ew, eh);
    }
    
    public boolean contains(Point2D point) {
        return this.getBounds().contains(point.getX(), point.getY());
    }

    @XmlTransient
    public GElement getParent() {
        return this.parent;
    }

    public Element getNiftyElement() {


        return nElement;


    }

    public void setXmlAttributes(Map<QName, String> attrib) {
        for (QName n : attrib.keySet()) {
            this.attributes.put(n.getLocalPart(), attrib.get(n));
        }
    }

    @XmlAnyAttribute
    public Map<QName, String> getXmlAttributes() {
        Map<QName, String> res = new HashMap<QName, String>();
        for (String s : this.attributes.keySet()) {
            if (!s.equals("id") && !s.equals("name")) {
                QName qname = QName.valueOf(XMLConstants.NULL_NS_URI + s);
                res.put(qname, this.attributes.get(s));
            }
        }
        return res;
    }

    public Map<String, String> listAttributes() {
        Map<String, String> res = new HashMap<String, String>();
        for (String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve("elementType")) {
                String defvalue = getAttribute(prop);
                res.put(prop, defvalue);
        }
        return res;
    }


    public LinkedList<GElement> getElements() {
        return this.children;
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
        if (key.equals("style")) {
            this.nElement.setStyle("");
            this.nElement.getElementType().removeWithTag("style");
            this.nElement.getRenderer(ImageRenderer.class).setImage(null);
        } else {
            Attributes att = this.nElement.getElementType().getAttributes();
            att.set(key, "");
            this.toBeRemoved.add(key);
        }

    }

    protected void processRemoved() {
        Attributes att = this.nElement.getElementType().getAttributes();
        for (String s : this.toBeRemoved) {
            att.remove(s);
        }
    }

    public String getAttribute(String key) {
        String res = "";
        if(nElement != null){
         Attributes att = this.nElement.getElementType().getAttributes();
        if (att.get(key) != null) {
            res= att.get(key);
        }
        }
        return res;
    }

    public void addAttribute(String key, String val) {
        //Fixme find an alternative from this line
        Attributes att = this.nElement.getElementType().getAttributes();
        if (key.equals("id")) {
            this.id = val;
        } else if (key.equals("style")) {
            this.oldStyle = att.get("style");
        }
        attributes.put(key, val);
        att.set(key, val);
    }
    /*
     * Heavy method for controls should be called not often
     */

    public void refresh() {
        Nifty temp = nElement.getNifty();
        Attributes att = this.nElement.getElementType().getAttributes();
        String newStyle = att.get("style");
        Attributes attcopy = new Attributes(att);
        // Add the old style if there was one
        if (oldStyle != null && !oldStyle.equals(newStyle)) {

            att.set("style", oldStyle);
            nElement.setStyle(newStyle);
            attcopy = att;
            oldStyle = newStyle;
        }
        if (att.isSet("renderOrder")) {
            int renderorder = att.get("renderOrder").isEmpty() ? this.parent.children.indexOf(this) : att.getAsInteger("renderOrder");
            nElement.setRenderOrder(renderorder);
        }
        nElement.setId(id);
        this.internalRefresh(temp, attcopy);
        this.processRemoved();
    }
    /*
     * used for simple elment attributes
     */

    public void lightRefresh() {
        Nifty temp = nElement.getNifty();
        Screen currentScreen = temp.getCurrentScreen();
        Attributes att = this.nElement.getElementType().getAttributes();
        nElement.initializeFromAttributes(currentScreen, att, temp.getRenderEngine());
        currentScreen.layoutLayers();
    }

    private void lightRefresh(Attributes att) {
        Nifty temp = nElement.getNifty();
        Screen currentScreen = temp.getCurrentScreen();
        nElement.initializeFromAttributes(currentScreen, att, temp.getRenderEngine());
        currentScreen.layoutLayers();
    }
    
    protected void internalRefresh(Nifty nifty,Attributes att){
        Screen currentScreen = nifty.getCurrentScreen();
        nElement.initializeFromAttributes(currentScreen, att, nifty.getRenderEngine());
        currentScreen.layoutLayers();
    }

    public void reloadElement(Nifty manager) {
        Nifty nif = manager;
        if (nElement != null) {
            nif = nElement.getNifty();
        }
        nElement = parent.nElement.findElementById(id);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public boolean equals(Object e) {
        if (e instanceof GElement) {
            GElement temp = (GElement) e;
            return UniID == temp.getUniID();
        } else {
            return false;
        }
    }

    public Element getDropContext() {
        return nElement;
    }
    

    public void createNiftyElement(Nifty nifty) {
        for (String sel : attributes.keySet()) {
            builder.set(sel, attributes.get(sel));
        }
        nElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext());

    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    //FIXME : remove this method 
    public abstract GElement create(String id);

    public abstract void initDefault();
}
