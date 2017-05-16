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

import NCNF.*;
import static NCNF.OpType.*;
import Report.Report;
import static Satplan.TimeMarks.*;
import Stat.Stat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import lighthouse.Action;
import lighthouse.Instance;
import lighthouse.PredicateInst;

/**
 *
 * @author A1248198
 */
public class FilterAxioms
{
 
    
    //public static void mkSatPlan(Instance i, SatPlanInstance spi, Report r){
    //    mkSatPlan(i,spi,r,null);
    //}
    public static Set<AzioneGround> mkSatPlan(Instance i, SatPlanInstance spi, Report r,Stat stat){
        long start  = System.currentTimeMillis();
        finalState(i,spi,r);
        Set<PredicateInst> fluentiEsplorati =  new HashSet<>();
        Set<AzioneGround> azioniEsplorate = new HashSet<>();
        int iterations=0;
        int runs = 0;
        for(Action azione : i.getActions()){
            findAllFeasibleGroundActions(azione,azioniEsplorate,fluentiEsplorati,i,spi,r);
        }
        Map<String,PredicateInst> nomiFluenti = assioma4(azioniEsplorate,fluentiEsplorati,i,spi,r);
        assioma2(nomiFluenti,i,spi,r);
        for(AzioneGround a:azioniEsplorate){
            //System.out.println(a);
            r.putLeaf("Azione", a.mkLitName(), "Assioma1","Azioni");
        }
        for(PredicateInst p:fluentiEsplorati){
            System.out.println(p);
            r.putLeaf("Fluente", p.mkLiteralName(), "Assioma1","Fluenti");
        }
        int timetaken = (int)(System.currentTimeMillis()-start);
        if(stat!=null){
            stat.report("Filter", azioniEsplorate.size(), timetaken, spi.numClauses());
        }
        return azioniEsplorate;
        
    }
    private static void assioma2(Map<String,PredicateInst>fluenti,Instance i, SatPlanInstance spi,Report r){
        System.out.println("========Assioma2=========");
        for(PredicateInst fluente:fluenti.values()){
            PredicateInst positive = fluente.mkPosClone();
            boolean negated;
            if(i.initialState.contains(positive)) {
                negated = false;
            }
            else{
                negated = true;
            }
            Clause clause = new Clause();
            Literal lit = new Literal(fluente.mkLiteralName(),negated,T0);
            clause.add(lit);
            spi.addClause(clause);
            System.out.println(clause);
            r.putLeaf("Clause", clause.toString(), "Assioma2",fluente.getName());
                
        }
        
        System.out.println("=+=+=+=====Assioma2======+=+=+=");
    }
    
    private static Formula mkAssioma4Formula(String name){
        Formula formula = new Formula();
        formula.put(new Operator(BIGAND,"_{T=0..N-1}"));
        formula.put(new Leaf(name,TP1),0);
        formula.put(new Operator(IFF), 0);
        formula.put(new Operator(OR),0);
        return formula;
        
    }
    public static Map<String,PredicateInst> assioma4(Set<AzioneGround> azioni, Set<PredicateInst> fluenti,Instance i, SatPlanInstance spi,Report r){
        Map<String,PredicateInst> nomiFluenti = new HashMap<>();
        for(PredicateInst p:fluenti){
            nomiFluenti.put(p.mkLiteralName(), p);
        }
        Map<String,Formula> formulaMap = new HashMap<>();
        Map<String,Clause> causeOrMap = new HashMap<>();
        Map<String,Clause> alreadyOrMap = new HashMap<>();
        for(String name:nomiFluenti.keySet()){
            Formula formula = mkAssioma4Formula(name);
            formula.put(new Leaf(mkLitNameForAlready(name),T),0,2);
            formula.put(new Leaf(mkLitNameForCause(name),T),0,2);
            PredicateInst p = nomiFluenti.get(name);
            r.putLeafLazy("formula", formula, "Assioma4",p.getName(),p.toParamString());

                Clause c = new Clause();
                Literal fneg  = new Literal(name,true,TimeMarks.TP1);
                Literal ca = new Literal(mkLitNameForCause(name),false,TimeMarks.T);
                Literal alrd = new Literal(mkLitNameForAlready(name),false,TimeMarks.T);
                c.add(fneg,ca,alrd);
                spi.registerName(ca);
                spi.registerName(alrd);
                spi.registerName(fneg);//TODO remove and check
                c.verify();
                spi.addClause(c);
                r.putLeaf("Clause", c.toString(), "Assioma4",p.getName(),p.toParamString());
                //r.putLeaf("origine", "lato se", "Assioma4",p.getName(),paramSubString,"Clause");
                c=new Clause();
                Literal nalrd =  new Literal(mkLitNameForAlready(name),true,TimeMarks.T);
                Literal ncause = new Literal(mkLitNameForCause(name),true,TimeMarks.T);
                Literal fpos = new Literal(name,false,TimeMarks.TP1);
                c.add(nalrd,fpos);
                spi.addClause(c);
                r.putLeaf("Clause", c.toString(), "Assioma4",p.getName(),p.toParamString());
                //r.putLeaf("origine", "lato solo se", "Assioma4",p.getName(),paramSubString,"Clause");
                c=new Clause();
                c.add(ncause,fpos);
                c.verify();
                spi.addClause(c);
                r.putLeaf("Clause", c.toString(), "Assioma4",p.getName(),p.toParamString());
        }


        for(String name : nomiFluenti.keySet()){
            PredicateInst pred = nomiFluenti.get(name);
            PredicateInst positive = pred.mkPosClone();
            PredicateInst negative = pred.mkNegClone();
            Clause causeOr = new Clause();
            Clause alreadyOr = new Clause();
            Formula causeFormula = new Formula();
            Formula alreadyFormula = new Formula();

            alreadyFormula.put(new Operator(BIGAND,"_{T=0..N}"));
            alreadyFormula.put(new Leaf(mkLitNameForAlready(name),T),0);
            alreadyFormula.put(new Operator(IFF),0);
            alreadyFormula.put(new Operator(AND),0);
            alreadyFormula.put(new Leaf(name,T),0,2);
            causeFormula.put(new Operator(BIGAND,"_{T=0..N}"));
            causeFormula.put(new Leaf(mkLitNameForCause(name),T),0);
            causeFormula.put(new Operator(IFF),0);
            causeFormula.put(new Operator(OR),0);
            
            Literal causePositive = new Literal(mkLitNameForCause(name),false,T);
            Literal causeNegative = new Literal(mkLitNameForCause(name),true,T);
            Literal alreadyPositive = new Literal(mkLitNameForAlready(name),false,T);
            Literal alreadyNegative = new Literal(mkLitNameForAlready(name),true,T);
            Literal fluentNegative = new Literal(name,true,T);
            Literal fluentPositive = new Literal(name,false,T);
            alreadyOr.add(alreadyPositive);
            alreadyOr.add(fluentNegative);
            causeOr.add(causeNegative);
            Clause alrdImplalrd = new Clause();
            alrdImplalrd.add(fluentPositive);
            alrdImplalrd.add(alreadyNegative);
            spi.addClause(alrdImplalrd);
            r.putLeaf("Clause", alrdImplalrd.toString(), "Assioma4",pred.getName(),pred.toParamString());
            for(AzioneGround act : azioni){
                Literal actionPositive = new Literal(act.mkLitName(),false,T);
                Literal actionNegative = new Literal(act.mkLitName(),true,T);
                if(act.causes(positive)){
                    causeFormula.put(new Leaf(act.getName()+act.toFormulaParams(),T), 0,2);
                    Clause azioneImplicaCausa = new Clause();
                    azioneImplicaCausa.add(causePositive);
                    azioneImplicaCausa.add(actionNegative);
                    spi.addClause(azioneImplicaCausa);
                    r.putLeaf("Clause", azioneImplicaCausa.toString(), "Assioma4",pred.getName(),pred.toParamString());
                    causeOr.add(actionPositive);
                    
                }
                else if(act.causes(negative)){
                    alreadyFormula.put(new Operator(NOT),0, 2);
                    alreadyFormula.put(new Leaf(act.getName()+act.toFormulaParams(),T),0,2,-1);
                    Clause NandAzioneAlready = new Clause();
                    NandAzioneAlready.add(alreadyNegative);
                    NandAzioneAlready.add(actionNegative);
                    spi.addClause(NandAzioneAlready);
                    r.putLeaf("Clause", NandAzioneAlready.toString(), "Assioma4",pred.getName(),pred.toParamString());
                    alreadyOr.add(actionPositive);

                    
                }

            }
            //if(causeOr.numLiterals()==1){
            //    throw new RuntimeException(causeOr.toString());
            //}
            spi.addClause(causeOr);
            spi.addClause(alreadyOr);
            r.putLeaf("Clause", causeOr.toString(), "Assioma4",pred.getName(),pred.toParamString());
            r.putLeaf("Clause", alreadyOr.toString(), "Assioma4",pred.getName(),pred.toParamString());
            r.putLeaf("Formula", alreadyFormula.toString(), "Assioma4",pred.getName(),pred.toParamString());
            r.putLeaf("Formula", causeFormula.toString(), "Assioma4",pred.getName(),pred.toParamString());
            
        }

        return nomiFluenti;
        
    }
    private static Formula mkAxiom5Formula(AzioneGround a){
        Formula formula = new Formula();
        formula.put(new Operator(BIGAND,"_{T=0..N}"));
        formula.put(new Leaf(a.toFormulaName(),TimeMarks.T),0);
        formula.put(new Operator(FI),0);
        formula.put(new Operator(AND),0);
        return formula; 
            
    }
    public static void assioma5(AzioneGround ag,Set<AzioneGround> esplorati,Set<PredicateInst>fluentiEsplorati,Instance i, SatPlanInstance spi,Report r){
        ////System.out.println(ag);
        Literal l1 = new Literal(ag.mkLitName(),true,TimeMarks.T);
        spi.registerName(l1);
        List<PredicateInst> precs = ag.mkPreconditions(i);
        Formula formula = mkAxiom5Formula(ag);
        r.putLeafLazy("formula", formula, "Assioma5",ag.getName(),ag.toFormulaParams());
        for(PredicateInst prec: precs){
            ////System.out.println("prec:::::"+prec.toString());
            //if(!fluentiEsplorati.contains(prec)){
            //    fluentQueue.add(prec);//possible fluent discovery
            //}
            fluentiEsplorati.add(prec);
            Literal l2 = prec.mkLiteral(TimeMarks.T);
            Clause c = new Clause();
            c.add(l1);
            c.add(l2);
            spi.registerName(l2);
            r.putLeaf("Clause", c.toString(), "Assioma5",ag.getName(),ag.toCNFParams());
            formula.put(new Leaf(l2.name,TimeMarks.T),0, 2);

            spi.addClause(c);
            
        }
        
    }
    public static void findAllFeasibleGroundActions(Action action,Set<AzioneGround> esplorati,Set<PredicateInst> fluentiEsplorati,Instance i, SatPlanInstance spi, Report r){
        
        ActionFactory af = new ActionFactory(action,i);
        while(af.hasNext()){
            AzioneGround ag = af.get();
            if(ag!=null){
                ////System.out.println(ag.toString());
                //run asioma5
                
                assioma6(ag,esplorati,i,spi,r);
                assioma5(ag,esplorati,fluentiEsplorati,i,spi,r);
                esplorati.add(ag);
            }
        }
        
    }
    
    public static void assioma6(AzioneGround discovered,Set<AzioneGround> esplorati,Instance i, SatPlanInstance spi, Report r){
            Literal b = new Literal(discovered.mkLitName(),true,TimeMarks.T);
            spi.registerName(b);
        for(AzioneGround ag:esplorati){
            Clause c = new Clause();
            Literal a = new Literal(ag.mkLitName(),true,TimeMarks.T);
            c.add(a);
            c.add(b);
            spi.registerName(b);
            spi.addClause(c);//exclusion
            ////System.out.println(c.toString());
            r.putLeaf("Clause",c.toString() , "Assioma6", ag.getName()+discovered.getName());
        }
    }

    public static void finalState(Instance i, SatPlanInstance spi,Report r){
        List<String> variablesInTheFinalState = i.getVariablesInFinalState();
        List<PredicateInst> volatilePredicates = i.getGoalsWithVar();
        for(PredicateInst pi : i.getGoalsWithoutVar()){
            String name = pi.getName();
            for(String p: pi.getParams()){
                name+=p;
            }
            //make an and for the predicates which appear with a constant
            Clause c = new Clause();
            Literal l = new Literal(name,pi.isNegated(),TimeMarks.TN);
            c.add(l);
            c.verify();
            spi.registerName(l);
            spi.addClause(c);
            r.putLeaf("Clause", c.toString(), "Assioma3");
        }


        
        int numberOfVars = variablesInTheFinalState.size();
        ConstMult1 cm = new ConstMult1(numberOfVars,i.getConstants());
        TreeMap<String,Integer> map = new TreeMap<>();
        int j=0;
        for(String v : variablesInTheFinalState){
            map.put(v,j);
            j+=1;
        }
        String comPrefix = spi.getTempPrefixFrom(SatPlanSettings.FINALPREFIX);
        Clause bigOr = new Clause();
        int k = 0;
        while(cm.hasNext()){
            String comName = spi.makeCommodityLiteralName(comPrefix, k);
            spi.registerNameSubT(comName);
            Literal pos = new Literal(comName,false,TimeMarks.TN);
            bigOr.add(pos);
            Literal neg = new Literal(comName,true,TimeMarks.TN);
            for(PredicateInst pi : volatilePredicates){
                Clause c = new Clause();
                c.add(neg);
                String litName = pi.getName();
                ArrayList<String> al = new ArrayList<>(pi.getArity());
                for(String p : pi.getParams()){
                    if(i.isVariable(p)){

                        litName+=cm.getConstantForIndex(map.get(p));
                        al.add(cm.getConstantForIndex(map.get(p)));
                    }
                    else{
                        litName+=p;
                        al.add(p);
                    }
                }
                PredicateInst finalFluent = new PredicateInst(pi.getName(),al,pi.isNegated());
                finalFluent.setSource(pi.getSource());
                TimeMarks tm;
                if(pi.isFluent()){
                    tm = TN;
                }
                else{
                    tm = ALL;
                }
                Literal lit = new Literal(litName,pi.negated,tm);
                c.add(lit);
                c.verify();
                spi.registerName(lit);
                spi.addClause(c);//final state
                r.putLeaf("Clause", c.toString(), "Assioma3");
            }
            //cm.get();
            k+=1;
            cm.moveToNext();
        }
        if(!bigOr.isEmpty()){
            bigOr.verify();
            spi.addClause(bigOr);
            r.putLeaf("Clause", bigOr.toString(), "Assioma3");
            
        }
    }
    public static String mkLitNameForCause(String s){
        return SatPlanSettings.CAUSEPREFIX+s;
    }
    public static String mkLitNameForAlready(String s){
        return SatPlanSettings.ALREADYPREFIX+s;
    }

    
    
}
   
