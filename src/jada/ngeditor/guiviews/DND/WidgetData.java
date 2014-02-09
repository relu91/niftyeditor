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
package jada.ngeditor.guiviews.DND;

import jada.ngeditor.model.elements.GElement;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;

/**
 *
 * @author cris
 */
public class WidgetData implements Transferable{
    public static final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "widget");
    public static final DataFlavor POINTFLAVOR = new DataFlavor(Point2D.class, "poin2d");
    private final GElement e;
    private final Point2D p;
   
    
    public WidgetData(GElement element, int diffX,int diffY){
        e=element;
        p = new Point2D.Double(diffX, diffY);
    }
    public WidgetData(GElement element){
        this.e = element;
        p = null;
    }
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { FLAVOR , POINTFLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if(flavor == FLAVOR)
            return  true;
        else if (flavor == POINTFLAVOR && p!=null){
            return true;
        }
        return false;
            
    }
   
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if(isDataFlavorSupported(flavor)){
            if(flavor == FLAVOR)
                return e;
            else 
                return p;
        }else
            throw new UnsupportedFlavorException(flavor);
      
    }
    
}
