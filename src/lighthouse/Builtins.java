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
package lighthouse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author dm
 */
public class Builtins
{
    private static final HashMap<String, VarArityPredicate> nameToCode = new HashMap<>();
    private static final HashMap<String, Integer> nameToArity = new HashMap<>();

    static{
        nameToCode.put("=", new Equality());
        nameToArity.put("=", 2);
    }
    public static int getArity(String s){
        return nameToArity.get(s);
    }
    public static boolean isBuiltin(String s){
        return nameToCode.containsKey(s);
    }
    public static boolean test(String name, String ... args){
        return nameToCode.get(name).test(args);
    }

    public static Set<String> getNames(){
        return nameToCode.keySet();
    }
    
    static class Equality implements VarArityPredicate
    {
        @Override
        public boolean test(String... sbj)
        {
            if(sbj.length!=2){
                throw new RuntimeException(String.format("equality binary predicate, got %d arguments instead",sbj.length));
            }
            if(sbj[0].equals(sbj[1])){
                return true;
            }
            else{
                return false;
            }
        }
    };
}
