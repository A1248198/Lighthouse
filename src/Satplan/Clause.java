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
import LHExceptions.EmptyClauseException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author A1248198
 */
public class Clause
{
    public List<Literal> literalList;
    public boolean dynamic=false;
    public Clause(){
        literalList = new LinkedList<>();
    }
    public boolean add(Literal ...l){
        for(Literal ll :l){
            add(ll);
        }
        return true;
    }
    public boolean isDynamic(){
        return dynamic;
    }
    public boolean add(Literal l){
        if(l.dynamic()){
            dynamic = true;
        }
        literalList.add(l);
        return true;
    }
    public boolean add(String name, boolean negated, TimeMarks t){
        return add(new Literal(name,negated,t));
    }
    public boolean isEmpty(){
        return literalList.isEmpty();
    }
    public void verify(){
        if(isEmpty()){
            throw new EmptyClauseException();
        }
    }
    @Override
    public String toString(){
        String separator = "   ";
        String res="( ";
        for(Literal l:literalList){
            res+=l.toString()+ separator;
        }
        return res+")";
    }
     
}
