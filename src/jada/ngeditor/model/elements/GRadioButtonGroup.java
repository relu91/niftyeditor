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
    
    public GRadioButtonGroup(String id , Element ele){
        super(id,ele);
        this.builder = new RadioGroupBuilder(id);
        name="radioButtonGroup";
    }
    @Override
    public Types getType() {
        return Types.RADIOBUTTONGROUP;
    }

    @Override
    public GElement create(String id, Element ele) {
        return new GRadioButtonGroup(id,ele);
    }

    @Override
    public void initDefault() {
        this.element.setAttribute("width", "0px");
        this.element.setAttribute("height", "0px");
    }
     @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
