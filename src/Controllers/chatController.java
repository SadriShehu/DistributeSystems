package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.User;
import application.clientMain;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class chatController implements Initializable{

    @FXML
    private TableView<User> tblAnetaretAktiv;

    @FXML
    private TableColumn<User, String> listaAnetareve;

    @FXML
    private JFXTextArea txtShkruaj;

    @FXML
    private JFXButton btnSend;

    @FXML
    private TextArea txtMesazhet;
    
    @FXML
    private Label onlineCountLabel;
    
    @FXML
    private Label usernameLabel;
    
    @FXML
    private JFXButton btnAudio;
    
    private clientListener gateway;
    
    ObservableList<User> AnetarList = FXCollections.observableArrayList();
    
    String user = ControllerLogin.getInstance().username();
       
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gateway = new clientListener(txtMesazhet);
        gateway.sendHandle(user);
        updateTable();
        new Thread(new TranscriptCheck(gateway, txtMesazhet)).start();
	}

	public void btnSendAction() throws IOException {
		String mesazhi = txtShkruaj.getText();
		if(!(mesazhi == null)) {
			gateway.sendComment(mesazhi);
			txtShkruaj.clear();
		}
		
	}
	
	public void enterAction(KeyEvent event){
		if(event.getCode().equals(KeyCode.ENTER))
	    {
			String mesazhi = txtShkruaj.getText().replaceAll("\\n", "");
			if(!(mesazhi == null)) {
				gateway.sendComment(mesazhi);
				txtShkruaj.clear();
			}
	    }
	}
	
	public void btnAudioAction() throws IOException {
		String mesazhi = "Filloi lidhje me zë!";
		gateway.sendComment(mesazhi);
		clientMain start = new clientMain();
		try {
			Stage NewStage = new Stage();
			start.start(NewStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	public void updateTable() {
		listaAnetareve.setCellValueFactory(new PropertyValueFactory<>("User"));
		AnetarList.add(new User(user));
		tblAnetaretAktiv.setItems(AnetarList);
	}
}

class TranscriptCheck implements Runnable, application.ChatConstants {
    private clientListener gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private int N; // How many comments we have read
    
    /** Construct a thread */
    public TranscriptCheck(clientListener gateway,TextArea textArea) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.N = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
          if(gateway.getCommentCount() > N) {
              String newComment = gateway.getComment(N);
              Platform.runLater(()->textArea.appendText(newComment + "\n"));
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }