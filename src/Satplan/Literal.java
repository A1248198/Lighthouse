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
import java.util.Objects;

/**
 *
 * @author A1248198
 */
public class Literal
{
    public String name;
    public TimeMarks tm;
    public boolean negated;

    public Literal(){
        name = "";
        negated = false;
        tm=TimeMarks.ALL;
    }
    public Literal(String name, boolean negated, TimeMarks t)
    {
        this.name = name;
        this.negated = negated;
        this.tm = t;
    }
    public boolean dynamic(){
        boolean dyn = true;
        switch(tm){
            case T0:
                dyn = false;
                break;
            case T:
                break;
            case TP1:
                break;
            case TN:
                dyn = false;
                break;
            case ALL:
                dyn = false;
                break;
            default:
                throw new AssertionError(tm.name());
            
        }
        return dyn;
    }
    public boolean volat(){
        boolean vol = true;
        switch(tm){
            case T0:
                break;
            case T:
                break;
            case TP1:
                break;
            case TN:
                break;
            case ALL:
                vol = false;
                break;
            default:
                throw new AssertionError(tm.name());
            
        }
        return vol;
    }
    public String toString(){
        if(negated){
            return "-"+name+tm.toString();
        }
        else{
            return name+tm.toString();
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.tm);
        hash = 79 * hash + (this.negated ? 1 : 0);
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
        final Literal other = (Literal) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.tm != other.tm) {
            return false;
        }
        if (this.negated != other.negated) {
            return false;
        }
        return true;
    }

    
}
