/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import n.queens.problem.Chessboard;
import n.queens.problem.FitnessEnum;

/**
 *
 * @author yannick
 */
public abstract class MinConflicts
{
    
    
    public static void execute(int chessboardSize)
    {
        Chessboard c = new Chessboard(chessboardSize);
        
        /*while(c.get(FitnessEnum.CONFLICT) != 0)
        {
            
        }*/
    }
}
