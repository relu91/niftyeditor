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

import jada.ngeditor.persistence.XmlTags;
import jada.ngeditor.model.elements.GElement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Id util generator
 * @author Cris
 */
public class IDgenerator {
    private static HashMap<Class<? extends GElement>,ArrayList<String>> ids ;
    private int [] counter;
    private static IDgenerator instance=null ;
    
    
    public static IDgenerator getInstance(){
        if(instance==null){
            instance = new IDgenerator();
            return instance;
        }else
            return instance;
        
    }
    private IDgenerator(){
        invalidate();
    }
    
    
    public boolean isUnique(java.lang.Class<? extends GElement> t,String id){
        if(ids.containsKey(t))
            return !ids.get(t).contains(id);
        else
            return true;
    }
    
    public String generate(java.lang.Class<? extends GElement> t){
        int i = 0;
        if(this.ids.get(t) != null){
            i= this.ids.get(t).size();
        }     
        String res = t.getSimpleName()+i;
        this.addID(res, t);
        return res;
    }
    
    public void addID(String id, java.lang.Class<? extends GElement> t){
        if(!isUnique(t,id))
            throw new IllegalArgumentException("The ID is not unique");
        
        if(this.ids.containsKey(t))
            ids.get(t).add(id);
        else {
            ArrayList<String> tmp = new ArrayList<String>();
            tmp.add(id);
            ids.put(t, tmp);
        }
            
    }

    public final void invalidate() {
        ids= new HashMap<Class<? extends GElement>,ArrayList<String>>();
        counter = new int [XmlTags.values().length];
        for(int i=0;i<counter.length;i++)
            counter[i]=0;
    }
    
}
