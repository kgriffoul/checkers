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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stage) {

        final Canvas canvas = new Canvas(640, 640);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GOLD);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());



        Board board = new Board(new Player("a"), new Player("b"));

        /* temp */
        board.getPieceAt(0, 0).setPos(3, 5);
        board.removePieceAt(1, 7);
        board.removePieceAt(5, 7);
        board.removePieceAt(7, 9);
        /*      */

        drawBoard(gc, board);

        canvas.setOnMouseClicked(event -> {

//            drawBoard(gc, board);

            int x = (int) event.getX() / 64;
            int y = (int) event.getY() / 64;
            System.out.printf("Click at %d %d%n", x, y);
//            if (board.isPieceAt(x, y)
//                    // la pièce a la même couleur que celui qui a le trait
//                    && board.getPieceAt(x, y).getColor() == board.getMove()) {
//                gc.drawImage(SELECT, x * 64, y * 64, 64, 64);
//                for(String pos : board.getPieceAt(x, y).getMoveList()) {
//                    gc.drawImage(AVAILABLE, Integer.valueOf(pos.split(",")[0])*64, Integer.valueOf(pos.split(",")[1])*64);
//                }
//            }

//            if (board.isPieceAt(x, y)
//                    // la pièce a la même couleur que celui qui a le trait
//                    && board.getPieceAt(x, y).getColor() == board.getMove()) {
//                board.selectPiece(board.getPieceAt(x, y));
//                gc.drawImage(SELECT, x * 64, y * 64, 64, 64); // affichage sélection
//                /* affichage coups possibles */
//                for(String pos : board.getPieceAt(x, y).getMoveList()) {
//                    gc.drawImage(AVAILABLE, Integer.valueOf(pos.split(",")[0])*64, Integer.valueOf(pos.split(",")[1])*64);
//                }
//            } else if (!board.isPieceAt(x, y) && board.getSelectedPiece() != null) { // si clic sur case vide & pièce sélectionnée = on veut se déplacer
//                board.getSelectedPiece().setPos(x, y);
//                board.switchMove(); // coup à l'autre joueur
//                /* mise à jour du plateau */
//                gc.clearRect(0, 0, 640, 640);
//                drawBoard(gc, board);
//            }

            HashMap<Piece, List<List<String>>> allowedMoves = board.getAllowedMoves(board.getMove());

            /* si on sélectionne une pièce */
            if (board.isPieceAt(x, y) && allowedMoves.containsKey(board.getPieceAt(x, y))) {
                gc.clearRect(0, 0, 640, 640);
                drawBoard(gc, board);
                board.selectPiece(board.getPieceAt(x, y));
                gc.drawImage(SELECT, x * 64, y * 64, 64, 64); // affichage sélection

                /* affichage coups possibles */
                System.out.println(allowedMoves.get(board.getSelectedPiece()));
                for (List<String> list : allowedMoves.get(board.getSelectedPiece())) {
                    System.out.println("a");
                    for (String pos : Board.getDiagonalPos(
                            board.getSelectedPiece().getX(),
                            board.getSelectedPiece().getY(),
                            Integer.parseInt(list.getFirst().split(",")[0]),
                            Integer.parseInt(list.getFirst().split(",")[1]))) {
                        int newX = Integer.parseInt(pos.split(",")[0]);
                        int newY = Integer.parseInt(pos.split(",")[1]);
                        if (board.isPieceAt(newX, newY) && board.getPieceAt(newX, newY).getColor() != board.getSelectedPiece().getColor()) {
                            System.out.println(newX +","+ newY);
                            gc.drawImage(EAT, newX * 64, newY * 64, 64, 64);
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
                            if (board.isPieceAt(newX, newY) && board.getPieceAt(newX, newY).getColor() != board.getSelectedPiece().getColor()) {
                                System.out.println(newX +","+ newY);
                                gc.drawImage(EAT, newX * 64, newY * 64, 64, 64);
                            }
                        }
                    }
                    String pos = list.getLast();
                    gc.drawImage(AVAILABLE, Integer.valueOf(pos.split(",")[0])*64, Integer.valueOf(pos.split(",")[1])*64);
                }
            } else if (!board.isPieceAt(x, y) && board.getSelectedPiece() != null) { // si clic sur case vide & pièce sélectionnée = on veut se déplacer
                /* on vérifie si la case sélectionnée est une case disponible */
                boolean canMove = false;
                for (List<String> list : allowedMoves.get(board.getSelectedPiece())) {
                    String pos = list.getLast();
                    if (!canMove && x == Integer.valueOf(pos.split(",")[0]) && y == Integer.valueOf(pos.split(",")[1])) {
                        /* case disponible, on se déplace */
                        canMove = true;
                        board.move(board.getSelectedPiece(), list);

                        /* on réinitialise l'affichage */
                        gc.clearRect(0, 0, 640, 640);
                        drawBoard(gc, board);

                        /* à l'autre joueur de jouer */
                        board.switchMove();
                    }
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
    private static final Image AVAILABLE = new Image("resources/available.png");
    private static final Image EAT = new Image("resources/eat.png");
    private static final Image WHITE_PIECE = new Image("resources/white_piece.png");
    private static final Image BLACK_PIECE = new Image("resources/black_piece.png");
}
