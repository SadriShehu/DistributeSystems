package application;

import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("serial")
public class Qyteti implements Serializable{
	String ID, Qyteti, ZIP;
	
	public ObservableList<Qyteti> QytetiList = FXCollections.observableArrayList();
	
	public Qyteti(String ID, String Qyteti, String ZIP) {
		this.ID = ID;
		this.Qyteti = Qyteti;
		this.ZIP = ZIP;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getQyteti() {
		return Qyteti;
	}

	public void setQyteti(String qyteti) {
		Qyteti = qyteti;
	}

	public String getZIP() {
		return ZIP;
	}

	public void setZIP(String zIP) {
		ZIP = zIP;
	}
	
	
}
