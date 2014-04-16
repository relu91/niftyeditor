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
public abstract class VisitorAdapter implements Visitor{
   
    public VisitorAdapter() {
       
    }
    
    @Override
    public void visit(GScreen screen) {
      
    }

    @Override
    public void visit(GLayer layer) {
        
    }

    @Override
    public void visit(GPanel panel) {
    
    }

    @Override
    public void visit(GButton button) {
       
    }

    @Override
    public void visit(GImage image) {
        
    }

    @Override
    public void visit(GCheckbox checkbox) {
        
    }

    @Override
    public void visit(GDraggable draggable) {
     
    }

    @Override
    public void visit(GDropDown dropdown) {
        
    }

    @Override
    public void visit(GDroppable droppable) {
        
    }

    @Override
    public void visit(GHScrollbar bar) {
       
    }

    @Override
    public void visit(GImageSelect select) {
      
    }

    @Override
    public void visit(GLabel label) {
       
    }

    @Override
    public void visit(GListBox box) {
        
    }

    @Override
    public void visit(GRadioButton radiobutton) {
        
    }

    @Override
    public void visit(GRadioGroup group) {
        
    }

    @Override
    public void visit(GScrollbar bar) {
       
    }

    @Override
    public void visit(GScrollPanel scrollpanel) {
        
    }

    @Override
    public void visit(GTextfield field) {
      
    }

    @Override
    public void visit(GVScrollbar vbar) {
        
    }

    @Override
    public void visit(GTree tree) {
       
    }

    @Override
    public void visit(GWindow window) {
        
    }

    @Override
    public void visit(GElement element) {
       
    }
    
    

    @Override
    public void visit(GUI gui) {
    }
}
