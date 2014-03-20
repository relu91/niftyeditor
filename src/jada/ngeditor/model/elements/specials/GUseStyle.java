/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.elements.specials;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author cris
 */
public class GUseStyle {
     private String filename = "";
     @XmlAttribute
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
