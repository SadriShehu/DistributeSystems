package Controllers;

import java.io.IOException;
import java.net.ServerSocket;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Transcript;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import javafx.application.Platform;
import javafx.event.ActionEvent;
public class serverMainController implements Initializable{
		
    @FXML
    private TextArea txtServerLog;

    @FXML
    private JFXButton btnKyqu;

    @FXML
    private JFXTextField txtHost;

    @FXML
    private JFXTextField txtPort;

    @FXML
    private JFXButton btnAnulo;
    
    private int clientNo = 0;
    private Transcript transcript;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void btnKyquAction() {
		int port = Integer.parseInt(txtPort.getText());
		transcript = new Transcript();
	      
	      new Thread( () -> {
	      try {
	        @SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(port);
	        txtServerLog.appendText("Server started normally " + " at " + new Date() + '\n');
	        while (true) {
	          // Listen for a new connection request
	          Socket socket = serverSocket.accept();
	    
	          // Increment clientNo
	          clientNo++;
	          
	          Platform.runLater( () -> {
	            // Display the client number
	        	  txtServerLog.appendText("Starting thread for client " + clientNo +
	              " at " + new Date() + '\n');
	            });
	          
	          // Create and start a new thread for the connection
	          new Thread(new HandleAClient(socket,transcript,txtServerLog)).start();
	        }
	      }
	      catch(IOException ex) {
	        System.err.println(ex);
	      }
	    }).start();
	    }
	
	public void btnCkyquAction(ActionEvent e) throws IOException {
		System.exit(0);
	}
}

class HandleAClient implements Runnable, application.ChatConstants {
    private Socket socket; // A connected socket
    private Transcript transcript; // Reference to shared transcript
    private TextArea textArea;
    private String handle;

    public HandleAClient(Socket socket,Transcript transcript,TextArea textArea) {
      this.socket = socket;
      this.transcript = transcript;
      this.textArea = textArea;
    }

	public void run() {      
    	try {
        // Create reading and writing streams
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outputToClient = new PrintWriter(socket.getOutputStream());

        // Continuously serve the client
        while (true) {
          // Receive request code from the client
          int request = Integer.parseInt(inputFromClient.readLine());
          // Process request
          switch(request) {
              case SEND_HANDLE: {
                  handle = inputFromClient.readLine();
                  break;
              }
              case SEND_COMMENT: {
                  String comment = inputFromClient.readLine();
                  transcript.addComment(handle + "> " + comment);
                  break;
              }
              case GET_COMMENT_COUNT: {
                  outputToClient.println(transcript.getSize());
                  outputToClient.flush();
                  break;
              }
              case GET_COMMENT: {
                  int n = Integer.parseInt(inputFromClient.readLine());
                  outputToClient.println(transcript.getComment(n));
                  outputToClient.flush();
              }
          }
        }
      }
      catch(IOException ex) {
          Platform.runLater(()->textArea.appendText("Exception in client thread: "+ex.toString()+"\n"));
      }
    }
  }
