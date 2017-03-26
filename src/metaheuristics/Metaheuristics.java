/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaheuristics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import n.queens.problem.Chessboard;

/**
 *
 * @author yannick
 */
public abstract class Metaheuristics
{
    public static void TabuSearch()
    {
        
    }   
    
    public static void Recuit()
    {
        Chessboard solution_initial;
        int temperature_initial;
    }
    
    // chromosomes -> nombres de populations initiales
    public static void Genetic(int nbChromosomes, int size, String[] initalChrom, int nbIt) {
        List<String[]> population = new ArrayList<String[]>();
        population.add(initalChrom);
        
        for(int i=1; i<nbChromosomes; i++) {
            String[] chromo = generateColumns(size);
            population.add(chromo);
        }
        
        for(int i=1; i<nbIt; i++) {
            
        }
    }
    
    //duplicate, we'll refractor after
    private static String[] generateColumns(int size) {
        String[] columns = new String[] {""};
        Set<String> taken = new HashSet<String>();
        for(int i=0; i<size; i++) {
            int randomNum;
            do {
                randomNum = ThreadLocalRandom.current().nextInt(0, size);
            } while (taken.contains(String.valueOf(randomNum)));
            
            taken.add(String.valueOf(randomNum));
            columns[i] = String.valueOf(randomNum);
        }
        
        return columns;
    }
    
    public static float ComputeInitialTemperatureForRecuit(int chessboardSize, float wantedProbability)
    {
        return 0;
    }
}
