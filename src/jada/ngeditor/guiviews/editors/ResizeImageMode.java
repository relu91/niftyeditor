/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

/**
 *
 * @author cris
 */
public class ResizeImageMode extends ImageModeModel{
    private int y2;
    private int x8;
    private int x7;
    private int x6;
    private int y1;
    private int x5;
    private int x4;
    private int x3;
    private int y0;
    private int x2;
    private int x1;
    private int x0;
    

    public ResizeImageMode(String image, String basepath, String value) {
        super(image, basepath, value);
        String[] values = paramenters.split(",");
        int[] upperVectorX = new int[]{Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2])};
        int[] centerVectorX = new int[]{Integer.parseInt(values[4]),Integer.parseInt(values[5]),Integer.parseInt(values[6])};
        int[] downVectorX = new int[]{Integer.parseInt(values[8]),Integer.parseInt(values[9]),Integer.parseInt(values[10])}; 
        int[] vectorY = new int[]{Integer.parseInt(values[3]),Integer.parseInt(values[7]),Integer.parseInt(values[11])}; 
        x0 = upperVectorX[0];
        x1 = upperVectorX[1];
        x2 = upperVectorX[2];
        y0 = vectorY[0];
        x3 = centerVectorX[0];
        x4 = centerVectorX[1];
        x5 = centerVectorX[2];
        y1 = vectorY[1];
        x6 = downVectorX[0];
        x7 = downVectorX[1];
        x8 = downVectorX[2];
        y2 = vectorY[2];
    }

    /**
     * @return the y2
     */
    public int getY2() {
        return y2;
    }

    /**
     * @param y2 the y2 to set
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
     * @return the x8
     */
    public int getX8() {
        return x8;
    }

    /**
     * @param x8 the x8 to set
     */
    public void setX8(int x8) {
        this.x8 = x8;
    }

    /**
     * @return the x7
     */
    public int getX7() {
        return x7;
    }

    /**
     * @param x7 the x7 to set
     */
    public void setX7(int x7) {
        this.x7 = x7;
    }

    /**
     * @return the x6
     */
    public int getX6() {
        return x6;
    }

    /**
     * @param x6 the x6 to set
     */
    public void setX6(int x6) {
        this.x6 = x6;
    }

    /**
     * @return the y1
     */
    public int getY1() {
        return y1;
    }

    /**
     * @param y1 the y1 to set
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * @return the x5
     */
    public int getX5() {
        return x5;
    }

    /**
     * @param x5 the x5 to set
     */
    public void setX5(int x5) {
        this.x5 = x5;
    }

    /**
     * @return the x4
     */
    public int getX4() {
        return x4;
    }

    /**
     * @param x4 the x4 to set
     */
    public void setX4(int x4) {
        this.x4 = x4;
    }

    /**
     * @return the x3
     */
    public int getX3() {
        return x3;
    }

    /**
     * @param x3 the x3 to set
     */
    public void setX3(int x3) {
        this.x3 = x3;
    }

    /**
     * @return the y0
     */
    public int getY0() {
        return y0;
    }

    /**
     * @param y0 the y0 to set
     */
    public void setY0(int y0) {
        this.y0 = y0;
    }

    /**
     * @return the x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * @param x2 the x2 to set
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * @return the x1
     */
    public int getX1() {
        return x1;
    }

    /**
     * @param x1 the x1 to set
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * @return the x0
     */
    public int getX0() {
        return x0;
    }

    /**
     * @param x0 the x0 to set
     */
    public void setX0(int x0) {
        this.x0 = x0;
    }
    
}
