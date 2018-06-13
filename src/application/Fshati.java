package application;

public class Fshati {
	String ID;
	String Fshati;
	String Qyteti;
	String ZIP;
	
	public Fshati(String ID, String Fshati, String Qyteti, String ZIP) {
		this.ID = ID;
		this.ZIP = ZIP;
		this.Qyteti = Qyteti;
		this.Fshati = Fshati;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFshati() {
		return Fshati;
	}
	public void setFshati(String fshati) {
		Fshati = fshati;
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

