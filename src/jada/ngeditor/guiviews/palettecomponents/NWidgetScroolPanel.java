/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;

/**
 *
 * @author cris
 */
public class NWidgetScroolPanel extends NWidget {
    
    public NWidgetScroolPanel(){
        super();
        this.text.setText( "ScrollPanel");
    }
    @Override
    public WidgetData getData() {
        GElement e = GUIFactory.getInstance().newGElement(Types.SCROLLPANEL.toString());
        return new WidgetData(e);
    }
    
}
