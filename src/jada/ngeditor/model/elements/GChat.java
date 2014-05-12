/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.chatcontrol.builder.ChatBuilder;
import jada.ngeditor.persistence.ControlBinding;
import jada.ngeditor.persistence.XmlTags;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.NIFTYCHAT)
public class GChat extends GControl {

    public GChat(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new ChatBuilder(id, 5);
        this.name = XmlTags.NIFTYCHAT.toString();
    }

    @Override
    public void initDefault() {
        super.initDefault();
        this.attributes.put("lines", "5");
        attributes.put("width", "380px");
        attributes.put("height", "159px");
    }
    
    
    
    @Override
    public GElement create(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
