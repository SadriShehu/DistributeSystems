package DBConnections;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionHandler extends Configs {
	
	Connection dbconnection;
	
	public Connection getConnection() {
		
		String connectionString = "jdbc:mysql://"+ Controllers.ControllerLogin.getInstance().server().toString() + ":" + Configs.dbPort + "/" + Configs.dbName;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			dbconnection = DriverManager.getConnection(connectionString, Configs.dbUser, Configs.dbPass);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return dbconnection;
	}
}
