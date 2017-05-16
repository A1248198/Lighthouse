/*
 * Copyright (C) 2017 dm
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
package Stat;

import static java.lang.Math.pow;
import java.util.HashMap;
import java.util.Map;
import lighthouse.Action;
import lighthouse.Instance;

/**
 *
 * @author dm
 */
public class Stat
{
    public int numActions;
    public int numConsts;
    public Map<String,Integer> groundAct = new HashMap<>();
    public Map<String,Integer> time = new HashMap<>();
    public Map<String,Integer> clauses = new HashMap<>();
    public int numClauses[]=new int[5];
    public void init(Instance i){
        numActions = i.getActions().size();
        numConsts = i.getConstants().size();
        int numGroundActionsExp = 0;
        for(Action a :  i.getActions()){
            numGroundActionsExp+=pow(numConsts,a.getArity());
        }
        groundAct.put("Expected", numGroundActionsExp);
    }
    public void report(String method,int ground,int tim,int cla){
        groundAct.put(method, ground);
        time.put(method, tim);
        clauses.put(method, cla);
    }
    public void print(){
        System.out.println(buildStr());   
    }
    public String buildStr(){
        return buildStr("Number of ground actions ",groundAct) + "\n" + buildStr("time taken to encode",time) + "\n" + buildStr("number of clauses ",clauses) +"\n";
    }
    private String buildStr(String name, Map<String,Integer> map){
        return name+" "+buildStr(map);
    }
    private String buildStr(Map<String,Integer> map){
        String res = "";
        for(String met : map.keySet()){
            res += met+" : "+map.get(met)+"  ";
        }
        return res;
    }
    
}
