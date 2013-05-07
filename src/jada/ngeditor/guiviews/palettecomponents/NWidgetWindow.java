/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.Types;
import jada.ngeditor.model.elements.GElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author cris
 */
public class NWidgetWindow extends NWidget {
    public NWidgetWindow() throws IOException{
        super();
         BufferedImage image = ImageIO.read(getIcon("window.png"));
        this.icon.setIcon(new ImageIcon(image));
        this.text.setText("Window");
    }
    @Override
    public WidgetData getData() {
        GElement res = GUIFactory.getInstance().newGElement(Types.WINDOW.toString());
        return new WidgetData(res);
    }
    
}
