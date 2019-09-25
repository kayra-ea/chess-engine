package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Chess {
    public static boolean first = true;
    public static void main(String[] args){

        JFrame frame = new JFrame("Main.Chess");
        Title title = new Title();
        Game game = new Game();
        Game game2 = new Game();

        JPanel titlePanel = title.getTitlePanel();
        JPanel gamePanel = game.getChessBoard();
        JPanel gamePanel2 = game2.getChessBoard();

        frame.setSize(new Dimension(600,350));

        title.Startbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.getContentPane().remove(titlePanel);
                if(first){frame.getContentPane().add(gamePanel);} else {frame.getContentPane().add(gamePanel2); }

                frame.setSize(new Dimension(813,610 ));
                frame.revalidate();
            }
        });

        title.Rulebtn.addActionListener(new ActionListener() {
            boolean printed = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(printed == false){
                    System.out.println("So, you want to learn to play chess? \n Well, you have come to the perfect place. Lets go over some basic rules" +
                            " to get you started: \n 1. You play either Black or White and you control all of 16 piece of your color \n 2. Movement \n" +
                            "    >Pawns only move in the direction of the opposing team and only 1 square at a time, expect for the first move \n" +
                            "    >Knights move in an 'L' shape (two over, one up or two up, one over)\n" +
                            "    >Bishops only move on diagonals\n" +
                            "    >Rooks only move in a straight line (not on diagonals)\n" +
                            "    >Queens can move in any direction\n" +
                            "    >Kings can move in any direction, but only one square at a time\n" +
                            "\n" +
                            "3. The objective is to put the opponents king in Checkmate (A position where the king is under attack and cannot be taken out of an attack position)\n\n" +
                            "If you can do this: YOU WIN\n" +
                            "If the opponent does it first, they win\n" +
                            "There's not much else to it, but if you wish for a more detailed \n explanation of the beautiful game of chess, check this link: http://www.chessvariants.com/d.chess/chess.html");
                    printed = true;
                }
            }
        });

        game.ReturnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().remove(gamePanel);
                frame.getContentPane().add(titlePanel);
                frame.setSize(new Dimension(600,350 ));
                frame.revalidate();
            }
        });

        game.ResignBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "By resigning, you have finished the game", "In Game Message", JOptionPane.ERROR_MESSAGE);

                first = false;
                frame.getContentPane().remove(gamePanel);
                frame.getContentPane().add(titlePanel);
                frame.setSize(new Dimension(600,350 ));
                frame.revalidate();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(titlePanel);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
