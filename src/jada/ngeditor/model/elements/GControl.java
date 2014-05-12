/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.xml.xpp3.Attributes;
import jada.ngeditor.persistence.XmlTags;
import java.util.Map;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
public abstract class GControl extends GElement{
    
    protected String name;
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public GControl() {
        
    }

    
    public GControl(String id) throws IllegalArgumentException {
        super(id);
    }
    
    @Override
    public Map<String,String> listAttributes(){
       Map<String,String> res = super.listAttributes();
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve("controlType")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
      for(String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve(this.getName()+"Type")){
          String defvalue = getAttribute(prop);
          res.put(prop, defvalue);
      }
       return res;
    }

    @Override
    protected void internalRefresh(Nifty nifty, Attributes att) {
         int index = parent.getNiftyElement().getChildren().indexOf(nElement);
        final GElement telement = this;

        nElement.markForRemoval(new EndNotify() {
            @Override
            public void perform() {
                this.buildChild(telement);
            }

            private void buildChild(GElement ele) {
                for (GElement e : ele.getElements()) {
                    ele.getDropContext().addChild(e.getNiftyElement());
                    e.refresh();
                    this.buildChild(e);
                }
            }
        });
        for (String sel : attributes.keySet()) {
            builder.set(sel, attributes.get(sel));
        }

        nElement = builder.build(nifty, nifty.getCurrentScreen(), this.parent.getDropContext(), index);
        nifty.getCurrentScreen().layoutLayers();
    }
    @Override
    public void initDefault() {
        
    }

    @Override
    public GElement create(String id) {
        return null;
    }
    
    
}
