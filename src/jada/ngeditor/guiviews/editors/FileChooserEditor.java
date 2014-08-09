/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import jada.ngeditor.model.utils.FileUtils;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

/**
 * Nifty editor file chooser.
 * @author cris
 */
public class FileChooserEditor extends AbstractCellEditor implements TableCellEditor,ActionListener,PropertyChangeListener{
    
    
    private javax.swing.JFileChooser jFileChooser1;
    private String editedValue;
    private final File assets;
    private JTextField copyText;
    private ButtonGroup group;
    private JRadioButton copy;
    private JRadioButton absolute;
    private JRadioButton relative;
    /**
     * Creates new form FileChooserEditor
     */
    public FileChooserEditor(File assets) {
        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setAccessory(this.createAccessor());
        jFileChooser1.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, this);
        this.assets = assets;
    }
    public JFileChooser getFileChooser(){
        return this.jFileChooser1;
    }
    @Override
    public Object getCellEditorValue() {
        return this.editedValue;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editedValue = (String) value;
	if(value != null) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                     int res = jFileChooser1.showOpenDialog(null);
             if(res == JFileChooser.APPROVE_OPTION){
                        traslateFile();
             }
           fireEditingStopped();
                }

                
            });
	  
	}
	return new JLabel(String.valueOf(editedValue));
    }
        public String traslateFile() {
                    File selected = jFileChooser1.getSelectedFile();
                    ButtonModel selection = group.getSelection(); 
                    if(selection.equals(absolute.getModel())){
                       editedValue= selected.getAbsolutePath();
                    }else if (selection.equals(copy.getModel())){
                        
                        try { 
                            File dest = new File(this.copyText.getText()+"//"+selected.getName());
                            FileUtils.copyFile(selected, dest);
                            editedValue = assets.toURI().relativize(dest.toURI()).getPath();
                        } catch (IOException ex) {
                            Logger.getLogger(FileChooserEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }else{
                          editedValue= createReletive(selected);
                    }
                    return editedValue;
                }
   private String createReletive(File selected) {
       String res = editedValue;
       String parentPath = selected.getParent();
       String absAssets = assets.getAbsolutePath();
       if (!parentPath.contains(absAssets)) {
           try {
               absAssets = assets.getCanonicalPath();
               if (!parentPath.contains(absAssets)) {
                   JOptionPane.showMessageDialog(null, "Sorry you can't relativize this file. Tip : the file must be inside the assets folder");
               } else {
                   res = assets.toURI().relativize(selected.toURI()).getPath();
               }
           } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Sorry you can't relativize this file");
           }
       } else {
           res = assets.toURI().relativize(selected.toURI()).getPath();
       }
       return res;      
    }
    private JPanel createAccessor(){
        JPanel result = new JPanel();
        BoxLayout layout = new BoxLayout(result, BoxLayout.Y_AXIS);
        result.setLayout(layout);
        absolute = new JRadioButton("Absolute path");
        relative = new JRadioButton("Relative to Assets folder");
        copy = new JRadioButton("Copy file in Assets folder");
        copy.addActionListener(this);
        JTextField absText = new JTextField();
        absText.setEditable(false);
        JTextField relText = new JTextField();
        relText.setEditable(false);
        copyText = new JTextField();
        copyText.setMaximumSize(new Dimension(400, 25));
        copyText.setEnabled(false);
        group = new ButtonGroup();
        group.add(copy);
        group.add(relative);
        group.add(absolute);
        absolute.setSelected(true);
        result.add(new ImagePreview(jFileChooser1));
        result.add(absolute);
        result.add(relative);
        result.add(copy);
        result.add(copyText);
        result.add(new JPanel());
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!copyText.isEnabled()){
            copyText.setEnabled(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        File selectedFile = this.jFileChooser1.getSelectedFile();
        if (selectedFile != null) {
            copyText.setText(this.assets.getPath() + File.separator + selectedFile.getName());
        }
    }
    private class ImagePreview extends JComponent
                          implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File file = null;

    public ImagePreview(JFileChooser fc) {
        setPreferredSize(new Dimension(100, 50));
        fc.addPropertyChangeListener(this);
    }

    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 90) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(90, -1,
                                                      Image.SCALE_DEFAULT));
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();
        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}
}
