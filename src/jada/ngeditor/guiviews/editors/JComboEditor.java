/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.guiviews.editors;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellEditor;
/**
 * 
 * @author tukano.pgcom
 */
public class JComboEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private final DefaultComboBoxModel model = new DefaultComboBoxModel();
    private final JComboBox box = new JComboBox(model);
    private Object editorValue;
    private Object tableValue;

    public JComboEditor(Object...defaultValues) {
	box.setEditable(false);
	for (int i = 0; i < defaultValues.length; i++) {
	    Object object = defaultValues[i];
	    model.addElement(object);
	}
	box.addActionListener(this);
    }

    public JComboEditor setBoxRenderer(ListCellRenderer renderer) {
	box.setRenderer(renderer);
	return this;
    }

    public Object getCellEditorValue() {
	return editorValue;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	editorValue = tableValue = value;
	model.setSelectedItem(value);
	return box;
    }

    public void actionPerformed(ActionEvent e) {
	editorValue = model.getSelectedItem();
	fireEditingStopped();
    }

    @Override
    public void cancelCellEditing() {
	editorValue = tableValue;
	fireEditingCanceled();
    }

    @Override
    public boolean stopCellEditing() {
	editorValue = box.getSelectedItem();
	fireEditingStopped();
	return true;
    }
}