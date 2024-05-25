package game;

import java.util.*;

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

    public List<List<String>> getMoveList() {
        List<List<String>> list = new ArrayList<>();
        int newY;
        if (color == Color.WHITE) {
            newY = y + 1;
        } else {
            newY = y - 1;
        }
        if (newY >= 0 && newY < 10) {
            if (x + 1 < 10 && !board.isPieceAt(x + 1, newY)) {
                List templist = new ArrayList<>();
                templist.add(positionToString(x + 1, newY));
                list.add(templist);
            }
            if (x - 1 >= 0 && !board.isPieceAt(x - 1, newY)) {
                List templist = new ArrayList<>();
                templist.add(positionToString(x - 1, newY));
                list.add(templist);
            }
        }

        return list;
    }

    public HashMap getJumpList() {
        HashMap map = new HashMap<>();
//        System.out.println(Arrays.toString(board.getPieces().toArray()));
        ArrayList<Piece> pieces = new ArrayList<>(this.board.getPieces()); // clone the original board
        getJumpList(pieces, this.getX(), this.getY(), map);
        return map;
    }

    private void getJumpList(ArrayList<Piece> pieces, int x, int y, HashMap map) {

        if (   Board.isPieceAt(pieces, x + 1, y - 1) // there is a piece in diagonal
                && Board.getPieceAt(pieces,x + 1, y - 1).getColor() != this.getColor() // the color is not the same
                && !Board.isPieceAt(pieces, x + 2, y - 2) // there is an empty space after the piece
                && x + 2 < 10 && y - 2 >= 0) // the coordinates are in the board
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
//            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x + 1, y - 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x + 2, y - 2), pos);
            getJumpList(newPieces, x + 2, y - 2, pos);
        }
        if (   Board.isPieceAt(pieces, x + 1, y + 1)
                && Board.getPieceAt(pieces, x + 1, y + 1).getColor() != this.getColor()
                && !Board.isPieceAt(pieces, x + 2, y + 2)
                && x + 2 < 10 && y + 2 < 10)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
//            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x + 1, y + 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x + 2, y + 2), pos);
            getJumpList(newPieces, x + 2, y + 2, pos);
        }
        if (   Board.isPieceAt(pieces, x - 1, y - 1)
                && Board.getPieceAt(pieces, x - 1, y - 1).getColor() != this.getColor()
                && !Board.isPieceAt(pieces, x - 2, y - 2)
                && x - 2 >= 0 && y - 2 >= 0)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
//            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x - 1, y - 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x - 2, y - 2), pos);
            getJumpList(newPieces, x - 2, y - 2, pos);
        }
        if (   Board.isPieceAt(pieces, x - 1, y + 1)
                && Board.getPieceAt(pieces, x - 1, y + 1).getColor() != this.getColor()
                && !Board.isPieceAt(pieces, x - 2, y + 2)
                && x - 2 >= 0 && y + 2 < 10)
        {
            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
//            System.out.println(Arrays.toString(newPieces.toArray()));
            Board.removePieceAt(newPieces, x - 1, y + 1);
            HashMap<String, HashMap> pos = new HashMap<>();
            map.put(positionToString(x - 2, y + 2), pos);
            getJumpList(newPieces, x - 2, y + 2, pos);
        }

    }

    public HashMap getJumpListCrown() {
        HashMap map = new HashMap<>();
//        System.out.println(Arrays.toString(board.getPieces().toArray()));
        ArrayList<Piece> pieces = new ArrayList<>(this.board.getPieces()); // clone the original board
        getJumpListCrown(pieces, this.getX(), this.getY(), map, null);
        return map;
    }

    public void getJumpListCrown(ArrayList<Piece> pieces, int x, int y, HashMap<String, HashMap> map, Direction lastDirection) {
        // Diagonale haut-gauche
        int i = 1;
        while (x + i < 10 && y - i >= 1 && !Board.isPieceAt(pieces, x + i, y - i) && lastDirection != Direction.NW) {
            if (Board.isPieceAt(pieces, x + i, y - i) && Board.getPieceAt(pieces, x + i, y - i).getColor() != this.getColor()) {
                int j = 1;
                while (x + i + j < 10 && y - i - j >= 1) {
                    if (!Board.isPieceAt(pieces, x + i + j, y - i - j)) {
                        ArrayList<Piece> newPieces = new ArrayList<>(pieces);
                        Board.removePieceAt(newPieces, x + i, y - i);
                        HashMap<String, HashMap> pos = new HashMap<>();
                        map.put(positionToString(x + i + j, y - i - j), pos);
                        getJumpListCrown(pieces, x + i + j, y - i - j, pos, Direction.NW);
                    } else {
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        // Diagonale haut-droite
        i = 1;
        while (x + i < 10 && y + i < 10 && !Board.isPieceAt(pieces,x + i, y + i) && lastDirection != Direction.NE) {
            if (Board.isPieceAt(pieces,x + i, y + i) && Board.getPieceAt(pieces,x + i, y + i).getColor() != this.getColor()) {
                int j = 1;
                while (x + i + j < 10 && y + i + j < 10) {
                    if (!Board.isPieceAt(pieces,x + i + j, y + i + j)) {
                        ArrayList<Piece> newPieces = new ArrayList<>(pieces);
                        Board.removePieceAt(newPieces, x + i, y + i);
                        HashMap<String, HashMap> pos = new HashMap<>();
                        map.put(positionToString(x + i + j, y + i + j), pos);
                        getJumpListCrown(pieces,x + i + j, y + i + j, pos, Direction.NE);
                    } else {
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        // Diagonale bas-gauche
        i = 1;
        while (x - i >= 1 && y - i >= 1 && !Board.isPieceAt(pieces, x - i, y - i)) {
            if (Board.isPieceAt(pieces, x - i, y - i) && Board.getPieceAt(pieces, x - i, y - i).getColor() != this.getColor() && lastDirection != Direction.SW) {
                int j = 1;
                while (x - i - j >= 1 && y - i - j >= 1) {
                    if (!board.isPieceAt(x - i - j, y - i - j)) {
                        ArrayList<Piece> newPieces = new ArrayList<>(pieces);
                        Board.removePieceAt(newPieces, x - i, y - i);
                        HashMap<String, HashMap> pos = new HashMap<>();
                        map.put(positionToString(x - i - j, y - i - j), pos);
                        getJumpListCrown(pieces, x - i - j, y - i - j, pos, Direction.SW);
                    } else {
                        break;
                    }
                    j++;
                }
            }
            i++;
        }

        // Diagonale bas-droite
        i = 1;
        while (x - i >= 1 && y + i < 10 && Board.isPieceAt(pieces, x - i, y + i)) {
            if (Board.isPieceAt(pieces, x - i, y + i) && Board.getPieceAt(pieces, x - i, y + i).getColor() != this.getColor() && lastDirection != Direction.SE) {
                int j = 1;
                while (x - i - j >= 1 && y + i + j < 10) {
                    if (!Board.isPieceAt(pieces, x - i - j, y + i + j)) {
                        ArrayList<Piece> newPieces = new ArrayList<>(pieces);
                        Board.removePieceAt(newPieces, x - i, y + i);
                        HashMap<String, HashMap> pos = new HashMap<>();
                        map.put(positionToString(x - i - j, y + i + j), pos);
                        getJumpListCrown(pieces, x - i - j, y + i + j, pos, Direction.SE);
                    } else {
                        break;
                    }
                    j++;
                }
            }
            i++;
        }
    }


    public boolean canEat() {

        return     board.isPieceAt(x + 1, y - 1) // there is a piece in diagonal
                && board.getPieceAt(x + 1, y - 1).getColor() != this.getColor() // the color is not the same
                && !board.isPieceAt(x + 2, y - 2) // there is an empty space after the piece
                && x + 2 < 10 && y - 2 >= 0 // the coordinates are in the board
                || board.isPieceAt(x + 1, y + 1)
                && board.getPieceAt(x + 1, y + 1).getColor() != this.getColor()
                && !board.isPieceAt(x + 2, y + 2)
                && x + 2 < 10 && y + 2 < 10
                || board.isPieceAt(x - 1, y - 1)
                && board.getPieceAt(x - 1, y - 1).getColor() != this.getColor()
                && !board.isPieceAt(x - 2, y - 2)
                && x - 2 >= 0 && y - 2 >= 0
                || board.isPieceAt(x - 1, y + 1)
                && board.getPieceAt(x - 1, y + 1).getColor() != this.getColor()
                && !board.isPieceAt(x - 2, y + 2)
                && x - 2 >= 0 && y + 2 < 10;
    }

    private static String positionToString(int x, int y) {
        return x + "," + y;
    }



    public List<List<String>> getAllPaths(HashMap<?, ?> map) {
        List<List<String>> paths = new ArrayList<>();
        getAllPathsHelper(map, new ArrayList<>(), paths);
        return paths;
    }

    // Méthode auxiliaire pour parcourir la HashMap et accumuler les chemins
    private void getAllPathsHelper(HashMap<?, ?> map, List<String> currentPath, List<List<String>> paths) {
        if (map == null || map.isEmpty()) {
            // Ajouter le chemin si nous atteignons une feuille vide
            if (!currentPath.isEmpty()) {
                paths.add(new ArrayList<>(currentPath));
            }
            return;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            List<String> newPath = new ArrayList<>(currentPath);
            newPath.add((String) entry.getKey());
            Object value = entry.getValue();
            if (value instanceof HashMap<?, ?>) {
                getAllPathsHelper((HashMap<?, ?>) value, newPath, paths);
            } else {
                // Ajouter le chemin courant si c'est une feuille
                newPath.add((String) value); // Ajouter la valeur finale aussi si nécessaire
                paths.add(newPath);
            }
        }
    }

    public List<List<String>> getLongestPath(List<List<String>> allPaths) {

        if (allPaths.size() == 1) {
            return allPaths;
        }

        List<List<String>> longestPath = new ArrayList<>();

        for (int i = 1; i < allPaths.size(); i++) {
            if (allPaths.get(i-1).size() >= allPaths.get(i).size()) {
                longestPath.add(allPaths.get(i-1));
            }
        }

        return longestPath;
    }

}