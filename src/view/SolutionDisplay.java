/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        this.pack();
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    private JPanel createJPanelFromBoard(Chessboard chess)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for(int col : chess.getColumns())
        {
            JLabel label = new JLabel();
            StringBuilder tmp = new StringBuilder("");
            for(int i=0; i<chess.getColumns().length; i++)
            {
                tmp.append(" - ");
            }
            tmp.setCharAt(3 * col + 1, '0');
            label.setText(tmp.toString());
            label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 22));
            panel.add(label);
        }
        return panel;
    }
}
