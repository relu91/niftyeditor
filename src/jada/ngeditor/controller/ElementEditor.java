/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.controller;

import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class ElementEditor {
    private GElement selected;
    
    public ElementEditor(GElement toEdit){
        selected=toEdit;
    }
    public ElementEditor setAttribute(String key,String value){
        if(selected!=null){
            selected.addAttribute(key, value);
            selected.refresh();
        }
        return this;
    }
    
    public ElementEditor removeAttribute(String key){
        if(selected!=null){
            selected.removeAttribute(key);
            selected.refresh();
        }
        return this;
    }
    public void normalizeSize(){
        Element parent = selected.getParent().getNiftyElement();
        Element sel = selected.getNiftyElement();
        float width = (float)sel.getWidth()/parent.getWidth();
        float height = (float)sel.getHeight()/parent.getHeight();
        int percW = (int) (width*100);
        int percH = (int)(height*100);
       
        selected.addAttribute("width", ""+percW+"%");
        selected.addAttribute("height", ""+percH+"%");
        selected.lightRefresh();
    }
    public void setEdited(GElement toEdit){
        selected=toEdit;
    }
    public void setVisibile(boolean visibility){
        selected.getNiftyElement().setVisible(visibility);
    }
    public int getIndex(){
        return selected.getParent().getElements().indexOf(selected);
    }
    
    public void setVisibileSelected(boolean vis){
        if(!this.selected.getType().equals(Types.SCREEN)){
        if(vis){
            this.selected.getNiftyElement().showWithoutEffects();
        } else
            this.selected.getNiftyElement().hideWithoutEffect();
        }
    }
    public Element getNiftyElement(){
        return selected.getNiftyElement();
    }
    
}
