/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queens.problem;
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
    
    public Chessboard(int size) {
        this.size = size;
        this.columns = new String[this.size];
        
        this.generateColumns();
    }
    
    private void generateColumns() {
        Set<String> taken = new HashSet<String>();
        for(int i=0; i<this.size; i++) {
            int randomNum;
            do {
                randomNum = ThreadLocalRandom.current().nextInt(0, this.size);
            } while (taken.contains(String.valueOf(randomNum)));
            
            taken.add(String.valueOf(randomNum));
            this.columns[i] = String.valueOf(randomNum);
        }
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
}
