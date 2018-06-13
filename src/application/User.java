package application;

import java.util.ArrayList;
import java.util.List;

public class User {
	String User;
	
	public List<String> Users = new ArrayList<String>();
	
	public String getUsers() {
		return Users.toString();
	}
	
	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public User(String user) {
		this.User = user;
		Users.add(User);
	}
}
