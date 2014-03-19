/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model.visitor;

import jada.ngeditor.model.elements.GButton;
import jada.ngeditor.model.elements.GCheckbox;
import jada.ngeditor.model.elements.GDraggable;
import jada.ngeditor.model.elements.GDropDown;
import jada.ngeditor.model.elements.GDroppable;
import jada.ngeditor.model.elements.GElement;
import jada.ngeditor.model.elements.GHorizontalScrollbar;
import jada.ngeditor.model.elements.GImage;
import jada.ngeditor.model.elements.GImageSelect;
import jada.ngeditor.model.elements.GLabel;
import jada.ngeditor.model.elements.GLayer;
import jada.ngeditor.model.elements.GListBox;
import jada.ngeditor.model.elements.GPanel;
import jada.ngeditor.model.elements.GRadioButton;
import jada.ngeditor.model.elements.GRadioButtonGroup;
import jada.ngeditor.model.elements.GScreen;
import jada.ngeditor.model.elements.GScrollPanel;
import jada.ngeditor.model.elements.GScrollbar;
import jada.ngeditor.model.elements.GTextfield;
import jada.ngeditor.model.elements.GTree;
import jada.ngeditor.model.elements.GVerticalScrollbar;
import jada.ngeditor.model.elements.GWindow;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author cris
 */
public class toXmlVisitor implements Visitor{
    private final Document document;
    private final Element root;
    private Element parent;

    public toXmlVisitor() throws ParserConfigurationException{
       document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
       root = document.createElement("nifty");
       Element style = document.createElement("useStyles");
       Element controls = document.createElement("useControls");
       style.setAttribute("filename", "nifty-default-styles.xml");
       controls.setAttribute("filename", "nifty-default-controls.xml");
       document.appendChild(root);
       root.appendChild(style);
       root.appendChild(controls);
       parent=root;
    }
    
    @Override
    public void visit(GScreen screen) {
        Element pare = this.addXmlNode("screen");
        Stack parentstack = new Stack<GElement>();
        parentstack.push(null);
        GElement top = screen ;
        while(top!=null){
            if(!(top instanceof GScreen)){
                top.accept(this);
            }
            int size = top.getElements().size()-1;
            for(int i=size;i>-1;i--){
                parentstack.push(top.getElements().get(i));
            }
            top =(GElement) parentstack.peek();
            parentstack.pop();
        }
    }

    @Override
    public void visit(GLayer layer) {
        System.out.println("Visited Layer");
    }

    @Override
    public void visit(GPanel panel) {
      System.out.println("Visited pane");
    }

    @Override
    public void visit(GButton button) {
        System.out.println("Visited button");
    }

    @Override
    public void visit(GImage image) {
        System.out.println("Visited Image");
    }

    @Override
    public void visit(GCheckbox checkbox) {
        System.out.println("Visited chekc");
    }

    @Override
    public void visit(GDraggable draggable) {
        System.out.println("drag");
    }

    @Override
    public void visit(GDropDown dropdown) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GDroppable droppable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GHorizontalScrollbar bar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GImageSelect select) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GLabel label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GListBox box) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GRadioButton radiobutton) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GRadioButtonGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GScrollbar bar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GScrollPanel scrollpanel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GTextfield field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GVerticalScrollbar vbar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GTree tree) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GWindow window) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(GElement element) {
      
    }
    
    private Element addXmlNode(String name){
       Element newElement = document.createElement(name);
       parent.appendChild(newElement);
       return newElement;
    }
}
