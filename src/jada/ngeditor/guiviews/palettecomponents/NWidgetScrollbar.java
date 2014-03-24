/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GHorizontalScrollbar;
import jada.ngeditor.model.exception.NoProductException;

/**
 *
 * @author cris
 */
public class NWidgetScrollbar extends NWidget{

    public NWidgetScrollbar() {
        super();
        this.text.setText("HorizScrollbar");
    }

    
    @Override
    public WidgetData getData() {
        try {
            GElement e = GUIFactory.getInstance().newGElement(GHorizontalScrollbar.class);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
           return new WidgetData(null);
        }
    }
    
}
