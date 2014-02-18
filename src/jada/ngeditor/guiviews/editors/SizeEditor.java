package jada.ngeditor.guiviews.editors;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class SizeEditor extends AbstractCellEditor implements TableCellEditor, Runnable {
    private SizeEditorComponent editor = new SizeEditorComponent();
    private JLabel label = new JLabel();

    public Object getCellEditorValue() {
	return editor.getValueAsString();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	editor.showDialog(table, asInteger(value), 100, this);
	label.setText(String.valueOf(value));
	return label;
    }

    private Integer asInteger(Object value) {
	if(value == null) {
	    return 0;
	} else {
	    StringBuilder buff = new StringBuilder();
	    String s = value.toString();
	    for(int i = 0; i < s.length(); i++) {
		char c = s.charAt(i);
		if(Character.isDigit(c)) {
		    buff.append(c);
		}
	    }
	    if(buff.length() == 0) {
		buff.append(0);
	    }
	    int num = Integer.parseInt(buff.toString());
	    if(s.endsWith("px")) {
		editor.radiobuttonpixel.setSelected(true);
		editor.updateEditorState();
		editor.spinnerpixel.setValue(num);
	    } else if(s.endsWith("%")) {
		editor.radiobuttonpercent.setSelected(true);
		editor.updateEditorState();
		editor.spinnerpercent.setValue(num);
	    } else if(s.equals("*")) {
		editor.radiobuttonfill.setSelected(true);
		editor.updateEditorState();
	    }
	    return num;
	}
    }

    public void run() {
	fireEditingStopped();
    }

}
