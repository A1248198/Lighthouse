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

import Report.Report;
import Satplan.FilterAxioms;
import Satplan.SatPlanInstance;
import Satplan.SatPlanSettings;
import it.uniroma1.di.tmancini.teaching.ai.SATCodec.SATModelDecoder;
import it.uniroma1.di.tmancini.utils.CmdLineOptions;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
/**
 *
 * @author A1248198
 */
public class LightHouse
{

    /**
     * @param args the command line arguments
     */
    
    
//run with --satplan --pddl --p test_1.lhd --i test_1.lhp --log result --dimacs-prefix sat --t 5
    
    public static void main(String[] args) throws FileNotFoundException, Exception
    {
        
		CmdLineOptions clo = new CmdLineOptions("java -jar ./LightHouse.jar", "2017-02-01", "Artem Ageev (1248198@studenti.uniroma.it)", 
			"This software is a compiler for planning problems\n The output  formats are PDDL domain/instance and/or a DIMACS encoding performed following the SATPlan algorithm.\n Allows lua scripting \n\n "
                    + "THE SOFTWARE IS PROVIDED \"AS IS\" AND THE AUTHOR DISCLAIMS ALL WARRANTIES\n" +
"WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF\n" +
"MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR\n" +
"ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES\n" +
"WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN\n" +
"ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF\n" +
"OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.\n\n\n"+"if in doubt try: --satplan --pddl --p examples/test.lhdomain --i examples/test.lhinstance --show  --dimacs-prefix sat --t 5");
		
		// Second argument = true iff the option is mandatory. Default: false.
        clo.addFlag("satplan", "generate a SATPLAN instance");
		clo.addOption("dimacs-prefix", "prefix to use for dimacs file output", false);
		clo.addOption("pddl-prefix", "prefix to use for pddl output",false);
        clo.addOption("domain","name of the domain file", true);
	    clo.addOption("instance", "name of the instance file", true);
        clo.addOption("maxt", "generate satplan instances to find plans of max lenght t");
        clo.addFlag("pddl", "generate pddl");
        clo.addFlag("nosolve", "dont use the sat solver");
        clo.addFlag("noscript", "disable script execution");
        clo.addOption("log", "filename for log file in xml format");
        clo.addFlag("show", "show the resulting xml");
		// Parses the command-line arguments. It raises an error in case mandatory options are not given
		clo.parse(args);

        
        Instance instance = null;
        try{
            instance = doParse(clo);
        }
        catch(Throwable t){
            System.err.println(t.toString());
            System.exit(1);
        }
        System.out.println(instance.getString());
        doPDDL(clo,instance);
        doSatPlan(clo,instance); 
        

        
    }

    private static void doSatPlan(CmdLineOptions clo,Instance instance){
        if(clo.isFlagSet("satplan")){
            int t = Integer.parseInt( clo.getOptionValue("maxt"));
            SatPlanInstance spi = new SatPlanInstance();
            Report r = new Report();
            //Report r = new DummyReport();
            //Axioms.i=p.getInstance();
            //Axioms.Axiom2(p.getInstance(),spi,r);
            //Axioms.Axiom3(p.getInstance(),spi,r);
            //HybridAxioms.mkSatPlan(p.getInstance(), spi, r);
            FilterAxioms.mkSatPlan(instance, spi, r);
            //Axioms.Axiom1(p.getInstance(),spi,r);
            spi.verify();
            String show = SatPlanSettings.ACTIONPREFIX+".*";
            String prefix  = clo.getOptionValue("dimacs-prefix");
            if(prefix == null){
                prefix = "cnf";
            }
            boolean solve = !clo.isFlagSet("nosolve");
            for(int j = 2; j<=t+1; j+=1){
                spi.encode2(prefix+(j-1),j);
                boolean sat = false;
                if(solve){
                    try {
                        sat = sat4jSolve(prefix+(j-1),show);
                    } catch (ParseFormatException ex) {
                        Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ContradictionException ex) {
                        Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TimeoutException ex) {
                        Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(sat){
                        break;
                    }
                }
            }
            PrintStream ps = null;
            String logFileName = clo.getOptionValue("log");
            if(logFileName == null){
                logFileName= "result.xml";
            }
            File logFile = new File(logFileName);
            try {
                ps = new PrintStream(logFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
            }
            r.printOut(ps);
            if(clo.isFlagSet("show")){
                explorer.XMLTreeView.show(logFile);
            }
        }
    }
    private static void doPDDL(CmdLineOptions clo, Instance instance){
        if(clo.isFlagSet("pddl")){
            OutputStream domain = null;
            OutputStream problem =null;
            try {
                domain = new FileOutputStream(clo.getOptionValue("pddl-prefix")+"domain.pddl");
                problem = new FileOutputStream(clo.getOptionValue("pddl-prefix")+ "problem.pddl");
                StripsEncoder encoder = new StripsEncoder(instance,domain,problem);
                encoder.generateStrips();
            } catch (IOException ex) {
                Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    domain.close();
                    problem.close();
                } catch (IOException ex) {
                    Logger.getLogger(LightHouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

    private static Instance doParse(CmdLineOptions clo) throws FileNotFoundException, Exception{
        List<java.io.File> readers = new LinkedList<>();
        
        String domainFileName = clo.getOptionValue("domain");
        String instanceFileName = clo.getOptionValue("instance");
        if(domainFileName == null ){
            throw new RuntimeException("missing domain filename");
        }
        if(instanceFileName == null ){
            throw new RuntimeException("missing instance filename");
        }
        readers.add(new File(domainFileName));
        readers.add(new File(instanceFileName));
        Lexer lexer;
        lexer = new Lexer(readers);
        Parser p = new Parser(lexer);
        
        if(clo.isFlagSet("noscript")){
            p.getInstance().setNoscript(true);
        }
        p.parse();
        return p.getInstance();
    }
    
    public static boolean sat4jSolve(String filename,String show) throws FileNotFoundException, ParseFormatException, ContradictionException, IOException, TimeoutException{
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);
        Reader reader = new DimacsReader(solver);
        InputStream bis = new FileInputStream(new File(filename));
        //ByteArrayInputStream bis = new ByteArrayInputStream (bs.toByteArray());
        IProblem problem = reader.parseInstance(bis);
        System.out.println("sat instance file: "+filename);
        if(problem.isSatisfiable()){
            System.out.println("satisfiable");
            //System.out.println(reader.decode(problem.model()));
            SATModelDecoder smd = new SATModelDecoder();
            //ByteArrayInputStream bis2 = new ByteArrayInputStream (bs.toByteArray());
            bis = new FileInputStream(new File(filename));
            smd.decodeSat4j(bis, reader.decode(problem.model()));
            smd.printout(true,null);
            smd.printout(true,show);
            return true;
        }
        else{
            System.out.println("not sat");
            return false;
        }
        
    }
    
}
