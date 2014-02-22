/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;

/**
 *
 * @author cris
 */
public class NWidgetDraggable extends NWidget {
    
    

    public NWidgetDraggable() {
        super();
        this.text.setText("Draggable");
    }
    @Override
    public WidgetData getData() {
        return new WidgetData(GUIFactory.getInstance().newGElement(Types.DRAGGABLE));
    }
    
}
