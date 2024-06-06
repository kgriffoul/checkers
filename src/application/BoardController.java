package application;

import game.Board;
import game.Color;
import game.Piece;
import game.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class BoardController {

	private GraphicsContext gc;
	private Board board;
	private boolean canPlay;
	private boolean isPaused;
	private boolean shouldSave;
	private boolean showMoves;
	
	@FXML
	private Label whitePlayer;
	
	@FXML
    private TextField whitePlayerPrompt;
	
	@FXML
	private Label blackPlayer;
	
	@FXML
    private TextField blackPlayerPrompt;
	
	@FXML
    private BorderPane window;
	
    @FXML
    private Canvas boardCanvas;
	
	@FXML
    private Button settings;

    @FXML
    private Button save;

    @FXML
    private Button rules;
    
    @FXML
    private Button load;

    @FXML
    private Button quit;

    @FXML
    private Button pause;
    
    @FXML
    private Button forgive1;
    
    @FXML
    private Button forgive2;
    
    @FXML
    private Button play;
    
    @FXML
    private Label turn;
    
    @FXML
    private Label timer;
	
	@FXML
	private void initialize() {
		
		loadImage("Classique");
		loadBoard(boardCanvas);
		
		canPlay = false;
		isPaused = false;
		shouldSave = false;
		
		showMoves = true;
		
		whitePlayer.setManaged(false);
		blackPlayer.setManaged(false);
		
		turn.setManaged(false);
		
		gc.drawImage(GRAY, 0, 0);

//		settings.setCursor(Cursor.CLOSED_HAND);

        settings.setGraphic(SETTINGS);
        rules.setGraphic(RULES);
        pause.setGraphic(PAUSE);
        load.setGraphic(IMPORT);
        save.setGraphic(SAVE);
        quit.setGraphic(QUIT);
        play.setGraphic(PLAY);
        
        forgive1.setGraphic(FORGIVE1);
        forgive2.setGraphic(FORGIVE2);
        
        initializeButtons();
	}
	
	private void initializeButtons() {
		settings.setOnMouseEntered(e -> settings.setGraphic(SETTINGS_HOVER));
        settings.setOnMouseExited(e -> settings.setGraphic(SETTINGS));
        
        rules.setOnMouseEntered(e -> rules.setGraphic(RULES_HOVER));
        rules.setOnMouseExited(e -> rules.setGraphic(RULES));
        
        pause.setOnMouseEntered(e -> {
        	if (isPaused) {
        		pause.setGraphic(RESUME_HOVER);
        	} else {
        		pause.setGraphic(PAUSE_HOVER);
        	}
        });
        pause.setOnMouseExited(e -> {
	        if (isPaused) {
	    		pause.setGraphic(RESUME);
	    	} else {
	    		pause.setGraphic(PAUSE);
	    	}
	    });
        
        load.setOnMouseEntered(e -> load.setGraphic(IMPORT_HOVER));
        load.setOnMouseExited(e -> load.setGraphic(IMPORT));
        
        save.setOnMouseEntered(e -> {
        	if (shouldSave) {
        		save.setGraphic(SAVE_ITALIC_HOVER);
        	} else {
        		save.setGraphic(SAVE_HOVER);
        	}
        });
        save.setOnMouseExited(e -> {
	        if (shouldSave) {
	        	save.setGraphic(SAVE_ITALIC);
	    	} else {
	    		save.setGraphic(SAVE);
	    	}
	    });
        
        quit.setOnMouseEntered(e -> quit.setGraphic(QUIT_HOVER));
        quit.setOnMouseExited(e -> quit.setGraphic(QUIT));
        
        play.setOnMouseEntered(e -> play.setGraphic(PLAY_HOVER));
        play.setOnMouseExited(e -> play.setGraphic(PLAY));
        
        forgive1.setOnMouseEntered(e -> forgive1.setGraphic(FORGIVE_HOVER1));
        forgive1.setOnMouseExited(e -> forgive1.setGraphic(FORGIVE1));
        
        forgive2.setOnMouseEntered(e -> forgive2.setGraphic(FORGIVE_HOVER2));
        forgive2.setOnMouseExited(e -> forgive2.setGraphic(FORGIVE2));
	}
	
	@FXML
	private void displayRules(ActionEvent event) {

		VBox vbox = new VBox();
		vbox.setSpacing(10);


		String[] images = {"/resources/rules/materiel.png", "/resources/rules/pieces1.png", "/resources/rules/pieces2.png", 
				"/resources/rules/prise1.png", "/resources/rules/prise2.png", "/resources/rules/prise3.png", 
				"/resources/rules/prise4.png", "/resources/rules/irregularite.png", "/resources/rules/finpartie.png", 
		"/resources/rules/resultat.png"};
		
		for (String imagePath : images) {
			Image image = new Image(getClass().getResource(imagePath).toString());
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(400); 
			vbox.getChildren().add(imageView);
		}


		ScrollPane scrollPane = new ScrollPane(vbox);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefViewportHeight(400); 


		scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
			vbox.getChildren().stream()
			.filter(node -> node instanceof ImageView)
			.map(node -> (ImageView) node)
			.forEach(imageView -> imageView.setFitWidth(newValue.getWidth()));
		});

		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.DECORATED);
		stage.setHeight(700);
		stage.setWidth(1000);
		stage.setTitle("Règles");
		stage.getIcons().add(BLACK_PIECE);

		Scene scene = new Scene(scrollPane);
		stage.setScene(scene);

		stage.showAndWait();
	}
	
    @FXML
    private void openSettings(ActionEvent event) {
    	Stage stage = new Stage();
		stage.setHeight(200);
		stage.setWidth(400);
		stage.setTitle("Paramètres");
		stage.getIcons().add(BLACK_PIECE);
		
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		
		HBox application = new HBox();
		application.setAlignment(Pos.CENTER_LEFT);
		
		Label applicationLabel = new Label("Thème de l'application : ");
		ComboBox<String> applicationList = new ComboBox<>();
		applicationList.getItems().addAll("Clair", "Sombre");
		applicationList.getSelectionModel().selectFirst();
		application.getChildren().addAll(applicationLabel, applicationList);
		applicationList.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			   changeTheme(newValue);
		});
		
		HBox theme = new HBox();
		theme.setAlignment(Pos.CENTER_LEFT);
		
		Label themeLabel = new Label("Thème du jeu : ");
		ComboBox<String> themeList = new ComboBox<>();
		themeList.getItems().addAll("Classique", "Biscuit", "Poker");
		themeList.getSelectionModel().selectFirst();
		theme.getChildren().addAll(themeLabel, themeList);
		themeList.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			   loadImage(newValue);
		}); 
		
		HBox moves = new HBox();
		moves.setAlignment(Pos.CENTER_LEFT);
		
		Label movesLabel = new Label("Aide aux coups :  ");
		CheckBox movesCheckbox = new CheckBox();
		movesCheckbox.setSelected(showMoves);
		movesCheckbox.selectedProperty().addListener((options, oldValue, newValue) -> {
			   showMoves = newValue;
		});
		moves.getChildren().addAll(movesLabel, movesCheckbox);
	
		vbox.getChildren().addAll(application, theme, moves);
		
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		
		stage.showAndWait();
    }
    
    @FXML
    private void pauseGame(ActionEvent event) {
    	if (isPaused) {
    		pause.setGraphic(PAUSE_HOVER);
    		isPaused = false;
    		canPlay = true;
    		drawBoard(gc, board);
    		board.selectPiece(null);
    	} else if (!isPaused && canPlay) {
    		pause.setGraphic(RESUME_HOVER);
    		isPaused = true;
    		canPlay = false;
    		drawBoard(gc, board);
    		gc.drawImage(GRAY, 0, 0);
    	}
    }
    
    @FXML
    private void saveGame(ActionEvent event) {
    	
    	shouldSave = false;
    	save.setGraphic(SAVE_HOVER);
    	
		String data;

		data = String.valueOf(board.getMove()) + "\n"
			   + String.valueOf(board.getPlayers().get(Color.WHITE)) + ",WHITE\n"
			   + String.valueOf(board.getPlayers().get(Color.BLACK)) + ",BLACK\n";

		for (int i = 0; i < board.getPieces().size(); i++) {
			data += String.valueOf(board.getPieces().get(i).getColor()) + ","
					+ String.valueOf(board.getPieces().get(i).getX()) + ","
					+ String.valueOf(board.getPieces().get(i).getY()) + ","
					+ String.valueOf(board.getPieces().get(i).isCrown()) + "\n";
		}
		
		try {
			File directory = new File("data/");
		    if (!directory.exists()){
		        directory.mkdir();
		    }
		    
			FileWriter writer = new FileWriter("data/" + new SimpleDateFormat("yyyy-MM-dd_HHmmssSSSS").format(new Date()) + ".dat");

			// write the string to the file
			writer.write(data);
			// close the writer
			writer.close();

		} catch (Exception e) {
			System.out.println(e);
			System.err.println("Erreur lors de la sauvegarder du fichier.");
		}
    }
    
    @FXML
    private void importGame(ActionEvent event) {
		Color playerTurn = null;
		Player player1 = null;
		Player player2 = null;
		List<Piece> pieces = new ArrayList<>();

		FileChooser chooser = new FileChooser();
		
		File directory = new File("data/");
	    if (!directory.exists()){
	        directory.mkdir();
	    }
	    
		chooser.setInitialDirectory(directory);
		
		Stage stage = new Stage();
		stage.setScene(null);
		File selectedFile = chooser.showOpenDialog(stage);
		if (selectedFile != null) {
		
			try {
				// Création d'un fileReader pour lire le fichier
				FileReader fileReader = new FileReader(selectedFile);
	
				// Création d'un bufferedReader qui utilise le fileReader
				BufferedReader reader = new BufferedReader(fileReader);
	
	
				// une fonction à essayer pouvant générer une erreur
				String informations = reader.readLine();
	
				int index = 0;
				while (informations != null) {
					
					// affichage de la ligne
					System.out.println(informations);
					if (informations.equals("WHITE")) {
						playerTurn = Color.WHITE;
					} else if (informations.equals("BLACK")) {
						playerTurn = Color.BLACK;
					}
	
					if (informations.contains(",WHITE")) {
						player1 = new Player(informations.substring(0, informations.indexOf(',')));
					} else if (informations.contains(",BLACK")) {
						player2 = new Player(informations.substring(0, informations.indexOf(',')));
					}
					
					if (index > 2) {
						String[] parts = informations.split(",");
						
						int x = Integer.valueOf(parts[1]);
						int y = Integer.valueOf(parts[2]);
						boolean crown = Boolean.valueOf(parts[3]);
						
						if (parts[0].equals("WHITE")) {
							Piece piece = new Piece(Color.WHITE, x, y,crown);
							pieces.add(piece);
		
						} else if (parts[0].equals("BLACK")) {
							Piece piece = new Piece(Color.BLACK, x, y,crown);
							pieces.add(piece);
		
						}
					}
					// lecture de la prochaine ligne
					informations = reader.readLine();
					
					index++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			board = new Board(pieces, player1, player2, playerTurn);
			
			whitePlayerPrompt.setManaged(false);
			whitePlayerPrompt.setVisible(false);
			blackPlayerPrompt.setManaged(false);
			blackPlayerPrompt.setVisible(false);
			
			whitePlayer.setText(player1.getUsername());
			blackPlayer.setText(player2.getUsername());
			
			whitePlayer.setManaged(true);
			whitePlayer.setVisible(true);
			blackPlayer.setManaged(true);
			blackPlayer.setVisible(true);
			
			play.setManaged(false);
			play.setVisible(false);
			turn.setManaged(true);
			turn.setVisible(true);
			turn.setText("Tour des " + (board.getMove() == Color.WHITE ? "blancs" : "noirs"));
			canPlay = true;
			
			/* reload canvas */
	    	drawBoard(gc, board);
		}
    }
    
    @FXML
    private void closeApp(ActionEvent event) {
    	Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    	((Stage) confirmation.getDialogPane().getScene().getWindow()).getIcons().add(BLACK_PIECE);
		confirmation.setTitle("Confirmation");
		confirmation.setHeaderText("Êtes-vous sûr de vouloir quitter ?\n(La partie en cours ne sera pas sauvegardée)");
		ButtonType okButton = new ButtonType("Oui", ButtonData.OK_DONE);

		ButtonType cancelButton = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

		confirmation.getButtonTypes().setAll(cancelButton, okButton);

		confirmation.showAndWait().ifPresent(response -> {
			if (response == okButton) {
				Platform.exit();
			}
		});
    }
    
    @FXML
    private void startGame(ActionEvent event) {
    	/* reset board */
    	board = new Board(new Player(whitePlayerPrompt.getText()), new Player(blackPlayerPrompt.getText()));
    	whitePlayer.setText(whitePlayerPrompt.getText());
    	blackPlayer.setText(blackPlayerPrompt.getText());
    	
    	whitePlayerPrompt.setManaged(false);
    	blackPlayerPrompt.setManaged(false);
    	whitePlayerPrompt.setVisible(false);
    	blackPlayerPrompt.setVisible(false);
    	
    	whitePlayer.setManaged(true);
    	whitePlayer.setVisible(true);
    	blackPlayer.setManaged(true);
    	blackPlayer.setVisible(true);

    	canPlay = true;
    	drawBoard(gc, board);
    	play.setManaged(false);
    	play.setVisible(false);
    	turn.setManaged(true);
    	turn.setVisible(true);
    	turn.setText("Tour des blancs");
    }
    
    @FXML
    private void forgiveBlack(ActionEvent event) {
    	if (canPlay && board.getMove() == Color.BLACK) {
    		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        	((Stage) confirmation.getDialogPane().getScene().getWindow()).getIcons().add(BLACK_PIECE);
    		confirmation.setTitle("Confirmation");
    		confirmation.setHeaderText("Êtes-vous sûr de vouloir abandonner ?");
    		ButtonType okButton = new ButtonType("Oui", ButtonData.OK_DONE);

    		ButtonType cancelButton = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

    		confirmation.getButtonTypes().setAll(cancelButton, okButton);

    		confirmation.showAndWait().ifPresent(response -> {
    			if (response == okButton) {
                	play.setManaged(true);
                	play.setVisible(true);
                	turn.setManaged(false);
                	turn.setVisible(false);
                	gc.drawImage(GRAY, 0, 0);
            		canPlay = false;
            		
            		changePlayerNames();
                }
    		});
    	}
    }
    
    @FXML
    private void forgiveWhite(ActionEvent event) {
    	if (canPlay && board.getMove() == Color.WHITE) {
    		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        	((Stage) confirmation.getDialogPane().getScene().getWindow()).getIcons().add(BLACK_PIECE);
    		confirmation.setTitle("Confirmation");
    		confirmation.setHeaderText("Êtes-vous sûr de vouloir abandonner ?");
    		ButtonType okButton = new ButtonType("Oui", ButtonData.OK_DONE);

    		ButtonType cancelButton = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

    		confirmation.getButtonTypes().setAll(cancelButton, okButton);

    		confirmation.showAndWait().ifPresent(response -> {
    			if (response == okButton) {
                	play.setManaged(true);
                	play.setVisible(true);
                	turn.setManaged(false);
                	turn.setVisible(false);
                	gc.drawImage(GRAY, 0, 0);
            		canPlay = false;
            		
            		changePlayerNames();
                }
    		});
    	}
    }

    public void loadBoard(Canvas canvas) {

        gc = canvas.getGraphicsContext2D();


        board = new Board(new Player("a"), new Player("b"));

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

            HashMap<Piece, List<List<String>>> allowedMoves = board.getAllowedMoves(board.getMove());
            System.out.println("Coups possibles:" + allowedMoves);

            /* si on sélectionne une pièce */
            if (canPlay && board.isPieceAt(x, y) && allowedMoves.containsKey(board.getPieceAt(x, y))) {
                gc.clearRect(0, 0, 640, 640);
                drawBoard(gc, board);
                board.selectPiece(board.getPieceAt(x, y));
                gc.drawImage(SELECT, x * 64, y * 64, 64, 64); // affichage sélection

                /* affichage coups possibles */
                System.out.println("Coups possibles pour cette pièce :" + allowedMoves.get(board.getSelectedPiece()));
                if (showMoves) {
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
	                                gc.drawImage(EAT, newX * 64, newY * 64, 64, 64);
	                            }
	                        }
	                    }
	                    String pos = list.getLast();
	                    gc.drawImage(AVAILABLE, Integer.valueOf(pos.split(",")[0])*64, Integer.valueOf(pos.split(",")[1])*64);
	                }
                }
            } else if (canPlay && board.isPieceAt(x, y) && !allowedMoves.containsKey(board.getPieceAt(x, y))) { // si clic sur pièce injouable
            	gc.clearRect(0, 0, 640, 640);
                drawBoard(gc, board);
            	gc.drawImage(UNALLOWED, x*64,y*64);
            	board.selectPiece(null);
            } else if (canPlay && !board.isPieceAt(x, y) && board.getSelectedPiece() != null) { // si clic sur case vide & pièce sélectionnée = on veut se déplacer
                /* on vérifie si la case sélectionnée est une case disponible */
                boolean canMove = false;
                for (List<String> list : allowedMoves.get(board.getSelectedPiece())) {
                    String pos = list.getLast();
                    if (!canMove && x == Integer.valueOf(pos.split(",")[0]) && y == Integer.valueOf(pos.split(",")[1])) {
                        /* case disponible, on se déplace */
                        canMove = true;
                        board.move(board.getSelectedPiece(), list);
                        shouldSave = true;
                        save.setGraphic(SAVE_ITALIC);

                        /* check for crown */
                        if (   board.getSelectedPiece().getColor() == Color.WHITE && board.getSelectedPiece().getY() == 9
                            || board.getSelectedPiece().getColor() == Color.BLACK && board.getSelectedPiece().getY() == 0) {
                            board.getSelectedPiece().setCrown(true);
                        }

                        board.selectPiece(null);

                        /* on réinitialise l'affichage */
                        gc.clearRect(0, 0, 640, 640);
                        drawBoard(gc, board);

                        /* à l'autre joueur de jouer */
                        board.switchMove();
                        turn.setText("Tour des " + (board.getMove() == Color.WHITE ? "blancs" : "noirs"));
                        
                        /* on vérifie si un joueur a gagné */
                        boolean white = false;
                        boolean black = false;
                        for (Piece piece : board.getPieces()) {
                        	if (piece.getColor() == Color.WHITE) {
                        		white = true;
                        	} else if (piece.getColor() == Color.BLACK) {
                        		black = true;
                        	}
                        }
                        
                        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                        ((Stage) confirmation.getDialogPane().getScene().getWindow()).getIcons().add(BLACK_PIECE);
                        confirmation.setTitle("Victoire !");
                        if (!black) {
                    		confirmation.setHeaderText("Les blancs ont gagné !");
                    		
                        } else if (!white) {
                        	confirmation.setHeaderText("Les noirs ont gagné !");
                        }
                        
                        if (!black || !white) {
                        	confirmation.show();
                        	
                        	play.setManaged(true);
                        	play.setVisible(true);
                        	turn.setManaged(false);
                        	turn.setVisible(false);
                        	gc.drawImage(GRAY, 0, 0);
                    		canPlay = false;
                    		
                    		changePlayerNames();
                        }
                    }
                }
                
                /* la case n'est pas disponible */
                if (!canMove) {
                	gc.drawImage(UNALLOWED, x*64,y*64);
                }
            }

        });

    }


    public static void drawBoard(GraphicsContext gc, Board board) {
        gc.drawImage(BOARD, 0, 0, 640, 640);
        for (Piece piece : board.getPieces()) {
            if (piece.getColor() == Color.WHITE) {
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
    
    private void changeTheme(String theme) {
    	
    	switch (theme) {
    		case "Clair" -> {
    			window.setStyle("-fx-background-color :  #F5F5F5;");
    		}
    		case "Sombre" -> {
    			window.setStyle("-fx-background-color :  #696969;");
    		}
    	}
    }
    
    
    private void loadImage(String theme) {
    	String path;
    	switch (theme) {
    		case "Classique" -> path = "resources/classic/";
    		case "Biscuit" -> path = "resources/biscuit/";
    		case "Poker" -> path = "resources/poker/";
    		default -> path = "resources/classic/";
    	}
    	
    	BOARD = new Image(path + "board.png");

    	WHITE_PIECE = new Image(path + "white_piece.png", 64, 64, true, true);
    	BLACK_PIECE = new Image(path + "black_piece.png", 64, 64, true, true);
    	WHITE_CROWN = new Image(path + "white_crown.png", 64, 64, true, true);
    	BLACK_CROWN = new Image(path + "black_crown.png", 64, 64, true, true);
    	
    	/* reload canvas */
    	if (gc != null) {
    		drawBoard(gc, board);
    		if (!canPlay) {
    			gc.drawImage(GRAY, 0, 0);
    		}
    	}
    }
    
    private void changePlayerNames() {
    	whitePlayerPrompt.setManaged(true);
    	blackPlayerPrompt.setManaged(true);
    	whitePlayerPrompt.setVisible(true);
    	blackPlayerPrompt.setVisible(true);
    	
    	whitePlayer.setManaged(false);
    	whitePlayer.setVisible(false);
    	blackPlayer.setManaged(false);
    	blackPlayer.setVisible(false);
    }
    
    private static Image GRAY = new Image("resources/gray.png");
    private static Image SELECT = new Image("resources/select.png");
    private static Image AVAILABLE = new Image("resources/available.png");
    private static Image EAT = new Image("resources/eat.png");
    private static Image UNALLOWED = new Image("resources/unallowed.png");

    private static Image BOARD;

    private static Image WHITE_PIECE;
    private static Image BLACK_PIECE;
    private static Image WHITE_CROWN;
    private static Image BLACK_CROWN;
    
    private static final ImageView SETTINGS = new ImageView(new Image("resources/buttons/settings.png", 150, 50, true, true));
    private static final ImageView SETTINGS_HOVER = new ImageView(new Image("resources/buttons/settings_hover.png", 150, 50, true, true));
    private static final ImageView RULES = new ImageView(new Image("resources/buttons/rules.png", 150, 50, true, true));
    private static final ImageView RULES_HOVER = new ImageView(new Image("resources/buttons/rules_hover.png", 150, 50, true, true));
    private static final ImageView PAUSE = new ImageView(new Image("resources/buttons/pause.png", 150, 50, true, true));
    private static final ImageView PAUSE_HOVER = new ImageView(new Image("resources/buttons/pause_hover.png", 150, 50, true, true));
    private static final ImageView RESUME = new ImageView(new Image("resources/buttons/resume.png", 150, 50, true, true));
    private static final ImageView RESUME_HOVER = new ImageView(new Image("resources/buttons/resume_hover.png", 150, 50, true, true));
    private static final ImageView IMPORT = new ImageView(new Image("resources/buttons/import.png", 150, 50, true, true));
    private static final ImageView IMPORT_HOVER = new ImageView(new Image("resources/buttons/import_hover.png", 150, 50, true, true));
    private static final ImageView SAVE = new ImageView(new Image("resources/buttons/save.png", 150, 50, true, true));
    private static final ImageView SAVE_HOVER = new ImageView(new Image("resources/buttons/save_hover.png", 150, 50, true, true));
    private static final ImageView SAVE_ITALIC = new ImageView(new Image("resources/buttons/save_italic.png", 150, 50, true, true));
    private static final ImageView SAVE_ITALIC_HOVER = new ImageView(new Image("resources/buttons/save_italic_hover.png", 150, 50, true, true));
    private static final ImageView QUIT = new ImageView(new Image("resources/buttons/quit.png", 150, 50, true, true));
    private static final ImageView QUIT_HOVER = new ImageView(new Image("resources/buttons/quit_hover.png", 150, 50, true, true));
    private static final ImageView PLAY = new ImageView(new Image("resources/buttons/play.png", 150, 50, true, true));
    private static final ImageView PLAY_HOVER = new ImageView(new Image("resources/buttons/play_hover.png", 150, 50, true, true));
    private static final ImageView FORGIVE1 = new ImageView(new Image("resources/buttons/forgive.png", 150, 50, true, true));
    private static final ImageView FORGIVE_HOVER1 = new ImageView(new Image("resources/buttons/forgive_hover.png", 150, 50, true, true));
    private static final ImageView FORGIVE2 = new ImageView(new Image("resources/buttons/forgive.png", 150, 50, true, true));
    private static final ImageView FORGIVE_HOVER2 = new ImageView(new Image("resources/buttons/forgive_hover.png", 150, 50, true, true));
}