package pieces;

public class Position {
    private byte x1,y1;
    boolean equals;
    public Position(byte x1, byte y1){
        this.x1 = x1;
        this.y1 = y1;
    }

    public byte returnX(){return x1;}
    public byte returnY(){return y1;}

    public void setX(byte x){ this.x1 = x; }
    public void setY(byte y){ this.y1 = y; }

    public boolean equals(Position pos){
        equals = false;
        if(x1 == pos.returnX() && y1 == pos.returnY()){
            equals = true;
        }
        return equals;
    }
}
