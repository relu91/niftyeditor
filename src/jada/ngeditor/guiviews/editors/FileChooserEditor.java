/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import jada.ngeditor.model.utils.FileUtils;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
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
    /**
     * Creates new form FileChooserEditor
     */
    public FileChooserEditor(File assets) {
        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setAccessory(this.createAccessor());
        jFileChooser1.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, this);
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
                 ButtonModel selection = group.getSelection(); 
                 if(!selection.equals(copy))
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
        BoxLayout layout = new BoxLayout(result, BoxLayout.Y_AXIS);
        result.setLayout(layout);
        JRadioButton absolute = new JRadioButton("Absolute path");
        JRadioButton relative = new JRadioButton("Relative to Assets folder");
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
        copyText.setText(this.assets.getPath()+File.separator+this.jFileChooser1.getSelectedFile().getName());
    }
}
