package pieces;

import java.awt.*;
import java.util.*;

public abstract class Piece {
    int value;
    short position[] = new short[2];

    final byte X = 0;
    final byte Y = 1;

    final byte boardBufferX = 48;
    final byte boardBufferY = 100;

    String symbol;

    final byte WHITE = 0;
    final byte BLACK = 1;

    final byte PAWN = 0;
    final byte ROOK = 1;
    final byte KNIGHT = 2;
    final byte BISHOP = 3;
    final byte KING = 4;
    final byte QUEEN = 5;

    /**
     * Draws a string character of the piece at its given position.
     * @param g: java graphics object; allows objects to be drawn to the GUI
     */
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Font font = new Font("Arial", Font.PLAIN, 66);
        g2d.setFont(font);

        //Drawing the piece
        g2d.drawString(symbol, position[X], position[Y]);
    }

    /** This method converts a grid position into a pixel value that will correspond to
     * the appropriate grid on the chessboard.
     * @param gridPosX: the x position of a piece on the chessboard (0-7)
     * @return
     */
    public short setPositionX(byte gridPosX){
        //Adding the board buffer is important to ensuring that the clicks align with the chessboard on screen.
        position[X] = (short)(gridPosX*60 + boardBufferX);
        return position[X];
    }

    /** This method converts a grid position into a pixel value that will correspond to
     * the appropriate grid on the chessboard.
     * @param gridPosY: the y position of a piece on the chessboard (0-7)
     * @return
     */
    public short setPositionY(byte gridPosY){
        //Adding the board buffer is important to ensuring that the clicks align with the chessboard on screen.
        position[Y] = (short)(gridPosY*60 + boardBufferY);
        return position[Y];
    }


    public abstract ArrayList<Position> calcPossibleMoves();
    public abstract void move(Position pos);

    public abstract int getType();
    public abstract int getValue();
    public abstract String getSymbol();
    public abstract byte getColor();
    public abstract byte getGridX();
    public abstract byte getGridY();
    public abstract void setFirstMove(boolean b);
    public abstract boolean getFirstMove();
}
