package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import pieces.*;

public class fileWriter{
    int BLACK = 1; int WHITE = 0;
    FileWriter out = new FileWriter(new File("output.txt"));

    public fileWriter() throws IOException{
    }

    public void dumpBoard(Piece[][] board) throws IOException{
        int i,j;
        Piece p;
        for (i=0; i<8; i++){
            String line;
            line = "";
            for (j=0; j<8; j++){
                p = board[j][i];
                if (p == null) {
                    line += ".";
                }else {
                    int t = p.getType();
                    byte c = p.getColor();
                    if (t == 0) {
                        if (c == BLACK) {
                            line += "P";
                        } else {
                            line += "p";
                        }
                    } else if (t== 1){
                        if (c == BLACK) {
                            line += "R";
                        } else {
                            line += "r";
                        }
                    } else if (t == 2){
                        if (c == BLACK) {
                            line += "H";
                        } else {
                            line += "h";
                        }
                    } else if (t == 3){
                        if (c == BLACK) {
                            line += "B";
                        } else {
                            line += "b";
                        }
                    } else if (t == 5){
                        if (c == BLACK) {
                            line += "Q";
                        } else {
                            line += "q";
                        }
                    } else if (t == 4){
                        if (c == BLACK) {
                            line += "K";
                        } else {
                            line += "k";
                        }
                    } else {
                        if (c == BLACK) {
                            line += "w";
                        } else {
                            line += "W";
                        }
                    } // not a known type W white w black
                } // non null piece
            } // j
            //System.out.println("line "+i+": "+line); System.out.flush();
            out.write("line "+i+": "+line);
        } // i
        return;
    }
}
