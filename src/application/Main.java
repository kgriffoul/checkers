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
        final Image whitePiece = new Image(WHITE_PIECE);
        final Image blackPiece = new Image(BLACK_PIECE);


        final Canvas canvas = new Canvas(640, 640);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GOLD);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Board board = new Board(new Player("a"), new Player("b"));
        for (Piece piece : board.getPieces()) {
        	if (piece.getColor() == game.Color.WHITE) {
        		gc.drawImage(whitePiece, piece.getX()*64, piece.getY()*64, 64, 64);
        	} else {
        		gc.drawImage(blackPiece, piece.getX()*64, piece.getY()*64, 64, 64);
        	}
        }
        gc.drawImage(whitePiece, 0, 0, 64, 64);

        stage.setScene(new Scene(new Group(canvas)));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private static final String WHITE_PIECE = "resources/white_piece.png";
    private static final String BLACK_PIECE = "resources/black_piece.png";
}
