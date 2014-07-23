/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import java.awt.Rectangle;

/**
 *
 * @author cris
 */
public class SubImageModel extends ImageModeModel {
    private final Rectangle rect;

    public SubImageModel(String image, String basepath,String value) {
        super(image, basepath,value);
        String[] recDim = paramenters.split(",");
        int x = Integer.parseInt(recDim[0]);
        int y = Integer.parseInt(recDim[1]);
        int w = Integer.parseInt(recDim[2]);
        int h = Integer.parseInt(recDim[3]);
        this.rect = new Rectangle(x, y, w, h);
    }

    /**
     * @return the rect
     */
    public Rectangle getRectangle() {
        return rect;
    }

    @Override
    public String toString() {
        return "subImage:"+this.rect.x+","+this.rect.y+","+this.rect.width+","+this.rect.height;
    }
    
    
    
    
}
