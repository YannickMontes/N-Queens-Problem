/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import java.util.ArrayList;
import n.queens.problem.Chessboard;
import n.queens.problem.FitnessEnum;

/**
 *
 * @author yannick
 */
public abstract class TabuSearch
{
    public static final int TABU_LIST_SIZE = 7;
    public static final int MAX_ITERATIONS = 5000;
    public static final FitnessEnum FITNESS_TYPE = FitnessEnum.CONFLICT;
    
    /**
     * Method to execute tabu search algorithm on given chessboard size with given parameters
     * @param chessboardSize The size of the chessboard (required)
     * @param tabuSizeTab The size for the tabu list (7 if null given)
     * @param maxIt The number of iterations maximum (5000 if null given)
     * @param fit The fitness we want to use
     * @return The best chessboard
     */
    public static Chessboard execute(int chessboardSize, Integer tabuSizeTab, Integer maxIt, FitnessEnum fit)
    {
        //Init parameters variables
        int maxIterations = maxIt != null ? maxIt : MAX_ITERATIONS;
        int tabuTabSize = tabuSizeTab != null ? tabuSizeTab : TABU_LIST_SIZE;
        FitnessEnum fitness = fit != null ? fit : FITNESS_TYPE;
        
        //First let's take a random solution
        Chessboard solution = new Chessboard(chessboardSize);
        ArrayList<String> tabu = new ArrayList();
        
        //Save the best solution
        Chessboard bestSolution = solution;
        int bestFitness = solution.computeFitness(fitness);
        
        //Current iteraton & max iteration number
        int currentIteration = 0;
        
        //The list of neighbours without tabu neighbours
        ArrayList<String> neighsWithoutTabu;
        
        do
        {
            ArrayList<String> neighs = solution.getNeighboursString();
            
            neighsWithoutTabu = TabuSearch.getNeighsNotTabu(neighs, tabu);
            
            String choosedNeigh = TabuSearch.chooseRandomNeighbours(neighs);
            Chessboard neighChess = new Chessboard(chessboardSize, choosedNeigh);
            
            int neighFitness = neighChess.computeFitness(fitness);
            int solutionFitness = neighChess.computeFitness(fitness);
            
            int deltaFitness = neighFitness - solutionFitness;
            
            if(deltaFitness >= 0)
            {
                TabuSearch.addToTabuList(tabu, neighChess.getSolution(), tabuTabSize);
            }
            
            if(neighFitness < bestFitness)
            {
                bestSolution = neighChess;
                bestFitness =  neighFitness;
            }
            
            solution = neighChess;
            currentIteration++;
        }while(currentIteration < maxIterations && !neighsWithoutTabu.isEmpty());
        
        return bestSolution;
    }
    
    public static ArrayList<String> getNeighsNotTabu(ArrayList<String> neighs, ArrayList<String> tabu)
    {
        tabu.stream().filter((sol) -> (neighs.contains(sol))).forEach((sol) ->
        {
            neighs.remove(sol);
        });
        return neighs;
    }
    
    private static String chooseRandomNeighbours(ArrayList<String> neighs)
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
