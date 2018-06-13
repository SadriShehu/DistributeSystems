package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class NewScene implements Initializable{
	public Stage NewStage = new Stage();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void newScene(String frmURL, int sizeX, int sizeY, boolean resizeable) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(frmURL.toString()));
			Scene scene = new Scene(root,sizeX,sizeY);
			NewStage.setScene(scene);
			NewStage.show();
			NewStage.setResizable(resizeable);
			NewStage.setTitle("Sistemi për regjistrimin e adresave të vendbanimeve");
			NewStage.getIcons().add(new Image("/pics/main-template-rks-logo.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
