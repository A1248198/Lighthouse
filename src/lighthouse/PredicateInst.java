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
package lighthouse;

import Satplan.Literal;
import Satplan.TimeMarks;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author A1248198
 */
public class PredicateInst implements Comparable
{
    public String name;

    public List<String> params;
    public boolean negated;
    public Predicate source;



    public PredicateInst(String name, boolean negated)
    {
        this(name,null,negated);
    }

    public PredicateInst(String name,List<String> params, boolean negated )
    {
        if(params == null){
            params = new LinkedList<>();
        }
        this.params = params;
        this.negated = negated;
        this.name = name;
    }

    public Predicate getSource()
    {
        return source;
    }
    public String getName()
    {
        return name;
    }

    public PredicateInst(String name,List<String> params )
    {
        this(name,params,false);
    }

    public void setSource(Predicate source)
    {
        this.source = source;
    }
    public int getArity(){
        if(source != null){
            return source.getArity();
        }
        else{
            return params.size();
        }
        
    }  

    public String getParam(int i){
        return params.get(i);
    }

    public void setNegated(boolean negated)
    {
        this.negated = negated;
    }

    public List<String> getParams()
    {
        return params;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.params);
        hash = 47 * hash + (this.negated ? 1 : 0);
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
        final PredicateInst other = (PredicateInst) obj;
        if(this.source!=other.source){
            return false;
        }
        if (!Objects.equals(this.params, other.params)) {
            return false;
        }
        if (this.negated != other.negated) {
            return false;
        }
        return true;
    }

    

    public int getParamListSize(){
        return params.size();
    }

    public boolean isNegated()
    {
        return negated;
    }
    public String getParamString(int i){
        return params.get(i);
    }
    @Override
    public String toString()
    {
        String pre = negated?"-":"";
        return pre + name + "(" + Utils.prettyPrintComma(params) + ')';
    }
    
    public final static PredicateInstComparator comp = new PredicateInstComparator();
    
    public static PredicateInstComparator getComparator(){
        return comp;
    }
    public boolean isFluent(){
        return source.isFluent();
    }

    @Override
    public int compareTo(Object o)
    {
        if(o == null){
            return 1;
        }
        if(o  instanceof PredicateInst){
           PredicateInst o1 = this;
           PredicateInst o2 = (PredicateInst) o;
            int res = o1.getName().compareTo(o2.getName());
            if(res!= 0 ){
                return res;
            }

            res = o1.getParamListSize()- o2.getParamListSize();
            if(res!=0){
                return Integer.signum(res);
            }
            int size = o1.params.size();
            
            for(int i = 0; i< size; i+=1){
                res =o1.getParamString(i).compareTo(o2.getParamString(i));
                if(res!=0){
                    return res;
                }
            }
            return 0;
            
            
            
        }
        else{
            Comparable c = (Comparable) o;
            return -c.compareTo(this);
        }
    }

    public Literal mkLiteral(TimeMarks timeMarks)
    {
        return new Literal(mkLiteralName(),negated,timeMarks);
    }
    public String mkLiteralName(){
        String res = getName();
        for(String arg:params){
            res+=arg;
        }
        return res;
    }
    public String toParamString(){
        String res = "";
        for(String arg:params){
            res+=arg;
        }
        return res;
        
    }
    public PredicateInst mkNegClone(){
        PredicateInst res = new PredicateInst(this.name,this.params,true);
        res.setSource(source);
        return res;
    }
    public PredicateInst mkPosClone(){
        PredicateInst res = new PredicateInst(this.name,this.params,false);
        res.setSource(source);
        return res;
    }

}    
    class PredicateInstComparator implements Comparator<Comparable>{
        public PredicateInstComparator(){
            
        }

        @Override
        public int compare(Comparable o1, Comparable o2)
        {
            if (o1 == null){
                if(o2 == null ){
                    return 0;
                }
                return 1;
            }
            if(o2 == null){
                return 1;
            }
            return o1.compareTo(o2);
        }
    } 
