package Computational;

import pieces.*;
import java.util.ArrayList;

public class ChessAI{

    public Piece[][] miniMax(ChessDisplay game){
        int lowestScore = Integer.MAX_VALUE;
        Piece[][] bestMove = null;

        ArrayList<Piece[][]> moves = game.getAllBoards(1, game.chessboard);
        int score = 0;
        for (Piece[][] move : moves) {
            score = min(move, game, 3, game.BLACK, -10000, 10000);
            if (score < lowestScore) {
                lowestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public int min(Piece[][] board, ChessDisplay game, int depth, byte color, int alpha, int beta){
        if(depth == 0){
            return game.EvaluateBoard(board);
        }
        int highestScore = Integer.MAX_VALUE;

        byte next_color;
        if (color == game.WHITE) {
            next_color = game.BLACK;
        } else {
            next_color = game.WHITE;
        }

        ArrayList<Piece[][]> moves = game.getAllBoards(color, board); //creates the children nodes
        int score = 0;
        for (Piece[][] move : moves) {
            score = max(move, game, depth -1, next_color, alpha, beta);
            if(score <= alpha){
                return alpha;
            }
            if(score < beta){
                beta = score;
            }
            highestScore = beta;
        }

        return highestScore;
    }

    public int max(Piece[][] board, ChessDisplay game, int depth, byte color, int alpha, int beta){
        if (depth == 0) {
            return game.EvaluateBoard(board);
        }
        int lowestScore = Integer.MIN_VALUE;

        byte next_color;
        if (color == game.WHITE) {
            next_color = game.BLACK;
        } else {
            next_color = game.WHITE;
        }

        ArrayList<Piece[][]> moves = game.getAllBoards(color, board);
        int score = 0;
        for (Piece[][] move : moves) {
            score = min(move, game, depth-1, next_color, alpha, beta);
            if(score >= beta){
                return beta;
            }
            if(score > alpha){
                alpha = score;
            }
            lowestScore = alpha;
        }

        return lowestScore;
    }
}
