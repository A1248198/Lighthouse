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

import LHExceptions.CloseWorldViolationError;
import LHExceptions.DoubleDeclarationException;
import LHExceptions.UndeclaredPredicateException;
import Scripting.Interactive;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A planning instance
 * @author A1248198
 */
public class Instance
{
    //public Set<PredicateInst> initialState = new TreeSet<>(PredicateInst.getComparator());
    public Set<PredicateInst> initialState = new HashSet<>();
    public Set<PredicateInst> finalState = new TreeSet<>(PredicateInst.getComparator());
    public List<String> constants = new ArrayList<>();
    public List<Action> actions = new ArrayList<>(); 
    public List<String> scripts  = new LinkedList<>();
    private TreeMap<String,Set<List<String>>> timeIndipInitial= new TreeMap<>();
    private Map<String,boolean[][][]>initialCache = new HashMap<>();
    private final int cacheLimit = 3;
    boolean noscript=false;
    private boolean postprocessed;
    private Map<String,Integer> rev;
    private List<PredicateInst> goalsWithVar;
    private List<PredicateInst> goalsWithoutVar;
    private List<String> variablesInFinalState;
    TreeMap<String, Predicate> predicates = new TreeMap<>();
    public String name;

    
    public Instance(){
        name="unknown";
        try {
            registerPredicate("=",2);
        } catch (DoubleDeclarationException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void postprocess(){
        int conNum = constants.size();
        int indices[] ;
        rev = new HashMap<>();
        for(int i = 0; i<conNum; i+=1){
            rev.put(constants.get(i), i);
        }
        for(String pname:predicates.keySet()){
            Predicate pred = predicates.get(pname);
            if(pred.getArity()<=cacheLimit && !pred.isFluent()){
                boolean cacheEntry[][][] = new boolean[conNum][conNum][conNum];
                Set<List<String>> predInitialState = timeIndipInitial.get(pname);
                if(predInitialState == null){
                    continue;
                }
                try{
                for(List<String> predargs : predInitialState){
                    indices = calcIndex(predargs);
                    cacheEntry[indices[0]][indices[1]][indices[2]]=true;
                }
                initialCache.put(pname, cacheEntry);
                }
                catch(Exception e){
                    e.printStackTrace();
                    System.out.println(pname);
                    System.out.println(Arrays.toString(timeIndipInitial.keySet().toArray()));
                }
            }
        }
        this.goalsWithVar = new LinkedList<>();
        this.goalsWithoutVar = new LinkedList<>();
        this.variablesInFinalState = new LinkedList<>();
        this.variablesVolPreds(goalsWithVar, goalsWithoutVar, variablesInFinalState);

        postprocessed = true;
        
    }

    public List<PredicateInst> getGoalsWithVar()
    {
        if(!postprocessed){
            postprocess();
        }
        return goalsWithVar;
    }

    public List<PredicateInst> getGoalsWithoutVar()
    {
        if(!postprocessed){
            postprocess();
        }
        return goalsWithoutVar;
    }

    public List<String> getVariablesInFinalState()
    {
        if(!postprocessed){
            postprocess();
        }
        return variablesInFinalState;
    }

    private int revSearch(String what){
        return rev.get(what);
    }
    private int[] calcIndex(List<String> state){
        int res[];
        int index0,index1,index2;
        switch(state.size()){
            case 0:
                res = new int[]{0,0,0};
                break;
            case 1:
                index0 = revSearch(state.get(0));
                res = new int[]{0,0,index0};
                break;
            case 2:
                index0 = revSearch(state.get(0));
                index1 = revSearch(state.get(1));
                res = new int[]{0,index1,index0};
                break;
            case 3:
                index0 = revSearch(state.get(0));
                index1 = revSearch(state.get(1));
                index2 = revSearch(state.get(2));
                res = new int[]{index2,index1,index0};
                break;
            default:
                throw new RuntimeException("argument list too long");
        }
        return res;
    }

    public Instance(String name){
        this();
        this.name = name;
    }
    public void addScript(String script){
        this.scripts.add(script);
    }
    /**
     * Execute scripts
     */
    public void runScripts(){
        if(noscript){
            return;
        }
        //System.out.println("running scripts");
        try {
            Interactive.RunScripts(this);
        } catch (IOException ex) {
            Logger.getLogger(Instance.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.scripts = new LinkedList<>();
    }
    /**
     * allows to set the noscript attribute
     * when set the script execution will be disabled
     * @param to new value for the noscript attribute
     */
    public void setNoscript(boolean to){
        this.noscript = to;
    }
    public List<String> getScripts(){
        return scripts;
    }
    public Set<PredicateInst> getInitialState()
    {
        return initialState;
    }
    public boolean debug =  false;
    private void dbg(String s){
        System.out.println(s);
    }
    public boolean isInInitialS(String searchPred, List<String> searchArgs){
        if(searchPred.equals("=")){
            if(searchArgs.size()!=2){
                throw new RuntimeException();
            }
            else{
                return searchArgs.get(0).equals(searchArgs.get(1));
            }
        }
        if(!postprocessed){
            postprocess();
        }
        Predicate pred = predicates.get(searchPred);
        if(pred.getArity()<=cacheLimit){
            return cacheGet(searchPred,searchArgs);
        }
        Set<List<String>> where = timeIndipInitial.get(searchPred);
        if(debug)dbg(String.format("comparing %s, %s", searchPred,Arrays.toString(searchArgs.toArray())));
        return where.contains(searchArgs);
    }

    public Set<PredicateInst> getFinalState()
    {
        return finalState;
    }

    public List<String> getConstants()
    {
        return constants;
    }

    public List<Action> getActions()
    {
        return actions;
    }


    private void variablesVolPreds(List<PredicateInst> goalsWithVar,List<PredicateInst> goalsWithoutVar,  List<String> variables){
        for(PredicateInst pi : finalState){
            boolean hasVars = false;
            //Find out if the final state has any variables and which
            for(String p: pi.getParams()){
                if(!isConstant(p)){
                    hasVars = true;
                    variables.add(p);
                }
                
            }
            //Save the fluents which appear with a variable in a final state
            if(hasVars){
                goalsWithVar.add(pi);
            }
            else{
                goalsWithoutVar.add(pi);
            }
            }
    }


    public String getName()
    {
        return name;
    }
    
    /**
     * Add a predicate/fluent instance to the initial state
     * the argument must not have negated==true for the close world assumption
     * @param pi predicate instance to add to the initial state
     */
    public void addToInitial(PredicateInst pi){
        postprocessed = false;
        if(pi.negated){
            throw new CloseWorldViolationError();
        }
        initialState.add(pi);
        Predicate source = predicates.get(pi.getName());
        pi.setSource(source);
        Set<List<String>> set;
        set = timeIndipInitial.get(source.getName());
        if( set == null){
            set = new HashSet<>();
            timeIndipInitial.put(source.getName(), set);
        }
        set.add(pi.getParams());
        
        
    }
    public void setInitial(List<PredicateInst> p){
        for( PredicateInst pi : p){
            addToInitial(pi);
        }
    }
   // private int binSearchPredicate(PredicateInst p){
    //     
             // return Collections.binarySearch(predicates, p, c) ;
              //
               // }
    
    /**
     * Register a new predicate with the instance
     * @param name desired predicate name
     * @param num  arity
     * @throws DoubleDeclarationException if the predicate name was already declared
     */
    public  void registerPredicate(String name, int num) throws DoubleDeclarationException
    {
        postprocessed = false;
        Predicate p = new Predicate (name,num);
        if(predicates.containsKey(name)){
            throw new DoubleDeclarationException();
        }
        predicates.put(name, p);
        
    }
    @Override
    public String toString(){
        return getString();
    }
    public  String getString()
    {
        String res  = "initial:" + Utils.prettyPrintComma(initialState) + ";\nfinal:" + Utils.prettyPrintComma(finalState) +
                ";\nconstants: " + Utils.prettyPrintComma(constants) + ";\n" +
                ";\npredicates: " + Utils.prettyPrintComma(predicates.values()) + ";\n" +
                "\nactions: " + Utils.prettyPrintComma(actions);
        return res;
    }
    public void setFinal(java.util.Collection<PredicateInst> p){
        for(PredicateInst what : p){
            addFinal(what);
        }
    }
    public void addFinal(PredicateInst what){
        postprocessed = false;
        Predicate source = this.predicates.get(what.getName());
        what.setSource(source);
        this.finalState.add(what);
    }


    public  void registerConstant(String c)
    {
        postprocessed = false;
        constants.add(c);
    }

    /**
     * Returns true if this instance has a constant with name == name
     * @param name name of a constant to search
     * @return true if name is a constant
     */
    public boolean isConstant(String name){
        return constants.contains(name);
    
    }
    public boolean existsPredicate( String name){
        return predicates.containsKey(name);
    }
    public Predicate getPredicate(String name){
        return predicates.get(name);
    }
    public Collection<Predicate> getPredicates(){
        return predicates.values();
    }
    public String mkActionRegex(){
        String res="";
        if(actions.isEmpty()){
            return res;
        }
        for(int i =0 ;i<actions.size()-1;i+=1){
            res+=actions.get(i).getName()+".*"+"|";
            
        }
        res+=actions.get(actions.size()-1).getName();
        return res;
    }
    
    /**
     * Add a new Action to the instance.
     * this method updates checks the post conditions of the Action
     * if a predicate appears in the post conditions it is considered a fluent from now on.
     * @param actionName action name not null
     * @param varList a list of variable names not null
     * @param pre a list of preconditions not null
     * @param post a list of postconditions not null
     * @throws UndeclaredPredicateException if a pre/postcondition makes use of a predicate which has not been declared.
     */
    public  void registerAction(String actionName, List<String> varList, List<PredicateInst> pre, List<PredicateInst> post) throws UndeclaredPredicateException
    {
        postprocessed = false;
        for(PredicateInst p:pre){
            if(!existsPredicate(p.getName())){
                throw new UndeclaredPredicateException(p.getName());
            }
            else{
                Predicate source = getPredicate(p.getName());
                p.setSource(source);
            }
        }
        for(PredicateInst p:post){
            if(!existsPredicate(p.getName())){
                throw new UndeclaredPredicateException(p.getName());
            }
            else{
                Predicate source =  getPredicate(p.getName());
                p.setSource(source);
                source.setFluent(true);
            }
        }
        actions.add(new Action(actionName,varList,pre,post,this));
        
    }

    public boolean isVariable(String param)
    {
        if(constants.contains(param)){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean cacheGet(String searchPred, List<String> searchArgs)
    {
        int index[] = calcIndex(searchArgs);
        return initialCache.get(searchPred)[index[0]][index[1]][index[2]];
    }

    
}
