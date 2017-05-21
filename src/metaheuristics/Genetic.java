/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import n.queens.problem.Chessboard;
import n.queens.problem.FitnessEnum;

/**
 *
 * @author yannick
 */
public abstract class Genetic {

    private static final int MAX_ITERATIONS = 1000;
    private static final int POPULATION_SIZE = 100;
    private static final int MUTATION_PROBABILITY = 10;
    private static final FitnessEnum FITNESS_TYPE = FitnessEnum.CONFLICT;

    /**
     * Method to execute tabu search algorithm on given chessboard size with
     * given parameters
     *
     * @param chessboardSize The size of the chessboard (required)
     * @param populationSize The size for the population list (10 if null given)
     * @param maxIt The number of iterations maximum (5000 if null given)
     * @param fit The fitness we want to use
     * @return The best chessboard
     */
    public static Chessboard execute(int chessboardSize, Integer populationSize, Integer maxIt, FitnessEnum fit, Integer mutationProb) {
        //Init parameters variables
        int maxIterations = maxIt != null ? maxIt : MAX_ITERATIONS;
        FitnessEnum fitness = fit != null ? fit : FITNESS_TYPE;
        int populationSiz = populationSize != null ? populationSize : POPULATION_SIZE;
        int mutationProbability = mutationProb != null ? mutationProb : MUTATION_PROBABILITY;
        
        HashMap<Chessboard, Double> population = new HashMap<Chessboard, Double>();

        //First let's generate our population
        for (int i = 0; i < populationSiz; i++) {
            Chessboard solution = new Chessboard(chessboardSize);
            solution.generateRandomNeigh();

            population.put(solution, 0.0);
        }
        
        Chessboard bestSolution = (Chessboard) population.keySet().toArray()[0];

        //Let's start the algorithm
        for (int i = 0; i < maxIterations; i++) {
            System.out.println("Génération : " + i);
            Double fitnessSum = 0.0;
            Double probaSum = 0.0;

            //Compute the cumulated fitness    
            for (Chessboard c : population.keySet()) {
                fitnessSum += c.getFitness();
                population.replace(c, 0.0);
            }
            
            //Compute the cumulated probability
            for (Chessboard c : population.keySet()) {
                probaSum += (c.getFitness()/fitnessSum) * 100;
                population.replace(c, probaSum);
            }

            //Create a new population
            HashMap<Chessboard, Double> newPopulation = new HashMap<Chessboard, Double>();

            for (int j = 0; j < populationSiz; j++) {
                HashMap<Chessboard, Double> tempPopulation = new HashMap<Chessboard, Double>(population);
                Double random = Math.random() * 100;
                Chessboard father = null;
                Chessboard mother = null;

                for (Chessboard c : tempPopulation.keySet()) {
                    if (random <= tempPopulation.get(c)) {
                        father = c;
                        tempPopulation.remove(c);
                        
                        break;
                    }
                }

                probaSum = 0.0;
                fitnessSum = 0.0;
                //Compute the cumulated fitness    
                for (Chessboard c : tempPopulation.keySet()) {
                    fitnessSum += c.getFitness();
                }
                
                for (Chessboard c : tempPopulation.keySet()) {
                    probaSum += (c.getFitness()/fitnessSum) * 100;
                    tempPopulation.replace(c, probaSum);
                }
                
                random = Math.random() * 100;
                
                for (Chessboard c : tempPopulation.keySet()) {
                    if (random <= tempPopulation.get(c)) {
                        mother = c;
                        
                        break;
                    }
                }

                Random rand = new Random();
                int splitIndex = rand.nextInt(chessboardSize);

                String[] leftSolution = father.getColumnsBefore(splitIndex);
                String[] rightSolution = mother.getColumnsAfter(splitIndex);

                String[] solution = combineSolutions(leftSolution, rightSolution);

                Chessboard finalSolution = new Chessboard(solution);

                int mutationChance = rand.nextInt(100);

                if (mutationChance <= mutationProbability) {
                    finalSolution.mutate();
                }

                newPopulation.put(finalSolution, 0.0);
            }
            
            population = newPopulation;
            
            for (Chessboard c : population.keySet()) {
                if (c.getFitness() > bestSolution.getFitness()) {
                    bestSolution = c;
                }
            }
            
            System.out.println(bestSolution.getFitness());
            
            if( bestSolution.getFitness() == (chessboardSize * (chessboardSize-1))/2 ) {
                return bestSolution;
            }
        }
        
        return bestSolution;
    }

    private static String[] combineSolutions(String[] leftSolution, String[] rightSolution) {
        int length = leftSolution.length + rightSolution.length;
        String[] result = new String[length];
        System.arraycopy(leftSolution, 0, result, 0, leftSolution.length);
        System.arraycopy(rightSolution, 0, result, leftSolution.length, rightSolution.length);

        return result;
    }
}
