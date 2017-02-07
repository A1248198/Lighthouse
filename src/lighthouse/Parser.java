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

import java_cup.runtime.*;
import java.util.List;
import java.util.LinkedList;
import lighthouse.Instance;
import lighthouse.LHType;
import lighthouse.Predicate;
import LHExceptions.ParserExceptions.*;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20150930 (SVN rev 66) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class Parser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  @Deprecated
  public Parser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public Parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public Parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\040\000\002\002\004\000\002\021\002\000\002\021" +
    "\005\000\002\002\006\000\002\002\005\000\002\002\005" +
    "\000\002\003\005\000\002\003\003\000\002\004\005\000" +
    "\002\004\004\000\002\016\006\000\002\011\006\000\002" +
    "\017\006\000\002\005\005\000\002\005\003\000\002\010" +
    "\006\000\002\010\007\000\002\010\005\000\002\010\006" +
    "\000\002\020\005\000\002\020\003\000\002\014\006\000" +
    "\002\014\005\000\002\013\004\000\002\013\003\000\002" +
    "\012\012\000\002\012\011\000\002\006\006\000\002\006" +
    "\005\000\002\007\006\000\002\022\003\000\002\015\013" +
    "" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\131\000\006\014\000\024\006\001\002\000\004\014" +
    "\012\001\002\000\004\002\011\001\002\000\004\030\007" +
    "\001\002\000\004\024\010\001\002\000\010\002\uffff\014" +
    "\uffff\015\uffff\001\002\000\004\002\001\001\002\000\010" +
    "\003\122\005\117\026\120\001\002\000\004\006\015\001" +
    "\002\000\004\025\074\001\002\000\006\005\017\026\016" +
    "\001\002\000\004\011\026\001\002\000\004\026\016\001" +
    "\002\000\006\004\uffe9\026\uffe9\001\002\000\006\004\023" +
    "\026\016\001\002\000\006\004\uffea\026\uffea\001\002\000" +
    "\004\025\uffeb\001\002\000\006\004\025\026\016\001\002" +
    "\000\004\025\uffec\001\002\000\006\012\030\026\027\001" +
    "\002\000\010\004\uffed\012\uffed\020\uffed\001\002\000\004" +
    "\021\070\001\002\000\006\012\033\020\032\001\002\000" +
    "\004\026\067\001\002\000\004\021\034\001\002\000\004" +
    "\007\036\001\002\000\004\010\062\001\002\000\004\005" +
    "\037\001\002\000\010\004\043\023\044\026\041\001\002" +
    "\000\006\004\ufff3\020\ufff3\001\002\000\004\011\055\001" +
    "\002\000\006\004\053\020\052\001\002\000\004\010\uffe5" +
    "\001\002\000\004\026\045\001\002\000\004\011\046\001" +
    "\002\000\006\012\047\026\027\001\002\000\006\004\uffef" +
    "\020\uffef\001\002\000\006\012\051\020\032\001\002\000" +
    "\006\004\ufff1\020\ufff1\001\002\000\006\023\044\026\041" +
    "\001\002\000\004\010\uffe6\001\002\000\006\004\ufff4\020" +
    "\ufff4\001\002\000\006\012\056\026\027\001\002\000\006" +
    "\004\ufff0\020\ufff0\001\002\000\006\012\060\020\032\001" +
    "\002\000\006\004\ufff2\020\ufff2\001\002\000\004\022\066" +
    "\001\002\000\004\005\063\001\002\000\006\023\044\026" +
    "\041\001\002\000\006\004\065\020\052\001\002\000\004" +
    "\022\uffe4\001\002\000\006\004\uffe8\026\uffe8\001\002\000" +
    "\010\004\uffee\012\uffee\020\uffee\001\002\000\004\007\036" +
    "\001\002\000\004\010\062\001\002\000\004\022\073\001" +
    "\002\000\006\004\uffe7\026\uffe7\001\002\000\006\015\uffe3" +
    "\024\uffe3\001\002\000\006\015\000\024\006\001\002\000" +
    "\004\015\100\001\002\000\004\013\104\001\002\000\004" +
    "\005\101\001\002\000\004\026\027\001\002\000\006\004" +
    "\103\020\032\001\002\000\004\013\ufff5\001\002\000\004" +
    "\005\114\001\002\000\004\016\106\001\002\000\004\005" +
    "\111\001\002\000\006\002\000\024\006\001\002\000\004" +
    "\002\uffe2\001\002\000\006\023\044\026\041\001\002\000" +
    "\006\004\113\020\052\001\002\000\006\002\ufff6\024\ufff6" +
    "\001\002\000\006\023\044\026\041\001\002\000\006\004" +
    "\116\020\052\001\002\000\004\016\ufff7\001\002\000\004" +
    "\026\120\001\002\000\004\017\130\001\002\000\006\004" +
    "\126\020\125\001\002\000\004\004\124\001\002\000\006" +
    "\004\ufffa\020\ufffa\001\002\000\004\006\ufffc\001\002\000" +
    "\004\026\120\001\002\000\004\006\ufffd\001\002\000\006" +
    "\004\ufffb\020\ufffb\001\002\000\010\004\ufff8\020\ufff8\027" +
    "\131\001\002\000\006\004\ufff9\020\ufff9\001\002\000\006" +
    "\004\133\020\125\001\002\000\004\006\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\131\000\006\015\004\021\003\001\001\000\004\002" +
    "\012\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\006\003" +
    "\120\004\122\001\001\000\004\014\013\001\001\000\004" +
    "\022\074\001\001\000\006\012\017\013\020\001\001\000" +
    "\002\001\001\000\006\012\017\013\023\001\001\000\002" +
    "\001\001\000\004\012\021\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\012\021\001\001\000\002\001\001" +
    "\000\004\020\030\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\006\034\001\001\000\004\007\060\001\001\000" +
    "\002\001\001\000\006\005\041\010\037\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\020\047" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\004\010\053\001\001\000\002\001\001\000\002" +
    "\001\001\000\004\020\056\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\005\063\010\037\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\006\070\001\001\000\004\007\071\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\021\075\001\001\000\004\017\076\001\001\000\004\016" +
    "\104\001\001\000\002\001\001\000\004\020\101\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\011\106\001\001\000\002\001\001\000\004\021\107" +
    "\001\001\000\002\001\001\000\006\005\111\010\037\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\005\114" +
    "\010\037\001\001\000\002\001\001\000\002\001\001\000" +
    "\006\003\131\004\122\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\004\004\126\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$Parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$Parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$Parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}




Instance i = new Instance();

public Instance getInstance(){
return i ;
}

private String filename(){
    
    Lexer l = (Lexer) this.getScanner();
    return l.getFilename();
}
public String getWhere(){

    Lexer l = (Lexer) this.getScanner();
    return (l.getFilename()+" line "+(l.getLine()+1)+" col: "+(l.getColumn()+1));
}

public void printWhere(){

    Lexer l = (Lexer) this.getScanner();
    System.out.println("Line "+(l.getLine()+1)+" col: "+(l.getColumn()+1));
}
 public void report_fatal_error(String message, Object info){
    //printWhere();
    report_error(message,info);
    throw new SyntaxErrorError("Syntax error");
    //super.report_fatal_error("",info);
}


@Override
	protected void report_expected_token_ids() {
	  List<Integer> ids = expected_token_ids();
	  LinkedList<String> list = new LinkedList<String>();
	  for (Integer expected : ids)
		  list.add(symbl_name_from_id(expected));
	  String msg = "instead expected token classes are " + list + "error token is "+ symbl_name_from_id(super.cur_token.sym);
	  System.err.println(msg);
//errMan.Error(ErrorSource.Parser, msg);
	}

private void warningMissing(String str){
System.err.println("WARNING: Missing \""+str+"\" in "+getWhere());
}
private void error(String str){
System.err.println("ERROR:  "+str);
throw new SyntaxErrorError("Syntax error");
}

private void abort(){
System.exit(1);
}

protected int error_sync_size(){
return 1;
}
    

public void report_error(String message, Object info) {
   
        StringBuffer m = new StringBuffer("Error");
        m.append(filename());
        if (info instanceof java_cup.runtime.Symbol) {
            java_cup.runtime.Symbol s = ((java_cup.runtime.Symbol) info);
   
            if (s.left >= 0) {                
                m.append(" in line "+(s.left+1));   
                if (s.right >= 0)                    
                    m.append(", column "+(s.right+1));
            }
        }
   
        m.append(" : "+message);
   
        System.err.println(m);
        //super.report_error("",info);
    }




/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$Parser$actions {
  private final Parser parser;

  /** Constructor */
  CUP$Parser$actions(Parser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$Parser$do_action_part00000000(
    int                        CUP$Parser$act_num,
    java_cup.runtime.lr_parser CUP$Parser$parser,
    java.util.Stack            CUP$Parser$stack,
    int                        CUP$Parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$Parser$result;

      /* select the action based on the action number */
      switch (CUP$Parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= PDDL EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		RESULT = start_val;
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$Parser$parser.done_parsing();
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // script ::= 
            {
              Object RESULT =null;
		
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("script",15, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // script ::= SCRIPTBOUND SCRIPTCODE SCRIPTBOUND 
            {
              Object RESULT =null;
		int codeleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int coderight = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		String code = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		System.out.println(code);i.addScript(code);i.runScripts();
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("script",15, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // predicatesdecl ::= PREDICATES COL predicateDeclarationList SEMI 
            {
              Object RESULT =null;
		
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicatesdecl",0, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // predicatesdecl ::= PREDICATES predicateDeclarationList SEMI 
            {
              Object RESULT =null;
		warningMissing(":"); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicatesdecl",0, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // predicatesdecl ::= PREDICATES error SEMI 
            {
              Object RESULT =null;
		 error("syntax error in the predicate declaration"); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicatesdecl",0, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // predicateDeclarationList ::= predicateDeclarationList COMMA predicateDeclaration 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicateDeclarationList",1, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // predicateDeclarationList ::= predicateDeclaration 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicateDeclarationList",1, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // predicateDeclaration ::= ID SLASH NUMBER 
            {
              Object RESULT =null;
		int nameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int nameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		String name = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		int numleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).left;
		int numright = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).right;
		Integer num = (Integer)((java_cup.runtime.Symbol) CUP$Parser$stack.peek()).value;
		i.registerPredicate(name,num);
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicateDeclaration",2, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // predicateDeclaration ::= ID SLASH 
            {
              Object RESULT =null;
		 error("Missing number, should be predicate/arity"); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicateDeclaration",2, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // initialdecl ::= INITIAL COL predicates SEMI 
            {
              Object RESULT =null;
		int pcsleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int pcsright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> pcs = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		  i.setInitial(pcs); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("initialdecl",12, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // finaldecl ::= FINAL COL predicates SEMI 
            {
              Object RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int pright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> p = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 i.setFinal(p); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("finaldecl",7, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // constantsdecl ::= CONSTANTS COL mixlist SEMI 
            {
              Object RESULT =null;
		int clleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int clright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<String> cl = (List<String>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		  for (String c : cl){ i.registerConstant(c); } 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("constantsdecl",13, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // predicates ::= predicates COMMA predicate 
            {
              List<PredicateInst> RESULT =null;
		int ppleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int ppright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		List<PredicateInst> pp = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		int pleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).right;
		PredicateInst p = (PredicateInst)((java_cup.runtime.Symbol) CUP$Parser$stack.peek()).value;
		 RESULT = pp; pp.add(p); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicates",3, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // predicates ::= predicate 
            {
              List<PredicateInst> RESULT =null;
		int pleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).left;
		int pright = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).right;
		PredicateInst p = (PredicateInst)((java_cup.runtime.Symbol) CUP$Parser$stack.peek()).value;
		 RESULT = new LinkedList<PredicateInst>(); RESULT.add(p); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicates",3, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // predicate ::= ID LPAREN mixlist RPAREN 
            {
              PredicateInst RESULT =null;
		int pnameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)).left;
		int pnameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)).right;
		String pname = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-3)).value;
		int clleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int clright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<String> cl = (List<String>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 RESULT = new PredicateInst(pname,cl); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicate",6, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // predicate ::= MINUS ID LPAREN mixlist RPAREN 
            {
              PredicateInst RESULT =null;
		int pnameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)).left;
		int pnameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)).right;
		String pname = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-3)).value;
		int clleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int clright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<String> cl = (List<String>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 RESULT = new PredicateInst(pname,cl,true); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicate",6, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-4)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // predicate ::= ID LPAREN RPAREN 
            {
              PredicateInst RESULT =null;
		int pnameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int pnameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		String pname = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		 RESULT = new PredicateInst(pname,false); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicate",6, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // predicate ::= MINUS ID LPAREN RPAREN 
            {
              PredicateInst RESULT =null;
		int pnameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int pnameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		String pname = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		 RESULT = new PredicateInst(pname,true); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("predicate",6, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // mixlist ::= mixlist COMMA ID 
            {
              List<String> RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		List<String> m = (List<String>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		int cleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).left;
		int cright = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).right;
		String c = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.peek()).value;
		RESULT = m ; m.add(c);
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("mixlist",14, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // mixlist ::= ID 
            {
              List<String> RESULT =null;
		int idleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).left;
		int idright = ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()).right;
		String id = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.peek()).value;
		 RESULT = new LinkedList<>(); RESULT.add(id); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("mixlist",14, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // actiondecl ::= ACTIONS COL actionlist SEMI 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actiondecl",10, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // actiondecl ::= ACTIONS actionlist SEMI 
            {
              Object RESULT =null;
		 warningMissing(":"); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actiondecl",10, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // actionlist ::= actionlist actiond 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actionlist",9, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // actionlist ::= actiond 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actionlist",9, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // actiond ::= ID LPAREN mixlist RPAREN LCURLY precond postcond RCURLY 
            {
              Object RESULT =null;
		int actionNameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-7)).left;
		int actionNameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-7)).right;
		String actionName = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-7)).value;
		int varListleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-5)).left;
		int varListright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-5)).right;
		List<String> varList = (List<String>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-5)).value;
		int preleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int preright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		List<PredicateInst> pre = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		int postleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int postright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> post = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 i.registerAction(actionName,varList,pre,post); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actiond",8, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-7)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // actiond ::= ID LPAREN RPAREN LCURLY precond postcond RCURLY 
            {
              Object RESULT =null;
		int actionNameleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-6)).left;
		int actionNameright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-6)).right;
		String actionName = (String)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-6)).value;
		int preleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).left;
		int preright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)).right;
		List<PredicateInst> pre = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-2)).value;
		int postleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int postright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> post = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 i.registerAction(actionName,null,pre,post); 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("actiond",8, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-6)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // precond ::= PRE COL predicates SEMI 
            {
              List<PredicateInst> RESULT =null;
		int ppleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int ppright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> pp = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 RESULT = pp; 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("precond",4, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // precond ::= PRE COL SEMI 
            {
              List<PredicateInst> RESULT =null;
		RESULT = new LinkedList<PredicateInst>();
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("precond",4, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-2)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // postcond ::= POST COL predicates SEMI 
            {
              List<PredicateInst> RESULT =null;
		int ppleft = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).left;
		int ppright = ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-1)).right;
		List<PredicateInst> pp = (List<PredicateInst>)((java_cup.runtime.Symbol) CUP$Parser$stack.elementAt(CUP$Parser$top-1)).value;
		 RESULT = pp; 
              CUP$Parser$result = parser.getSymbolFactory().newSymbol("postcond",5, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-3)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // fileBreak ::= MANEOF 
            {
              Object RESULT =null;
		
//System.out.println("reached end of file");

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("fileBreak",16, ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // PDDL ::= script predicatesdecl actiondecl fileBreak script constantsdecl initialdecl finaldecl script 
            {
              Object RESULT =null;

              CUP$Parser$result = parser.getSymbolFactory().newSymbol("PDDL",11, ((java_cup.runtime.Symbol)CUP$Parser$stack.elementAt(CUP$Parser$top-8)), ((java_cup.runtime.Symbol)CUP$Parser$stack.peek()), RESULT);
            }
          return CUP$Parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$Parser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$Parser$do_action(
    int                        CUP$Parser$act_num,
    java_cup.runtime.lr_parser CUP$Parser$parser,
    java.util.Stack            CUP$Parser$stack,
    int                        CUP$Parser$top)
    throws java.lang.Exception
    {
              return CUP$Parser$do_action_part00000000(
                               CUP$Parser$act_num,
                               CUP$Parser$parser,
                               CUP$Parser$stack,
                               CUP$Parser$top);
    }
}

}
