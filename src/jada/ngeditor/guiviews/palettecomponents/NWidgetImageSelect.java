/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GImageSelect;
import jada.ngeditor.model.exception.NoProductException;

/**
 *
 * @author cris
 */
public class NWidgetImageSelect extends NWidget {
    

    public NWidgetImageSelect() {
        super();
        this.text.setText("ImageSelect");
    }

    @Override
    public WidgetData getData() {
         try {
            GElement e = GUIFactory.getInstance().newGElement(GImageSelect.class);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
           return new WidgetData(null);
        }
    }
    
    
    
}
