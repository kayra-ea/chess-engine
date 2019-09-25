package pieces;
import java.util.*;

public class King extends Piece {

    byte type = KING;
    byte color;
    short rawX, rawY;
    byte gridPosX, gridPosY;

    public ArrayList<Position> possibleMovesArr;
    boolean isFirstMove;
    boolean castled = false;

    public King(byte color, Position pos, boolean b){
        value = 4;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♚";
        if (color == WHITE){
            symbol = "♔";
        }

        gridPosX = pos.returnX();
        gridPosY = pos.returnY();

        rawX = setPositionX(pos.returnX());
        rawY = setPositionY(pos.returnY());

        position[X] = rawX;
        position[Y] = rawY;
    }

    public void move(Position pos){
        gridPosX = pos.returnX();
        gridPosY = pos.returnY();

        short x = setPositionX(pos.returnX()); //takes a grid position and converts it a pixel value
        short y = setPositionY(pos.returnY());

        position[X] = x;
        position[Y] = y;
    }

    public ArrayList<Position> calcPossibleMoves(){
        //clearing the previous array to restart
        possibleMovesArr.clear();

        possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY - 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX + 1), (byte)(gridPosY - 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX + 1), gridPosY));
        possibleMovesArr.add(new Position((byte)(gridPosX + 1), (byte)(gridPosY + 1)));
        possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY + 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 1), (byte)(gridPosY + 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 1), gridPosY));
        possibleMovesArr.add(new Position((byte)(gridPosX - 1), (byte)(gridPosY - 1)));

        return possibleMovesArr;
    }

    public ArrayList<Position> getPossibleMovesArr(){
        return possibleMovesArr;
    }
    public byte getGridX(){
        return gridPosX;
    }
    public byte getGridY(){
        return gridPosY;
    }
    public int getType(){
        return type;
    }
    public int getValue(){
        return value;
    }
    public byte getColor(){return color;}
    public String getSymbol(){return symbol;}
    public boolean getFirstMove(){ return isFirstMove;}
    public void setFirstMove(boolean b){ isFirstMove = b;}
    public void setCastled(boolean b){castled = b;}
    public boolean getCastled(){return castled;}
}

