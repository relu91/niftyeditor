/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

/**
 *
 * @author cris
 */
public class SpriteImageMode extends ImageModeModel {
    private final int width;
    private final int heigth;
    private final int index;

    public SpriteImageMode(String image, String basepath, String value) {
        super(image, basepath, value);
        String[] values = paramenters.split(",");
        width = Integer.parseInt(values[0]);
        heigth = Integer.parseInt(values[1]);
        index = Integer.parseInt(values[2]);
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the heigth
     */
    public int getHeigth() {
        return heigth;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "sprite:"+this.width+","+this.heigth+","+this.index;
    }
    
    
}
