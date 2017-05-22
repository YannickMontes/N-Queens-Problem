/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;

import metaheuristics.SimulatingAnnealing;
import metaheuristics.TabuSearch;

/**
 *
 * @author yannick
 */
public class NQueensProblem
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        int chessSize = 500;
        double meanTime = 0.0d;
        int sample = 1;
        for(int i=0; i<sample; i++)
        {
            long deb = System.currentTimeMillis();
            Chessboard c = SimulatingAnnealing.execute(chessSize, null, 0.0f, null, FitnessEnum.CONFLICT);
            //Chessboard c = TabuSearch.execute(chessSize, null, null, FitnessEnum.CONFLICT);
            //Chessboard c = ton appel de méthode
            long end = System.currentTimeMillis() - deb;
            System.out.println(c.computeFitness(FitnessEnum.CONFLICT));
            meanTime += end;
        }
        meanTime = meanTime/sample;
        
        System.out.println("Temps moyen d'exécution: "+meanTime);
        
        /*
        Chessboard chess = new Chessboard(4, "1302");
        chess.getConflicts(1);
        chess.getIndexConflicted();
        /*System.out.println("Genetic:");
        long time = System.currentTimeMillis();
        Chessboard c = GeneticImproved.execute(100, null, null, null, null);
        System.out.println("Nombre de conflits " + c.computeFitness(null));
        System.out.println("Annealing take "+(System.currentTimeMillis() - time)+" ms");*/
        
        /*System.out.println("Simulated annealing:");
        time = System.currentTimeMillis();
        c = SimulatingAnnealing.execute(100, null, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Annealing take "+(System.currentTimeMillis() - time)+" ms");
        
        System.out.println("Tabu search:");
        time = System.currentTimeMillis();
        c = TabuSearch.execute(25, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Tabu take "+(System.currentTimeMillis() - time)+" ms");*/
        
    }
}
