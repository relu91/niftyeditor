/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author cris
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ControlBinding {
    public XmlTags name();
}
