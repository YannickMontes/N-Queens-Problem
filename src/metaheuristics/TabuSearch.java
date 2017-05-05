/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import java.util.ArrayList;
import n.queens.problem.Chessboard;

/**
 *
 * @author yannick
 */
public abstract class TabuSearch
{
    private static final int TABU_LIST_SIZE = 7;
    private static final int MAX_ITERATIONS = 5000;
    
    /**
     * Method to execute tabu search algorithm on given chessboard size with given parameters
     * @param chessboardSize The size of the chessboard (required)
     * @param tabuSizeTab The size for the tabu list (7 if null given)
     * @param maxIt The number of iterations maximum (5000 if null given)
     * @return The best chessboard
     */
    public static Chessboard execute(int chessboardSize, Integer tabuSizeTab, Integer maxIt)
    {
        //Init parameters variables
        int maxIterations = maxIt != null ? maxIt : TabuSearch.MAX_ITERATIONS;
        int tabuTabSize = tabuSizeTab != null ? tabuSizeTab : TabuSearch.TABU_LIST_SIZE;
        
        //First let's take a random solution
        Chessboard solution = new Chessboard(chessboardSize);
        ArrayList<String> tabu = new ArrayList();
        
        //Save the best solution
        Chessboard bestSolution = solution;
        int bestFitness = solution.fitnessConflict();
        
        //Current iteraton & max iteration number
        int currentIteration = 0;
        
        //The list of neighbours without tabu neighbours
        ArrayList<Chessboard> neighsWithoutTabu;
        
        do
        {
            ArrayList<Chessboard> neighs = solution.getNeighbours();
            
            neighsWithoutTabu = TabuSearch.getNeighsNotTabu(neighs, tabu);
            
            Chessboard choosedNeigh = TabuSearch.chooseRandomNeighbours(neighs);
            
            int neighFitness = choosedNeigh.fitnessConflict();
            int solutionFitness = choosedNeigh.fitnessConflict();
            
            int deltaFitness = neighFitness - solutionFitness;
            
            if(deltaFitness >= 0)
            {
                TabuSearch.addToTabuList(tabu, choosedNeigh.getSolution(), tabuTabSize);
            }
            
            if(neighFitness < bestFitness)
            {
                bestSolution = choosedNeigh;
                bestFitness =  neighFitness;
            }
            
            solution = choosedNeigh;
            currentIteration++;
        }while(currentIteration < maxIterations && !neighsWithoutTabu.isEmpty());
        
        return bestSolution;
    }
    
    public static ArrayList<Chessboard> getNeighsNotTabu(ArrayList<Chessboard> neighs, ArrayList<String> tabu)
    {
        ArrayList<Chessboard> ret = new ArrayList();
        for(Chessboard chessboard : neighs)
        {
            if(!tabu.contains(chessboard.getSolution()))
            {
                ret.add(chessboard);
            }
        }
        return ret;
    }
    
    private static Chessboard chooseRandomNeighbours(ArrayList<Chessboard> neighs)
    {
        int rand = (int) (Math.random() * (neighs.size()-1));
        return neighs.get(rand);
    }

    private static void addToTabuList(ArrayList<String> tabu, String solution, int tabuTabSize)
    {
        if(tabu.size() == tabuTabSize)
        {
            tabu.remove(0);
        }
        tabu.add(solution);
    }
}
