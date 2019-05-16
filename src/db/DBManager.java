package db;
import java.io.IOException;
import java.sql.*;

public class DBManager {
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	private String USER = "root";
	private String PASS = "";
	private String DBNAME = "osen";
	public void conClose() {
		try {
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		connect = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/"+this.DBNAME,this.USER,this.PASS);
			System.out.println("Conexion con la DB establecida");
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error conectando la base de datos");
		}
		return connect;
	}
	public ResultSet executeQuery(String query) {
		connect = this.getConnection();
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	public int executeUpdate(String query) {
		connect = this.getConnection();
		int result=0;
		try {
			statement = connect.createStatement();
			result = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean execute(String query) {
		connect = this.getConnection();
		boolean result=false;
		try {
			statement = connect.createStatement();
			result = statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}