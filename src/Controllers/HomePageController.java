package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class HomePageController implements Initializable{

    
	@FXML
    private JFXButton btnKomuniko;
	
    @FXML 
    private AnchorPane homeDashboard;
    
    @FXML 
    private Pane holderPane;
    
    @FXML
    private JFXButton btnFillimi;

    @FXML
    private JFXButton btnQytet;
    
    @FXML
    private JFXButton btnFshat;
    
    @FXML
    private JFXButton btnRruge;
   
    @FXML
    private JFXButton btnCkyqu;
    
    @FXML
    private Label lblUser;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createPage("/FXML/Dashboard.fxml");
		btnFillimi.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		setUsername(ControllerLogin.getInstance().username());
	}
	
	public void setUsername(String user) {
		lblUser.setText(user);
	}
	
	private void setNode(Node node) {
		holderPane.getChildren().clear();
		holderPane.getChildren().add((Node) node);
		
		FadeTransition ft = new FadeTransition(Duration.millis(1500));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}
	
	private void createPage(String frmURL) {
		try {
			homeDashboard = FXMLLoader.load(getClass().getResource(frmURL));
			setNode(homeDashboard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void btnQytetAction(ActionEvent e) throws IOException {
		createPage("/FXML/regjistroQytet.fxml");
		btnQytet.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		btnFillimi.setStyle("-fx-text-fill: #95a5a6;");
		btnFshat.setStyle("-fx-text-fill: #95a5a6;");
		btnRruge.setStyle("-fx-text-fill: #95a5a6;");
		btnKomuniko.setStyle("-fx-text-fill: #95a5a6;");
	}
	
	public void btnFillimiAction(ActionEvent e) throws IOException {
		createPage("/FXML/Dashboard.fxml");
		btnFillimi.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		btnQytet.setStyle("-fx-text-fill: #95a5a6;");
		btnFshat.setStyle("-fx-text-fill: #95a5a6;");
		btnRruge.setStyle("-fx-text-fill: #95a5a6;");
		btnKomuniko.setStyle("-fx-text-fill: #95a5a6;");
	}
	
	public void btnFshatAction(ActionEvent e) throws IOException {
		createPage("/FXML/regjistroFshat.fxml");
		btnFshat.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		btnFillimi.setStyle("-fx-text-fill: #95a5a6;");
		btnQytet.setStyle("-fx-text-fill: #95a5a6;");
		btnRruge.setStyle("-fx-text-fill: #95a5a6;");
		btnKomuniko.setStyle("-fx-text-fill: #95a5a6;");
	}
	
	public void btnRrugeAction(ActionEvent e) throws IOException {
		createPage("/FXML/regjistroRruge.fxml");
		btnRruge.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		btnFillimi.setStyle("-fx-text-fill: #95a5a6;");
		btnQytet.setStyle("-fx-text-fill: #95a5a6;");
		btnFshat.setStyle("-fx-text-fill: #95a5a6;");
		btnKomuniko.setStyle("-fx-text-fill: #95a5a6;");
	}
	
	public void btnKomunikoAction(ActionEvent e) throws IOException {
		createPage("/FXML/chatPage.fxml");
		btnKomuniko.setStyle("-fx-background-color: WHITE; -fx-text-fill: #000000");
		btnFillimi.setStyle("-fx-text-fill: #95a5a6;");
		btnQytet.setStyle("-fx-text-fill: #95a5a6;");
		btnFshat.setStyle("-fx-text-fill: #95a5a6;");
		btnRruge.setStyle("-fx-text-fill: #95a5a6;");
	}
	
	public void btnCkyquAction(ActionEvent e) throws IOException {
		System.exit(0);
	}
}
