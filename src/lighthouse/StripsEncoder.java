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
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author A1248198
 */
public class StripsEncoder
{
    public static  String generateStrips(Instance i,OutputStream domain,OutputStream problem) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        __generateStrips(i,baos,problem);
        String res = baos.toString();
        res= indent(res);
        domain.write(res.getBytes());
        return res;
    }
    private static  String __generateStrips(Instance i,OutputStream domain,OutputStream problem){
        PrintStream ps = new PrintStream(domain);
        ps.println("(define (domain "+i.name+")");
        Collection<Predicate> pred = i.getPredicates();
        ps.print("(:predicates ");
        for (Predicate p : pred){
            int arity = p.getArity();
            ps.print("("+p.getName()+" ");
            for (int j = 0; j<arity ; j+=1){
                ps.print("?"+getLetter(j)+" ");
            }
            ps.print(" )");
        }
        ps.println(")");

        for(Action a : i.getActions()){
            printAction(a,ps,i);
        }
        ps.print(")");
        ps.close();
        ps = new PrintStream(problem);
        ps.println("(define (problem "+i.name+")");
        ps.println("(:domain "+i.name+")");
        ps.println( "(:objects ");
        for (String c  : i.getConstants()){
            ps.print(c+" ");
        }
        ps.println(")");
        ps.println( "(:init ");
        for(PredicateInst p:i.getInitialState()){
            ps.print("("+p.getName()+" ");
            for (String param : p.getParams()){
                
                ps.print(param+" " );
            }
            ps.println(")");
        }
        ps.println(")");
        ps.println( "(:goal (and");
        for(PredicateInst p:i.getFinalState()){
            if(p.negated){
                ps.print("( not ");
            }
            ps.print("("+p.getName()+" ");
            for (String param : p.getParams()){
                ps.print(param+" " );
            }
            ps.println(")");
            if(p.negated){
                ps.println(")");
            }
        }
        ps.println(")))");

        return "";
    }
    private static void printParam(PrintStream ps,String param,Instance i){
        ps.print(" ");
        if (i.isVariable(param)){
            ps.print("?");
        }
        ps.print(param);
        ps.print(" ");
    }
    private static void printAction(Action a, PrintStream ps,Instance i){
        ps.println("\n(:action "+a.name+"\n");
        ps.print("   :parameters\n    (");
        for(String v: a.getParameters()){
            ps.print("?"+v+"\n    ");
        }
        ps.println(")\n");
        ps.print("   :precondition\n    ( and\n    ");
        for(PredicateInst p:a.getPre()){
            if(p.negated){
                ps.print("    ( not "+ "(" + p.name);
                for (String param:p.getParams()){
                    printParam(ps,param,i);
                }
                ps.print("))\n    ");
            }
            else{
                ps.print("    ("+p.name);
                for (String param:p.getParams()){
                    printParam(ps,param,i);
                }
                ps.print(")\n    ");
            }
        }
        ps.println(")"); //close and and preconditions
        ps.print("   :effect\n   (and\n    ");
        for(PredicateInst p:a.getPostNeg()){
            
                ps.print("    ( not "+ "(" + p.name);
                for (String param:p.getParams()){
                    printParam(ps,param,i);
                }
                ps.print("))\n    ");
        }
        for(PredicateInst p:a.getPostPos()){
                ps.print("    ("+p.name);
                for (String param:p.getParams()){
                    printParam(ps,param,i);
                }
                ps.print(")\n    ");
        }
        ps.print(")\n)"); //close  preconditions
        
    }
    public static String getLetter(int i){
        char c = Character.forDigit(i+10, 36);
        return ""+c;
    }

    private static String indent(String res)
    {
        
        return res;
    }

    private String mkSpacer(int level){
        String res = "";
        for(int i = 0;i<level;i+=1){
            res += "    ";
        }
        return res;
    }
    
}
