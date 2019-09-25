package Computational;

import pieces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ChessDisplay extends JPanel{
    static Piece[][] chessboard;
    public Stack<Piece[][]> boardStack = new Stack<>();

    final byte WHITE = 0, BLACK = 1;

    private boolean Bcheck, Wcheck, pieceisSelected, opening, midgame, endgame, turnIsWhite = true, calculating = false;

    private short mouseX, mouseY;

    private final byte boardBufferX = 50;
    private final byte boardBufferY = 50;

    private byte selectedX, selectedY;

    Piece selectionPiece;
    Position moveTo = new Position((byte)0,(byte)0);

    ArrayList<Position> possibleMovesArr, toDrawArr;

    ChessAI AI = new ChessAI();

    AIPlayer aiPlayer;
    Thread   aiPlayerThread;

    public ChessDisplay(){
        chessboard = new Piece[8][8];
        setUpBoard();

        pieceisSelected = false;
        opening = true;
        midgame = false;
        endgame = false;

        calculating = false;
        turnIsWhite = true;

        this.addMouseListener(new ClickListener());
        this.addMouseMotionListener(new MotionListener());

        aiPlayer = new AIPlayer(this);
        aiPlayerThread = new Thread(aiPlayer);
        aiPlayerThread.start();
    }

    public void setUpBoard(){
        for( int i = 0; i < chessboard.length; i++ )
            Arrays.fill( chessboard[i], null );

        for(byte i = 0; i < 8; i ++){ //Setting up Black Pawns
            chessboard[i][1] = new Pawn(BLACK, new Position(i, (byte)1), true);
        }
        for(byte y = 0; y < 8; y++){     //Setting up white pawns
            chessboard[y][6] = new Pawn(WHITE, new Position(y, (byte)6), true);
        }


        chessboard[0][0] = new Rook(BLACK, new Position((byte)0,(byte)0), true);
        chessboard[7][0] = new Rook(BLACK, new Position((byte)7, (byte)0), true);
        chessboard[1][0] = new Knight(BLACK, new Position((byte)1, (byte)0), true);       //Manually adding black pieces
        chessboard[6][0] = new Knight(BLACK, new Position((byte)6, (byte)0), true);
        chessboard[2][0] = new Bishop(BLACK, new Position((byte)2, (byte)0), true);
        chessboard[5][0] = new Bishop(BLACK, new Position((byte)5, (byte)0 ), true);
        chessboard[4][0] = new King(BLACK, new Position((byte)4,(byte)0), true);
        chessboard[3][0] = new Queen(BLACK, new Position((byte)3, (byte)0), true);

        chessboard[0][7] = new Rook(WHITE, new Position((byte)0, (byte)7), true);
        chessboard[7][7] = new Rook(WHITE, new Position((byte)7, (byte)7), true);     //Manually adding white pieces
        chessboard[1][7] = new Knight(WHITE, new Position((byte)1, (byte)7), true);
        chessboard[6][7] = new Knight(WHITE, new Position((byte)6, (byte)7), true);
        chessboard[2][7] = new Bishop(WHITE, new Position((byte)2,(byte)7), true);
        chessboard[5][7] = new Bishop(WHITE, new Position((byte)5, (byte)7), true);
        chessboard[4][7] = new King(WHITE, new Position((byte)4,(byte)7), true);
        chessboard[3][7] = new Queen(WHITE, new Position((byte)3, (byte)7), true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        drawGrid(g);
        drawCoordinates(g);
        //drawing the pieces
        for(int i = 0; i < 8; i++) {
            for (int y = 0; y < 8; y++) {
                if(chessboard[i][y] != null)
                    chessboard[i][y].draw(g);
            }
        }
        drawSelectionSquare(g);
        drawMoveIndicator(g);
    }

    private void drawGrid(Graphics g) {
        super.paintComponent(g);

        final int MAX_RANKS = 7; //row
        final int MAX_FILES = 3; //column

        int yCoord = 50;
        int xCoord = 50;
        int count = 0;

        //This is the for loop to draw the green squares in toDrawArr chessboard pattern
        g.setColor(new Color(153, 77, 0));
        for (int i = 0; i <= MAX_RANKS; i++) {
            for (int j = 0; j <= MAX_FILES; j++) {
                g.fillRect(xCoord, yCoord, 60, 60);

                xCoord += 120;
            }
            xCoord = 110;

            if (count == 1) {
                xCoord = 50;
                count = 0;
            }
            if (xCoord == 110)
                count = 1;
            yCoord += 60;
        }

        //These variables set the coordinates for the gray squares
        int xCoord2 = 110;
        int yCoord2 = 50;
        int count2 = 0;

        //This is the for loop to draw the gray squares in toDrawArr chessboard pattern
        g.setColor(new Color(255, 194, 102));
        for (int i = 0; i <= MAX_RANKS; i++) {
            for (int j = 0; j <= MAX_FILES; j++) {
                g.fillRect(xCoord2, yCoord2, 60, 60);

                xCoord2 += 120;
            }
            xCoord2 = 50;
            if (count2 == 1) {
                xCoord2 = 110;
                count2 = 0;
            }
            if (xCoord2 == 50)
                count2 = 1;
            yCoord2 += 60;
        }

        int Ax1, Ay1, Ax2, Ay2, Bx1, By1, Bx2, By2;
        Ax1 = 50;Ay1 = 50;Ax2 = 50;Ay2 = 530;Bx1 = 50;By1 = 50;Bx2 = 530;By2 = 50;
        g.setColor(Color.black);
        for (int i = 0; i <= 8; i++) {
            g.drawLine(Ax1, Ay1, Ax2, Ay2);
            g.drawLine(Bx1, By1, Bx2, By2);
            Ax1 += 60;
            Ax2 += 60;
            By1 += 60;
            By2 += 60;
        }
    }

    private void drawCoordinates(Graphics g) {

        String[] files = new String[]{"A","B","C","D","E","F","G","H"};
        String[] ranks = new String[]{"8","7","6","5","4","3","2","1"};

        Font font = new Font("LucidaSans", Font.PLAIN, 17);
        g.setFont(font);

        int player = 0;

        int filesX = 75;
        int filesY1 = 550;
        int filesY2 = 45;

        int ranksX1 = 35;
        int ranksX2 = 535;
        int ranksY = 85;

        if (player == 0 ){
        }

        if(player == 1){
            List<String> filelist = Arrays.asList(files);
            Collections.reverse(filelist);
            files = (String[]) filelist.toArray();

            List<String> rankslist = Arrays.asList(ranks);
            Collections.reverse(rankslist);
            ranks = (String[]) rankslist.toArray();
        }

        for(int i = 0; i < 8; i ++) {
            g.drawString(files[i], filesX, filesY1);
            g.drawString(files[i], filesX, filesY2);
            filesX+=60;
        }
        for(int i = 0; i < 8; i++){
            g.drawString(ranks[i], ranksX1, ranksY);
            g.drawString(ranks[i], ranksX2, ranksY);
            ranksY+=60;
        }
    }

    private void drawSelectionSquare(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g.setColor(Color.yellow);
        if(pieceisSelected == true){
            g.drawRect((selectedX*60)+boardBufferX,(selectedY*60)+boardBufferY,60,60);
        }
    }

    private void drawMoveIndicator(Graphics g){
        if(pieceisSelected == true){
            toDrawArr = filterMoves(selectionPiece, chessboard);
            for(int i = 0; i < toDrawArr.size(); i++){
                g.setColor(new Color(255,255,255));
                g.drawRect(toDrawArr.get(i).returnX()*60 +boardBufferX, toDrawArr.get(i).returnY()*60 +boardBufferY, 60, 60);
            }
        }
    }

    private boolean moveToSquare(Piece p, byte selectedX, byte selectedY, Piece[][] board, boolean force, boolean isAI){
        boolean isLegalMove = false, castled = false;
        moveTo.setX(selectedX);
        moveTo.setY(selectedY);
        byte posX = p.getGridX(); //the original position of the piece
        byte posY = p.getGridY(); //the original position of the piece

        if(moveTo.equals(new Position(p.getGridX(), p.getGridY())) == false) {
            for (Position pos : filterMoves(p, board)) {
                if (pos.equals(moveTo)) {
                    isLegalMove = true;
                }
            }

            //check if the king is castling, if so, then move the rook as well
            if(isAI == false){
                CastleKing(p, board);
            }

            //checking if the king is in check

            if (isLegalMove || force == true) {
                board[p.getGridX()][p.getGridY()] = null;
                p.move(moveTo);
                board[selectedX][selectedY] = p;

                p.setFirstMove(false);
            }
        }

        return isLegalMove;
    }

    private boolean CastleKing(Piece p, Piece[][] board){
        boolean castled = true;
        if (p instanceof King){
            if (moveTo.equals(new Position((byte)(p.getGridX()+2), p.getGridY()))) { //if the king is being moved over by two positions in either way.
                ((King) p).setCastled(true);
                Position pos = new Position((byte)(p.getGridX() + 1), p.getGridY());

                board[p.getGridX() + 1][p.getGridY()] = board[p.getGridX() + 3][p.getGridY()];
                board[p.getGridX() + 3][p.getGridY()].move(pos);
                board[p.getGridX() + 3][p.getGridY()] = null;
            }
            if (moveTo.equals(new Position((byte)(p.getGridX()-2), p.getGridY()))){
                ((King) p).setCastled(true);
                Position pos = new Position((byte)(p.getGridX() - 1), p.getGridY());

                board[p.getGridX() - 1][p.getGridY()] = board[p.getGridX() - 4][p.getGridY()];
                board[p.getGridX() - 4][p.getGridY()].move(pos);
                board[p.getGridX() - 4][p.getGridY()] = null;
            }
        }
        return castled;
    }

    private ArrayList<Position> filterMoves(Piece p, Piece[][] board) {
        int allmoves = 0;
        int removedxy = 0;
        int samecolor = 0;
        int obstructed = 0;
        int castling = 0;
        int pawned = 0;

        possibleMovesArr = p.calcPossibleMoves();
        allmoves = possibleMovesArr.size();

        for (int i = 0; i < possibleMovesArr.size(); i++) {
            //if the x value is off the board
            if (possibleMovesArr.get(i).returnX() > 7 || possibleMovesArr.get(i).returnX() < 0) {
                possibleMovesArr.remove(i);
                removedxy++;
                i--;

                continue;
            }
            //if the y value is off the board
            if (possibleMovesArr.get(i).returnY() > 7 || possibleMovesArr.get(i).returnY() < 0) {
                possibleMovesArr.remove(i);
                removedxy++;
                i--;

                continue;
            }
            //if the color is the same
            if (board[possibleMovesArr.get(i).returnX()][possibleMovesArr.get(i).returnY()] != null) {
                if (board[possibleMovesArr.get(i).returnX()][possibleMovesArr.get(i).returnY()].getColor() == p.getColor()) {
                    possibleMovesArr.remove(i);
                    samecolor++;
                    i--;

                    continue;
                }
            }

            //checking for obstructions in path (from the intended position to the piece)
            if (p instanceof Knight == false) {
                int dirX = 0, dirY = 0, looper;
                if (p.getGridX() < possibleMovesArr.get(i).returnX()) {
                    dirX = -1;
                }
                if (p.getGridX() > possibleMovesArr.get(i).returnX()) {
                    dirX = +1;
                }
                if (p.getGridX() == possibleMovesArr.get(i).returnX()) {
                    dirX = 0;
                }
                if (p.getGridY() < possibleMovesArr.get(i).returnY()) {
                    dirY = -1;
                }
                if (p.getGridY() > possibleMovesArr.get(i).returnY()) {
                    dirY = +1;
                }
                if (p.getGridY() == possibleMovesArr.get(i).returnY()) {
                    dirY = 0;
                }

                //determining how many times to loop
                if (Math.abs(p.getGridX() - possibleMovesArr.get(i).returnX()) > Math.abs(p.getGridY() - possibleMovesArr.get(i).returnY())) {
                    looper = Math.abs(p.getGridX() - possibleMovesArr.get(i).returnX());
                } else { //either the y distance is greater or they are equal
                    looper = Math.abs(p.getGridY() - possibleMovesArr.get(i).returnY());
                }

                //y has beened changed from 0 to 1
                int y;
                if (p instanceof Pawn) {
                    y = 0;
                } else {
                    y = 1;
                }
                for (int w = y; w < looper; w++) {
                    if (board[possibleMovesArr.get(i).returnX() + dirX * w][possibleMovesArr.get(i).returnY() + dirY * w] != null && board[possibleMovesArr.get(i).returnX() + dirX * w][possibleMovesArr.get(i).returnY() + dirY * w].equals(p) == false) {
                        possibleMovesArr.remove(i);
                        obstructed++;
                        i--;
                        break;
                    }
                }

            }
        }

        //Checking for castling possibilities
        if (p instanceof King){
            if(((King) p).getFirstMove() == true){
                int dirX1 = +1, dirX2 = -1;
                boolean pathIsClear1 = false, pathIsClear2 = false;
                for(int w = 1; w < 3; w++){ //check first direction (to the right and look for pieces blocking the path)
                    if(board[p.getGridX()+dirX1*w][p.getGridY()] != null){
                        pathIsClear1 = false;
                        break;
                    } else {
                        pathIsClear1 = true;
                    }
                }
                for(int z = 1; z < 4; z++){ //check second direction (to the left and look for pieces blocking the path)
                    if(board[p.getGridX()+dirX2*z][p.getGridY()] != null){
                        pathIsClear2 = false;
                        break;
                    } else {
                        pathIsClear2 = true;
                    }
                }
                if(pathIsClear1){
                    //if the path is clear, then we can add the extra move to the list of possible moves for the king.
                    possibleMovesArr.add(new Position((byte)(p.getGridX() +2), p.getGridY()));
                    castling++;
                } if (pathIsClear2){
                    possibleMovesArr.add(new Position((byte)(p.getGridX()-2), p.getGridY()));
                    castling++;
                }
            }
        }


        //also making sure the rook hasn't moved yet
        //if((Rook)chessboard[p.getGridX()+2][p.getGridY()].get){

        //}
        //Pawn Capturing
        if(p instanceof Pawn){
            if(p.getColor() == WHITE){
                if (p.getGridX() > 0 && p.getGridY() > 0){
                    if(board[p.getGridX() -1][p.getGridY() -1] != null){
                        if(board[p.getGridX() -1][p.getGridY() -1].getColor() != p.getColor()){
                            pawned++;
                            possibleMovesArr.add(new Position((byte)(p.getGridX() -1),(byte)(p.getGridY() -1)));
                        }
                    }
                }
                if(p.getGridX() < 7 && p.getGridY() > 0){
                    if(board[p.getGridX() + 1][p.getGridY() -1] != null){
                        if(board[p.getGridX() +1][p.getGridY() -1].getColor() != p.getColor()){
                            pawned++;
                            possibleMovesArr.add(new Position((byte)(p.getGridX() +1),(byte)(p.getGridY() -1)));
                        }
                    }
                }
            }
            if(p.getColor() == BLACK){
                if(p.getGridX() > 0 && p.getGridY() < 7){
                    if(board[p.getGridX() -1][p.getGridY() +1]!= null){
                        if(board[p.getGridX() -1][p.getGridY() +1].getColor() != p.getColor()){
                            pawned++;
                            possibleMovesArr.add(new Position((byte)(p.getGridX() -1),(byte)(p.getGridY() +1)));
                        }
                    }
                }
                if(p.getGridX() < 7 && p.getGridY() < 7){
                    if(board[p.getGridX() + 1][p.getGridY() +1] != null){
                        if(board[p.getGridX() +1][p.getGridY() +1].getColor() != p.getColor()){
                            pawned++;
                            possibleMovesArr.add(new Position((byte)(p.getGridX() +1),(byte)(p.getGridY() +1)));
                        }
                    }
                }
            }
        }

        return possibleMovesArr;
    }

    private boolean checkForCheck(int colour, Piece[][] board){ //should take in toDrawArr colour as toDrawArr parameter to check for that specific side
        boolean check = false;

        byte kingX = 0, kingY = 0;
        //BkingCheck = false; WkingCheck = false;
        //find the king
        for(Piece[] pp: board) {
            for (Piece p : pp) {
                if(p != null){
                    if(p instanceof King == true && p.getColor() == colour){
                        kingX = p.getGridX();
                        kingY = p.getGridY();
                        break;
                    }
                }
            }
        }

        for(Piece[] pp: board){
            for(Piece p : pp){
                if(p != null && p.getColor() != colour){
                    for(Position i: filterMoves(p, board)){
                        if(i.equals(new Position(kingX, kingY))) { //if the king is in check
                            if(colour == WHITE){check = true;}
                            if(colour == BLACK){check = true;}
                        }
                    }
                }
            }
        }

        return check;
    }

    public ArrayList<Piece[][]> getAllBoards(int color, Piece[][] board){
        ArrayList<Piece[][]> boards = new ArrayList<>();

        //find all the possible board one move from the current board
        for(int x = 0; x < 8; x ++){
            for(int y =0; y < 8; y++){
                Piece p = board[x][y];
                if(p != null && p.getColor() == color){
                    for(Position i: filterMoves(p, board)){
                        Piece[][] copy1 = createNewBoard(board);
                        if (moveToSquare(copy1[x][y], i.returnX(), i.returnY(), copy1, false, true) == true) {
                            boards.add(copy1);
                        }
                    }
                }
            }
        }

        return boards;
    }

    private Piece[][] createNewBoard(Piece[][] board){
        Piece[][] copy = new Piece[8][8];
        for(int i = 0;i < 8; i++){
            for(int y = 0 ; y < 8; y++){
                if(board[i][y] != null){
                    Piece temp = board[i][y];
                    byte color = temp.getColor();
                    byte posX = temp.getGridX();
                    byte posY = temp.getGridY();
                    int type = temp.getType();
                    boolean isFirstMove = temp.getFirstMove();
                    //create toDrawArr similar piece
                    if(type == 0){ copy[i][y] = new Pawn(color, new Position(posX, posY), isFirstMove);}
                    else if(type == 1){ copy[i][y] = new Rook(color, new Position(posX, posY), isFirstMove);}
                    else if(type == 2){ copy[i][y] = new Knight(color, new Position(posX, posY), isFirstMove);}
                    else if(type == 3){ copy[i][y] = new Bishop(color, new Position(posX, posY), isFirstMove);}
                    else if(type == 4){ copy[i][y] = new King(color, new Position(posX, posY), isFirstMove);}
                    else if(type == 5){ copy[i][y] = new Queen(color, new Position(posX, posY), isFirstMove);}
                }
            }
        }

        return copy;
    }

    private boolean detectMiddleGame(){
        //the opening is defined to be at the beginning, and ends when the majority of the pieces have been developed.
        //knight and bishop have developed, most pawns have developed.
        //rooks and queen don't necesarily have to be moved.
        //the king has castled
        //50% of the criteria has to be filled, to proceed to midgame
        byte totalFlags = 26;
        byte checkSum = 0;
        int minorpiececount = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = chessboard[x][y];
                if (p != null) {
                    //check if the bishop or knight has moved
                    if (p instanceof Knight || p instanceof Bishop) {
                        minorpiececount ++;
                        if (p.getFirstMove() == false) { //it has moved
                            checkSum += 1;
                        }
                    }
                    //if the king has castled
                    if (p instanceof King) {
                        if (((King) p).getCastled() == true) {
                            checkSum += 1;
                        }
                    }
                    //check is some pawns have developed.
                    if (p instanceof Pawn) {
                        if (((Pawn) p).isFirstMove == false) { //the pawn has moved
                            minorpiececount++;
                            checkSum += 1;
                        }
                    }
                }
            }
        }

        //alternative (if pieces are traded off right at the beginning, then the mid-game starts)
        if(minorpiececount < 7){
            midgame = true;
            opening = false;
            return midgame;
        }

        //if most of the criteria is met, we are now in the midgame, or else, we are still in the opening.
        if ((((double) checkSum / (double) totalFlags) * 100)>50){
            midgame = true;
            opening = false;
        } else {
            opening = true;
            midgame = false;
        }

        return midgame;
    }

    private boolean detectEndGame(){
        //to define the endgame, most pieces must be gone, and this is when the king will come out to play.
        //this method will use piece points detection to determine whether we are in the endgame.
        //4 pawns on each side, one king, either one bishop, knigh or rook, and one queen (optional)

        int Bpoints = 0;
        int Wpoints = 0;
        for(int x = 0; x < 8; x ++){
            for(int y = 0; y< 8; y ++){
                Piece p = chessboard[x][y];
                if(p != null){
                    //exclude counting king points
                    if(p.getColor() == BLACK && p instanceof King == false){
                        Bpoints += p.getValue();
                    } else if (p.getColor() == WHITE && p instanceof King == false){
                        Wpoints += p.getValue();
                    }
                }
            }
        }

        //16 points with 4 pawns, 1 bishop/knight, 1 queen
        //18 points with 4 pawns, 1 rook, 1 queen
        // 13 points with 4 pawns and 3 minor pieces (bishop / knight)
        // 15 points with 4 pawns, 2 bishop/knight, 1 rook
        if(Bpoints < 15 || Wpoints < 15){
            endgame = true;
            midgame = false;
        }
        return true;
    }

    private class ClickListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(calculating == false){
                mouseX = (short)e.getX();
                mouseY = (short)e.getY();
                float rawX = (float)((mouseX - boardBufferX)/60);
                float rawY = (float)((mouseY - boardBufferY)/60);

                if(Math.floor(rawX) >= 0 && Math.floor(rawX) < 8 && Math.floor(rawY) >= 0 && Math.floor(rawY) < 8){
                    selectedX = (byte)(Math.floor(rawX));
                    selectedY = (byte)(Math.floor(rawY));
                }

                outerloop:
                if(pieceisSelected == true){
                    if(turnIsWhite){
                        byte posX = selectionPiece.getGridX();
                        byte posY = selectionPiece.getGridY();
                        if(selectionPiece.getColor() == WHITE){
                            Wcheck = checkForCheck(WHITE, chessboard);
                            if (moveToSquare(selectionPiece, selectedX, selectedY, chessboard, false, false) == true){
                                Wcheck = checkForCheck(WHITE, chessboard);
                                if(Wcheck) {//move the piece back
                                    boolean moved;
                                    moved = moveToSquare(selectionPiece, posX, posY, chessboard, true, false);
                                    repaint();
                                    break outerloop;
                                }
                                switchTurn();
                            } else {
                                break outerloop;
                            }
                        }
                    }

                    if (turnIsWhite == false) {
                        //System.out.println("AIPlayer is playing? "+ aiPlayerThread.isAlive()); System.out.flush();
                    }
                    //two players
                    /*else if(turnIsWhite == false){
                        checkForCheck(BLACK,  chessboard);
                        if(selectionPiece.getColor() == BLACK){
                            if(moveToSquare(selectionPiece, selectedX, selectedY, chessboard, false)==true){
                                switchTurn();
                            }
                        }
                    }*/
                }

                //unselect the piece
                pieceisSelected = false;
                selectionPiece = null;
                if(pieceisSelected == false){
                    selectionPiece = chessboard[(int)selectedX][(int)selectedY];
                    if(selectionPiece == null){//no piece has really been selected.
                        pieceisSelected = false;
                    } else{ //toDrawArr piece has really been selected
                        pieceisSelected = true;
                    }
                }
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class MotionListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    public void UndoMove(){
        if(calculating == false){
            if(boardStack.size()> 0) {
                //turnIsWhite = !turnIsWhite;
                if (turnIsWhite){
                    boardStack.pop();
                    boardStack.pop();
                }

                if(boardStack.size() > 0){
                    chessboard = boardStack.peek();
                } else {
                    setUpBoard();
                }

                //
                //if(turnIsWhite){ System.out.println("It is white's turn");} else { System.out.println("It is black's turn");}
            }
            repaint();
        }
    }

    public void switchTurn(){
        repaint();

        if(turnIsWhite && checkForCheck(WHITE, chessboard) == true){
            //white wins
            JOptionPane.showMessageDialog(null, "BLACK WINS", "In Game Message", JOptionPane.ERROR_MESSAGE);
            setUpBoard();
            turnIsWhite = true;

        } else if (turnIsWhite == false && checkForCheck(BLACK, chessboard) == true){
            //black wins
            JOptionPane.showMessageDialog(null, "WHITE WINS", "In Game Message", JOptionPane.ERROR_MESSAGE);
            setUpBoard();
            turnIsWhite = true;

        }
        turnIsWhite = !turnIsWhite;

        Piece[][] temp = createNewBoard(chessboard);
        boardStack.push(temp);
    }

    public int EvaluateBoard(Piece[][] board){
        int rating = 0;

        //material value
        for(int x = 0; x < 8; x ++){
            for(int y = 0; y< 8; y++){
                if(board[x][y] != null){
                    if(board[x][y].getColor() == WHITE) {
                        rating += board[x][y].getValue();
                    } else if(board[x][y].getColor() == BLACK){
                        rating -= board[x][y].getValue();
                    }
                }
            }
        }
        //Movability for major pieces
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if (board[x][y] != null) {
                    if(board[x][y] instanceof Knight || board[x][y] instanceof Bishop || board[x][y] instanceof Rook ||board[x][y] instanceof Queen){
                        if(board[x][y].getColor() == WHITE) {
                            rating += filterMoves(board[x][y], board).size();
                        } else if(board[x][y].getColor() == BLACK){
                            rating -= filterMoves(board[x][y], board).size();
                        }
                    }
                }
            }
        }

        //piece square implementation
        for(int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if(p instanceof King){
                    if(endgame == true){
                        //king is better in the middle of the board
                        if(x > 1 && x < 6){
                            if(p.getColor() == WHITE)
                                rating += 10;
                            else if (p.getColor() == BLACK)
                                rating -= 10;
                        }
                    }
                    if(midgame == true || opening == true){
                        if(((King) p).getCastled() == true){
                            if(p.getColor() == WHITE)
                                rating += 10;
                            else if (p.getColor() == BLACK)
                                rating -= 10;
                        }
                    }
                }
            }
        }

        Wcheck = checkForCheck(WHITE, board);
        Bcheck = checkForCheck(BLACK, board);
        if(Wcheck){
            rating -= 100;
        }
        if(Bcheck){
            rating += 100;
        }

        //only detect midgame if we are in the opening
        if (opening == true){
            detectMiddleGame();
        }
        if(midgame == true){
            //check if we are in the endgame
            detectEndGame();
        }

        return rating;
    }

    private class AIPlayer implements Runnable {
        private ChessDisplay father;
        public void run() {
            while (true) {
                if (!turnIsWhite) {
                    calculating = true;
                    //System.out.println("AIPlayer starting.."); System.out.flush();

                    chessboard = AI.miniMax(father);

                    //System.out.println("AIPlayer done."); System.out.flush();

                    switchTurn();
                    calculating = false;

                } else {
                    // it is human's turn. Just wait until turns switch
                    try { Thread.sleep(100); }
                    catch (InterruptedException e) {
                        // nothing
                    }
                }
            }
        } // run
        public AIPlayer(ChessDisplay dad){
            father = dad;
        }

    }
}