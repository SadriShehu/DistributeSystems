package application;

import javafx.application.Application;
import javafx.stage.Stage;
import application.NewScene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		NewScene root = new NewScene();
		root.newScene("/FXML/LoginMain.fxml", 800, 600, false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}