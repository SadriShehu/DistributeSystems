package Controllers;

import java.net.URL;
import application.Qyteti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DBConnections.ConnectionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class qytetController implements Initializable{
	
	private Connection connection;
	private ConnectionHandler handler;
	private PreparedStatement pst, pst1;	
	
    @FXML
    private JFXTextField txtQyteti;

    @FXML
    private JFXButton btnRegjistro;

    @FXML
    private JFXTextField txtZIP;

    @FXML
    private TableView<Qyteti> tblQyteti;
    
    @FXML
    private TableColumn<Qyteti, String> ID;
    
    @FXML
    private TableColumn<Qyteti, String> Qyteti;
    
    @FXML
    private TableColumn<Qyteti, String> ZIP;
    
    @FXML
    private Label lblRezultati;
    
    ObservableList<Qyteti> QytetiList = FXCollections.observableArrayList();
    
    //clientListenerDB clientListen;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		handler = new ConnectionHandler();
		updateTable();
	}
	
	public void updateTable() {
		
		connection = handler.getConnection();
		String merrQytet = "SELECT * FROM tblQyteti";
		try {
			tblQyteti.getItems().clear();
			
			pst1 = connection.prepareStatement(merrQytet);
			ResultSet rs1 = pst1.executeQuery();
			ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			Qyteti.setCellValueFactory(new PropertyValueFactory<>("Qyteti"));
			ZIP.setCellValueFactory(new PropertyValueFactory<>("ZIP"));
			
			while(rs1.next()) {
				QytetiList.add(new Qyteti(rs1.getString("qid"), rs1.getString("Qyteti"), rs1.getString("zip")));
			}
			
			tblQyteti.setItems(QytetiList);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void btnRegjistroAction(ActionEvent e) {
		if(txtQyteti.getText().isEmpty() || txtZIP.getText().isEmpty()) {
			txtQyteti.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			txtZIP.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			txtQyteti.setPromptText("Qyteti duhet te shenohet");
			txtZIP.setPromptText("ZIP duhet te shenohet");
		}
		else {
			connection = handler.getConnection();
			String insertoQytet = "Insert into tblqyteti values(null, ?, ?)";

			try {
				pst = connection.prepareStatement(insertoQytet);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				pst.setString(1, txtQyteti.getText());
				pst.setString(2, txtZIP.getText());
				pst.executeUpdate();
				lblRezultati.setText("Insertimi u krye me sukses!");
				updateTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				lblRezultati.setText("Insertimi ka deshtuar!");
				lblRezultati.setStyle("-fx-text-fill: RED;");
				e1.printStackTrace();
			}
		}
	}
}