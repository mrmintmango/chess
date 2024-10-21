package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenParent {

    ChessGame.TeamColor teamColor;
    public QueenParent(ChessGame.TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    public QueenParent() {

    }

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //Check up right move if bishop is closer to the top
        if (up < right) {
            moves.addAll(addMoves(up,1,1,myPosition,board));
        }
        //Check up right move if bishop is closer to the right
        if (up >= right) {
            moves.addAll(addMoves(right,1,1,myPosition,board));
        }
        //Check up left move if bishop is closer to the top
        if (up < left) {
            moves.addAll(addMoves(up,1,-1,myPosition,board));
        }
        //Check up left move if bishop is closer to the left
        if (left <= up) {
            moves.addAll(addMoves(left,1,-1,myPosition,board));
        }
        //Check down left move if bishop is closer to the bottom
        if (down < left) {
            moves.addAll(addMoves(down,-1,-1,myPosition,board));
        }
        //Check down left move if bishop is closer to the left
        if (left <= down) {
            moves.addAll(addMoves(left,-1,-1,myPosition,board));
        }
        //Check down right move if bishop is closer to the bottom
        if (down < right) {
            moves.addAll(addMoves(down,-1,1,myPosition,board));
        }
        //Check down right move if bishop is closer to the right
        if (right <= down) {
            moves.addAll(addMoves(right,-1,1,myPosition,board));
        }

        return moves;
    }

    public ArrayList<ChessMove> addMoves(int iter, int row, int col, ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (int i = 1; i <= iter; i++) {
            ChessPosition move = new ChessPosition(myPosition.getRow()+(row*i), myPosition.getColumn()+(col*i));
            if (board.getPiece(move) == null){
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
            }
            else if (board.getPiece(move).getTeamColor() != this.teamColor) {
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
                break;
            }
            else if (board.getPiece(move).getTeamColor() == this.teamColor) {
                break;
            }
        }
        return moves;
    }

    public Collection<ChessMove> dpadMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //check up
        moves.addAll(addMove(up,myPosition,board,1));
        //check down
        moves.addAll(addMove(down,myPosition,board,2));
        //check left
        moves.addAll(addMove(left,myPosition,board,3));
        //check Right
        moves.addAll(addMove(right,myPosition,board,4));

        return moves;
    }

    public ArrayList<ChessMove> addMove(int iter, ChessPosition myPosition, ChessBoard board, int direction) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (int i = 1; i <= iter; i++) {
            ChessPosition oneUp = getChessPosition(myPosition, i, direction);
            if (board.getPiece(oneUp) == null) {
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() == teamColor) {
                break;
            }
        }
        return moves;
    }

    public ChessPosition getChessPosition(ChessPosition myPosition, int iter, int direction) {
        if (direction == 1){
            return new ChessPosition(myPosition.getRow()+iter, myPosition.getColumn());
        }
        else if (direction == 2){
            return new ChessPosition(myPosition.getRow()-iter, myPosition.getColumn());
        }
        else if (direction == 3){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()-iter);
        }
        else if (direction == 4){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()+iter);
        }
        else {
            return null;
        }
    }
}
