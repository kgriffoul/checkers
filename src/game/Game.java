package game;

public class Game {
	
	public static void main(String[] args) {
		Board board = new Board(new Player("a"), new Player("b"));
		System.out.println(board.getPieces().get(0).getJumpListCrown());
	}
	
}
