package game;

import java.util.*;

public class Board {

    private final List<Piece> pieces;
    private final HashMap<Color, Player> players;
    private Color move;

    public Board(Player player1, Player player2) {

        // default state of board
        pieces = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 4; y++) {
                if (x % 2 == y % 2) {
                    pieces.add(new Piece(Color.WHITE, x, y, this));
                    pieces.add(new Piece(Color.BLACK, 9 - x, 9 - y, this));
                }
            }
        }

        this.players = new HashMap<>();
        this.players.put(Color.WHITE, player1);
        this.players.put(Color.BLACK, player2);
        this.move = Color.WHITE;
    }

    public Board(List<Piece> pieces, Player player1, Player player2) {
        this.pieces = pieces;
        this.players = new HashMap<>();
        this.players.put(Color.WHITE, player1);
        this.players.put(Color.BLACK, player2);
        this.move = Color.WHITE;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public HashMap<Color, Player> getPlayers() {
        return players;
    }

    public Color getMove() {
        return move;
    }

    public void switchMove() {
        if (this.move == Color.WHITE) {
            this.move = Color.BLACK;
        } else {
            this.move = Color.WHITE;
        }
    }

    public boolean isPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the piece at coordinates x, y
     * @param x coordinate
     * @param y coordinate
     * @return the piece
     * @throws IllegalArgumentException if there is no piece at these coordinates
     */
    public Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        throw new IllegalArgumentException();
    }

    public void removePieceAt(int x, int y) {
        int pieceIndex = 0;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getX() == x && pieces.get(i).getY() == y) {
                pieceIndex = i;
            }
        }
        pieces.remove(pieceIndex);
    }


    /* Utils */
    public static boolean isPieceAt(ArrayList<Piece> pieces, int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the piece at coordinates x, y
     * @param x coordinate
     * @param y coordinate
     * @return the piece
     * @throws IllegalArgumentException if there is no piece at these coordinates
     */
    public static Piece getPieceAt(ArrayList<Piece> pieces, int x, int y) {
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void removePieceAt(ArrayList<Piece> pieces, int x, int y) {
        int pieceIndex = 0;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getX() == x && pieces.get(i).getY() == y) {
                pieceIndex = i;
            }
        }
        pieces.remove(pieceIndex);
    }
}
