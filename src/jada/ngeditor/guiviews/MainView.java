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

package jada.ngeditor.guiviews;


import jada.ngeditor.controller.GUIEditor;
import jada.ngeditor.guiviews.DND.PaletteDropTarget;
import jada.ngeditor.guiviews.DND.TrasferHandling;
import jada.ngeditor.listeners.ClosingListener;
import jada.ngeditor.persistence.XmlFileFilter;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author cris
 */
public class MainView extends javax.swing.JFrame {
    private GUIEditor editor;
    private J2DNiftyView cont,wel;
    private JFileChooser IOmanager;
    private final TrasferHandling trans;
  
    /**
     * Creates new form MainView
     */
    public MainView() {
        this.editor = new GUIEditor();
        IOmanager = new JFileChooser();
        XmlFileFilter filter = new XmlFileFilter();
        IOmanager.setFileFilter(filter);
        trans = new TrasferHandling();
        initComponents();
        this.addWindowListener(new ClosingListener());
           
    
        PaletteDropTarget tmp = new PaletteDropTarget();
        this.cont.setTransferHandler(trans);
        this.cont.setDropTarget(tmp);
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        Dimension frameSize = this.getSize ();
        this.setLocation ((screenSize.width - frameSize.width) / 2,(screenSize.height - frameSize.height) / 2);
        this.editor.addObserver(tmp);
        this.editor.addObserver(trans);
        this.editor.addObserver(this.treeGuiView1);
        this.editor.addObserver(cont);
        this.editor.addObserver(proprietesView2);
        this.newGui();
        jTabbedPane2.setSelectedIndex(1);
       
    }

    @Override
    public void dispose() {
        cont.close();
        wel.close();
        System.out.println("Nifty Disposed");
        super.dispose(); 
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        proprietesView2 = new jada.ngeditor.guiviews.ProprietesView();
        jScrollPane1 = new javax.swing.JScrollPane();
        paletteView1 = new jada.ngeditor.guiviews.PaletteView();
        treeGuiView1 = new jada.ngeditor.guiviews.TreeGuiView();
        guiView = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        refresh = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        CheckPane = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nifty-Editor v. 0.5.7");
        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setMinimumSize(new java.awt.Dimension(50, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        proprietesView2.setBorder(javax.swing.BorderFactory.createTitledBorder("Proprities"));
        jSplitPane1.setBottomComponent(proprietesView2);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(paletteView1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.add(jSplitPane1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

        treeGuiView1.setBorder(javax.swing.BorderFactory.createTitledBorder("Navigator"));
        treeGuiView1.setPreferredSize(new java.awt.Dimension(200, 200));
        getContentPane().add(treeGuiView1, java.awt.BorderLayout.LINE_START);

        guiView.setMaximumSize(new java.awt.Dimension(200, 200));
        guiView.setPreferredSize(new java.awt.Dimension(800, 600));
        guiView.setLayout(new javax.swing.BoxLayout(guiView, javax.swing.BoxLayout.LINE_AXIS));
        guiView.add(jTabbedPane2);

        javax.swing.JPanel welcome = new javax.swing.JPanel();
        javax.swing.JPanel working = new javax.swing.JPanel();
        welcome.setLayout(new javax.swing.BoxLayout(welcome,javax.swing.BoxLayout.LINE_AXIS));
        wel = new J2DNiftyView(800,600);
        welcome.add(wel);

        wel.init();
        working.setLayout(new javax.swing.BoxLayout(working,javax.swing.BoxLayout.LINE_AXIS));
        cont=  new J2DNiftyView(800,600);

        working.add(cont);
        jTabbedPane2.addTab("WorkGUI", working);
        jTabbedPane2.addTab("Welcome", welcome);
        cont.init();

        getContentPane().add(guiView, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 51));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New..");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Save");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        refresh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        refresh.setText("Refresh");
        refresh.setToolTipText("click here to reload the gui");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jMenu2.add(refresh);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Option");

        CheckPane.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        CheckPane.setText("Show Panels");
        CheckPane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckPaneActionPerformed(evt);
            }
        });
        jMenu3.add(CheckPane);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("?");
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        jMenuItem6.setText("About");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void newGui(){
        try {
           int ris = JOptionPane.YES_OPTION;
           if(!editor.isfirstGui())
                ris = JOptionPane.showConfirmDialog(this, "The current gui will be deleted , continue?","New Gui",JOptionPane.YES_NO_OPTION);
           
           if(ris == JOptionPane.YES_OPTION){
               editor.addObserver(treeGuiView1);
               editor.addObserver(proprietesView2);
               editor.createNewGui(cont.getNifty()); 
           }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        newGui();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
       try{
       int res = IOmanager.showSaveDialog(this);
       if(res == JFileChooser.APPROVE_OPTION){
           File select = IOmanager.getSelectedFile();
           if(IOmanager.accept(select)){
               if(!select.exists()){
                editor.saveGui(select.getPath());
               } else{
                   int ris = JOptionPane.showConfirmDialog(this,"File exists, it will be overwritten, continue?", "advice", JOptionPane.YES_NO_OPTION);
                   if(ris == JOptionPane.YES_OPTION){
                     editor.saveGui(select.getPath());
                   }
               }
           }else{
               //append .xml
               select = new File(select.getPath()+".xml");
                if(!select.exists()){
                 editor.saveGui(select.getPath());
               } else{
                   int ris = JOptionPane.showConfirmDialog(this,"File exists, it will be overwritten, continue?", "advice", JOptionPane.YES_NO_OPTION);
                   if(ris == JOptionPane.YES_OPTION){
                       editor.saveGui(select.getPath());
                   }
               }
               
           }
       }
       } catch (FileNotFoundException ex) {
          Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void CheckPaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckPaneActionPerformed
      boolean state = this.CheckPane.getState();
      this.cont.getNifty().setDebugOptionPanelColors(state);
    }//GEN-LAST:event_CheckPaneActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        //Refresh
        if(this.editor!=null){
            try{
                this.editor.refresh(cont.getNifty());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ops something goes wrong");
                Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "No gui to refresh");
    }//GEN-LAST:event_refreshActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      int res = this.IOmanager.showOpenDialog(this);
      if(res == JFileChooser.APPROVE_OPTION)
      {
        File select = IOmanager.getSelectedFile();
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            String errors = this.editor.createNewGui(cont.getNifty(),select);
            if(!errors.equals("")){
                 JOptionPane.showMessageDialog(this, "Some elements cannot be handled: "+errors);
            }
            jTabbedPane2.setSelectedIndex(0);
        } catch (ParserConfigurationException ex) {
           JOptionPane.showMessageDialog(this, " Errore nel parsing del file ");
        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
           JOptionPane.showMessageDialog(this, " Errore nel parsing del file ");
        }catch (RuntimeException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gui cannot be loaded are you using relative paths for resources?");
        }catch (Exception ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
         this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
   
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    
    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
      
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new AboutForm().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem CheckPane;
    private javax.swing.JPanel guiView;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private jada.ngeditor.guiviews.PaletteView paletteView1;
    private jada.ngeditor.guiviews.ProprietesView proprietesView2;
    private javax.swing.JMenuItem refresh;
    private jada.ngeditor.guiviews.TreeGuiView treeGuiView1;
    // End of variables declaration//GEN-END:variables
}
