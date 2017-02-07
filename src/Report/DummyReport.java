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
package Report;

import java.io.PrintStream;

/**
 * A dummy report.
 * Use when more performance is needed.
 * @author A1248198
 */
public class DummyReport extends Report
{


    public DummyReport()
    {
    }

    @Override
    public void putLeafLazy(String name, Object textMaker, String... where)
    {
    }

    @Override
    public void putLeaf(String name, String text, String... where)
    {
    }

    @Override
    public void put(String name, String... where)
    {
    }

    @Override
    public void unlazy()
    {
    }

    @Override
    public void printOut()
    {
    }

    @Override
    public void printOut(PrintStream pw)
    {
    }

    @Override
    public void init()
    {
    }
 


}
