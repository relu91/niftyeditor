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
public class NWidgetRadioButton extends NWidget{
    public NWidgetRadioButton() throws IOException{
        super();
          BufferedImage image = ImageIO.read(getIcon("radiobutton.png"));
        this.icon.setIcon(new ImageIcon(image));
        this.text.setText("RadioButton");
    }
    @Override
    public WidgetData getData() {
         GElement res = GUIFactory.getInstance().newGElement(Types.RADIOBUTTON.toString());
         return new WidgetData(res);
    }
    
}
