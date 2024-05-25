package game;

import java.util.*;

public class Board {

    private final List<Piece> pieces;
    private final HashMap<Color, Player> players;
    private Color move;

    private Piece selectedPiece;

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

    public Piece getSelectedPiece() {
        return this.selectedPiece;
    }

    public void selectPiece(Piece piece) {
        this.selectedPiece = piece;
    }

    public void move(Piece piece, List<String> list) {
        for (String pos : Board.getDiagonalPos(
                piece.getX(),
                piece.getY(),
                Integer.parseInt(list.getFirst().split(",")[0]),
                Integer.parseInt(list.getFirst().split(",")[1]))) {
            int newX = Integer.parseInt(pos.split(",")[0]);
            int newY = Integer.parseInt(pos.split(",")[1]);
            if (isPieceAt(newX, newY) &&getPieceAt(newX, newY).getColor() != piece.getColor()) {
                removePieceAt(newX, newY);
            }

        }
        for (int i = 1; i < list.size(); i++) {
            int x1 = Integer.parseInt(list.get(i-1).split(",")[0]);
            int y1 = Integer.parseInt(list.get(i-1).split(",")[1]);
            int x2 = Integer.parseInt(list.get(i).split(",")[0]);
            int y2 = Integer.parseInt(list.get(i).split(",")[1]);
            for (String pos : Board.getDiagonalPos(x1, y1, x2, y2)) {
                int newX = Integer.parseInt(pos.split(",")[0]);
                int newY = Integer.parseInt(pos.split(",")[1]);
                if (isPieceAt(newX, newY) && getPieceAt(newX, newY).getColor() != piece.getColor()) {
                    removePieceAt(newX, newY);
                }
            }
        }
        piece.setPos(list.getLast());
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

    public HashMap<Piece, List<List<String>>> getAllowedMoves(Color color) {
        boolean eat = false;
        /* check if at least one piece can eat */
        for (Piece piece : getPieces()) {
            if (piece.getColor() == color && piece.canEat()) {
                eat = true;
            }
        }

        HashMap<Piece, List<List<String>>> moves = new HashMap<>();
        if (eat) {
            for (Piece piece : getPieces()) {
                if (piece.getColor() == color && piece.canEat()) {
                    System.out.println("Jump list" + piece.getJumpList());
                    System.out.println("All Paths" + piece.getAllPaths(piece.getJumpList()));
                    System.out.println("Longest Path" + piece.getLongestPath(piece.getAllPaths(piece.getJumpList())));
                    moves.put(piece, piece.getLongestPath(piece.getAllPaths(piece.getJumpList())));
                }
            }
        } else {
            for (Piece piece : getPieces()) {
                if (piece.getColor() == color && !piece.getMoveList().isEmpty()) {
                    moves.put(piece, piece.getMoveList());
                }
            }
        }

        return moves;
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


    public static List<String> getDiagonalPos(int x1, int y1, int x2, int y2) {
        if (Math.abs(y2 - y1) == Math.abs(x2 - x1)) {

            List<String> pos = new ArrayList<>();

            int startX = Math.min(x1, x2);
            int endX = Math.max(x1, x2);

            if ((y2 - y1) == (x2 - x1)) {
                // Diagonale descendante
                int c = y1 - x1;
                for (int x = startX; x <= endX; x++) {
                    int y = x + c;
                    pos.add(x + "," + y);
                }
            } else {
                // Diagonale ascendante
                int c = y1 + x1;
                for (int x = startX; x <= endX; x++) {
                    int y = -x + c;
                    pos.add(x + "," + y);
                }
            }
            return pos;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
