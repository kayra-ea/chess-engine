package Main;//import ComputatChessDisplay;

import Computational.ChessDisplay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Game{

    private JPanel ChessBoard;
    public JPanel ChessDisplay;
    private JPanel MenuPanel;
    private JButton UndoMoveBtn;
    JButton ResignBtn;
    private JLabel TurnLbl;
    JButton ReturnBtn;

    public JOptionPane popup = new JOptionPane();

    boolean isplayersTurn = false;

    int playercolor;
    String player;

    public Game(){

        TurnLbl.setText("You play White");

        ReturnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        UndoMoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((ChessDisplay)ChessDisplay).UndoMove();
            }
        });
        ResignBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "By resigning, you have finished the game", "In Game Message", JOptionPane.ERROR_MESSAGE);
                ((ChessDisplay)ChessDisplay).setUpBoard();
            }
        });
    }

    public JPanel getChessBoard(){
        return ChessBoard;
    }

    public JPanel getNewChessBoard(){
        JPanel ChessBoard2;
        return ChessBoard;
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        ChessDisplay = new ChessDisplay();
    }
}
