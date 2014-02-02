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
import jada.ngeditor.listeners.ClosingListener;
import jada.ngeditor.model.Types;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author cris
 */
public class NiftyEditor {
    static {
        try {
            System.out.println("Loding all gui components");
            String classPack = "jada.ngeditor.model.elements";
            for(Types type : Types.values()){
              if(!type.equals(Types.NIFTYCONSOLE)){
              String suffix = "G"+Character.toUpperCase(type.toString().charAt(0));
              String name = suffix+type.toString().substring(1);
              Class.forName(classPack+"."+name);
              }
            }
            Class.forName(classPack+"."+"GConsole");
            System.out.println("Done");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NiftyEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
              g.setColor(Color.red);
              g.setPaintMode();
              g.fillRect(10,200,130,10);
              splash.update();
             Properties props = new Properties();
             props.put("logoString", "Nifty-Editor");
             props.put("backgroundPattern","off");
             props.put("dynamicLayout","on");
             HiFiLookAndFeel.setTheme(props);
             UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
             JPopupMenu.setDefaultLightWeightPopupEnabled(false);
             g.fillRect(10,200,280,10);
             splash.update(); 
             MainView editor = new MainView();
            
            java.net.URL icon = editor.getClass().getResource("/jada/ngeditor/resources/icon.png");
            editor.setIconImage(Toolkit.getDefaultToolkit().getImage(icon));
            
            
        
            editor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editor.addWindowListener(new ClosingListener());
            
            editor.starGuiEditor();
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

