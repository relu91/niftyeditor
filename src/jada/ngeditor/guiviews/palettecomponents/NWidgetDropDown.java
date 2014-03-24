/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GDropDown;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;

/**
 *
 * @author cris
 */
public class NWidgetDropDown extends NWidget {

    public NWidgetDropDown() {
        super();
        this.text.setText("DropDown");
    }
    
    
    @Override
    public WidgetData getData() {
       try {
            GElement e = GUIFactory.getInstance().newGElement(GDropDown.class);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
           return new WidgetData(null);
        }
    }
    
}
