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

    public List<List<String>> getMove() {
        if (isCrown()) {
            return getMoveListCrown();
        } else {
            return getMoveList();
        }
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

    public List<List<String>> getMoveListCrown() {
        List<List<String>> list = new ArrayList<>();

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newX = x + i;
                int newY = y + j;
                while (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
                    if (!board.isPieceAt(newX, newY)) {
                        List templist = new ArrayList<>();
                        templist.add(positionToString(newX, newY));
                        list.add(templist);
                    } else {
                        break;
                    }
                    newX += i;
                    newY += j;
                }
            }
        }


        return list;
    }

    public HashMap getJump() {
        if (isCrown()) {
            return getJumpListCrown();
        } else {
            return getJumpList();
        }
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
//        ArrayList<Piece> pieces = new ArrayList<>(this.board.getPieces()); 
        ArrayList<Piece> pieces = new ArrayList<>(); // clone the original board
        for (Piece piece : board.getPieces()) {
        	if (!piece.equals(this)) {
        		pieces.add(piece);
        	}
        }
        getJumpListCrown(pieces, this.getX(), this.getY(), map, null);
//        System.out.println("Map: " + map);
        return map;
    }

    private void getJumpListCrown(ArrayList<Piece> pieces, int x, int y, HashMap map, Direction direction) {
    	System.out.println(direction);
        int i;

        i = 1;
        while (!Board.isPieceAt(pieces, x + i, y - i) && x + i < 10 && y - i >= 0) {
            i++;
        }
        if (   x + i + 1 < 10 && y - i - 1 >= 0     // on est toujours dans le plateau, même après le saut
            && Board.getPieceAt(pieces, x+i, y-i).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x+i+1, y-i-1) // il n'y a pas de pièce après
            && direction != Direction.NW    
        ) {

            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            Board.removePieceAt(newPieces, x + i, y - i);

            int j = 1;
            while (x + i + j < 10 && y - i - j >= 0 && !Board.isPieceAt(pieces, x + i + j, y - i - j)) {

                HashMap<String, HashMap> pos = new HashMap<>();
                map.put(positionToString(x + i + j, y - i - j), pos);
                getJumpListCrown(newPieces, x + i + j, y - i - j, pos, Direction.SE);
                j++;

            }
        }


        i = 1;
        while (!Board.isPieceAt(pieces, x + i, y + i) && x + i < 10 && y + i < 10) {
            i++;
        }
        if (   x + i + 1 < 10 && y + i + 1 < 10
            && Board.getPieceAt(pieces, x+i, y+i).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x+i+1, y+i+1) && direction != Direction.SW) {

            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            Board.removePieceAt(newPieces, x + i, y + i);

            int j = 1;
            while (x + i + j < 10 && y + i + j < 10 && !Board.isPieceAt(pieces, x + i + j, y + i + j)) {

                HashMap<String, HashMap> pos = new HashMap<>();
                map.put(positionToString(x + i + j, y + i + j), pos);
                getJumpListCrown(newPieces, x + i + j, y + i + j, pos, Direction.NE);
                j++;

            }
        }


        i = 1;
        while (!Board.isPieceAt(pieces, x - i, y - i) && x - i >= 0 && y - i >= 0) {
            i++;
        }
        if (   x - i - 1 >= 0 && y - i - 1 >= 0
            && Board.getPieceAt(pieces, x-i, y-i).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x-i-1, y-i-1) && direction != Direction.NE) {

            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            Board.removePieceAt(newPieces, x - i, y - i);

            int j = 1;
            while (x - i - j >= 0 && y - i - j >= 0 && !Board.isPieceAt(pieces, x - i - j, y - i - j)) {

                HashMap<String, HashMap> pos = new HashMap<>();
                map.put(positionToString(x - i - j, y - i - j), pos);
                getJumpListCrown(newPieces, x - i - j, y - i - j, pos, Direction.SW);
                j++;

            }
        }


        i = 1;
        while (!Board.isPieceAt(pieces, x - i, y + i) && x - i >= 0 && y + i < 10) {
            i++;
        }
        if (   x - i - 1 >= 0 && y + i + 1 < 10
            && Board.getPieceAt(pieces, x-i, y+i).getColor() != this.getColor()
            && !Board.isPieceAt(pieces, x-i-1, y+i+1) && direction != Direction.SE) {

            ArrayList<Piece> newPieces = new ArrayList<>(pieces);
            Board.removePieceAt(newPieces, x - i, y + i);

            int j = 1;
            while (x - i - j >= 0 && y + i + j < 10 && !Board.isPieceAt(pieces, x - i - j, y + i + j)) {

                HashMap<String, HashMap> pos = new HashMap<>();
                map.put(positionToString(x - i - j, y + i + j), pos);
                getJumpListCrown(newPieces, x - i - j, y + i + j, pos, Direction.NW);
                j++;

            }
        }
    }


    public boolean canEat() {
        if (isCrown()) {
            return canEatCrown();
        }

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

    public boolean canEatCrown() {
    	boolean eat = false;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newX = x + i;
                int newY = y + j;
                while (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
                    if (board.isPieceAt(newX, newY)) {
                        if (board.getPieceAt(newX, newY).getColor() != getColor()
                            && !board.isPieceAt(newX + i, newY + j)
                            && newX + i >= 0 && newX + i < 10 && newY + j >= 0 && newY + j < 10) {
                            eat = true;
                        }
                    }
                    newX += i;
                    newY += j;
                }
            }
        }
        return eat;
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

//        if (allPaths.size() == 1) {
//            return allPaths;
//        }
//
//        List<List<String>> longestPath = new ArrayList<>();
//
//        for (int i = 1; i < allPaths.size(); i++) {
//            if (allPaths.get(i-1).size() >= allPaths.get(i).size()) {
//                longestPath.add(allPaths.get(i-1));
//            }
//        }

        int maxSize = 0;
        for (List<String> list : allPaths) {
            if (list.size() > maxSize) {
                maxSize = list.size();
            }
        }

        List<List<String>> longestPath = new ArrayList<>();
        for (List<String> list : allPaths) {
            if (list.size() == maxSize) {
                longestPath.add(list);
            }
        }

        return longestPath;
    }

    @Override
    public String toString() {
        return getColor() + " " + getX() + "," + getY();
    }
}