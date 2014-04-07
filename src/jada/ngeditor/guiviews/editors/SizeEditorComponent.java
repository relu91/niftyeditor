package jada.ngeditor.guiviews.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SizeEditorComponent {

    JSlider sliderpercent = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    JSlider sliderpixel = new JSlider(JSlider.HORIZONTAL, 0, 2000, 100);
    JSpinner spinnerpercent = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
    JSpinner spinnerpixel = new JSpinner(new SpinnerNumberModel(100, 0, 2000, 1));
    JRadioButton radiobuttonpercent = new JRadioButton("Percent");
    JRadioButton radiobuttonpixel = new JRadioButton("Pixel");
    JRadioButton radiobuttonfill = new JRadioButton("Fill (*)");
    JButton buttonaccept = new JButton("Ok");
    JButton buttoncancel = new JButton("Cancel");
    JPanel panel = new JPanel(new BorderLayout(8, 8));
    JPanel west = new JPanel(new GridLayout(3, 1, 4, 4));
    JPanel east = new JPanel(new GridLayout(3, 1, 4, 4));
    JPanel center = new JPanel(new GridLayout(3, 1, 4, 4));
    JPanel south = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    int sourcevalue = 0;
    int editorvalue = 0;
    JDialog dialog;

    public SizeEditorComponent() {
	ButtonGroup radiogroup = new ButtonGroup();
	radiogroup.add(radiobuttonfill);
	radiogroup.add(radiobuttonpercent);
	radiogroup.add(radiobuttonpixel);
	sliderpercent.setLabelTable(sliderpercent.createStandardLabels(25));
	sliderpixel.setLabelTable(sliderpixel.createStandardLabels(500));
	sliderpercent.setMajorTickSpacing(25);
	sliderpercent.setMinorTickSpacing(5);
	sliderpercent.setPaintTicks(true);
	sliderpercent.setPaintLabels(true);
	sliderpixel.setPaintLabels(true);
	sliderpixel.setMajorTickSpacing(500);
	sliderpixel.setMinorTickSpacing(100);
	sliderpixel.setPaintTicks(true);
	west.add(radiobuttonpercent);
	west.add(radiobuttonpixel);
	west.add(radiobuttonfill);
	center.add(sliderpercent);
	center.add(sliderpixel);
	//east.add(new PreferredSizeBox(spinnerpercent, true));
	//east.add(new PreferredSizeBox(spinnerpixel, true));
	south.add(buttonaccept);
	south.add(buttoncancel);
	//south.setBorder(new TopBorder(Color.BLACK));
	panel.add(west, BorderLayout.WEST);
	panel.add(center, BorderLayout.CENTER);
	panel.add(east, BorderLayout.EAST);
	panel.add(south, BorderLayout.SOUTH);

	buttonaccept.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		grabValueFromEditorComponents();
		disposeDialog();
	    }
	});
	buttoncancel.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		restoreSourceValue();
		disposeDialog();
	    }
	});
	radiobuttonpercent.setSelected(true);
	updateEditorState();

	sliderpixel.addChangeListener(new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {
		spinnerpixel.setValue(sliderpixel.getValue());
	    }
	});
	spinnerpixel.addChangeListener(new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {
		sliderpixel.setValue((Integer) spinnerpixel.getValue());
	    }
	});
	sliderpercent.addChangeListener(new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {
		spinnerpercent.setValue(sliderpercent.getValue());
	    }
	});
	spinnerpercent.addChangeListener(new ChangeListener() {

	    public void stateChanged(ChangeEvent e) {
		sliderpercent.setValue((Integer) spinnerpercent.getValue());
	    }
	});
	radiobuttonfill.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		updateEditorState();
	    }
	});
	radiobuttonpercent.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		updateEditorState();
	    }
	});
	radiobuttonpixel.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		updateEditorState();
	    }
	});
    }

    private void disposeDialog() {
	if(dialog != null) {
	    dialog.dispose();
	    dialog = null;
	}
    }

    void updateEditorState() {
	if (radiobuttonfill.isSelected()) {
	    sliderpercent.setEnabled(false);
	    sliderpixel.setEnabled(false);
	    spinnerpercent.setEnabled(false);
	    spinnerpixel.setEnabled(false);
	} else if (radiobuttonpercent.isSelected()) {
	    sliderpercent.setEnabled(true);
	    sliderpixel.setEnabled(false);
	    spinnerpercent.setEnabled(true);
	    spinnerpixel.setEnabled(false);
	} else if (radiobuttonpixel.isSelected()) {
	    sliderpercent.setEnabled(false);
	    sliderpixel.setEnabled(true);
	    spinnerpercent.setEnabled(false);
	    spinnerpixel.setEnabled(true);
	}
    }

    void grabValueFromEditorComponents() {
	if (radiobuttonfill.isSelected()) {
	    editorvalue = -1;
	} else if (radiobuttonpercent.isSelected()) {
	    editorvalue = sliderpercent.getValue();
	} else if (radiobuttonpixel.isSelected()) {
	    editorvalue = sliderpixel.getValue();
	}
    }

    void restoreSourceValue() {
	editorvalue = sourcevalue;
    }

    public void showDialog(Component parent, int sourceValue, int delay, final Runnable closeNotifiable) {
	disposeDialog();
	this.sourcevalue = sourceValue;
	Frame owner = JOptionPane.getFrameForComponent(parent);
	dialog = new JDialog(owner, true);
	dialog.add(panel);
	dialog.pack();
	JOptionPane.showMessageDialog(owner, panel, "Size editor", JOptionPane.PLAIN_MESSAGE);
	dialog.setVisible(true);
	closeNotifiable.run();
	
    }

    public String getValueAsString() {
	if (radiobuttonfill.isSelected()) {
	    return "*";
	} else if (radiobuttonpercent.isSelected()) {
	    return editorvalue + "%";
	} else if (radiobuttonpixel.isSelected()) {
	    return editorvalue + "px";
	} else {
	    throw new IllegalStateException("No type selected");
	}
    }

    public int getValue() {
	return editorvalue;
    }

    public static void main(String[] args) {
	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		JFrame w = new JFrame("test");
		w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		w.add(new SizeEditorComponent().panel);
		w.pack();
		w.setVisible(true);
	    }
	});
    }
}
