/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 *
 * @author yoannlathuiliere
 */
public class Chessboard
{

    private int[] columns;

    public int[] getColumns()
    {
        return columns;
    }

    private int size;
    private int fitness;

    public int getFitness()
    {
        return fitness;
    }

    
    public Chessboard(int size, boolean alldiago)
    {
        this.size = size;
        this.columns = new int[this.size];
        
        for(int i=0; i<this.size; i++)
        {
            this.columns[i] = i;
        }
        
        this.fitness = this.computeFitness(FitnessEnum.CONFLICT);
    }

    public Chessboard(int size)
    {
        this.size = size;
        this.columns = new int[this.size];

        //TO DELETE
        this.fitness = 0;

        this.generateColumns();

        this.fitness = this.computeFitness(FitnessEnum.CONFLICT);
    }

    public Chessboard(int size, int[] solution)
    {
        this(size);
        this.setSolution(solution);
        this.fitness = this.computeFitness(FitnessEnum.CONFLICT);
    }

    public Chessboard(int[] cols)
    {
        this.size = cols.length;
        this.columns = cols;

        //TO DELETE     
        this.fitness = this.computeFitness(FitnessEnum.CONFLICT);
    }

    private void generateColumns()
    {
        Set<Integer> taken = new HashSet();
        for (int i = 0; i < this.size; i++)
        {
            int randomNum;
            do
            {
                randomNum = ThreadLocalRandom.current().nextInt(0, this.size);
            }
            while (taken.contains(randomNum));

            taken.add(randomNum);
            this.columns[i] = randomNum;
        }
    }

    private int computeFitness(FitnessEnum fitness)
    {
        if (fitness == null)
        {
            fitness = FitnessEnum.CONFLICT;
        }
        int fitnessResult = 0;
        switch (fitness)
        {
            case CONFLICT:
                fitnessResult = this.fitnessConflict();
                break;

            case NO_CONFLICT:
                fitnessResult = this.fitnessNoConflitct();
                break;

            default:
                fitnessResult = this.fitnessConflict();
                break;
        }
        return fitnessResult;
    }

    private int fitnessConflict()
    {
        this.fitness = fitnessConflictGeneric(this.columns);
        return this.fitness;
    }
    
    private int fitnessConflictGeneric(int[] cols)
    {
        int fit = 0;
        for (int i = 0; i < cols.length - 1; i++)
        {
            for (int j = i + 1; j < cols.length; j++)
            {
                int currentQueen = cols[i];
                int comparedQueen = cols[j];

                if (IsOnTheSameDiagonal(i, j, currentQueen, comparedQueen) || IsOnSameColumns(currentQueen, comparedQueen))
                {
                    fit++;
                }
            }
        }
        return fit;
    }

    private int fitnessNoConflitct()
    {
        this.fitness = ((this.size * (this.size - 1)) / 2) - fitnessConflict();

        return ((this.size * (this.size - 1)) / 2) - fitnessConflict();
    }

    public void setSolution(int[] solution)
    {
        if (solution.length == size)
        {
            System.arraycopy(solution, 0, this.columns, 0, solution.length);
        }
    }

    public String getSolution()
    {
        String solution = "";
        for (int i = 0; i < this.size; i++)
        {
            solution += this.columns[i];
        }
        return solution;
    }

    public ArrayList<Chessboard> getNeighbours()
    {
        ArrayList<Chessboard> neighbours = new ArrayList();
        for (int i = 0; i < this.size - 1; i++)
        {
            int[] neighbour = new int[this.size];
            //String[] neighbour = this.columns.clone();
            System.arraycopy(this.columns, 0, neighbour, 0, this.size);
            for (int j = i + 1; j < this.size; j++)
            {
                int tmp = neighbour[i];
                neighbour[i] = neighbour[j];
                neighbour[j] = tmp;
                neighbours.add(new Chessboard(neighbour));
            }
        }
        return neighbours;
    }
    
    /* FOR TABU*/
   /*ù public Chessboard getFirstBestNeigh(ArrayList<int> tabu)
    {
        int[] best = new int[this.size];
        int fitBest = Integer.MAX_VALUE;
        for(int i=0; i<this.size; i++)
        {
            int[] neighbour = new int[this.size];
            System.arraycopy(this.columns, 0, neighbour, 0, this.size);
            for (int j = i + 1; j < this.size; j++)
            {
                int tmp = neighbour[i];
                neighbour[i] = neighbour[j];
                neighbour[j] = tmp;
                if(!tabu.contains(test))
                {
                    int tmpFit = this.fitnessConflictGeneric(neighbour);
                    if(tmpFit <= this.fitness)
                    {
                        return new Chessboard(neighbour);
                    }
                    if(tmpFit <= fitBest)
                    {
                        fitBest = tmpFit;
                        System.arraycopy(neighbour, 0, best, 0, neighbour.length);
                    }
                }   
            }
        }
        if(best == null)
        {
            return null;
        }
        return new Chessboard(best);
    }*/

    public ArrayList<int[]> getNeighboursint()
    {
        ArrayList<int[]> neighbours = new ArrayList();
        for (int i = 0; i < this.size - 1; i++)
        {
            int[] neighbour = new int[this.size];
            System.arraycopy(this.columns, 0, neighbour, 0, this.size);
            for (int j = i + 1; j < this.size; j++)
            {
                int tmp = neighbour[i];
                neighbour[i] = neighbour[j];
                neighbour[j] = tmp;
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public Chessboard generateRandomNeigh()
    {
        int[] neighint = new int[this.size];
        System.arraycopy(this.columns, 0, neighint, 0, this.size);

        int choosedColumn1 = (int) (Math.random() * (this.size));
        int choosedColumn2 = choosedColumn1;
        while (choosedColumn1 == choosedColumn2)
        {
            choosedColumn2 = (int) (Math.random() * (this.size));
        }

        int tmp = neighint[choosedColumn1];
        neighint[choosedColumn1] = neighint[choosedColumn2];
        neighint[choosedColumn2] = tmp;

        return new Chessboard(neighint);
    }

    @Override
    public String toString()
    {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < this.size; i++)
        {
            stb.append(this.columns[i]);
            //stb.append(" ");
        }

        return stb.toString();
    }

    private boolean IsBetweenIncluded(int value, int inf, int sup)
    {
        return (value >= inf || value <= sup);
    }

    private boolean IsOnSameColumns(int a, int b)
    {
        return a == b;
    }

    private boolean IsOnTheSameDiagonal(int col1, int col2, int line1, int line2)
    {
        return Math.abs((col1 - col2)) == Math.abs(line1 - line2);
    }

    public int[] getColumnsBefore(int index)
    {
        return Arrays.copyOfRange(columns, 0, index);
    }

    public int[] getColumnsAfter(int index)
    {
        return Arrays.copyOfRange(columns, index, columns.length);
    }

    public void mutate()
    {
        Random rand = new Random();
        int index = rand.nextInt(this.size - 1);
        int value = rand.nextInt(this.size - 1);

        this.columns[index] = value;

        this.fitness = fitnessConflict();
    }
    
    public void mutateImproved() {
        Random rand = new Random(); 
        int index1 = rand.nextInt(this.size - 1);
        int index2 = rand.nextInt(this.size - 1);
        
        int tmp = this.columns[index1];
        
        this.columns[index1] = this.columns[index2];
        this.columns[index2] = tmp;
        
        this.fitness = fitnessConflict();
    }

    public boolean IsInConflict(int value, int index, int comparedValue, int comparedIndex)
    {
        return (Math.abs(value - index) == Math.abs(comparedValue - comparedIndex));
    }
    
    public ArrayList<Integer> getIndexConflicted()
    {
        ArrayList<Integer> indexConflicted = new ArrayList<>();
        
        for(int i=0; i<this.size-1; i++)
        {
            boolean conflicted = false;
            for(int j=i+1; j<this.size && !conflicted; j++)
            {
                if(IsInConflict(this.columns[i], i, this.columns[j], j))
                {
                    conflicted = true;
                    if(!indexConflicted.contains(i))
                        indexConflicted.add(i);
                    if(!indexConflicted.contains(j))
                        indexConflicted.add(j);
                }
            }
        }
        
        return indexConflicted;
    }

    public ArrayList<Integer> getConflicts(int index)
    {
        ArrayList<Integer> conflicts = new ArrayList<>();

        for (int i = 0; i < this.size; i++)
        {
            Chessboard sol = null;
            if (i == index)
            {
                sol = this;
            }
            else
            {
                int[] tmp = new int[this.size];
                System.arraycopy(this.columns, 0, tmp, 0, this.size);
                tmp[index] = i;
                sol = new Chessboard(tmp);
            }
            int conflict = 0;
            for(int j=0; j<this.size; j++)
            {
                if(IsInConflict(sol.columns[index], index, sol.columns[j], j))
                    conflict++;
            }
            conflicts.add(conflict); 
        }
        return conflicts ;
    }
}
