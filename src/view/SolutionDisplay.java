/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import n.queens.problem.Chessboard;

/**
 *
 * @author yannick
 */
public class SolutionDisplay extends JDialog
{
    public SolutionDisplay(Chessboard chess, JFrame p)
    {
        super(p, "Best solution found", true);
        
        JPanel board = createJPanelFromBoard(chess);
        
        this.add(board, BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private JPanel createJPanelFromBoard(Chessboard chess)
    {
        //for(chess.)
        return null;
    }
}
