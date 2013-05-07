/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.listeners;


import jada.ngeditor.controller.ElementEditor;
import jada.ngeditor.model.elements.GElement;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 *
 * @author cris
 */
public class ProprietiesListener implements TableModelListener{
    private ElementEditor editor;
    
    public ProprietiesListener(){
        editor = new ElementEditor(null);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        if(e.getType() == TableModelEvent.UPDATE){
          TableModel mod = (TableModel) e.getSource();
          String proName = (String) mod.getValueAt(e.getLastRow(), 0);
          String proVal = (String) mod.getValueAt(e.getLastRow(), 1);
          if(proName != null && !proName.isEmpty()) {
		    if(proVal == null || proVal.isEmpty()) {
			editor.removeAttribute(proName);
		    } else {
			editor.setAttribute(proName, proVal);
		    }
                    
		}
        
          
        }
    }
    
    public void setEditor(ElementEditor editor){
        this.editor = editor;
    }
    
}
