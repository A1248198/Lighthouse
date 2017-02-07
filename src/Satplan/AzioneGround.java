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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lighthouse.Action;
import lighthouse.Instance;
import lighthouse.PredicateInst;

/**
 *
 * @author A1248198
 */
public class AzioneGround implements Comparable<AzioneGround>
{

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.a);
        hash = 47 * hash + Objects.hashCode(this.params);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AzioneGround other = (AzioneGround) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        
        if (!Objects.equals(this.params, other.params)) {
            return false;
        }
        return true;
    }
    Action a;
    List<String> params;

    public AzioneGround(Action a, List<String> params)
    {
        this.a = a;
        this.params = params;
    }
    public List<PredicateInst> mkPreconditions(Instance i){
        return a.mkPreconditions(params);
    }
    public String getName(){
        return a.getName();
    }
    public String getCNFName(){
        return SatPlanSettings.ACTIONPREFIX+a.getName();
    }
    public String toCNFParams(){
        //return Arrays.toString(params.toArray());
        String res ="";
        for(String str:params){
            res +=str;
        }
        return res;
    }
    public String toFormulaParams(){
        String res ="";
        for(String str:params){
            res +=str;
        }
        return res;
    }

    @Override
    public String toString()
    {
        return "AzioneGround{" + "a=" + a + ", params=" + Arrays.toString(params.toArray()) + '}';
    }

    String toFormulaName()
    {
        return getName()+toFormulaParams();
    }

    String mkLitName()
    {
        return getCNFName()+toCNFParams();
    }

    @Override
    public int compareTo(AzioneGround o)
    {
        int res;
        res = a.getName().compareTo(o.getName());
        if(res!=0){
            return res;
        }
        else{
            for(int i = 0 ; i< params.size(); i+=1){
                res = params.get(i).compareTo(o.params.get(i));
                if(res!=0){
                    return res;
                }
            }
        }
        return 0;
        
    }

    boolean causes(PredicateInst what)
    {
        return a.causes(what,this.params);
    }
    

}
