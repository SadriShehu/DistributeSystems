package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Fshati;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.AnchorPane;

public class fshatController implements Initializable{
	private Connection connection;
	private ConnectionHandler handler;
	private PreparedStatement pst;
	private PreparedStatement pst1;

 	@FXML
    private AnchorPane homeDashboard;

 	@FXML
	private JFXTextField txtFshati;

	@FXML
	private JFXButton btnRegjistro;
 
	@FXML
	private TableView<Fshati> tblFshati;

	@FXML
	private Label lblRezultati;

	@FXML
	private JFXComboBox<String> cmbQyteti;
	
	@FXML
	private TableColumn<Fshati, String> ID; 
	
	@FXML
	private TableColumn<Fshati, String> Fshati;
	
	@FXML
	private TableColumn<Fshati, String> Qyteti;
	
	@FXML
	private TableColumn<Fshati, String> ZIP;
	
	ObservableList<Fshati> FshatiList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		handler = new ConnectionHandler();
		merrQytetet();
		updateTable();
	}
	
	public void merrQytetet() {
		connection = handler.getConnection();
		String merrQytetet = "SELECT Qyteti FROM tblQyteti";
				
		try {
			pst = connection.prepareStatement(merrQytetet);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				cmbQyteti.getItems().add(rs.getString("Qyteti"));
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	public void updateTable() {
		connection = handler.getConnection();
		String merrFshati = "SELECT f.fid, f.fshati, f.qyteti, q.zip FROM tblFshati f, tblQyteti q WHERE f.qyteti=q.qyteti";
		try {
			tblFshati.getItems().clear();
			
			pst1 = connection.prepareStatement(merrFshati);
			ResultSet rs1 = pst1.executeQuery();
			ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			Fshati.setCellValueFactory(new PropertyValueFactory<>("Fshati"));
			Qyteti.setCellValueFactory(new PropertyValueFactory<>("Qyteti"));
			ZIP.setCellValueFactory(new PropertyValueFactory<>("ZIP"));
			
			while(rs1.next()) {
				FshatiList.add(new Fshati(rs1.getString("fid"), rs1.getString("Fshati"), rs1.getString("Qyteti"), rs1.getString("zip")));
			}
			
			tblFshati.setItems(FshatiList);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	public void btnRegjistroAction(ActionEvent e) { 
		if(txtFshati.getText().isEmpty() || cmbQyteti.getValue().isEmpty()) {
			txtFshati.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			txtFshati.setPromptText("Fshati duhet te shenohet");
			cmbQyteti.setPromptText("Qyteti duhet te perzgjedhet");
			cmbQyteti.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			
		}
		else {			
			connection = handler.getConnection();
			String insertoQytet = "Insert into tblfshati values(null, ?, ?)";

			try {
				pst = connection.prepareStatement(insertoQytet);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				pst.setString(1, txtFshati.getText());
				pst.setString(2, cmbQyteti.getValue());
				pst.executeUpdate();
				lblRezultati.setStyle("-fx-text-fill: GREEN;");
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