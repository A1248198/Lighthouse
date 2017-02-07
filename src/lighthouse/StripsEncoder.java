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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author A1248198
 */
public class StripsEncoder
{
    Instance instance;
    OutputStream domain;
    OutputStream problem;
    

    public StripsEncoder(Instance instance, OutputStream domain, OutputStream problem)
    {
        this.instance = instance;
        this.domain = domain;
        this.problem = problem;
        
    }

    String buf ="";
    StringBuffer res = new StringBuffer();
    private int psPrint(String s){
        return psPrint(s,-1);
    } 
    private int psPrint(String s, int indentLevel){
        if(indentLevel<0){
            buf+=s;
        }
        else{
            String spacer = mkSpacer(indentLevel-buf.length());
            buf+=spacer+s;
        }
        return buf.length();
    }
    private int psPrintln(String s){
        return psPrintln(s,-1);
        
    } 
    private int psPrintln(String s, int indentLevel){
        int caret = psPrint(s,indentLevel);
        lineBreak();
        return caret;
    }
    private void lineBreak(){
        res.append(buf).append("\n");
        buf ="";
    }
        
    PrintStream ps ;

    private String postprocess(){
        if(!buf.equals("")){
            lineBreak();
        }
        return res.toString().replaceAll("\\s+\\)", ")");
    }
    public String generateStrips(){
        ps = new PrintStream(domain);
        psPrintln("(define (domain "+instance.name+")");
        
        mkPredicateDeclarations();
        for(Action a : instance.getActions()){
            printAction(a,ps,instance);
        }
        psPrint(")");
        ps.print(postprocess());
        res = new StringBuffer();
        ps.close();
        ps = new PrintStream(problem);
        psPrintln("(define (problem "+instance.name+")");
        psPrintln("(:domain "+instance.name+")");
        mkObjects();
        mkInitial();
        mkFinal();
        psPrintln(")");
        ps.print(postprocess());
        ps.close();
        return "";
    }
    private void mkFinal(){
        int goallvl = psPrintln( "(:goal (and");
        for(PredicateInst p:instance.getGoalsWithoutVar()){
            if(p.negated){
                psPrint("( not ",goallvl);
            }
            else{
                psPrint("",goallvl);
            }
            psPrint("("+p.getName()+" ");
            for (String param : p.getParams()){
                psPrint(param+" " );
            }
            if(p.negated){
                psPrintln("))");
            }
            else{
                psPrintln(")");
            }
        }
        if(!instance.getVariablesInFinalState().isEmpty()){
            mkExistentialFinal(goallvl);
        }
        psPrintln("))");
    }
    private void mkExistentialFinal(int startlvl){
        int existlvl = psPrintln( "(exists (",startlvl);
        for(String var : instance.getVariablesInFinalState()){
            psPrint("?"+var+" ");
        }
        psPrintln(") (and");
        printPreds(instance.getGoalsWithVar(),existlvl);
        
        psPrintln("))");
    }
    private void printPreds(List<PredicateInst> what, int lvl){
        for(PredicateInst p:what){
            if(p.negated){
                psPrint("( not ",lvl);
            }
            else{
                psPrint("",lvl);
            }
            psPrint("("+p.getName()+" ");
            for (String param : p.getParams()){
                if(instance.isConstant(param))
                    psPrint(param+" " );
                else
                    psPrint("?"+param+" ");
                
            }
            if(p.negated){
                psPrintln("))");
            }
            else{
                psPrintln(")");
            }
        }
    }
    private void mkInitial(){
        int ilvl = psPrintln( "(:init ");
        for(PredicateInst p:instance.getInitialState()){
            psPrint("("+p.getName()+" ",ilvl);
            for (String param : p.getParams()){
                
                psPrint(param+" " );
            }
            psPrintln(")");
        }
        psPrintln(")",ilvl);
    }
    private  void printParam(String param){
        psPrint(" ");
        if (instance.isVariable(param)){
            psPrint("?");
        }
        psPrint(param);
        psPrint(" ");
    }
    private void mkObjects(){
        int olvl = psPrintln( "(:objects ");
        for (String c  : instance.getConstants()){
            psPrintln(c,olvl);
        }
        psPrintln(")",olvl);
    }
    private void mkPredicateDeclarations(){
        Collection<Predicate> pred = instance.getPredicates();
        int lvl = psPrint("(:predicates ");
        for (Predicate p : pred){
            int arity = p.getArity();
            psPrint("("+p.getName()+" ",lvl);
            for (int j = 0; j<arity ; j+=1){
                psPrint("?"+getLetter(j)+" ");
            }
            psPrintln(")");
        }
        psPrintln(")",lvl);
    }
    private  void printAction(Action a, PrintStream ps,Instance i){
        int actlvl = psPrintln("(:action "+a.name+"");
        int parlvl = psPrint(":parameters (",actlvl);
        for(String v: a.getParameters()){
            psPrintln("?"+v+"",parlvl);
        }
        psPrintln(")",parlvl);
        int prelvl = psPrint(":precondition ( and ",actlvl);
        for(PredicateInst p:a.getPre()){
            if(p.negated){
                psPrint("( not "+ "(" + p.name,prelvl);
                for (String param:p.getParams()){
                    printParam(param);
                }
                psPrintln("))");
            }
            else{
                psPrint("("+p.name,prelvl);
                for (String param:p.getParams()){
                    printParam(param);
                }
                psPrintln(")");
            }
        }
        psPrintln(")",prelvl); //close and and preconditions
        int postlvl = psPrint(":effect ( and ",actlvl);
        for(PredicateInst p:a.getPostNeg()){
            
                psPrint("( not "+ "(" + p.name,postlvl);
                for (String param:p.getParams()){
                    printParam(param);
                }
                psPrintln("))");
        }
        for(PredicateInst p:a.getPostPos()){
                psPrint("("+p.name,postlvl);
                for (String param:p.getParams()){
                    printParam(param);
                }
                psPrintln(")");
        }
        psPrintln("))",actlvl); //close postconditions
        
    }
    public  String getLetter(int i){
        char c = Character.forDigit(i+10, 36);
        return ""+c;
    }

    private  String indent(String res)
    {
        
        return res;
    }

    private String mkSpacer(int level){
        String res = "";
        for(int i = 0;i<level;i+=1){
            res += " ";
        }
        return res;
    }
    
}
