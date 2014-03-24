/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import jada.ngeditor.persistence.XmlTags;
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
    public GElement create(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initDefault() {
        
    }
}
