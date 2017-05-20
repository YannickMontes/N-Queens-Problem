/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author yoannlathuiliere
 */
public class Chessboard {
    private String[] columns;
    private int size;
    
    public Chessboard(int size) 
    {
        this.size = size;
        this.columns = new String[this.size];
        
        this.generateColumns();
    }
    
    public Chessboard(int size, String solution)
    {
        this(size);
        this.setSolution(solution);
    }
    
    public Chessboard(String[] cols)
    {
        this.size = cols.length;
        this.columns = new String[this.size];
        System.arraycopy(cols, 0, this.columns, 0, this.size);
    }
    
    private void generateColumns() {
        Set<String> taken = new HashSet();
        for(int i=0; i<this.size; i++) {
            int randomNum;
            do {
                randomNum = ThreadLocalRandom.current().nextInt(0, this.size);
            } while (taken.contains(String.valueOf(randomNum)));
            
            taken.add(String.valueOf(randomNum));
            this.columns[i] = String.valueOf(randomNum);
        }
    }
    
    public int computeFitness(FitnessEnum fitness)
    {
        if(fitness == null)
            fitness = FitnessEnum.CONFLICT;
        int fitnessResult = 0;
        switch(fitness)
        {
            case CONFLICT:
                fitnessResult = this.fitnessConflict();
                break;
                
            default:
                fitnessResult = this.fitnessConflict();
                break;
        }
        return fitnessResult;
    }
    
    private int fitnessConflict()
    {
        int fitness = 0;
        for(int i=0; i<this.size-1; i++)
        {
            for(int j=i+1; j<this.size; j++)
            {
                int currentQueen = Integer.parseInt(this.columns[i]);
                int comparedQueen = Integer.parseInt(this.columns[j]);
                
                if(IsOnTheSameDiagonal(i, j, currentQueen, comparedQueen))
                {
                    fitness++;
                }
            }
        } 
        return fitness;
    }
    
    public void setSolution(String solution)
    {
        if(solution.length() == size)
        {
            for(int i=0; i<solution.length(); i++)
            {
                String tmp = "" + solution.charAt(i);
                this.columns[i] = tmp;
            }
        }
    }
    
    public String getSolution()
    {
        String solution = "";
        for(int i=0; i<this.size; i++)
        {
            solution += this.columns[i];
        }
        return solution;
    }
    
    public ArrayList<Chessboard> getNeighbours()
    {
        ArrayList<Chessboard> neighbours = new ArrayList();
        for(int i=0; i<this.size-1; i++)
        {
            String[] neighbour = new String[this.size];
            System.arraycopy(this.columns, 0, neighbour, 0, this.size);
            for(int j=i+1; j<this.size; j++)
            {
                String tmp = neighbour[i];
                neighbour[i] = neighbour[j];
                neighbour[j] = tmp;
                neighbours.add(new Chessboard(neighbour));
            }
        }
        return neighbours;
    }
    
    public ArrayList<String> getNeighboursString()
    {
        ArrayList<String> neighbours = new ArrayList();
        for(int i=0; i<this.size-1; i++)
        {
            String[] neighbour = new String[this.size];
            System.arraycopy(this.columns, 0, neighbour, 0, this.size);
            for(int j=i+1; j<this.size; j++)
            {
                String tmp = neighbour[i];
                neighbour[i] = neighbour[j];
                neighbour[j] = tmp;
                neighbours.add(Arrays.toString(neighbour));
            }
        }
        return neighbours;
    }
    
    public Chessboard generateRandomNeigh()
    {        
        String[] neighString = new String[this.size];
        System.arraycopy(this.columns, 0, neighString, 0, this.size);
        
        int choosedColumn1 = (int)(Math.random() * (this.size));
        int choosedColumn2 = choosedColumn1;
        while(choosedColumn1 == choosedColumn2)
        {
            choosedColumn2 = (int)(Math.random() * (this.size));
        }
        
        String tmp = neighString[choosedColumn1];
        neighString[choosedColumn1] = neighString[choosedColumn2];
        neighString[choosedColumn2] = tmp;
        
        return new Chessboard(neighString);
    }
    
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i=0; i<this.size; i++) {
            stb.append(this.columns[i]);
            stb.append(" ");
        } 
       
        return stb.toString();
    }
    
    private boolean IsBetweenIncluded(int value, int inf, int sup)
    {
        return (value >= inf || value <= sup);
    }
    
    private boolean IsOnTheSameDiagonal(int col1, int col2, int line1, int line2)
    {
        return Math.abs((col1 - col2)) == Math.abs(line1-line2);
    }
            
            
}
