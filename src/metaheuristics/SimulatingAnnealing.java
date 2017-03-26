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
public abstract class SimulatingAnnealing
{
    public static Chessboard execute(int chessboardSize)
    {
        //Random initial solution & initial temperature computing
        Chessboard solution = new Chessboard(chessboardSize);
        float temperature = SimulatingAnnealing.computeInitialTemperatureForRecuit(chessboardSize, 0.8f, "conflict");
        
        //Variables to stock the best solution
        Chessboard bestSolution = null;
        int bestFitness = Integer.MAX_VALUE;
        
        //We choose a max numbers of iterations
        int maxIterations = 10000;
        int currentIteration = 0;
        
        //How the temperature will decrease through time
        float variationTemperatureMultiplier = 0.99f;
        
        //Then we loop through till the temperature is low enough or to a max number of iterations
        while(temperature > 0.005 && currentIteration < maxIterations && bestFitness > 0)
        {
            //Retrieve the neighbours of currentsolution
            long deb = System.currentTimeMillis();
            ArrayList<Chessboard> neighs = solution.getNeighbours();
            System.out.println("Neighs computing took "+ (System.currentTimeMillis() - deb)+" ms");
            
            //Choose a neighbour
            Chessboard choosedNeigh = SimulatingAnnealing.chooseRandomNeighbours(neighs);
            
            //Compute the delta fitness between the two solutions
            int solutionFitness = solution.fitnessConflict();
            int neighFitness = choosedNeigh.fitnessConflict();
            int deltaFitness = neighFitness - solutionFitness;
            
            //If the neighbour is better
            if(deltaFitness < 0)
            {
                //Next solution is the neighbour
                solution = choosedNeigh;
                
                //If neighbour is better than best solution found
                if(neighFitness < bestFitness)
                {
                    //Then save it
                    bestFitness = neighFitness;
                    bestSolution = choosedNeigh;
                }
            }
            else //If the neighbour is lower
            {
                //We take it according to acceptance function
                double rand = Math.random();
                deltaFitness *= -1;
                if(rand <= Math.exp( (deltaFitness/temperature)))
                {
                    solution = choosedNeigh;
                }
            }
            //Finally we update our temperature
            temperature *= variationTemperatureMultiplier;
            currentIteration += 1;
            System.out.println("Iteration took "+ (System.currentTimeMillis() - deb)+" ms");
        }
                    System.out.println(currentIteration + " - "+temperature);

        return bestSolution;
    }
    
    private static float computeInitialTemperatureForRecuit(int chessboardSize, float wantedProbability, String fitness)
    {
        int nbRandom1 = 10;
        int nbRandom2 = 100;
        int sumDeltaFitness = 0;
        for(int i=0; i<nbRandom1; i++)
        {
            Chessboard sol1 = new Chessboard(chessboardSize);
            int fitnessSol1 = sol1.fitnessConflict();
            for(int j=0; j<nbRandom2; j++)
            {
                Chessboard sol2 = new Chessboard(chessboardSize);
                sumDeltaFitness += Math.abs(fitnessSol1 - sol2.fitnessConflict());
            }
        }
        float averageDeltaFitness = sumDeltaFitness / (nbRandom1*nbRandom2);
        float startedTemparature = averageDeltaFitness/wantedProbability;
        return startedTemparature;
    }

    private static Chessboard chooseRandomNeighbours(ArrayList<Chessboard> neighs)
    {
        int rand = (int) (Math.random() * (neighs.size()-1));
        return neighs.get(rand);
    }
}