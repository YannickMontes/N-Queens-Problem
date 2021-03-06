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
public abstract class SimulatingAnnealing
{
    public static final int MAX_ITERATIONS = 20000;
    public static final float TEMPERATURE_STROPPING_THRESHOLD = 0f;
    public static final float TEMPRATURE_VARIATION_MULTIPLIER = 0.99f;
    public static final float ACCEPTANCE_PROBA = 0.80f;
    public static final FitnessEnum FITNESS_TYPE = FitnessEnum.CONFLICT;
    public static int CURRENT_ITERATION;
    public static ArrayList<Point> steps;
    
    /**
     * Execute a simulated annealing algorithm over a chessboard with given parameters
     * @param chessboardSize Numbers of quenn of the board 
     * @param max_iter Number max of iterations for the algorithme (stopping condition)
     * @param threshold_temp Lowest value for temperature (stopping condition)
     * @param variation_temp How the temperature will decrease through time
     * @param acceptance
     * @param fit The type of fitness we wanna use
     * @return The best solution found
     */
    public static Chessboard execute(int chessboardSize, Integer max_iter, Float threshold_temp, Float variation_temp, Float acceptance, FitnessEnum fit)
    {
        //We choose a max numbers of iterations
        int maxIterations = max_iter != null ? max_iter : MAX_ITERATIONS;
        //How the temperature will decrease through time
        float variationTemperatureMultiplier = variation_temp != null ? variation_temp : TEMPRATURE_VARIATION_MULTIPLIER;
        //When the temperature go under this threshold, we stop the algorithm
        float thresholdTemp = threshold_temp != null ? threshold_temp : TEMPERATURE_STROPPING_THRESHOLD;
        //What type of fitness we choosed
        FitnessEnum fitness = fit != null ? fit : FITNESS_TYPE;
        float acceptanceRate = acceptance != null ? acceptance : ACCEPTANCE_PROBA;
        
        //Random initial solution & initial temperature computing
        Chessboard solution = new Chessboard(chessboardSize);
        float temperature = SimulatingAnnealing.computeInitialTemperatureForRecuit(chessboardSize, acceptanceRate, fitness);
        
        //Variables to stock the best solution
        Chessboard bestSolution = null;
        int bestFitness = Integer.MAX_VALUE;
        
        steps = new ArrayList<>();
        
        
        CURRENT_ITERATION = 0;
                
        //Then we loop through till the temperature is low enough or to a max number of iterations
        while(temperature > thresholdTemp && CURRENT_ITERATION < maxIterations && bestFitness > 0)
        {           
            //Choose a neighbour
            Chessboard choosedNeigh = solution.generateRandomNeigh();
            
            //Compute the delta fitness between the two solutions
            int solutionFitness = solution.getFitness();
            int neighFitness = choosedNeigh.getFitness();
            int deltaFitness = neighFitness - solutionFitness;
            

            
            //If the neighbour is better
            if(deltaFitness <= 0)
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
                    solutionFitness = neighFitness;
                }
            }
            steps.add(new Point(CURRENT_ITERATION, solutionFitness));

            //Finally we update our temperature
            temperature *= variationTemperatureMultiplier;
            CURRENT_ITERATION += 1;
            
        }
        steps.add(new Point(CURRENT_ITERATION, bestSolution.getFitness()));
        System.out.println("Nombre d'itérations: "+CURRENT_ITERATION);

        return bestSolution;
    }
    
    private static float computeInitialTemperatureForRecuit(int chessboardSize, float wantedProbability, FitnessEnum fitness)
    {
        int nbRandom1 = 10;
        int nbRandom2 = 100;
        int sumDeltaFitness = 0;
        for(int i=0; i<nbRandom1; i++)
        {
            Chessboard sol1 = new Chessboard(chessboardSize);
            int fitnessSol1 = sol1.getFitness();
            for(int j=0; j<nbRandom2; j++)
            {
                Chessboard sol2 = new Chessboard(chessboardSize);
                sumDeltaFitness += Math.abs(fitnessSol1 - sol2.getFitness());
            }
        }
        float averageDeltaFitness = sumDeltaFitness / (nbRandom1*nbRandom2);
        float startedTemparature = averageDeltaFitness/wantedProbability;
        return startedTemparature;
    }
}
