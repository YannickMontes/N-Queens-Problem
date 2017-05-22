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
public abstract class GeneticImprovedV4 {

    private static final int MAX_ITERATIONS = 100;
    private static final int POPULATION_SIZE = 50;
    private static final int MUTATION_PROBABILITY = 90;
    private static final FitnessEnum FITNESS_TYPE = FitnessEnum.CONFLICT;

    /**
     * Method to execute tabu search algorithm on given chessboard size with
     * given parameters
     *
     * @param chessboardSize The size of the chessboard (required)
     * @param populationSize The size for the population list (10 if null given)
     * @param maxIt The number of iterations maximum (5000 if null given)
     * @param fit The fitness we want to use
     * @param mutationProb the mutation probability
     * @return The best chessboard
     */
    public static Chessboard execute(int chessboardSize, Integer populationSize, Integer maxIt, FitnessEnum fit, Integer mutationProb) {
        //Init parameters variables
        int maxIterations = maxIt != null ? maxIt : MAX_ITERATIONS;
        int populationSiz = populationSize != null ? populationSize : POPULATION_SIZE;
        int mutationProbability = mutationProb != null ? mutationProb : MUTATION_PROBABILITY;

        ArrayList<Chessboard> population = new ArrayList<>();

        //First let's generate our population
        generateFirstPopulation(population, populationSiz, chessboardSize);

        //Initialize the best solution
        Chessboard bestSolution = (Chessboard) population.get(0);
        
        for (Chessboard c : population) {
            if (c.getFitness() < bestSolution.getFitness()) {
                bestSolution = c;
            }

            if (c.getFitness() == 0) {
                return c;
            }
        }

        //Let's start the algorithm
        for (int i = 0; i < maxIterations; i++) {
            System.out.println("Génération : " + i);

            //Create a new population
            ArrayList<Chessboard> newPopulation = new ArrayList<>();

            for (int j = 0; j < populationSiz; j++) {
                // ------------------------ SELECTION --------------------------
                // -------------------------------------------------------------
                Chessboard father = selectRandomIn(population, populationSiz);
                Chessboard mother = null;
                while(mother == null || mother == father) {
                    mother = selectRandomIn(population, populationSiz);
                }

                // ------------------------ CROSSING ---------------------------
                // -------------------------------------------------------------
                Chessboard son = makeCrossing(mother, father, chessboardSize);

                // ------------------------ MUTATION ---------------------------
                // -------------------------------------------------------------
                mutate(son, mutationProbability);
                
                //Fill the new population
                if(son.getFitness() <= bestSolution.getFitness()) {
                    newPopulation.add(son);
                }
            }

            //if(isNewPopulationBetter(newPopulation, bestSolution)) {
                if(newPopulation.size() > 1) {
                    population = newPopulation;
                }
                
                for (Chessboard c : population) {
                if (c.getFitness() < bestSolution.getFitness()) {
                        bestSolution = new Chessboard(c.getColumns());
                    }

                    if (c.getFitness() == 0) {
                        return c;
                    }
                }
            //}

            System.out.println(bestSolution.getFitness());
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

    private static void computeCumulatedProbability(HashMap<Chessboard, Double> population) {
        Double fitnessSum = 0.0;
        Double probaSum = 0.0;

        //Compute the cumulated fitness    
        for (Chessboard c : population.keySet()) {
            fitnessSum += 1d / c.getFitness();
            population.replace(c, 0.0);
        }

        //Compute the cumulated probability
        for (Chessboard c : population.keySet()) {
            probaSum += ((1d / c.getFitness()) / fitnessSum) * 100;
            population.replace(c, probaSum);
        }
    }

    private static void generateFirstPopulation(ArrayList<Chessboard> population, int populationSize, int chessboardSize) {
        for (int i = 0; i < populationSize; i++) {
            Chessboard solution = new Chessboard(chessboardSize);

            population.add(solution);
        }
    }

    private static Chessboard selectRandomIn(ArrayList<Chessboard> population, int chessboardSize) {
        Random rand = new Random();
        int index = rand.nextInt(chessboardSize);

        return population.get(index);
    }
    
    private static boolean isNewPopulationBetter(ArrayList<Chessboard> newPopulation, Chessboard bestSolution) {
        for (Chessboard c : newPopulation) {
            if (c.getFitness() < bestSolution.getFitness()) {
                return true;
            }
        }

        return false;
    }

    private static Chessboard makeCrossing(Chessboard mother, Chessboard father, int chessboardSize) {
        Random rand = new Random();
        int splitIndex = rand.nextInt(chessboardSize);

        String[] leftSolution = father.getColumnsBefore(splitIndex);
        String[] rightSolution = mother.getColumnsAfter(splitIndex);

        String[] solution = combineSolutions(leftSolution, rightSolution);

        return new Chessboard(solution);
    }
    
    private static boolean containsElementInArray(String[] array, String element) {
        for (int i = 0; i<array.length; i++) {
            if (array[i] != null && array[i].equals(element)) {
                return true;
            }
        }
        
        return false;
    }

    private static void mutate(Chessboard son, int mutationProbability) {
        Random rand = new Random();
        int mutationChance = rand.nextInt(100);

        if (mutationChance <= mutationProbability) {
            son.mutateImproved();
        }
    }
}
