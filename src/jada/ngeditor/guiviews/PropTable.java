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
package jada.ngeditor.guiviews;

import jada.ngeditor.controller.CommandProcessor;
import jada.ngeditor.guiviews.editors.FileChooserEditor;
import jada.ngeditor.guiviews.editors.HexColorCellEditor;
import jada.ngeditor.guiviews.editors.JComboEditor;
import jada.ngeditor.guiviews.editors.SizeEditor;
import jada.ngeditor.model.GUI;
import jada.ngeditor.model.GuiEditorModel;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author cris
 */
public class PropTable extends JTable implements Observer{
    private static final JComboEditor layoutEditor = new JComboEditor("absolute","center", "vertical", "horizontal","overlay");
    private static final JComboEditor alingEditor = new JComboEditor("left","center", "right");
    private static final JComboEditor valingEditor = new JComboEditor("top","center", "bottom");
    private static final SizeEditor sizeEditor = new SizeEditor();
    private static final String [] sizeValues = {"height","width","x","y"};
    private GUI gui;
    public PropTable() {
	super();
        CommandProcessor.getInstance().getObservable().addObserver(this);
        this.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
    }
    
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
	TableCellEditor editor = super.getCellEditor(row, column);
	Object field = getValueAt(row, 0);
	if(field != null ) {
	    String pname = field.toString();
	    if(pname.equals("childLayout")) {
		editor = layoutEditor;
	    }else if(pname.contains("Color")||pname.contains("color") ){
                editor = new HexColorCellEditor();
            }else if(pname.equalsIgnoreCase("filename") 
                    || pname.equalsIgnoreCase("backgroundImage")){
                editor = new FileChooserEditor(this.gui.getAssetFolder());
            }else if(isSizeValue(pname)){
                editor = sizeEditor;
            }else if( pname.equalsIgnoreCase("valign")){
                editor = valingEditor;
            }else if( pname.equalsIgnoreCase("align")){
                editor = alingEditor;
            }
	return editor;
    }
       return editor;
    }
    
   

    private boolean isSizeValue(String pname) {
        boolean res = false;
        for(String s : sizeValues){
            if(s.equals(pname))
                res = true;
        }
        return res;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof GuiEditorModel){
            this.gui = ((GuiEditorModel)o).getCurrent();
        }
    }
    
}
