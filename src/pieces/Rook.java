package pieces;
import java.util.ArrayList;

public class Rook extends Piece {
    byte type = ROOK;
    byte color;
    short rawX, rawY;
    byte gridPosX, gridPosY;
    boolean isFirstMove;

    public ArrayList<Position> possibleMovesArr;

    public Rook(byte color, Position p, boolean b){
        value = 5;
        this.color = color;
        possibleMovesArr = new ArrayList<>();
        isFirstMove = b;

        if (color == BLACK)
            symbol = "♜";
        if (color == WHITE){
            symbol = "♖";
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

    public ArrayList<Position> calcPossibleMoves(){
        //clearing the previous array to restart
        possibleMovesArr.clear();

        for(byte i = 1; i <= gridPosX; i++){
            possibleMovesArr.add(new Position((byte)(gridPosX-i), (byte)(gridPosY)));
        }
        for(byte y = 1; y <= 7 - gridPosX; y++){
            possibleMovesArr.add(new Position((byte)(gridPosX+y), (byte)(gridPosY)));
        }
        for(byte w = 1; w <= gridPosY; w++){
            possibleMovesArr.add(new Position((byte)(gridPosX), (byte)(gridPosY-w)));
        }
        for(byte z = 1; z <= 7 - gridPosY; z++){
            possibleMovesArr.add(new Position((byte)(gridPosX), (byte)(gridPosY+z)));
        }

        return possibleMovesArr;
    }

    public int getType(){ return type; }
    public int getValue(){ return value; }
    public byte getGridX(){return gridPosX;}
    public byte getGridY(){ return gridPosY;}
    public byte getColor(){return color;}

    public String getSymbol(){return symbol;}
    public boolean getFirstMove(){ return isFirstMove;}
    public void setFirstMove(boolean b){ isFirstMove = b;}
}
