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
public class GShake extends GEffect {

    public GShake() {
        super();
        effectType.getAttributes().set("name", "shake");
        setAttribute("global", "false");
    }
    
    
    
}
