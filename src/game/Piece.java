package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Piece {

    private final Color color;
    private int x;
    private int y;
    private boolean crown;
    private final Board board;

    public Piece(Color color, int x, int y, Board board) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.crown = false;
        this.board = board;
    }

    public Piece(Color color, int x, int y, boolean crown, Board board) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.crown = crown;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(String xy) {
        String[] pos = xy.split(",");
        this.x = Integer.parseInt(pos[0]);
        this.y = Integer.parseInt(pos[1]);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isCrown() {
        return crown;
    }

    public void setCrown(boolean crown) {
        this.crown = crown;
    }

    public ArrayList<String> getMoveList() {
        ArrayList<String> list = new ArrayList<>();
        int newY;
        if (color == Color.WHITE) {
            newY = y + 1;
        } else {
            newY = y - 1;
        }
        if (newY >= 0 && newY <= 10) {
            if (x + 1 <= 10 && !board.isPieceAt(x + 1, newY)) {
                list.add(positionToString(x + 1, newY));
            }
            if (x - 1 >= 0 && !board.isPieceAt(x - 1, newY)) {
                list.add(positionToString(x - 1, newY));
            }
        }
        return list;
    }

    public HashMap getJumpList() {
        HashMap map = new HashMap<>();
        System.out.println(Arrays.toString(board.getPieces().toArray()));
        ArrayList<Piece> pieces = new ArrayList<>(this.board.getPieces()); // clone the original board
        getJumpList(pieces, this.getX(), this.getY(), map);
        return map;
    }

    private void getJumpList(ArrayList<Piece> pieces, int x, int y, HashMap map) {

        if (   Board.isPieceAt(pieces, x + 1, y - 1) // there is a piece in diagonal
            && Board.getPieceAt(pieces,x + 1, y - 1).getColor() != this.getColor() // the color is not the same
            && !Board.isPieceAt(pieces, x + 2, y - 2) // there is an empty space after the piece
            && x + 1 <= 8 && y - 1 >= 1) // the coordinates are in the board
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x + 1, y - 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x + 2, y - 2), pos);
            getJumpList(newPieces, x + 2, y - 2, pos);
        }
        if (   Board.isPieceAt(pieces, x + 1, y + 1)
            && Board.getPieceAt(pieces, x + 1, y + 1).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x + 2, y + 2)
            && x + 1 <= 8 && y + 1 <= 8)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x + 1, y + 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x + 2, y + 2), pos);
            getJumpList(newPieces, x + 2, y + 2, pos);
        }
        if (   Board.isPieceAt(pieces, x - 1, y - 1)
            && Board.getPieceAt(pieces, x - 1, y - 1).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x - 2, y - 2)
            && x - 1 >= 1 && y - 1 >= 1)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x - 1, y - 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x - 2, y - 2), pos);
            getJumpList(newPieces, x - 2, y - 2, pos);
        }
        if (   Board.isPieceAt(pieces, x - 1, y + 1)
            && Board.getPieceAt(pieces, x - 1, y + 1).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x - 2, y + 2)
            && x - 1 >= 1 && y + 1 <= 8)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x - 1, y + 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x - 2, y + 2), pos);
            getJumpList(newPieces, x - 2, y + 2, pos);
        }

    }

    public boolean canEat() {

        return     board.isPieceAt(x + 1, y - 1) // there is a piece in diagonal
                && board.getPieceAt(x + 1, y - 1).getColor() != this.getColor() // the color is not the same
                && !board.isPieceAt(x + 2, y - 2) // there is an empty space after the piece
                && x + 1 <= 8 && y - 1 >= 1 // the coordinates are in the board
                || board.isPieceAt(x + 1, y + 1)
                && board.getPieceAt(x + 1, y + 1).getColor() != this.getColor()
                && !board.isPieceAt(x + 2, y + 2)
                && x + 1 <= 8 && y + 1 <= 8
                || board.isPieceAt(x - 1, y - 1)
                && board.getPieceAt(x - 1, y - 1).getColor() != this.getColor()
                && !board.isPieceAt(x - 2, y - 2)
                && x - 1 >= 1 && y - 1 >= 1
                || board.isPieceAt(x - 1, y + 1)
                && board.getPieceAt(x - 1, y + 1).getColor() != this.getColor()
                && !board.isPieceAt(x - 2, y + 2)
                && x - 1 >= 1 && y + 1 <= 8;
    }

    private static String positionToString(int x, int y) {
        return x + "," + y;
    }
}
