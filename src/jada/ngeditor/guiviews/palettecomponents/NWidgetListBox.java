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
public class NWidgetListBox extends NWidget{
     
    public NWidgetListBox(){
        super();
        this.text.setText("Listbox");
    }
    @Override
    public WidgetData getData() {
       return new WidgetData(GUIFactory.getInstance().newGElement(Types.LISTBOX.toString()));
    }
    
}
