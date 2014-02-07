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

import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class ElementEditor {
    private GElement selected=null;
    private GUIEditor ref=null;
   
    public ElementEditor(GUIEditor editor){
       ref=editor; 
    }
    public ElementEditor(GElement toEdit){
        selected=toEdit;
    }
    public ElementEditor(GUIEditor editor , GElement toEdit){
         ref=editor;
         selected=toEdit;
    }
    public ElementEditor setAttribute(String key,String value){
        if(selected!=null){
            selected.addAttribute(key, value);
            selected.refresh();
        }
        this.update();
        return this;
    }
    
    public ElementEditor removeAttribute(String key){
        if(selected!=null){
            selected.removeAttribute(key);
            selected.refresh();
        }
        this.update();
        return this;
    }
    public void normalizeSize(){
        Element parent = selected.getParent().getNiftyElement();
        Element sel = selected.getNiftyElement();
        float hp = parent.getHeight();
        float wp = parent.getWidth();
        float totalPaddingHorz = parent.getPaddingLeft().getValue(wp) + parent.getPaddingRight().getValue(wp);
        float totalPaddingVert = parent.getPaddingTop().getValue(hp) + parent.getPaddingBottom().getValue(hp);
        float width = (float)sel.getWidth()/(parent.getWidth()- totalPaddingHorz);
        float height = (float)sel.getHeight()/(parent.getHeight()-totalPaddingVert);
        int percW = Math.round(width*100);
        int percH = Math.round(height*100);
       
        selected.addAttribute("width", ""+percW+"%");
        selected.addAttribute("height", ""+percH+"%");
        selected.lightRefresh();
        this.update();
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
    /**
     * Update selection not yet implemented
     */
    private void update(){
       // this.ref.fireUpdate(selected);
    }
    
}
