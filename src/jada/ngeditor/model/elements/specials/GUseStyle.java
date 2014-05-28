/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements.specials;

import de.lessvoid.nifty.Nifty;
import jada.ngeditor.model.GSpecial;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author cris
 */
public class GUseStyle implements GSpecial{
     private String filename = "";
     @XmlAttribute
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void createInNifty(Nifty manager) {
        manager.loadStyleFile(filename);
    }
}
