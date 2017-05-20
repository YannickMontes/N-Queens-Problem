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

    private static final int MAX_ITERATIONS = 5000;
    private static final int POPULATION_SIZE = 5;
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
        Double fitnessSum = 0.0;
        Double probaSum = 0.0;

        HashMap<Chessboard, Double> population = new HashMap<Chessboard, Double>();

        //First let's generate our population
        for (int i = 0; i < populationSiz; i++) {
            Chessboard solution = new Chessboard(chessboardSize);
            solution.generateRandomNeigh();

            population.put(solution, 0.0);
            fitnessSum += solution.getFitness();
        }

        //Let's start the algorithm
        for (int i = 0; i < maxIterations; i++) {
            for (Chessboard c : population.keySet()) {
                Double proba = probaSum + (100 - ((c.getFitness()/fitnessSum) * 100));
                population.replace(c, proba);
                
                probaSum += proba;
            }

            HashMap<Chessboard, Double> newPopulation = new HashMap<Chessboard, Double>();

            for (int j = 0; i < populationSiz; i++) {
                HashMap<Chessboard, Double> tempPopulation = population;
                Double random = Math.random() * 100;
                Chessboard father = null;
                Chessboard mother = null;

                for (Chessboard c : tempPopulation.keySet()) {
                    if (random < tempPopulation.get(c)) {
                        father = c;
                        tempPopulation.remove(c);

                        break;
                    }
                }

                for (Chessboard c : tempPopulation.keySet()) {
                    if (random < tempPopulation.get(c)) {
                        mother = c;
                        tempPopulation.remove(c);

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
        }

        Chessboard bestSolution = (Chessboard) population.keySet().toArray()[0];
        
        for (Chessboard c : population.keySet()) {
            if (c.getFitness() < bestSolution.getFitness()) {
                bestSolution = c;
            }
        }
        
        return bestSolution;
    }

    public static ArrayList<String> getNeighsNotTabu(ArrayList<String> neighs, ArrayList<String> tabu) {
        tabu.stream().filter((sol) -> (neighs.contains(sol))).forEach((sol)
                -> {
            neighs.remove(sol);
        });
        return neighs;
    }

    private static String chooseRandomNeighbours(ArrayList<String> neighs) {
        int rand = (int) (Math.random() * (neighs.size() - 1));
        return neighs.get(rand);
    }

    private static void addToTabuList(ArrayList<String> tabu, String solution, int tabuTabSize) {
        if (tabu.size() == tabuTabSize) {
            tabu.remove(0);
        }
        tabu.add(solution);
    }

    private static String[] combineSolutions(String[] leftSolution, String[] rightSolution) {
        int length = leftSolution.length + rightSolution.length;
        String[] result = new String[length];
        System.arraycopy(leftSolution, 0, result, 0, leftSolution.length);
        System.arraycopy(rightSolution, 0, result, rightSolution.length, rightSolution.length);

        return result;
    }
}
