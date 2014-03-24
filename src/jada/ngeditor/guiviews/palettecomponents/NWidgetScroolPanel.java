/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GScrollPanel;
import jada.ngeditor.model.exception.NoProductException;

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
        try {
            GElement e = GUIFactory.getInstance().newGElement(GScrollPanel.class);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
           return new WidgetData(null);
        }
    }
    
}
