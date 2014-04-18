package jada.ngeditor.guiviews.editors;

import de.lessvoid.nifty.tools.SizeValue;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

public class SizeEditor extends AbstractCellEditor implements TableCellEditor,ActionListener,PropertyChangeListener {
    private JPanel editorPane;
    private ButtonGroup group;
    private JRadioButton perc;
    private JRadioButton px;
    private JRadioButton fill;
    private ValueEditor percEditor = new ValueEditor();
    private ValueEditor pxEditor = new ValueEditor();
    private SizeValue edited = null;
    public SizeEditor(){
        GridBagLayout gridLayout = new GridBagLayout();
        editorPane = new JPanel(gridLayout);
        GridBagConstraints c = new GridBagConstraints();
        perc = new JRadioButton("Percentage");
        px = new JRadioButton("Pixel");
        fill = new JRadioButton("*");
        fill.setToolTipText("Wildcard, leave this value to layoutmanger");
        fill.addActionListener(this);
        px.addActionListener(this);
        perc.addActionListener(this);
        group = new ButtonGroup();
        group.add(perc);
        group.add(px);
        group.add(fill);
        px.setSelected(true);
        c.gridx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        editorPane.add(perc,c);
        c.gridx=1;
        editorPane.add(percEditor,c);
        c.gridx = 0;
        c.gridy = 1;
        editorPane.add(px,c);
        c.gridx = 1;
        c.gridy = 1;
        editorPane.add(pxEditor,c);
        c.gridx = 0;
        c.gridy = 2;
        editorPane.add(fill,c);
        this.percEditor.setEnabled(false);
        this.percEditor.setValue(SizeValue.percent(50));
        this.pxEditor.setEnabled(true);
        percEditor.addPropertyChangeListener(this);
        pxEditor.addPropertyChangeListener(this);
        
    }
    @Override
    public Object getCellEditorValue() {
	return this.edited.toString();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        SizeValue val = new SizeValue(value.toString());
        this.setUpByType(val);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               int res = JOptionPane.showConfirmDialog(null, editorPane,"SizeEditor", JOptionPane.OK_CANCEL_OPTION);
        if(res == JOptionPane.OK_OPTION){
            fireEditingStopped();
        }else{
            cancelCellEditing();
        }
            }
        });
	
	return new JLabel(this.edited.toString());
    }

    private void setUpByType(Object value) {
	if(value == null) {
	   this.px.setEnabled(true);
	} else {
	    String s = value.toString();
	    if(s.endsWith("px")) {
                this.percEditor.setEnabled(false);
                this.pxEditor.setEnabled(true);
                this.pxEditor.setValue((SizeValue)value);
                this.px.getModel().setSelected(true);
	    } else if(s.endsWith("%")) {
                this.percEditor.setEnabled(true);
                this.pxEditor.setEnabled(false);
                this.percEditor.setValue((SizeValue)value);
                this.perc.getModel().setSelected(true);
	    } else if(s.equals("*")) {
		this.percEditor.setEnabled(false);
                this.pxEditor.setEnabled(false);
                this.fill.getModel().setSelected(true);
                this.edited = SizeValue.wildcard();
            }
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(fill.equals(source)){
            this.setUpByType(new SizeValue("*"));
        }else if(px.equals(source)){
            this.setUpByType(new SizeValue("0px"));
        }else if(perc.equals(source)){
            this.setUpByType(new SizeValue("50%"));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("value")){
            this.edited = (SizeValue) evt.getNewValue();
        }
    }
}
