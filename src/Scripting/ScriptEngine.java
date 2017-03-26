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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lighthouse.Instance;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Print;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 *
 * @author A1248198
 */
public class ScriptEngine
{
    private static Globals globals;
    private static boolean print = false;
	private static String encoding = null;
    public static void interactive(Instance i) throws IOException{
        globals = LHScripting.g;
        if(globals == null){
            init(i);
        }
        interactiveMode();
    }
    public static void init(Instance i) throws IOException{
        boolean nodebug = false;
         globals = nodebug? JsePlatform.standardGlobals(): JsePlatform.debugGlobals();
         //LHScripting lhs = new LHScripting();
         LHScripting.i = i;
         LHScripting.g = globals;
         loadLibrary("Scripting.LHScripting");
         //LuaValue c = globals.loadfile("Scripts/integers.lua");
         //c.call();
    }
    public static void loadNCall(String filename){
        LuaValue chunk = globals.loadfile(filename);
        chunk.call();
    }
    public static void RunScripts(Instance i) throws IOException{
        globals = LHScripting.g;
        if(globals == null){
            init(i);
        }
        for(String script : i.getScripts()){
            LuaValue chunk = globals.load(script);
            chunk.call();
        }
    }
    

    private static void interactiveMode( ) throws IOException {
		//hLua l = new Lua();
        
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        globals.set("interactive", 1);
		while ( globals.get("interactive").checkint()==1 ) {
			System.out.print("> ");
			System.out.flush();
			String line = reader.readLine();
			if ( line == null )
				return;
			processScript( new ByteArrayInputStream(line.getBytes()), "=stdin", null, 0 );
		}
	}
    private static void loadLibrary( String libname ) throws IOException {
		LuaValue slibname =LuaValue.valueOf(libname); 
		try {
			// load via plain require
			globals.get("require").call(slibname);
		} catch ( Exception e ) {
			try {
				// load as java class
				LuaValue v = (LuaValue) Class.forName(libname).newInstance(); 
				v.call(slibname, globals);
			} catch ( Exception f ) {
				throw new IOException("loadLibrary("+libname+") failed: "+e+","+f );
			}
		}
	}
	
	private static void processScript( InputStream script, String chunkname, String[] args, int firstarg ) throws IOException {
		try {
			LuaValue c;
			try {
				script = new BufferedInputStream(script);
				c = encoding != null? 
						globals.load(new InputStreamReader(script, encoding), chunkname):
						globals.load(script, chunkname, "bt", globals);
			} finally {
				script.close();
			}
			if (print && c.isclosure())
				Print.print(c.checkclosure().p);
			Varargs scriptargs = setGlobalArg(chunkname, args, firstarg, globals);
			c.invoke( scriptargs );
		} catch ( Exception e ) {
			e.printStackTrace( System.err );
		}
	}

	private static Varargs setGlobalArg(String chunkname, String[] args, int i, LuaValue globals) {
		if (args == null)
			return LuaValue.NONE;
		LuaTable arg = LuaValue.tableOf();
		for ( int j=0; j<args.length; j++ )
			arg.set( j-i, LuaValue.valueOf(args[j]) );
		arg.set(0, LuaValue.valueOf(chunkname));
		arg.set(-1, LuaValue.valueOf("luaj"));
		globals.set("arg", arg);
		return arg.unpack();
	} 
}
