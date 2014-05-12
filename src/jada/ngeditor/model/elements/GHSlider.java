/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.slider.builder.SliderBuilder;
import jada.ngeditor.persistence.ControlBinding;
import jada.ngeditor.persistence.XmlTags;

/**
 *
 * @author cris
 */
@ControlBinding(name= XmlTags.HORIZONTALSLIDER)
public class GHSlider extends GControl {

    public GHSlider(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new SliderBuilder(id, false);
        this.name = XmlTags.HORIZONTALSLIDER.toString();
    }

    @Override
    public void initDefault() {
        super.initDefault();
        attributes.put("width","100px" );
    }
    
    
    
    
}
