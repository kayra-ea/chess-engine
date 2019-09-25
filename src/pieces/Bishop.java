package pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

    byte type = BISHOP;
    byte color;
    short rawX, rawY;
    byte gridPosX, gridPosY;
    boolean isFirstMove;

    public ArrayList<Position> possibleMovesArr;

    public Bishop(byte color, Position p, boolean b){
        value = 3;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♝";
        if (color == WHITE){
            symbol = "♗";
        }

        gridPosX = p.returnX();
        gridPosY = p.returnY();

        rawX = setPositionX(p.returnX());
        rawY = setPositionY(p.returnY());

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

        for(int i = 1; i <= gridPosX; i++){
            possibleMovesArr.add(new Position((byte)(gridPosX - i), (byte)(gridPosY - i)));
            possibleMovesArr.add(new Position((byte)(gridPosX - i), (byte)(gridPosY + i)));
        }
        for(int y = 1; y <= 7 - gridPosX; y++){
            possibleMovesArr.add(new Position((byte)(gridPosX+y), (byte)(gridPosY-y)));
            possibleMovesArr.add(new Position((byte)(gridPosX+y), (byte)(gridPosY+y)));
        }
        return possibleMovesArr;
    }

    public ArrayList<Position> setPossibleMovesArr(ArrayList<Position> arr) {
        possibleMovesArr.clear();
        possibleMovesArr = new ArrayList<Position>(arr);

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
    public void setFirstMove(boolean b){isFirstMove = b;}
    public boolean getFirstMove(){return isFirstMove;}
}
