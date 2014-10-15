/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements.effects;

/**
 *
 * @author cris
 */
public class GFade extends GEffect{

    public GFade() {
        setAttribute("name", "fade");
        setAttribute("start", "#ff");
        setAttribute("end","#00");
    }
    
}
