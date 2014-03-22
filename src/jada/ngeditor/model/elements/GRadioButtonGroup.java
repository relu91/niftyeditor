/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements;

import de.lessvoid.nifty.controls.radiobutton.builder.RadioGroupBuilder;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.visitor.Visitor;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

/**
 *
 * @author cris
 */
@XmlRootElement(name="control")
public class GRadioButtonGroup extends GElement {
    static{
         GUIFactory.registerProduct(new GRadioButtonGroup());
    }
    public GRadioButtonGroup(){
        super();
    }
    
    public GRadioButtonGroup(String id ){
        super(id);
        this.builder = new RadioGroupBuilder(id);
        name="radioButtonGroup";
    }
    @Override
    public Types getType() {
        return Types.RADIOBUTTONGROUP;
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
