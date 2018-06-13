package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
//import Controllers.clientListener;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import DBConnections.ConnectionHandler;
import application.Rruge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class rrugeController implements Initializable{
	
	private Connection connection;
	private ConnectionHandler handler;
	private PreparedStatement pst, pst1, pst2;
	
    @FXML
    private JFXTextField txtRruga;

    @FXML
    private JFXButton btnRegjistro;

    @FXML
    private TableView<Rruge> tblRruga;

    @FXML
    private TableColumn<Rruge, String> ID;

    @FXML
    private TableColumn<Rruge, String> Rruga;

    @FXML
    private TableColumn<Rruge, String> Fshati;

    @FXML
    private TableColumn<Rruge, String> Qyteti;

    @FXML
    private Label lblRezultati;

    @FXML
    private JFXComboBox<String> cmbQyteti;

    @FXML
    private JFXComboBox<String> cmbFshati;
    
    ObservableList<Rruge> RrugeList = FXCollections.observableArrayList();
    
    //private clientListener gateway;

    String user = ControllerLogin.getInstance().username();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		handler = new ConnectionHandler();
        
		merrQytet();
		merrFshat();
		updateTable();
	}
	
	public void merrFshat() {
		
		/*String merrFshat = "SELECT Fshati FROM tblFshati";
        gateway.sendHandle(user);
		gateway.getFshat(merrFshat);*/
		
		connection = handler.getConnection();
		String merrFshat = "SELECT Fshati FROM tblFshati";
		try {
			pst1 = connection.prepareStatement(merrFshat);
			
			ResultSet rs1 = pst1.executeQuery();
			
			while(rs1.next()) {
				cmbFshati.getItems().add(rs1.getString("Fshati"));
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
	
	public void merrQytet() {
		connection = handler.getConnection();
		String merrQytet = "SELECT Qyteti FROM tblQyteti";
		try {
			pst = connection.prepareStatement(merrQytet);
			
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
		String merrRruge = "SELECT * FROM tblRruge";
		try {
			tblRruga.getItems().clear();
			
			pst2 = connection.prepareStatement(merrRruge);
			ResultSet rs2 = pst2.executeQuery();
			
			ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			Rruga.setCellValueFactory(new PropertyValueFactory<>("Rruga"));
			Qyteti.setCellValueFactory(new PropertyValueFactory<>("Qyteti"));
			Fshati.setCellValueFactory(new PropertyValueFactory<>("Fshati"));
			
			while(rs2.next()) {
				RrugeList.add(new Rruge(rs2.getString("rid"), rs2.getString("Qyteti"), rs2.getString("Fshati"), rs2.getString("Rruga")));
			}
			
			tblRruga.setItems(RrugeList);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void btnRegjistroAction(ActionEvent e) {
		if(txtRruga.getText().isEmpty() || cmbQyteti.getValue().isEmpty() ||  cmbFshati.getValue().isEmpty()) {
			txtRruga.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			txtRruga.setPromptText("Rruga duhet te shenohet");
			cmbQyteti.setPromptText("Qyteti duhet te perzgjedhet");
			cmbQyteti.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			cmbFshati.setPromptText("Fshati duhet te perzgjedhet");
			cmbFshati.setStyle("-fx-prompt-text-fill: RED; -fx-text-fill: #000000;");
			
		}
		else {			
			connection = handler.getConnection();
			String insertoRruge = "Insert into tblRruge values(null, ?, ?, ?)";

			try {
				pst = connection.prepareStatement(insertoRruge);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				pst.setString(1, txtRruga.getText());
				pst.setString(2, cmbQyteti.getValue());
				pst.setString(3, cmbFshati.getValue());
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

/*class cmbFshat implements Runnable, application.ChatConstants {
    private clientListener gateway; // Gateway to the server
    private JFXComboBox<String> cmbQyteti; // Where to display comments
    private int N; // How many comments we have read
    
    public cmbFshat(clientListener gateway, JFXComboBox<String> cmbQyteti) {
      this.gateway = gateway;
      this.cmbQyteti = cmbQyteti;
      this.N = 0;
    }
    public void run() {
      while(true) {
          if(gateway.getCommentCount() > N) {
              String newComment = gateway.getComment(N);
              Platform.runLater(()->cmbQyteti.getItems().add(newComment));
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }*/
