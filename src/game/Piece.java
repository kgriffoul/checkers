package game;

public class Piece {

    private final Color color;
    private int x;
    private int y;
    private final boolean crown;

    public Piece(Color color, int x, int y, boolean crown) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.crown = crown;
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
    
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isCrown() {
        return crown;
    }
}
