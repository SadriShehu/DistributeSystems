package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class clientMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/clientForm.fxml"));
			Scene scene = new Scene(root,330,275);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Sistemi p�r regjistrimin e adresave t� vendbanimeve - Komunikimi me z�");
			primaryStage.getIcons().add(new Image("/pics/main-template-rks-logo.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
