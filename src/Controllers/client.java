package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.jfoenix.controls.JFXButton;

import application.MicThread;
import common.SoundPacket;
import common.Utils;
import application.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class client implements Initializable{

	@FXML
    private TextArea txtServerLog;

    @FXML
    private JFXButton btnKyqu;

    @FXML
    private JFXButton btnAnulo;
    
    private Thread client;
	
    public void btnKyquAction() {
    	try {
    		String server = ControllerLogin.getInstance().server();
    		client = new Thread(new Client(server, 9999));
            client.start(); //connect to specified server at specified port
            txtServerLog.appendText("Sherbimi me ze eshte aktivizuar. Kliko Anulo per te mbyllur!");
        } catch (Exception ex) { //connection failed
            txtServerLog.appendText(ex.toString());
            return;
        }
    	
    	micTester.close();
    }
    
	@SuppressWarnings("deprecation")
	public void btnCkyquAction(ActionEvent e) throws IOException {
		if(this.client.isAlive()) {
			this.client.stop();
			Stage stage = (Stage) btnAnulo.getScene().getWindow();
			stage.close();
		} else {
			Stage stage = (Stage) btnAnulo.getScene().getWindow();
			stage.close();
		}
	}
    
    private MicTester micTester;
    //this class is used to test the microphone. it manages the volume meter
    private class MicTester extends Thread{
        private TargetDataLine mic = null;
        public MicTester() {
        	
        }
        
        @Override
        public void run() {
                
                try {
                    AudioFormat af = SoundPacket.defaultFormat;
                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
                    mic = (TargetDataLine) (AudioSystem.getLine(info));
                    mic.open(af);
                    mic.start();
                } catch (Exception e) {
                    txtServerLog.appendText("Microphone not detected.\nPress OK to close this program");
                    System.exit(0);
                }
                while(true) {
                    Utils.sleep(10);
                    if(mic.available()>0){
                        byte[] buff=new byte[SoundPacket.defaultDataLenght];
                        mic.read(buff,0,buff.length);
                    }
                }
            }
        
		@SuppressWarnings("deprecation")
		private void close(){
			if(mic!=null) mic.close();
            stop();
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		
		micTester=new MicTester();
        micTester.start();
        int micVol = 100;
        MicThread.amplification=((double)(micVol))/100.0;
	}
}