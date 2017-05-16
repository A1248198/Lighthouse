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

import Satplan.*;
import LHExceptions.DoubleLiteralDeclarationException;
import LHExceptions.UnRegisteredLiteralNameException;
import it.uniroma1.di.tmancini.teaching.ai.SATCodec.IntRange;
import it.uniroma1.di.tmancini.teaching.ai.SATCodec.SATEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author A1248198
 */
public class SatPlanInstance
{
    public TreeMap<String,Integer> literalsWithoutT;
    public TreeMap<String,Integer> literalsSubT;
    public List<Clause> clauses;
    private int serial = 1;
    public SatPlanInstance(){
        literalsWithoutT = new TreeMap<>();
        literalsSubT = new TreeMap<>();
        clauses = new ArrayList<>();
    }
    public void printLiterals(){
        System.out.print("subt:");
        for(String s : literalsSubT.keySet()){
            System.out.print(s+" ");
        }
        System.out.print("\nwithoutt:");
        for(String s : literalsWithoutT.keySet()){
            System.out.print(s+" ");
        }
        System.out.println();
    }
    public void registerNameSubT(List<String> literalList){
        for(String s: literalList){
            registerName(s);
        }
    }
    public void registerName(Literal l){
        if(l.volat()){
            registerNameSubT(l.name);
        }
        else{
            registerName(l.name);
        }
    }
    public void registerNameSubT(String literal){
        Integer prev=literalsSubT.put(literal, serial);
        if(prev!=null){
            literalsSubT.put(literal, prev);
           // throw new DoubleLiteralDeclarationException();
        }
        else{
            
            serial+=1;
        }
    }
    public void addClause(Clause c){
        c.verify();
        for(Literal l :c.literalList){
            if(l.volat()){
                if(!literalsSubT.containsKey(l.name)){
                    printLiterals();
                   throw new UnRegisteredLiteralNameException(l.name); 
                }
            }
            else{
                if(!literalsWithoutT.containsKey(l.name)){
                   throw new UnRegisteredLiteralNameException(l.name); 
                }
            }
        }
        clauses.add(c);
    }
    public int numClauses(){
        return this.clauses.size();
    }
    public boolean verify(){
        for(Clause c:  this.clauses){
            for(Literal l :c.literalList){
                if(l.volat()){
                    if(!literalsSubT.containsKey(l.name)){
                       throw new UnRegisteredLiteralNameException(l.toString()); 
                    }
                }
                else{
                    if(!literalsWithoutT.containsKey(l.name)){
                       throw new UnRegisteredLiteralNameException(l.name); 
                    }
                }
            }
            
        }
        return true;
    }
    public String clausesToString(){
        String res ="";
        for(Clause c : clauses){
            res += c.toString();
        }
        return res;
    }
    public String getTempPrefixFrom(String desiredPrefix){
        return desiredPrefix;
    }
    public String makeCommodityLiteralName(String prefix, int n){
        return prefix +n;
    }
    public void registerName(String literal){
        Integer prev = literalsWithoutT.put(literal, serial);
        if(prev!=null){
            literalsWithoutT.put(literal, prev);
            //throw new DoubleLiteralDeclarationException();
        }
        else{
            serial +=1;

        }
        
    }
    public void registerName(List<String> literalList){
        for(String s:literalList){
            registerName(s);
        }
    }
    public String makeNameForPredicate(String predicateName){
        String res = predicateName + serial;
        serial+=1;
        return res;
    }
    public void encode2(String filename,int t){
        
		SATEncoder enc = new SATEncoder("satplan", filename);
        int n = literalsWithoutT.size();
        n+=literalsSubT.size()*(t+1);

		IntRange single = new IntRange("single", 1, 1);
        for(String s:literalsWithoutT.keySet()){
            
            enc.defineFamilyOfVariables(s, single );	
        }
        IntRange timed = new IntRange("subt",1,t);
        for(String s:literalsSubT.keySet()){
            enc.defineFamilyOfVariables(s, timed);
        }
        putClauses2(enc,t);
        enc.end();
    }
    private int findIndexForLiteral(Literal l,int t,int maxt){
        if(!l.volat()){
            return literalsWithoutT.get(l.name);
        }
        int tunitoffset = literalsSubT.size();
        int tzeroindex = literalsSubT.get(l.name);
        if(l.tm == TimeMarks.T0){
            return tzeroindex;
        }
        if(l.tm == TimeMarks.TN){
            t = maxt;
        }
        if(l.tm == TimeMarks.TP1){
            t = t +1;
        }
        
        int totaloffset = tunitoffset * t;
        return tzeroindex + totaloffset;
    }
    private void putClauses2(SATEncoder enc, int maxt){
        for(Clause c: clauses){
            if(c.isDynamic()){
                for(int t =1; t<maxt;t+=1){
                    putClause2(enc,c,t,maxt);
                }
            }
            else{
                putClause2(enc,c,0,maxt);
            }
        }
    }
    
    private void sizes(){
        System.out.println("without t :"+this.literalsWithoutT.size());
        System.out.println("with t t :"+this.literalsSubT.size());
        System.out.println(serial);
    }
    
    private String findIndexForLiteral3(Literal l,int t,int maxt){
       switch(l.tm){
            case T0:
                return ""+1;
            case T:
                return ""+t;
            case TP1:
                return ""+(t+1);
            case TN:
                return ""+maxt;
            case ALL:
                return ""+1;
            default:
                throw new AssertionError(l.tm.name());
           
       }
   } 

   private int findIndexForLiteral2(Literal l,int t,int maxt){
       switch(l.tm){
            case T0:
                return 1;
            case T:
                return t;
            case TP1:
                return t+1;
            case TN:
                return maxt;
            case ALL:
                return 1;
            default:
                throw new AssertionError(l.tm.name());
           
       }
   } 
    private void putClause2(SATEncoder enc,Clause c, int t,int maxt){
            for(Literal l : c.literalList){
                if(l.negated){
                    try{
                    enc.addNegToClause(l.name, findIndexForLiteral2(l,t,maxt));
                    }
                    catch(Exception e){
                        System.out.println(l+" "+t+" "+maxt+e.toString());
                        sizes();
                        throw e;
                    }
                }
                else{
                    try{
                    enc.addToClause(l.name, findIndexForLiteral2(l,t,maxt));
                    }
                    catch(Exception e){
                        System.out.println(l+" "+t+" "+maxt+e.toString());
                        sizes();
                        throw e;
                    }
                }
            }
            enc.endClause();
        
    }
    private void putClause3(SATEncoder enc,Clause c, int t,int maxt){
            System.out.print("(");
            for(Literal l : c.literalList){
                if(l.negated){
                    System.out.print("-");
                }
            System.out.print(l.name + findIndexForLiteral3(l,t,maxt));
            System.out.print(" ");
            }
            System.out.println(")");
        
    }
}
