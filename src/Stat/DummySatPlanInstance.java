/*
 * Copyright (C) 2017 dm
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
package Stat;

import Satplan.Clause;
import Satplan.Literal;
import Satplan.SatPlanInstance;
import java.util.List;

/**
 *
 * @author dm
 */
public class DummySatPlanInstance extends SatPlanInstance
{
    private int numCla;
    @Override
    public void encode2(String filename, int t)
    {
    }

    @Override
    public String makeNameForPredicate(String predicateName)
    {
        return super.makeNameForPredicate(predicateName); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerName(List<String> literalList)
    {
    }

    @Override
    public void registerName(String literal)
    {
        super.registerName(literal); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String makeCommodityLiteralName(String prefix, int n)
    {
        return super.makeCommodityLiteralName(prefix, n); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTempPrefixFrom(String desiredPrefix)
    {
        return super.getTempPrefixFrom(desiredPrefix); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String clausesToString()
    {
        return "";
    }

    @Override
    public boolean verify()
    {
        return true;
    }

    @Override
    public int numClauses()
    {
        return this.numCla;
    }

    @Override
    public void addClause(Clause c)
    {
        numCla+=1;
    }

    @Override
    public void registerNameSubT(String literal)
    {
    }

    @Override
    public void registerName(Literal l)
    {
    }

    @Override
    public void registerNameSubT(List<String> literalList)
    {
    }

    @Override
    public void printLiterals()
    {
    }
    
    
}
