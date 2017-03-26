/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;

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
        Chessboard c = SimulatingAnnealing.execute(25);
        System.out.println(c.fitnessConflict());
    }
}
