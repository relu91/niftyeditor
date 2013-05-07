/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews;

import jada.ngeditor.guiviews.editors.FileChooserEditor;
import jada.ngeditor.guiviews.editors.HexColorCellEditor;
import jada.ngeditor.guiviews.editors.JComboEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author cris
 */
public class PropTable extends JTable{
    private static final JComboEditor layoutEditor = new JComboEditor("center", "vertical", "horizontal");
    public PropTable() {
	super();
        this.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
    }
    
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
	TableCellEditor editor = super.getCellEditor(row, column);
	Object field = getValueAt(row, 0);
	if(field != null ) {
	    String pname = field.toString();
	    if(pname.equals("childLayout")) {
		editor = layoutEditor;
	    }else if(pname.contains("Color")||pname.contains("color") ){
                editor = new HexColorCellEditor();
            }else if(pname.equalsIgnoreCase("filename") 
                    || pname.equalsIgnoreCase("backgroundImage")){
                editor = new FileChooserEditor(JOptionPane.getFrameForComponent(this));
            }
	return editor;
    }
       return editor;
    }
    
}
