/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.effects.GEffect;

/**
 *
 * @author cris
 */
public class NWidgetEffect extends NWidget {
    private final GEffect effect;

    public NWidgetEffect(GEffect effect) {
        super(effect.getClass());
        this.effect = effect;
    }

    @Override
    public WidgetData getData() {
        return new WidgetData(effect); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
