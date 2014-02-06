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
package jada.ngeditor.model;

import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.parser.XSOMParser;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author cris
 */
public class PropretiesResolver {
    
    public final static PropretiesResolver inst= new PropretiesResolver();
    private HashMap<elementType,ArrayList<String>> prop;
    
    public PropretiesResolver (){
        try {
          // url to remote xsd file
          //  String xmlfile = "http://nifty-gui.sourceforge.net/nifty.xsd";
            String localfile = "/jada/ngeditor/resources/properties.xsd";
            URL res;
            try{
            res = new URL("https://raw.github.com/void256/nifty-gui/1.4/nifty-core/src/main/resources/nifty.xsd");
            }catch(Exception e){
                res = getClass().getResource(localfile);
            }
            
            XSOMParser parser = new XSOMParser();
            res = getClass().getResource(localfile);
            parser.parse(res);
            XSSchemaSet sset = parser.getResult();
            XSSchema s = sset.getSchema(1);
            prop = new HashMap<elementType,ArrayList<String>>();
    

      // =========================================================
      // types namepace
     
      Iterator<XSComplexType> ctiter = s.iterateComplexTypes();
     
      while (ctiter.hasNext())
      {
        XSComplexType ct = (XSComplexType) ctiter.next();
        String typeName = ct.getName();
        // these are extensiSons so look at the base type to see what it is
        String baseTypeName = ct.getBaseType().getName();
        Iterator<XSAttributeUse> t = (Iterator<XSAttributeUse>) ct.iterateDeclaredAttributeUses();
        ArrayList<String> pr = new ArrayList<String>();
        while(t.hasNext()){
          String prova = t.next().getDecl().getName();
          pr.add(prova);
      }
        this.prop.put(new elementType(typeName,baseTypeName), pr);
        
      }
       
        Iterator<XSSimpleType> iterate = s.iterateSimpleTypes();
        while(iterate.hasNext()){
            XSSimpleType type = (XSSimpleType) iterate.next();
           
        }
       } catch (SAXException ex) {
            Logger.getLogger(PropretiesResolver.class.getName()).log(Level.SEVERE, null, ex);
        } 
   }
    
    public ArrayList<String> resolve(String name){
        elementType key = null;
        ArrayList<String> result;
        for(elementType ele : prop.keySet()){
            if(ele.name.equals(name)){
                key=ele;
            }
        }
       if(key!=null){
        result = this.prop.get(key);
        recAddProp(key,result);
        return result;
       }else{
           key = new elementType("elementType","anyType");
           System.out.println(prop.get(key));
           return this.prop.get(key);
       }
           
    }
    
    private  void recAddProp(elementType ele,ArrayList<String> res){
        if(ele.parent.equals("anyType"))
            return ;
        else{
            
            elementType newEle=null;
            for(elementType element : prop.keySet()){
                if(element.name.equals(ele.parent)){
                    newEle=element;
                    break;
                }
            }
            res.addAll(this.prop.get(newEle));
            recAddProp(newEle,res);
        }
            
    }
    
    private class elementType extends Object{
        private final String parent;
        private final String name;
       
        public elementType(String name, String isA){
            this.name = name;
            this.parent= isA;
        }
        
        @Override
        public int hashCode(){
            return name.hashCode();
        }
        @Override
        public boolean equals(Object o){
            elementType t = (elementType)o;
            return t.name.equals(name);
        }
        
        
    }
}
