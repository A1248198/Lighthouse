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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author A1248198
 */
public class Report
{
    Document jdomDoc;
    List <LazyHelper> lazy;
    public Report(){
        jdomDoc = new Document();
        Element rootElement = new Element("SatPlan");
		jdomDoc.setRootElement(rootElement);
        lazy = new ArrayList<>();
        init();
    }
    public void init(){
        putLeaf("Descrizione","Ogni nodo \"Clause\" rappresenta una clausola. Tutte le clausole sono in and. ");
        putLeaf("Descrizione"," Per cercare un piano di lunghezza TMAX le clausole vanno \"istanziate\" T0 diventa 0, TN - TMAX, le clausole che dipendono da T diventano AND_{T=0..TMAX}( ...let_T ... ) ");
        for (int i =1;i<6;i+=1){
            put("Assioma"+i);
        }
        putLeaf("Descrizione","Proposizionalizzazione delle azioni istanziando le variabili a tutte le possibili costanti","Assioma1");
        putLeaf("Descrizione","Definizione dello stato iniziale","Assioma2");
        putLeaf("Descrizione","Proposizionalizzazione dell’obiettivo","Assioma3");
        putLeaf("Descrizione","Aggiunta di assiomi di stato successore","Assioma4");
        putLeaf("Descrizione","Assiomi di precondizione per le azioni","Assioma5");
        putLeaf("Descrizione","Assiomi di esclusione di azione","Assioma6");

    }
    public void printOut(PrintStream pw){
        unlazy();
		XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		pw.println(xml.outputString(jdomDoc));
		
    }
    public void printOut(){
        printOut(System.out);
    }
    public void unlazy(){
        for(LazyHelper lh:lazy){
            lh.unlazy();
        }
        lazy = new ArrayList<>();
    }
    public void put(String name,String ... where){
        putLeaf(name,null,where);
    }
    public void putLeaf(String name,String text,String ... where){
        Element current = jdomDoc.getRootElement();
        //Namespace ns = current.getNamespace();
        //String nsprefix = "";
        for(String s:where){
            Element child = current.getChild(s);
            //nsprefix += s;
            if(s.equals(""))
                continue;
            if(child == null){
                //ns = Namespace.getNamespace(nsprefix,s);
                child = new Element(s);
                //current.addNamespaceDeclaration(ns);
                current.addContent(child);
                current = child;
            }
            else{
                current =child;
                
                //ns = current.getNamespace();
            }
            
        }
        //ns = Namespace.getNamespace(nsprefix, name);
        //current.addNamespaceDeclaration(ns);
        Element child = new Element(name);
        if(text!=null){
            child.setText(text);
        }
        current.addContent(child);
    }
    public void putLeafLazy(String name,Object textMaker,String ... where){
        LazyHelper lh = new LazyHelper(name,textMaker,where);
        lazy.add(lh);
    }
    class LazyHelper{
        String name;
        Object textMaker;
        String where[];

        public LazyHelper(String name, Object textMaker, String[] where)
        {
            this.name = name;
            this.textMaker = textMaker;
            this.where = where;
        }
        public void unlazy(){
            putLeaf(name,textMaker.toString(),where);
        }

        
    }
    
    
}
