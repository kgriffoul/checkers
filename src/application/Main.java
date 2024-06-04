package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.io.IOException;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view.fxml"));
		Parent racine;
		try {
			racine = loader.load();
			Scene scene = new Scene(racine);
			scene.getRoot().requestFocus();
			primaryStage.setTitle("Jeu des Dames");
			primaryStage.setHeight(820);
			primaryStage.setWidth(1200);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("resources/classic/black_piece.png"));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Programme principal
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		launch(args); // appelera la méthode start
	}
}