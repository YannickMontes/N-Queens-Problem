/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import java.awt.Point;
import java.util.ArrayList;
import n.queens.problem.Chessboard;
import n.queens.problem.FitnessEnum;

/**
 *
 * @author yannick
 */
public abstract class TabuSearch
{
    public static final int TABU_LIST_SIZE = 50;
    public static final int MAX_ITERATIONS = 5000;
    public static final FitnessEnum FITNESS_TYPE = FitnessEnum.CONFLICT;
    public static ArrayList<Point> steps;
    
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
        Chessboard solution = new Chessboard(chessboardSize, true);
        ArrayList<Point> tabu = new ArrayList();
        
        //Save the best solution
        Chessboard bestSolution = solution;
        int bestFitness = solution.getFitness();
        
        //Current iteraton & max iteration number
        int currentIteration = 0;
        
        ArrayList<Chessboard> neighs;
        
        steps = new ArrayList<>();
        
        do
        {
            neighs = solution.getNeighbours(tabu);
                        
            if(!neighs.isEmpty())
            {
                Chessboard choosedNeigh = TabuSearch.chooseBestNeighbour(neighs);

                int neighFitness = choosedNeigh.getFitness();
                int solutionFitness = solution.getFitness();

                int deltaFitness = neighFitness - solutionFitness;

                if(deltaFitness >= 0)
                {
                    TabuSearch.addToTabuList(tabu, solution.getMouvement(), tabuTabSize);
                }

                if(neighFitness < bestFitness)
                {
                    bestSolution = choosedNeigh;
                    bestFitness =  neighFitness;
                }

                solution = choosedNeigh;
                steps.add(new Point(currentIteration, neighFitness));

                currentIteration++;
            }
        }while(currentIteration < maxIterations && !neighs.isEmpty() && solution.getFitness()!=0);
        
        System.out.println("Iteration "+currentIteration);
        
        steps.add(new Point(currentIteration, solution.getFitness()));
        
        return bestSolution;
    }
    
    public static ArrayList<Chessboard> getNeighsNotTabu(ArrayList<Chessboard> neighs, ArrayList<Chessboard> tabuList)
    {
        ArrayList<Chessboard> toRemove = new ArrayList<>();
        for(Chessboard neigh : neighs)
        {
            for(Chessboard tabu : tabuList)
            {
                if(neigh.getSolution().equals(tabu.getSolution()))
                {
                    toRemove.add(neigh);
                    break;
                }
            }
        }
        neighs.removeAll(toRemove);
        return neighs;
    }
    
    private static Chessboard chooseBestNeighbour(ArrayList<Chessboard> neigh)
    {
        int min = Integer.MAX_VALUE;
        Chessboard best = null;
        for(int i=0; i<neigh.size(); i++)
        {
            if(neigh.get(i).getFitness() < min)
            {
                min = neigh.get(i).getFitness();
                best = neigh.get(i);
            }
        }
        return best;
    }

    private static void addToTabuList(ArrayList<Point> tabu, Point mvmt, int tabuTabSize)
    {
        if(tabu.size() == tabuTabSize)
        {
            tabu.remove(0);
        }
        tabu.add(mvmt);
    }
}
