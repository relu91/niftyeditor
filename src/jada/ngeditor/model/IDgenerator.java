/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jada.ngeditor.model;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 *
 * @author Cris
 */
public class IDgenerator {
    private static EnumMap<Types,ArrayList<String>> ids ;
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
        ids= new EnumMap<Types,ArrayList<String>>(Types.class);
        counter = new int [Types.values().length];
        for(int i=0;i<counter.length;i++)
            counter[i]=0;
    }
    
    
    public boolean isUnique(Types t,String id){
        if(ids.containsKey(t))
            return !ids.get(t).contains(id);
        else
            return true;
    }
    
    public String generate(Types t){
        int i = t.ordinal();
        String res=""+t+""+counter[i]++;
        while(!isUnique(t,res))
            res=""+t+""+counter[i]++;
        this.addID(res, t);
        return res;
    }
    
    public void addID(String id, Types t){
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
    
}
