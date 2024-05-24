package application;
	
import game.Board;
import game.Piece;
import game.Player;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
    public void start(Stage stage) {

        final Canvas canvas = new Canvas(640, 640);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GOLD);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        
        
        Board board = new Board(new Player("a"), new Player("b"));
        drawBoard(gc, board);
        
        canvas.setOnMouseClicked(event -> {
        	
        	gc.clearRect(0, 0, 640, 640);
        	drawBoard(gc, board);
        	
            int x = (int) event.getX() / 64;
            int y = (int) event.getY() / 64;
            System.out.printf("Click at %d %d%n", x, y);
            if (board.isPieceAt(x, y)) {
            	gc.drawImage(SELECT, x * 64, y * 64, 64, 64);
        		for(String pos : board.getPieceAt(x, y).getMoveList()) {
        			gc.drawImage(SELECT, Integer.valueOf(pos.split(",")[0])*64, Integer.valueOf(pos.split(",")[1])*64);
        		}
            }
        });

        stage.setScene(new Scene(new Group(canvas)));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void drawBoard(GraphicsContext gc, Board board) {
    	gc.drawImage(BOARD, 0, 0, 640, 640);
    	for (Piece piece : board.getPieces()) {
        	if (piece.getColor() == game.Color.WHITE) {
        		gc.drawImage(WHITE_PIECE, piece.getX()*64, piece.getY()*64, 64, 64);
        	} else {
        		gc.drawImage(BLACK_PIECE, piece.getX()*64, piece.getY()*64, 64, 64);
        	}
        }
    }
    
    private static final Image BOARD = new Image("resources/board.png");
    private static final Image SELECT = new Image("resources/select.png");
    private static final Image WHITE_PIECE = new Image("resources/white_piece.png");
    private static final Image BLACK_PIECE = new Image("resources/black_piece.png");
}
