/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.radiobutton.builder.RadioGroupBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.visitor.Visitor;
import jada.ngeditor.persistence.ControlBinding;

/**
 *
 * @author cris
 */
 @ControlBinding(name= XmlTags.RADIOBUTTONGROUP)
public class GRadioButtonGroup extends GControl  {

    public GRadioButtonGroup() {
    }
  
    
    public GRadioButtonGroup(String id ){
        super(id);
        this.builder = new RadioGroupBuilder(id);
        name="radioButtonGroup";
    }
    

    @Override
    public GElement create(String id) {
        return new GRadioButtonGroup(id);
    }

    @Override
    public void initDefault() {
       attributes.put("width", "0px");
        attributes.put("height", "0px");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
