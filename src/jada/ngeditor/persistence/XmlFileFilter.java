/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.persistence;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author cris
 */
public class XmlFileFilter extends javax.swing.filechooser.FileFilter{

    @Override
    public boolean accept(File pathname) {
        if(pathname.getName().endsWith(".xml") || pathname.isDirectory()){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
       return "Xml gui file";
    }
    
}
