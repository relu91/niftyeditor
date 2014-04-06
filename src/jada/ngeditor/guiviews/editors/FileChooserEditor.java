/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import jada.ngeditor.model.utils.FileUtils;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * Nifty editor file chooser.
 * @author cris
 */
public class FileChooserEditor extends AbstractCellEditor implements TableCellEditor{
    
    
    private javax.swing.JFileChooser jFileChooser1;
    private String editedValue;
    private final File assets;
    /**
     * Creates new form FileChooserEditor
     */
    public FileChooserEditor(File assets) {
        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setAccessory(this.createAccessor());
        this.assets = assets;
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
                 JCheckBox selection = (JCheckBox) jFileChooser1.getAccessory().getComponent(0);
                 if(!selection.isSelected())
                    editedValue= jFileChooser1.getSelectedFile().getAbsolutePath();
                 else{
                     File selected = jFileChooser1.getSelectedFile();
                     try { 
                         File dest = new File(assets.getAbsolutePath()+"//"+selected.getName());
                         FileUtils.copyFile(selected, dest);
                         editedValue = assets.toURI().relativize(dest.toURI()).getPath();
                     } catch (IOException ex) {
                         Logger.getLogger(FileChooserEditor.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     
                 }
             }
           fireEditingStopped();
                }
            });
	  
	}
	return new JLabel(String.valueOf(editedValue));
    }

   
    private JPanel createAccessor(){
        JPanel result = new JPanel();
        result.add(new JCheckBox("Relative path"));
        return result;
    }
}
