package pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

    public  byte type = PAWN;
    public byte color;
    public short rawX, rawY;
    public byte gridPosX, gridPosY;

    public ArrayList<Position> possibleMovesArr;

    public boolean isFirstMove;

    public Pawn(byte color, Position p, boolean b){
        value = 1;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♟";
        else{
            symbol = "♙";
        }

        gridPosX = p.returnX();
        gridPosY = p.returnY();

        rawX = setPositionX(p.returnX());
        rawY = setPositionY(p.returnY());

        position[X] = rawX;
        position[Y] = rawY;
    }

    public void move(Position pos){
        isFirstMove = false;

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

        if(color == BLACK){
            possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY + 1)));
        } else {
            possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY - 1)));
        }

        if(isFirstMove == true){
            if(color == BLACK){
                possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY + 2)));
            } else {
                possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY - 2)));
            }
        }

        return possibleMovesArr;
    }

    public boolean getFirstMove(){return isFirstMove;}
    public byte getGridX(){ return gridPosX; }
    public byte getGridY(){ return gridPosY; }
    public int getType(){ return type; }
    public int getValue(){ return value; }
    public byte getColor(){return color;} //changed
    public String getSymbol(){return symbol;}
    public void setFirstMove(boolean b){isFirstMove = b;}
}