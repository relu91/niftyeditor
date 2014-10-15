/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements.specials;

import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.elements.Element;
import jada.ngeditor.model.elements.effects.GEffect;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

/**
 * An empty node that contains all the effects of an element
 * @author cris
 */
@XmlRootElement(name="effect")
public class GEffectsNode {
    private LinkedList<GEffect> effects = new LinkedList<GEffect>();
    private final Element element;
    
    //I added this because jxab is complaining 
    public GEffectsNode(){
        element =null;
    }
    public GEffectsNode(Element element){
        this.element = element;
    }
    
    public void addEffect(GEffect effect,EffectEventId eventType){
        effect.createEffectFor(element, eventType);
        this.effects.add(effect);
        effect.setEventType(eventType);
    }
    
    public void removeEffect(GEffect effect){
        //TODO: nifty doesn't have the support for removing effects
    }
    
    public void test(EffectEventId effectEvent){
        element.startEffect(effectEvent);
    }
    @XmlAnyElement
    public List<JAXBElement<GEffect>> getEffectsXml(){
        List<JAXBElement<GEffect>> res = new LinkedList<JAXBElement<GEffect>>();
        for(GEffect effect : effects){
            res.add(new JAXBElement<GEffect>(new QName(effect.getEventType().toString()),GEffect.class, effect));
        }
        return res;
    }
}
