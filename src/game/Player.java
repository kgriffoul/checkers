package game;

public class Player {

    private String username;
    private int timer;

    public Player(String username) {
        this.username = username;
        this.timer = 0;
    }

    public Player(String username, int timer) {
        this.username = username;
        this.timer = timer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTimer() {
        return timer;
    }

    public void increaseTimer() {
        this.timer++;
    }
}
