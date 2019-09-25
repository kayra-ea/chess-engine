package pieces;
import java.util.*;

public class Knight extends Piece {

    byte type = KNIGHT;
    byte color;
    short rawX,rawY;
    byte gridPosX, gridPosY;
    boolean isFirstMove;

    public ArrayList<Position> possibleMovesArr;

    public Knight(byte color, Position p, boolean b){
        value = 3;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♞";
        if (color == WHITE){
            symbol = "♘";
        }

        gridPosX = p.returnX();
        gridPosY = p.returnY();

        rawX = setPositionX(p.returnX());
        rawY = setPositionY(p.returnY());

        position[X] = rawX;
        position[Y] = rawY;
    }

    public void move(Position pos){ //changed
        isFirstMove = false;
        gridPosX = pos.returnX();
        gridPosY = pos.returnY();

        short x = setPositionX(pos.returnX()); //takes a grid position and converts it a pixel value
        short y = setPositionY(pos.returnY());

        position[X] = x;
        position[Y] = y;
    }

    public ArrayList<Position> calcPossibleMoves(){ //changed
        //clearing the previous array to restart
        possibleMovesArr.clear();

        possibleMovesArr.add(new Position((byte)(gridPosX + 1), (byte)(gridPosY - 2)));
        possibleMovesArr.add(new Position((byte)(gridPosX + 2), (byte)(gridPosY - 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX + 2), (byte)(gridPosY + 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX + 1), (byte)(gridPosY + 2)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 1), (byte)(gridPosY + 2)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 2), (byte)(gridPosY + 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 2), (byte)(gridPosY - 1)));
        possibleMovesArr.add(new Position((byte)(gridPosX - 1), (byte)(gridPosY - 2)));

        return possibleMovesArr;
    }

    public int getType(){
        return type;
    }
    public int getValue(){
        return value;
    }
    public String getSymbol(){return symbol;}
    public byte getGridX(){
        return gridPosX;
    }
    public byte getGridY(){
        return gridPosY;
    }
    public byte getColor(){return color;}
    public void setFirstMove(boolean b){isFirstMove = b;}
    public boolean getFirstMove(){return isFirstMove;}
}
