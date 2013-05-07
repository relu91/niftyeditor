/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author cris
 */
public class FileChooserEditor extends AbstractCellEditor implements TableCellEditor{
    
    private JDialog dialog;
    private javax.swing.JFileChooser jFileChooser1;
    private String editedValue;
    /**
     * Creates new form FileChooserEditor
     */
    public FileChooserEditor(java.awt.Frame parent) {
        dialog = new JDialog(parent,true);
    
        jFileChooser1 = new javax.swing.JFileChooser();

        dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

     
        
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(dialog.getContentPane());
        dialog.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        dialog.pack();
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
                     int res = jFileChooser1.showOpenDialog(dialog);
             if(res == JFileChooser.APPROVE_OPTION){
                 editedValue= jFileChooser1.getSelectedFile().getAbsolutePath();
             }
           fireEditingStopped();
                }
            });
	  
	}
	
	return new JLabel(String.valueOf(editedValue));
    }

   
}
