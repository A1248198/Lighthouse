/* 
 * Copyright (C) 2017 A1248198
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Satplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lighthouse.Action;
import lighthouse.Instance;

/**
 *
 * @author A1248198
 */
public class ActionFactory
{
    Action action;
    Instance instance;
    Map<String,String> unification;
    int indices[];
    List<String> constants;
    int count;
    private int size;
    private int listSize;
    int given =0;
    boolean set;
    private static final Map<String,String> emptyMap = new HashMap<>();
    public ActionFactory(Action a, Instance i){
        this(a,i,emptyMap);
    }
    public ActionFactory(Action a, Instance i, Map<String, String> unification)
    {
        this.action = a;
        this.instance = i;
        this.unification = unification;
        count = a.getArity() - unification.size();
        constants= i.getConstants();
        listSize = constants.size();
        size =(int) Math.pow(i.getConstants().size(), count);
        indices = new int[count];
    }
    public static boolean debug = false;
    private void dbg(String s){
        System.out.println(s);
    }
    public AzioneGround get(){
        return getex();
        
    }
    public AzioneGround getex(){
        String back[] = new String[action.getArity()];
        List<String> res = Arrays.asList(back);
        boolean exausted =false;
        while(!exausted){
            int k= 0;
            int j= 0;
            for(String varname:action.variables){
                if(debug)dbg(String.format("looking for %s, k=%d,j = %d, count=%d,given = %d,size =%d ", varname,k,j,count,given,size));
                if(debug)dbg(Arrays.toString(indices));
                String val = unification.get(varname);
                if(debug)dbg(val);
                if(val == null){
                    val = constants.get(indices[k]);
                    k+=1;
                }
                res.set(j,val);
                j+=1;
                if(debug)dbg(Arrays.toString(res.toArray()));
            }
            if(hasNext()){
                moveToNext();
            }
            else{
                exausted = true;
            }
            if(action.areValidArgs(res)){
                return new AzioneGround(action,res);
            }
        }
        return null;
    }

    public boolean hasNext()
    {
        if(given +1 < size){
            return true;
        }
        return false;
    //    if(count ==0){
    //        return false;
    //    }
    //    if(indices[0]==listSize){
    //        return false;
    //    }
    //    else{
    //        return true;
    //    }
    }
    public void moveToNext(){
        given+=1;
        indices[count-1]+=1;
        for(int i = count -1 ; i>0;i-=1){
            if(indices[i] >= listSize  ){
                indices[i-1]+=1;
                indices[i] = 0;
            }
        }
        
    }


}
