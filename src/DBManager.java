import java.io.IOException;
import java.sql.*;

public class DBManager {
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	private String USER = "root";
	private String PASS = "INnO4BcGBWIKa1HAfqlO51EeGwdF";
	private String DBNAME = "osen";
	public static void main(String[] args) throws IOException {
		DBManager manager=new DBManager();
		System.out.println(manager.executeUpdate("DROP DATABASE IF EXISTS OSEN;"));
		
	}
	protected DBManager() {
		
	}
	
	public Connection getConnection() {
		Connection connect = null;
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
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(query);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	public int executeUpdate(String query) {
		Connection con = this.getConnection();
		int result=0;
		try {
			statement = con.createStatement();
			result = statement.executeUpdate(query);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean execute(String query) {
		Connection con = this.getConnection();
		boolean result=false;
		try {
			statement = con.createStatement();
			result = statement.execute(query);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}

