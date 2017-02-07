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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Action declaration.
 * @author A1248198
 */
/**

*/
public class Action
{

    public String name;
    public List<String> variables;
    public List<PredicateInst> pre;
    public List<PredicateInst> postPos;
    public List<PredicateInst> postNeg;
    private Map<String, Integer> rev = new HashMap<>();
    private Instance instance;
    /**
     * Action declaration
     * @param name action name
     * @param variables list of variable names
     * @param pre preconditions
     * @param post post conditions
     * @param instance planning instance in which the action exists
     */
    public Action(String name, List<String> variables, List<PredicateInst> pre, List<PredicateInst> post,Instance instance)
    {
        this.name = name;
        this.variables = variables;
        this.pre = pre;
        this.instance = instance;
        postPos = new LinkedList<>();
        postNeg = new LinkedList<>();
        for (PredicateInst pi : post) {
            if (pi.negated) {
                postNeg.add(pi);
            }
            else {
                postPos.add(pi);
            }
        }
        int j = 0;
        for (String var : variables) {
            rev.put(var, j);
            j+=1;
        }
        

    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.name);
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
        final Action other = (Action) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    boolean debug = true;
    private void dbg(String str){
        System.out.println(str);
    }
    private String translate(String varName, List<String> args){
        int index =0;
        try{
        index = rev.get(varName);
        }
        catch(Exception e){
            System.out.println(this);
            System.out.println(varName);
            System.out.println(args);
            throw e;

        }
        return args.get(index);
    }
    private boolean isValid(PredicateInst pred, List<String> args){
        if (pred.isFluent()) {
            return true;
        }
        List<String> toCheck = new ArrayList<>(pred.getArity());
        boolean neg = pred.isNegated();
        for (String str : pred.getParams()) {
            if (instance.isConstant(str)) {
                toCheck.add(str);
            }
            else {
                toCheck.add(translate(str,args));

            }
        }
        if(instance.isInInitialS(pred.getName(), toCheck)!=neg){
            return true;
        }
        else{
            //if(debug){dbg("discarding: "+neg+pred.getName()+Arrays.toString(toCheck.toArray()));}
            return false;
        }
    }
    public boolean areValidArgs(List<String> args)
    {
        for (PredicateInst pr : this.pre) {

            if(!isValid(pr,args)){
                return false;
            }        

        }
        return true;
    }

    public int getArity()
    {
        return variables.size();
    }

    public int getIndexOfVar(String v)
    {
        int res = variables.indexOf(v);
        if (res != -1) {
            return res;
        }
        throw new RuntimeException("Variable not found" + v + toString());
    }

    public String getName()
    {
        return name;
    }

    public List<String> getParameters()
    {
        return variables;
    }

    public List<PredicateInst> getPre()
    {
        return pre;
    }

    public List<PredicateInst> getPostPos()
    {
        return postPos;
    }

    public List<PredicateInst> getPostNeg()
    {
        return postNeg;
    }

    @Override
    public String toString()
    {
        return name + "(" + Utils.prettyPrintComma(variables) + "){\npre:" + Utils.prettyPrintComma(pre) + "\npost:" + Utils.prettyPrintComma(postNeg) + Utils.prettyPrintComma(postPos) + '}';
    }

    private PredicateInst translate(PredicateInst what,List<String> params){
        List<String> res = new ArrayList<>(what.getArity());
        for(String arg : what.params){
            if(instance.isConstant(arg)){
                res.add(arg);
            }
            else{
                res.add(translate(arg,params));
            }
        }
        PredicateInst r = new PredicateInst(what.getName(),res,what.negated);
        r.setSource(what.getSource());
        return r;
    }

    public List<PredicateInst> mkPreconditions( List<String> params)
    {
        List<PredicateInst> res = new ArrayList<>(this.pre.size());
        for(PredicateInst p : pre){
            if(p.isFluent())
            res.add(translate(p,params));
        }
        return res;
        
    }

    public boolean causes(PredicateInst what, List<String> params)
    {
        List<PredicateInst> where;
        if(what.isNegated()){
            where = postNeg;
        }
        else{
            where = postPos;
        }
        for(PredicateInst p : where){
            PredicateInst translated = translate(p,params);
            if(what.equals(translated)){
                //System.out.println(name+" what: "+what+" prec: "+p);
                //System.out.println(toString());
                return true;
            }
        }
        return false;
    }

}
