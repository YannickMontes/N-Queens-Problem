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
        Chessboard c = SimulatingAnnealing.execute(50);
        System.out.println(c.fitnessConflict());
        
        System.out.println("Tabu search:");
        c = TabuSearch.execute(25, 5);
        System.out.println(c.fitnessConflict());
        
    }
}
