package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import DBConnections.ConnectionHandler;
import application.NewScene;

public class ControllerLogin implements Initializable{

	private Connection connection;
	private ConnectionHandler handler;
	private PreparedStatement pst;
	public static chatController con;
	
    @FXML
    private JFXTextField txtPerdoruesi;

    @FXML
    private JFXPasswordField txtFjalekalimi;
    
    @FXML
    private JFXTextField txtServer;

    @FXML
    private JFXButton btnKyqu;

    @FXML
    private JFXButton btnAnulo;

    @FXML
    private JFXButton btnNdryshoFjalekalimin;

    @FXML
    private JFXProgressBar progress;
    
    @FXML
    private Label lblMissmatch;
    
    @FXML
    private JFXTextField txtPort;
    
    public static ControllerLogin instance;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
		progress.setVisible(false);
		lblMissmatch.setVisible(false);
		
		handler = new ConnectionHandler();
		
	}
	
	public void loginAction(ActionEvent e) {
		if(txtPerdoruesi.getText().isEmpty() || txtFjalekalimi.getText().isEmpty() || txtPort.getText().isEmpty() || txtServer.getText().isEmpty()) {
			txtPerdoruesi.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: WHITE;");
			txtFjalekalimi.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: WHITE;");
			txtPort.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: WHITE;");
			txtServer.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: WHITE;");
			txtPerdoruesi.setPromptText("Perdoruesi eshte obligativ");
			txtFjalekalimi.setPromptText("Fjalëkalimi eshte obligativ");
			txtPort.setPromptText("Porti eshte obligativ");
			txtServer.setPromptText("Serveri eshte obligativ");
		}
		else {
			progress.setVisible(true);
			lblMissmatch.setVisible(false);
			PauseTransition pt = new PauseTransition();
			pt.setDuration(Duration.seconds(3));
			pt.setOnFinished(ev -> {
				
				connection = handler.getConnection();
				
				String gjejPerdoruesin = "Select * from tblPerdoruesit where perdoruesi = ? and fjalekalimi = ?";
				
				try {
					pst = connection.prepareStatement(gjejPerdoruesin);
					pst.setString(1, txtPerdoruesi.getText());
					pst.setString(2, txtFjalekalimi.getText());
					ResultSet rs = pst.executeQuery();				
					int count = 0;
					
					while(rs.next()) {
						count = count + 1;
						}
					
					if(count == 1) {						
						btnKyqu.getScene().getWindow().hide();
						NewScene root = new NewScene();
						root.newScene("/FXML/HomePage.fxml", 1000, 600, false);
						}					
					else {
						progress.setVisible(false); 
						lblMissmatch.setVisible(true);
						}
					} 
				catch (SQLException e1) {
					
					e1.printStackTrace();
					}
				
				finally {
					
					try {
						connection.close();
						} 
					catch (SQLException e1) {
						
						e1.printStackTrace();
						};
						}
				});
			pt.play();
		}
	}
	
	public void btnAnuloAction(ActionEvent e) throws IOException {
		System.exit(0);
	}
	
	public ControllerLogin() {
    	instance = this;
    }
    
    public static ControllerLogin getInstance() {
    	return instance;
    }
    
    public String username() {
    	return txtPerdoruesi.getText();
    }
    
    public String port() {
    	return txtPort.getText();
    }
    
    public String server() {
    	return txtServer.getText();
    }
}