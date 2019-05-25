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
	static DBManager instance;
	protected DBManager(String u,String p,String db,String ip) {
		this.USER=u;
		this.PASS=p;
		this.DBNAME=db;
		this.IP=ip;
	}
	public static DBManager getInstance(String u,String p,String db,String ip) {
		if (instance == null) {
			instance = new DBManager(u,p,db,ip);
		}
		return instance;
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
					"jdbc:mysql://"+this.IP+":3306/"+this.DBNAME+"?verifyServerCertificate=true"+ "&useSSL=true&noAccessToProcedureBodies=true"+ "&requireSSL=true",this.USER,this.PASS);
			System.out.println("Conexion con la DB establecida");
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error conectando la base de datos");
		}
		return connect;
	}
	public ResultSet executeQuery(String query) throws SQLException{
		connect = this.getConnection();
		statement = connect.createStatement();
		resultSet = statement.executeQuery(query);
	
		return resultSet;
	}
	public int executeUpdate(String query) throws SQLException {
		connect = this.getConnection();
		int result=0;
		statement = connect.createStatement();
		result = statement.executeUpdate(query);
		conClose();
		return result;
	}
	public boolean execute(String query) throws SQLException {
		connect = this.getConnection();
		boolean result=false;
		statement = connect.createStatement();
		result = statement.execute(query);
		conClose();
		return result;
	}
	public CallableStatement executeCall(String sql) throws SQLException {
		callState = this.getConnection().prepareCall(sql);
			
		return callState;
	}
	public ResultSet reCall(CallableStatement call) throws SQLException {
		resultSet=call.executeQuery();
			
		return resultSet;
	}
	
	
	
}