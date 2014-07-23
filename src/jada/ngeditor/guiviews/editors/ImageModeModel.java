/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import java.io.File;

/**
 *
 * @author cris
 */
public class ImageModeModel {
    
    private final String image;
    protected String paramenters;
    public ImageModeModel(String image,String basepath,String value){
        this.image = image;
        paramenters = value.split(":")[1];
    }    

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    
}
