package db;
import java.sql.*;

public class DBManager {
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callState;
	private String USER = "root";
	private String PASS = "";
	private String DBNAME = "osen";
	private String IP = "localhost";
	Connection connect;
	public DBManager(String u,String p,String db,String ip) {
		this.USER=u;
		this.PASS=p;
		this.DBNAME=db;
		this.IP=ip;
	}
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
					"jdbc:mysql://"+this.IP+":3306/"+this.DBNAME,this.USER,this.PASS);
			System.out.println("Conexion con la DB establecida");
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error conectando la base de datos");
		}
		return connect;
	}
	public ResultSet executeQuery(String query) {
		System.out.println(query);
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
	public boolean execute(String query) throws SQLException {
		connect = this.getConnection();
		boolean result=false;
		statement = connect.createStatement();
		result = statement.execute(query);
		return result;
	}
	public CallableStatement executeCall(String sql) {
		try {
			callState = this.getConnection().prepareCall(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return callState;
	}
	public ResultSet reCall(CallableStatement call) {
		try {
			resultSet=call.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return resultSet;
	}
	
	
	
}