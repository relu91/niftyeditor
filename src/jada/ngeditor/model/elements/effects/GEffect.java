/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements.effects;

import com.google.common.collect.Lists;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.loaderv2.types.EffectType;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

/**
 *
 * @author cris
 */

public abstract class GEffect {
    protected final EffectType effectType;
    @XmlTransient 
    private EffectEventId eventType;
    
    public GEffect(){
        effectType = new EffectType();
    }
    
    public void createEffectFor(Element ownerElement,EffectEventId eventID){
        effectType.materialize(ownerElement.getNifty(), ownerElement, eventID, new Attributes(),Lists.newLinkedList());
    }
    
    public void setAttribute(String name, String value){
        effectType.getAttributes().set(name, value);
    }
    
    public String getAttribute(String name){
        return effectType.getAttributes().get(name);
    }
    
    public Map<String, String> enumerateAttributes() {
        return this.searchListOfAttributesInFile("effectType");
    }
    
    protected final Map<String, String> searchListOfAttributesInFile(String name){
        Map<String, String> res = new HashMap<String, String>();
        for (String prop : jada.ngeditor.model.PropretiesResolver.inst.resolve(name)) {
                String defvalue = getAttribute(prop);
                res.put(prop, defvalue);
        }
        return res;
    }
    
     public void setXmlAttributes(Map<QName, String> attrib) {
        for (QName n : attrib.keySet()) {
            effectType.getAttributes().set(n.getLocalPart(), attrib.get(n));
        }
    }

    @XmlAnyAttribute
    public Map<QName, String> getXmlAttributes() {
        Map<QName, String> res = new HashMap<QName, String>();
        for (String s : this.effectType.getAttributes().getAttributes().keySet()) {
            
                QName qname = QName.valueOf(XMLConstants.NULL_NS_URI + s);
                res.put(qname, getAttribute(s));
            
        }
        return res;
    }

    /**
     * @return the eventType
     */
    @XmlTransient
    public EffectEventId getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EffectEventId eventType) {
        this.eventType = eventType;
    }
    
    
  
    
}
