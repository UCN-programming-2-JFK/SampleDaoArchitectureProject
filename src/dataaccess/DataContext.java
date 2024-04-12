package dataaccess;

import java.sql.*;

// this class is a singleton which stores a single connection object
// in the class instance and returns that object in calls to getConnection()
public class DataContext {

	static DataContext dataContextInstance = null;
	final static String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=MyTasks;user=Jakob;password=Test1234;encrypt=false;";
	private Connection connection;
	
	private DataContext() throws Exception {
		
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			throw new Exception("Error connecting to database. Error was: '" + e.getMessage() + "'", e);
		}
	}
	
	public static DataContext getInstance() throws Exception {
		if(dataContextInstance == null) {
			dataContextInstance = new DataContext();
		}
		return dataContextInstance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}