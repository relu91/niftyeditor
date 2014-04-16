/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.visitor;

import jada.ngeditor.model.GUI;
import jada.ngeditor.model.elements.GButton;
import jada.ngeditor.model.elements.GCheckbox;
import jada.ngeditor.model.elements.GDraggable;
import jada.ngeditor.model.elements.GDropDown;
import jada.ngeditor.model.elements.GDroppable;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GHScrollbar;
import jada.ngeditor.model.elements.GImage;
import jada.ngeditor.model.elements.GImageSelect;
import jada.ngeditor.model.elements.GLabel;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GListBox;
import jada.ngeditor.model.elements.GPanel;
import jada.ngeditor.model.elements.GRadioButton;
import jada.ngeditor.model.elements.GRadioGroup;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.model.elements.GScrollPanel;
import jada.ngeditor.model.elements.GScrollbar;
import jada.ngeditor.model.elements.GTextfield;
import jada.ngeditor.model.elements.GTree;
import jada.ngeditor.model.elements.GVScrollbar;
import jada.ngeditor.model.elements.GWindow;

/**
 *
 * @author cris
 */
public interface Visitor {
    
    public void visit(GUI gui);
    public void visit(GScreen screen);
    public void visit(GLayer layer);
    public void visit(GPanel panel);
    public void visit(GButton button);
    public void visit(GImage image);
    public void visit(GCheckbox checkbox);
    public void visit(GDraggable draggable);
    public void visit(GDropDown dropdown);
    public void visit(GDroppable droppable);
    public void visit(GHScrollbar bar);
    public void visit(GImageSelect select);
    public void visit(GLabel label);
    public void visit(GListBox box);
    public void visit(GRadioButton radiobutton);
    public void visit(GRadioGroup group);
    public void visit(GScrollbar bar);
    public void visit(GScrollPanel scrollpanel);
    public void visit(GTextfield field);
    public void visit(GVScrollbar vbar);
    public void visit(GTree tree);
    public void visit(GWindow window);
    public void visit(GElement element);
}
