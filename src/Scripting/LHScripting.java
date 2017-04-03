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
package Scripting;

import LHExceptions.DoubleDeclarationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lighthouse.Instance;
import lighthouse.Predicate;
import lighthouse.PredicateInst;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 *
 * @author A1248198
 */
public class LHScripting extends TwoArgFunction 
{
    public LHScripting(){
    }
    static Instance i;
    static Globals g;
    LuaValue env;
	public LuaValue call(LuaValue modname, LuaValue env) {
		//LuaValue library = tableOf();
        this.env = env;
		env.set( "getConstants", new getConstants() );
		env.set( "getInitial", new getInitial() );
		env.set( "getPredicates", new getPredicates() );
		env.set( "addConstant", new addConstant() );
		env.set( "printout", new printout() );
		env.set( "setInitial", new setInitial() );
        env.set("addPredicate", new addPredicate());
        env.set("stopInteractive", new stopInteractive());
        env.set("interactive",new interactive());
        
        return tableOf();
	}
    /**
     * allows the interactive mode
     */
    class interactive extends ZeroArgFunction{

        @Override
        public LuaValue call()
        {
            try {
                ScriptEngine.interactive(i);
            } catch (IOException ex) {
                Logger.getLogger(LHScripting.class.getName()).log(Level.SEVERE, null, ex);
            }
            return LuaValue.TRUE;
        }
        
    }

    
    class addConstant extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue lv)
        {
            i.registerConstant(lv.toString());
            return lv;
        }

        
    }
    /**
     * terminates an interactive session
     */
    class stopInteractive extends ZeroArgFunction {

        @Override
        public LuaValue call()
        {
            env.set("interactive", 0);
            return LuaValue.TRUE;
        }
        
    }
    class getConstants extends ZeroArgFunction {

        @Override
        public LuaValue call()
        {
            LuaValue result = tableOf();
            int index =1;
            for(String c: i.getConstants()){
                result.set(index,c);
                index += 1;
            }
            return result;
        }
        
    }
    class setInitial extends TwoArgFunction{

        @Override
        public LuaValue call(LuaValue predicateName, LuaValue constants)
        {
            List<String> clist = new LinkedList<>();
            LuaTable luaConsts = constants.checktable();
            for(LuaValue key : luaConsts.keys()){
                LuaValue val = luaConsts.get(key);
                String co = val.tojstring();
                clist.add(co);
            }
            
            PredicateInst res = new PredicateInst(predicateName.tojstring(),clist);
            i.addToInitial(res);
            return LuaValue.TRUE;
        }
        
    }
    class printout extends ZeroArgFunction{

        @Override
        public LuaValue call()
        {
            System.out.println(i.toString());
            return LuaValue.TRUE;
        }
        
    }
    class getInitial extends ZeroArgFunction {

        @Override
        public LuaValue call()
        {
            LuaValue result = tableOf();
            int index =1;
            for(PredicateInst pred : i.getInitialState()){
                LuaTable predTab = tableOf();
                predTab.set("name", pred.getName());
                predTab.set("arity", pred.getArity());
                int j = 1;
                for(String p:pred.getParams()){
                    predTab.set(j, p);
                    j+=1;
                }
                
                result.set(index,predTab);
                index += 1;
            }
            return result;
        }
        
    }
    
    class getPredicates extends ZeroArgFunction {

        @Override
        public LuaValue call()
        {
            LuaValue result = tableOf();
            int index =1;
            for(Predicate pred : i.getPredicates()){
                LuaTable predTab = tableOf();
                predTab.set("name", pred.getName());
                predTab.set("arity", pred.getArity());
                result.set(index,predTab);
                index += 1;
            }
            return result;
        }
        
    }
    class addPredicate extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue name,LuaValue arity)
        {
            try {
                i.registerPredicate(name.tojstring(), arity.toint());
            } catch (DoubleDeclarationException ex) {
                return LuaValue.valueOf(false);
            }
            return LuaValue.valueOf(true);
        }

        
    }


    public static final String LuaInitScript = ""
            + "lhPredicatesNeeded={}\n"
            + "function uses(what)\n"
            + "     lhPredicatesNeeded[what] = true\n"
            + "end\n"
            + "function isNeeded(what)\n"
            + "     return lhPredicatesNeeded[what]==true\n"
            + "end\n";
    
}
