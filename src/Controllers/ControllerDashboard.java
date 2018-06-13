package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnections.ConnectionHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerDashboard implements Initializable{
	private Connection connection;
	private ConnectionHandler handler;
	private PreparedStatement pst, pst1, pst2, pst3;	
	
    @FXML
    private Label nrPerdorues;
    
    @FXML
    private Label nrQyteti;
    
    @FXML
    private Label nrFshati;
    
    @FXML
    private Label nrRruge;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		handler = new ConnectionHandler();
		nrPerdoruesve();
	}
	
	public void nrPerdoruesve() {
		connection = handler.getConnection();
		
		String gjejPerdoruesin = "Select * from tblPerdoruesit";
		String gjejQytetet = "Select * from tblqyteti";
		String gjejFshatrat = "Select * from tblfshati";
		String gjejRruget = "Select * from tblrruge";
		
		try {
			pst = connection.prepareStatement(gjejPerdoruesin);
			pst1 = connection.prepareStatement(gjejQytetet);
			pst2 = connection.prepareStatement(gjejFshatrat);
			pst3 = connection.prepareStatement(gjejRruget);
			ResultSet rs = pst.executeQuery();
			ResultSet rs1 = pst1.executeQuery();
			ResultSet rs2 = pst2.executeQuery();
			ResultSet rs3 = pst3.executeQuery();
			
			int count = 0, count1 = 0, count2 = 0, count3 = 0;
			
			while(rs.next()) {
				count = count + 1;
				}
			
			while(rs1.next()) {
				count1 = count1 + 1;
			}
			
			while(rs2.next()) {
				count2 = count2 + 1;
			}
			
			while(rs3.next()) {
				count3 = count3 + 1;
			}
			
			String numriParse = Integer.toString(count);
			String numriParse1 = Integer.toString(count1);
			String numriParse2 = Integer.toString(count2);
			String numriParse3 = Integer.toString(count3);
			
			nrPerdorues.setText(numriParse);
			nrQyteti.setText(numriParse1);
			nrFshati.setText(numriParse2);
			nrRruge.setText(numriParse3);
			
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
	}

}
