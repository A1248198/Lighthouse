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
package NCNF;

/**
 *
 * @author A1248198
 */
public class Operator extends Node
{
    public OpType type;
    public String sub="";
    public Operator(OpType type)
    {
        super();
        this.type = type;
    }

    public Operator(OpType type, String sub)
    {
        this(type);
        this.sub = sub;
    }
    public String toString(){
        switch(type){
            case OR:
                return chldToString(""," OR ");
            case AND:
                return chldToString(""," AND ");
            case IF:
                return " IF ";
            case FI:
                return " THEN ";
            case IFF:
                return " IFF ";
            case NOT:
                return chldToString("-","");
            case BIGOR:
                return "OR"+sub + chldToString(""," ");
            case BIGAND:
                return "AND"+sub + chldToString(""," ");
            default:
                throw new AssertionError(type.name());
            
        }
    }
    private String chldToString(String prefix,String separator){
        if(children.size()==0){
            return "";
        }
        if(children.size()== 1){
            return prefix + children.get(0).toString();
        }
        String res = prefix + "( " +children.get(0).toString();
        for(int i = 1; i< children.size() ; i+=1){
            res += separator + children.get(i).toString();
        }
        return res + " )";
    }

    static class PrintHelper{
        
    }

}
