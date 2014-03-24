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
package jada.ngeditor;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import jada.ngeditor.guiviews.MainView;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author cris
 */
public class NiftyEditor {
    private static java.awt.Color line = new java.awt.Color(17,229,229);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
          
                    
       try{
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
        Graphics2D g = splash.createGraphics();
        if (g != null) {
              g.setComposite(AlphaComposite.Clear);
              g.setColor(line);
              g.setPaintMode();
              g.setColor(Color.DARK_GRAY);
              g.fillRect(10,205,280,5);
              g.setColor(line);
              g.fillRect(10,205,130,5);
              g.setColor(Color.black);
              g.drawRect(10,205,130,5);
              splash.update();
             Properties props = new Properties();
             props.put("logoString", "Nifty-Editor");
             props.put("backgroundPattern","off");
             props.put("dynamicLayout","on");
             HiFiLookAndFeel.setTheme(props);
             UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
             g.setColor(line);
             g.fillRect(10,205,200,5);
             g.setColor(Color.BLACK);
             g.drawRect(10,205,200,5);
             splash.update(); 
             MainView editor = new MainView();
             g.setColor(line);
             g.fillRect(10,205,280,5);
             g.setColor(Color.BLACK);
             g.drawRect(10,205,280,5);
             splash.update();
            java.net.URL icon = editor.getClass().getResource("/jada/ngeditor/resources/icon.png");
            editor.setIconImage(Toolkit.getDefaultToolkit().getImage(icon));
            editor.setVisible(true);
        }
        }
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(NiftyEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(NiftyEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NiftyEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(NiftyEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
}
              
          
            
          
       
    }

