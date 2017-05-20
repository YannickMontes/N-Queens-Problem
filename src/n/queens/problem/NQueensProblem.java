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
        System.out.println("Simulated annealing:");
        long time = System.currentTimeMillis();
        Chessboard c = SimulatingAnnealing.execute(100, null, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Annealing take "+(System.currentTimeMillis() - time)+" ms");
        
        System.out.println("Tabu search:");
        time = System.currentTimeMillis();
        c = TabuSearch.execute(25, null, null, null);
        System.out.println(c.computeFitness(null));
        System.out.println("Tabu take "+(System.currentTimeMillis() - time)+" ms");
        
    }
}
