package jada.ngeditor.guiviews.editors;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.Hashtable;
import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;


public class HexColorCellEditor extends AbstractCellEditor implements TableCellEditor {

    private class AlphaChooserPanel extends AbstractColorChooserPanel implements ChangeListener {

	private JSlider slider = new JSlider(0, 255, 255);

	public AlphaChooserPanel() {
	    slider.setPaintTicks(true);
	    slider.setMajorTickSpacing(85);
	    slider.setMinorTickSpacing(17);
	    Hashtable<Integer, JComponent> labels = new Hashtable<Integer, JComponent>();
	    labels.put(0, new JLabel("0"));
	    labels.put(85, new JLabel("85"));
	    labels.put(170, new JLabel("170"));
	    labels.put(255, new JLabel("255"));
	    slider.setLabelTable(labels);
	    slider.setPaintLabels(true);
	    slider.addChangeListener(this);
	    add(slider);
	}

	@Override
	public void updateChooser() {
	    Color color = getColorFromModel();
	    if (color.getAlpha() != slider.getValue()) {
		slider.setValue(color.getAlpha());
	    }
	}

	@Override
	protected void buildChooser() {
	}

	@Override
	public String getDisplayName() {
	    return "Alpha";
	}

	@Override
	public Icon getSmallDisplayIcon() {
	    return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
	    return null;
	}

	public void stateChanged(ChangeEvent e) {
	    int newAlpha = slider.getValue();
	    alpha = newAlpha;
	}
    };
    private int alpha = 255;
    private JDialog colorDialog;
    private JColorChooser colorChooser = new JColorChooser();
    private ActionListener okListener = new ActionListener() {

	public void actionPerformed(ActionEvent e) {
	    Color color = colorChooser.getColor();
	    if (color != null) {
		editedValue = colorToJunk(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
	    }
	    colorDialog.dispose();
	    fireEditingStopped();
	}
    };
    private ActionListener cancelListener = new ActionListener() {

	public void actionPerformed(ActionEvent e) {
	    colorDialog.dispose();
	    fireEditingCanceled();
	}
    };
    private Timer timer = new Timer(100, new ActionListener() {

	public void actionPerformed(ActionEvent e) {
	    colorDialog = JColorChooser.createDialog(frame, "Choose A Color", true, colorChooser, okListener, cancelListener);
	    colorDialog.setVisible(true);
	}
    });
    private String editedValue;
    private Frame frame;

    public HexColorCellEditor() {
	timer.setInitialDelay(100);
	timer.setRepeats(false);
	colorChooser.addChooserPanel(new AlphaChooserPanel());
    }

    public Object getCellEditorValue() {
	return editedValue;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	editedValue = (String) value;
	if(value != null && !editedValue.isEmpty()) {
	    Color c = junkToColor(editedValue);
	    colorChooser.setColor(c);
	    alpha = c.getAlpha();
	}
	frame = JOptionPane.getFrameForComponent(table);
	timer.start();
	return new JLabel(String.valueOf(editedValue));
    }

    @Override
    public boolean isCellEditable(EventObject e) {
	return true;
    }
    
    
    
      public static String colorToJunk(Color c) {
	return
		"#" +
		intToJunk(c.getRed()) +
		intToJunk(c.getGreen()) +
		intToJunk(c.getBlue()) +
		intToJunk(c.getAlpha());
    }

    public static String intToJunk(int value) {
	String s = Integer.toHexString(value & 0xff);
	return s.length() == 1 ? "0" + s : s;
    }

    public static Color junkToColor(String editedValue) {
	if(editedValue == null) {
	    return Color.RED;
	} else {
	    if(editedValue.startsWith("#")) {
		editedValue = editedValue.substring(1);
	    }
	    try {
		long rgba = Long.parseLong(editedValue, 16);
		int r = (int)((rgba >> 24) & 0xff);
		int g = (int)((rgba >> 16) & 0xff);
		int b = (int)((rgba >> 8) & 0xff);
		int a = (int)((rgba & 0xff));
		if(editedValue.length() == 2) {//only red
		    return new Color(r, 0, 0, 255);
		} else if(editedValue.length() == 4) {//rg
		    return new Color(r, g, 0, 255);
		} else if(editedValue.length() == 6) {
		    return new Color(r, g, b, 255);
		} else if(editedValue.length() == 8) {
		    return new Color(r, g, b, a);
		} else {
		    System.out.println("Unrecognized pattern: " + editedValue);
		    return Color.RED;
		}
	    } catch(Exception ex) {
		System.out.println("Unrecognized hexadecimal: " + editedValue);
		return Color.RED;
	    }
	}
    }
}
