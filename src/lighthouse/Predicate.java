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

import java.util.Objects;

/**
 *
 * Predicate declaration
 * @author A1248198
 */
public class Predicate
{
    private int arity;
    private String name;
    private boolean fluent;

    /**
     * Predicate/Fluent declaration
     * @param name predicate / fluent name
     * @param arity 
     */
    public Predicate( String name,int arity)
    {
        this.arity = arity;
        this.name = name;
        fluent = false;
    }
    @Override
    public String toString()
    {
        return  name + "/" + arity ;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.name);
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
        final Predicate other = (Predicate) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public void setFluent(boolean fluent)
    {
        this.fluent = fluent;
    }

    public int getArity()
    {
        return arity;
    }

    public String getName()
    {
        return name;
    }

    public boolean isFluent()
    {
        return fluent;
    }

}
