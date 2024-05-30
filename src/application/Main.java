package application;

import game.Board;
import game.Piece;
import game.Player;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
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
//        board.removePieceAt(0, 0);
//        board.removePieceAt(2, 0);
//        board.removePieceAt(4, 0);
//        board.removePieceAt(6, 0);
//        board.removePieceAt(8, 0);
//        board.removePieceAt(1, 1);
//        board.removePieceAt(3, 1);
//        board.removePieceAt(5, 1);
//        board.removePieceAt(7, 1);
//        board.removePieceAt(9, 1);
//        board.removePieceAt(0, 2);
//        board.removePieceAt(2, 2);
//        board.removePieceAt(4, 2);
//        board.removePieceAt(6, 2);
//        board.removePieceAt(8, 2);
//        board.removePieceAt(1, 3);
//        board.removePieceAt(3, 3);
//        board.removePieceAt(5, 3);
//        board.removePieceAt(7, 3);
//        board.removePieceAt(9, 3);
//        
//        board.removePieceAt(0, 6);
//        board.removePieceAt(2, 6);
//        board.removePieceAt(4, 6);
//        board.removePieceAt(6, 6);
//        board.removePieceAt(8, 6);
//        board.removePieceAt(1, 7);
//        board.removePieceAt(3, 7);
//        board.removePieceAt(5, 7);
//        board.removePieceAt(7, 7);
//        board.removePieceAt(9, 7);
//        board.removePieceAt(0, 8);
//        board.removePieceAt(2, 8);
//        board.removePieceAt(4, 8);
//        board.removePieceAt(6, 8);
//        board.removePieceAt(8, 8);
//        board.removePieceAt(1, 9);
//        board.removePieceAt(3, 9);
//        board.removePieceAt(5, 9);
//        board.removePieceAt(7, 9);
//        board.removePieceAt(9, 9);
        
//        board.getPieceAt(0, 0).setPos(5, 5);
//        board.getPieceAt(5, 5).setCrown(true);
//        board.getPieceAt(9, 9).setPos(7, 7);
//        board.getPieceAt(7, 9).setPos(6, 4);
//        board.getPieceAt(5, 9).setPos(6, 2);
//        board.getPieceAt(3, 9).setPos(4, 2);
        

//        board.getPieceAt(2, 0).setPos(7, 5);
//        board.getPieceAt(7, 5).setCrown(true);
//        board.removePieceAt(3, 7);
//        board.removePieceAt(9, 7);
        /*      */

        drawBoard(gc, board);

        /* Rotate canvas in the right form */
        Rotate rotate = new Rotate(180, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.getTransforms().add(rotate);

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
            System.out.println("Coups possibles:" + allowedMoves);

            /* si on sélectionne une pièce */
            if (board.isPieceAt(x, y) && allowedMoves.containsKey(board.getPieceAt(x, y))) {
                gc.clearRect(0, 0, 640, 640);
                drawBoard(gc, board);
                board.selectPiece(board.getPieceAt(x, y));
                gc.drawImage(SELECT, x * 64, y * 64, 64, 64); // affichage sélection

                /* affichage coups possibles */
                System.out.println("Coups possibles pour cette pièce :" + allowedMoves.get(board.getSelectedPiece()));
                for (List<String> list : allowedMoves.get(board.getSelectedPiece())) {
                    for (String pos : Board.getDiagonalPos(
                            board.getSelectedPiece().getX(),
                            board.getSelectedPiece().getY(),
                            Integer.parseInt(list.getFirst().split(",")[0]),
                            Integer.parseInt(list.getFirst().split(",")[1]))) {
                        int newX = Integer.parseInt(pos.split(",")[0]);
                        int newY = Integer.parseInt(pos.split(",")[1]);
                        if (board.isPieceAt(newX, newY) && board.getPieceAt(newX, newY).getColor() != board.getSelectedPiece().getColor()) {
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
                                //System.out.println(newX +","+ newY);
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

                        /* check for crown */
                        if (   board.getSelectedPiece().getColor() == game.Color.WHITE && board.getSelectedPiece().getY() == 9
                            || board.getSelectedPiece().getColor() == game.Color.BLACK && board.getSelectedPiece().getY() == 0) {
                            board.getSelectedPiece().setCrown(true);
                        }

                        board.selectPiece(null);

                        /* on réinitialise l'affichage */
                        gc.clearRect(0, 0, 640, 640);
                        drawBoard(gc, board);

                        /* à l'autre joueur de jouer */
                        board.switchMove();
                        /* rotate again */
//                        canvas.getTransforms().add(rotate);
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
                if (piece.isCrown()) {
                    gc.drawImage(WHITE_CROWN, piece.getX() * 64, piece.getY() * 64, 64, 64);
                } else {
                    gc.drawImage(WHITE_PIECE, piece.getX() * 64, piece.getY() * 64, 64, 64);
                }
            } else {
                if (piece.isCrown()) {
                    gc.drawImage(BLACK_CROWN, piece.getX()*64, piece.getY()*64, 64, 64);
                } else {
                    gc.drawImage(BLACK_PIECE, piece.getX()*64, piece.getY()*64, 64, 64);
                }
            }
        }
    }

    private static final Image BOARD = new Image("resources/board.png");
    private static final Image SELECT = new Image("resources/select.png");
    private static final Image AVAILABLE = new Image("resources/available.png");
    private static final Image EAT = new Image("resources/eat.png");
    private static final Image WHITE_PIECE = new Image("resources/white_piece.png");
    private static final Image BLACK_PIECE = new Image("resources/black_piece.png");
    private static final Image WHITE_CROWN = new Image("resources/white_crown.png");
    private static final Image BLACK_CROWN = new Image("resources/black_crown.png");
}