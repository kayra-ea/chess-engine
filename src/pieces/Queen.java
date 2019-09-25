package pieces;

import java.util.ArrayList;

public class Queen extends Piece {

    byte type = QUEEN;
    byte color;
    short rawX, rawY;
    byte gridPosX, gridPosY;
    boolean isFirstMove;

    public ArrayList<Position> possibleMovesArr;

    public Queen(byte color, Position p, boolean b){
        value = 9;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♛";
        if (color == WHITE){
            symbol = "♕";
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

    public ArrayList<Position> calcPossibleMoves(){ //changed
        //clearing the previous array to restart
        possibleMovesArr.clear();

        for(byte i = 1; i <= gridPosX; i++){
            possibleMovesArr.add(new Position((byte)(gridPosX-i), gridPosY));
            possibleMovesArr.add(new Position((byte)(gridPosX - i), (byte)(gridPosY - i)));
            possibleMovesArr.add(new Position((byte)(gridPosX - i), (byte)(gridPosY + i)));
        }
        for(byte y = 1; y <= 7 - gridPosX; y++){
            possibleMovesArr.add(new Position((byte)(gridPosX+y), gridPosY));
            possibleMovesArr.add(new Position((byte)(gridPosX+y), (byte)(gridPosY-y)));
            possibleMovesArr.add(new Position((byte)(gridPosX+y), (byte)(gridPosY+y)));
        }
        for(byte w = 1; w <= gridPosY; w++){
            possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY-w)));
        }
        for(byte z = 1; z <= 7 - gridPosY; z++){
            possibleMovesArr.add(new Position(gridPosX, (byte)(gridPosY+z)));
        }

        return possibleMovesArr;
    }

    public int getType(){ return type; }
    public int getValue(){ return value; }
    public String getSymbol(){return symbol;}

    public byte getGridX(){ return gridPosX; }
    public byte getGridY(){ return gridPosY; }
    public byte getColor(){return color;}
    public void setFirstMove(boolean b){isFirstMove = b;}
    public boolean getFirstMove(){return isFirstMove;}
}
