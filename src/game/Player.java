package game;

public class Player {
	
	private final String username;
	private final Color color;
	private int timer;
	
	public Player(String username, Color color) {
		this.username = username;
		this.color = color;
		this.timer = 0;
	}
	
	public Player(String username, Color color, int timer) {
		this.username = username;
		this.color = color;
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

	public void incrementTimer() {
		this.timer++;
	}

	public String getUsername() {
		return username;
	}

	public Color getColor() {
		return color;
	}
	
}
