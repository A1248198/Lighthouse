   
/* --------------------------Usercode Section------------------------ */
package lighthouse;
import java_cup.runtime.*;
import java.io.*;
import LHExceptions.ReservedSequenceException;      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
%class Lexer

%line
%column
    
%cup
   
%{  
java.util.List<java.io.File> inList;
java.io.File zzFile;

  Lexer(java.util.List<java.io.File> inList) throws FileNotFoundException{
    this.inList = inList;
    //this.zzReader = inList.get(0);
    //inList.remove(0);
    nextReader();
  }
public boolean hasNextReader(){
    return inList.size() > 0;
}
public void nextReader() throws FileNotFoundException{
    zzFile  = inList.get(0);
    this.zzReader = new java.io.FileReader(zzFile);
    yyreset(this.zzReader);
    inList.remove(0);
}
    public int getLine(){
        return yyline;
    }
    public int getColumn(){
        return yycolumn;
    }
    public String getFilename(){
        return zzFile.toString();
    }
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
    private void warning(String s){
        System.err.println("Warning: "+s);
    }
    private void checkLua(String s){
        if(s.matches(".*?__.*?")){
            warning("lo script contiene sequenza proibita __ ");
        }
    }
%}
   
%eofval{
    if(hasNextReader()){
        nextReader();
        //System.out.println("switching readers");
        return symbol(sym.MANEOF);
    }
    else{
        //System.out.println("no more readers");
        return symbol(sym.EOF);
    }

%eofval}

%state INSCRIPT

Endl = \r|\n|\r\n
   
WS     = {Endl} | [ \t\f]
COMMENT = "//"~{Endl}   
CAPNAME = [A-Z][A-Z0-9]*
LCNAME = [a-z][a-z0-9]*
ANYCASENAME = [A-Za-z=][A-Za-z0-9]*
NUMBER = 0 | [1-9][0-9]*   
LUATEXT = [^$]+
RESERVED = "___"
%%
<YYINITIAL> {RESERVED} { throw new ReservedSequenceException(yytext()); }
<YYINITIAL> "initial" { return symbol(sym.INITIAL); }   
<YYINITIAL> "predicates" { return symbol(sym.PREDICATES); }   
<YYINITIAL> "constants" {; return symbol(sym.CONSTANTS); }   
<YYINITIAL> "final" { return symbol(sym.FINAL); }   
<YYINITIAL> "pre" { return symbol(sym.PRE); }   
<YYINITIAL> "post" { return symbol(sym.POST); }   
<YYINITIAL> "actions" { return symbol(sym.ACTIONS); }   
<YYINITIAL> "$$$" {yybegin(INSCRIPT); return symbol(sym.SCRIPTBOUND);}
<INSCRIPT> "$$$" {yybegin(YYINITIAL); return symbol(sym.SCRIPTBOUND);}
<INSCRIPT> {LUATEXT} { checkLua(yytext()); return symbol(sym.SCRIPTCODE, yytext());}
<YYINITIAL> {
   
    ";"                { return symbol(sym.SEMI); }
    ":"                { return symbol(sym.COL); }
    "{"                {  return symbol(sym.LCURLY); }
    "}"                {  return symbol(sym.RCURLY); }
    "-"                {  return symbol(sym.MINUS); }
    "/"                {  return symbol(sym.SLASH); }
    ","                {  return symbol(sym.COMMA); }
    "("                {  return symbol(sym.LPAREN); }
    ")"                {  return symbol(sym.RPAREN); }
   
    {NUMBER}           {
                         return symbol(sym.NUMBER, new Integer (yytext()) );}
    {ANYCASENAME}      {
                        return symbol(sym.ID , yytext() ); }
    {WS}       { /* just skip what was found, do nothing */ }
    {COMMENT} { }   
}


[^]                    { throw new Error("Illegal character <"+yytext()+">"); }
