/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author yoannlathuiliere
 */
public class Chessboard {
    private String[] columns;
    private int size;
    
    public Chessboard(int size) {
        this.size = size;
        this.columns = new String[this.size];
        
        this.generateColumns();
    }
    
    private void generateColumns() {
        for(int i=0; i<this.size; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, this.size);
            this.columns[i] = String.valueOf(randomNum);
        }
    }
    
    public int fitnessConflict()
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
    
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i=0; i<this.size; i++) {
            stb.append(this.columns[i]);
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
