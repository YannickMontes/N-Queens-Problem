/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;

import java.util.ArrayList;
import metaheuristics.SimulatingAnnealing;

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
        Chessboard board = new Chessboard(100);
        long t0 = System.currentTimeMillis();
        //board.getNeighbours();
        long t1 = System.currentTimeMillis() - t0;
        System.out.println("Get neighs take "+t1);
        
        t0 = System.currentTimeMillis();
        //board.getFirstBestNeigh(new ArrayList<String>());
        t1 = System.currentTimeMillis() - t0;
        System.out.println("Get first best neighs take "+t1);
        
        
        
        int chessSize = 200;
        double meanTime = 0.0d;
        double meanIteration = 0.0d;
        int sample = 20;
        for(int i=0; i<sample; i++)
        {
            long deb = System.currentTimeMillis();
            Chessboard c = SimulatingAnnealing.execute(chessSize, 20000, 0.00000f, 0.90f, 0.20f, FitnessEnum.CONFLICT);
            //Chessboard c = TabuSearch.execute(chessSize, null, null, FitnessEnum.CONFLICT);
            //Chessboard c = ton appel de méthode
            long end = System.currentTimeMillis() - deb;
            System.out.println(i +" - " +c.getFitness());
            meanTime += end;
            meanIteration += SimulatingAnnealing.CURRENT_ITERATION;
        }
        meanTime = meanTime/sample;
        meanIteration = meanIteration/sample;
        
        System.out.println("Temps moyen d'exécution: "+meanTime);
        System.out.println("Nombre moyen d'iterations: "+meanIteration);
        /*
        Chessboard chess = new Chessboard(4, "1302");
        chess.getConflicts(1);
        chess.getIndexConflicted();
        System.out.println("Genetic:");
        long time = System.currentTimeMillis();
        Chessboard c = GeneticImproved.execute(25, null, null, null, null);
        System.out.println("Nombre de conflits " + c.computeFitness(null));
        System.out.println("Genetic take "+(System.currentTimeMillis() - time)+" ms");
        
        /*System.out.println("Simulated annealing:");
        long time = System.currentTimeMillis();
        Chessboard c = SimulatingAnnealing.execute(100, null, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Annealing take "+(System.currentTimeMillis() - time)+" ms");*/
        
        /*System.out.println("Tabu search:");
        time = System.currentTimeMillis();
        c = TabuSearch.execute(25, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Tabu take "+(System.currentTimeMillis() - time)+" ms");*/
        
    }
}
