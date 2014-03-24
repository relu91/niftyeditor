/* Copyright 2012 Aguzzi Cristiano

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package jada.ngeditor.guiviews.palettecomponents;

import jada.ngeditor.guiviews.DND.WidgetData;
import jada.ngeditor.model.GUIFactory;
import jada.ngeditor.model.elements.GCheckbox;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.exception.NoProductException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author cris
 */
public class NWidgetCheck extends NWidget {

    public NWidgetCheck() throws IOException {
        BufferedImage image = ImageIO.read(getIcon("check.png"));
        this.icon.setIcon(new ImageIcon(image));
        this.text.setText("CheckBox");
    }

    @Override
    public WidgetData getData() {
        try {
            GElement e = GUIFactory.getInstance().newGElement(GCheckbox.class);
            return new WidgetData(e);
        } catch (NoProductException ex) {
            ex.printStackTrace();
            return new WidgetData(null);
        }
    }
}
