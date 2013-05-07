/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;



import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.loaderv2.types.StyleType;
import de.lessvoid.xml.xpp3.Attributes;
import jada.ngeditor.model.Types;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public abstract class GElement {
    
    static private int UID = 0;
    private LinkedList<GElement> children;
    private int UniID;
    protected GElement parent;
    protected  String id;
    protected final org.w3c.dom.Element element;
    protected Element nElement;
    protected ElementBuilder builder;
    
    protected GElement(){
        element=null;
    }
    
    public GElement(org.w3c.dom.Element docElement){
       if(docElement != null){
       if(docElement.hasAttribute("id"))
            this.id      = docElement.getAttribute("id");
       else 
           id = "";
       
       this.element = docElement;
       this.parent = null;
       
       this.children = new LinkedList<GElement>();
       this.UniID=UID;
       UID++;
       } else 
           throw new IllegalArgumentException("Element null");
    }
    
    public GElement(String id, org.w3c.dom.Element docElement) throws IllegalArgumentException {
       if(docElement != null){
       this.id = id;
       this.element = docElement;
       this.parent = null;
       
       this.children = new LinkedList<GElement>();
       this.UniID=UID;
       UID++; 
       this.element.setAttribute("id", id);
       } else
            throw new IllegalArgumentException("Element null");
       
    }
    
    public int getUniID(){
        
        return this.UniID;
    }
    public void setIndex(int index){
        nElement.setIndex(index);
        if((index+1)<children.size()){
            GElement after = children.get(index+1);
            parent.element.removeChild(this.element);
            parent.element.insertBefore(element,after.element);
        }else{
           GElement after = parent.children.get(index);
           parent.element.insertBefore(element,after.element);
        }  
    }
    public void removeFromParent(){
        if(parent != null){
            this.parent.element.removeChild(this.element);
            this.parent.children.remove(this);
            this.parent= null;
        }
    }
    public void addChild(GElement toAdd,boolean xml){
        this.children.add(toAdd);
        toAdd.parent=this;
        if(xml)
            this.element.appendChild(toAdd.element);
        
    }
    
    
    public String getID(){
        return id;
    }
    
    public void setParent(GElement parent){
        this.parent=parent; 
    }
    public java.awt.Rectangle getBounds(){
        int ex = nElement.getX();
	int ey = nElement.getY();
	int ew = nElement.getWidth();
	int eh = nElement.getHeight();
	return new java.awt.Rectangle(ex, ey, ew, eh);
    }
    //GUI editor Vecchio
    public boolean contains(Point2D point){
        int ex = nElement.getX();
	int ey = nElement.getY();
	int ew = nElement.getWidth();
	int eh = nElement.getHeight();
	return new java.awt.Rectangle(ex, ey, ew, eh).contains(point.getX(), point.getY());
    }
    public GElement getParent(){
        return this.parent;
    }
    public Element getNiftyElement(){
        
        
        return nElement;
        
        
    }
    public Map<String,String> getAttributes(){
      Map<String,String> res = new HashMap<String,String>();
      Types type = getType();
      if(!type.isControl()){
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve(this.getType()+"Type")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
      }else{
         for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve(this.getType()+"Type")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      } 
      }
     return res;
    }
     public org.w3c.dom.Element toXml(){
        return this.element;
    }
    
    public LinkedList<GElement> getElements(){
        return this.children;
    }
    
    public void removeAttribute(String key){
        this.element.removeAttribute(key);
        Attributes att = this.nElement.getElementType().getAttributes();
        att.remove(key);
    }
    public String getAttribute(String key){
         Attributes att = this.nElement.getElementType().getAttributes();
         if(att.get(key)==null)
             return "";
         return att.get(key);
    }
    public void addAttribute(String key , String val){
        if(key.equals("id")){
            this.id = val;
        }
        this.element.setAttribute(key, val);
        Attributes att = this.nElement.getElementType().getAttributes();
        att.set(key, val);
    }
    /*
     * Heavy method for controls should be called not often
     */
    public void refresh(){
       Nifty temp = nElement.getNifty();
       Attributes att = this.nElement.getElementType().getAttributes();
       StyleType style = temp.getDefaultStyleResolver().resolve(nElement.getStyle());
       if(style != null)
        style.applyTo(nElement.getElementType(), temp.getDefaultStyleResolver());
       nElement.setId(id);
       if(getType().isControl()){
          this.heavyRefresh(temp);
       }else{
           this.lightRefresh();
       }
         
       
    }
    /*
     * used for simple elment attributes
     */
    public void lightRefresh(){
       Nifty temp = nElement.getNifty();
       Attributes att = this.nElement.getElementType().getAttributes();
       nElement.initializeFromAttributes(att, temp.getRenderEngine());
       temp.getCurrentScreen().layoutLayers();
    }
    
    private void heavyRefresh(Nifty nifty){
        int index= parent.getNiftyElement().getElements().indexOf(nElement);
        Attributes att = this.nElement.getElementType().getAttributes();
        if(att.isSet("renderOrder"))
                nElement.setRenderOrder(att.getAsInteger("renderOrder"));
         nElement.markForRemoval();
         final HashMap<String,String> attributes = new HashMap<String,String>();
        for(int i =0;i<element.getAttributes().getLength();i++){
            Node n = element.getAttributes().item(i);
            attributes.put(n.getNodeName(),n.getNodeValue());
            
        }
         for(String sel : attributes.keySet()){
               builder.set(sel, attributes.get(sel));
         }
         nElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext(),index);
         nifty.getCurrentScreen().layoutLayers();
    }
    public void reloadElement(Nifty manager){
           Nifty nif = manager;
        if(nElement != null)
            nif = nElement.getNifty();
        nElement = nif.getCurrentScreen().findElementByName(id);
    }
    @Override
    public String toString(){
        return this.id;
    }
    @Override
    public boolean equals(Object e){
        if(e instanceof GElement){
            GElement temp = (GElement)e;
            return UniID == temp.getUniID();
        } else
            return false;
    }
    protected Element getDropContext(){
        return nElement;
    }
    
    private void createWithChildren(Nifty nifty){
        this.createNiftyElement(nifty);
        for(GElement ele : children)
            ele.createWithChildren(nifty);
    }
    public abstract Types getType();
    public void createNiftyElement(Nifty nifty){
        final HashMap<String,String> attributes = new HashMap<String,String>();
        for(int i =0;i<element.getAttributes().getLength();i++){
            Node n = element.getAttributes().item(i);
            attributes.put(n.getNodeName(),n.getNodeValue());
            
        }
         for(String sel : attributes.keySet()){
               builder.set(sel, attributes.get(sel));
         }
         nElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext());
        
    }
    public abstract GElement create(String id, org.w3c.dom.Element ele);
    public abstract void initDefault();
   
    
   
    
}
