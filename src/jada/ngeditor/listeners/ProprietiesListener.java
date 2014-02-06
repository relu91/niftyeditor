/* Copyright 2012 Aguzzi Cristiano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package jada.ngeditor.listeners;


import jada.ngeditor.controller.ElementEditor;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 *
 * @author cris
 */
public class ProprietiesListener implements TableModelListener{
    private ElementEditor editor = null;
    
    public ProprietiesListener(){
        editor = new ElementEditor(null,null);
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
