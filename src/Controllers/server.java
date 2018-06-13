package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Log;
import application.Server;
import common.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class server implements Initializable{
	
	@FXML
    private TextArea txtServerLog;

    @FXML
    private JFXButton btnKyqu;

    @FXML
    private JFXButton btnAnulo;
	
    public void btnKyquAction() {
    	new Thread() { //start server in new thread
            @Override
            public void run() {
                try {
					new Server(9999);
                } catch (Exception ex) {
                    txtServerLog.appendText(ex.toString());
                    System.exit(0);
                }
            }
        }.start();
        
        new Thread() { //start logger
            @Override
            public void run() {
                for (;;) {
                    Utils.sleep(100);
                    if (!Log.get().equals(txtServerLog.getText())) {
                    	txtServerLog.setText(Log.get());
                    }
                }
            }
        }.start();
    }
    
    public void btnCkyquAction(ActionEvent e) throws IOException {
		System.exit(0);
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub   
	}
}
