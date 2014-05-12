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
@ControlBinding(name= XmlTags.VERTICALSLIDER)
public class GVSlider extends GControl{

    public GVSlider(String id) throws IllegalArgumentException {
        super(id);
        this.builder = new SliderBuilder(id, true);
        this.name = XmlTags.VERTICALSLIDER.toString();
    }

    @Override
    public void initDefault() {
        super.initDefault();
        attributes.put("height", "100px");
    }
    
    
}
