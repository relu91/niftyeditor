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
package jada.ngeditor.persistence;

/**
 * All type of elements available in this editor if you want to add one new ,
 * you need first to add a new Type here
 *
 * @author cris
 */
public enum XmlTags {

    SCREEN("screen"),
    LAYER("layer"),
    PANEL("panel"),
    BUTTON("button"),
    LABEL("label"),
    CHECKBOX("checkbox"),
    TEXTFIELD("textfield"),
    NIFTYCONSOLE("nifty-console"),
    WINDOW("window"),
    RADIOBUTTON("radioButton"),
    IMAGE("image"),
    LISTBOX("listBox"),
    SCROLLPANEL("scrollPanel"),
    DROPDOWN("dropDown"),
    HORIZONTALSCROLLBAR("horizontalScrollbar"),
    RADIOBUTTONGROUP("radioButtonGroup"),
    VERTICALSCROLLBAR("verticalScrollbar"),
    NIFTYTREEBOX("nifty-tree-box"),
    IMAGESELECT("imageSelect"),
    DRAGGABLE("draggable"),
    DROPPABLE("droppable");
    
    public static String CONTROL_TAG = "control";
    private String val;

    private XmlTags(String type) {
        this.val = type;
    }
    
    public static XmlTags fromTag(String tag){
        for(XmlTags etag : XmlTags.values()){
            if(etag.val.equals(tag))
                return etag;
        }
        return null;
    }
    @Override
    public String toString() {
        return val;
    }
}
