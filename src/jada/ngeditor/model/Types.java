/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

/**
 *
 * @author cris
 */
public enum Types {
    
    SCREEN("screen",false),
    LAYER("layer",false),
    PANEL("panel",false),
    BUTTON("button",true),
    LABEL("label",true),
    CHECKBOX("checkbox",true),
    TEXTFIELD("textfield",true),
    NIFTYCONSOLE("nifty-console",true),
    WINDOW("window",true),
    RADIOBUTTON("radioButton",true),
    IMAGE("image",false),
    LISTBOX("listBox",true);
    
    public static String CONTROL_TAG = "control";
    private String val;
    private boolean iscont;
    
    private Types(String type,boolean isControl){
        this.val = type;
        this.iscont=isControl;
    }
    
    public boolean isControl(){
        return this.iscont;
    }
    
    public static boolean isControlTag(String tag){
        return tag.equals(CONTROL_TAG);
    }
    
    
    public static String convert(String tag){
        String tmp = tag.toUpperCase();
        String result="";
        for(int i = 0;i<tmp.length();i++){
             char c = tmp.charAt(i);
             if(c!='-')
                 result+=c;
        }
        return result;
    }
    
    @Override
    public String toString(){
        return val;
    }
}
