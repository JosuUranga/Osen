package modelos;
import java.sql.*;
import db.DBManager;

public class UsuarioDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	static UsuarioDAO instance;

	protected UsuarioDAO(String u,String p,String db, String ip) {
		super(u, p, db, ip);
	}

	public static UsuarioDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new UsuarioDAO(u, p, db, ip);
		}
		return instance;
	}
	
	public Connection getConnection() {
		return super.getConnection();
	}
	
	public UsuarioVO getUser(String nombre, String password) {
		UsuarioVO user=null;
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(
					"select * from user where username='" + nombre + "' and password='" + password + "';");
			while (resultSet.next()) {
				user = new UsuarioVO(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getString("email"), resultSet.getInt("localizacion"),
						resultSet.getString("idioma"));
				user.setTipo(resultSet.getInt("tipo"));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public void updateUser(int id, String name, String username, String password, String birth, double initialWeight,
			double currentWeight, double height, double desiredWeight, int wantedDays, String type) {
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			statement.executeUpdate("UPDATE user SET TYPE='" + type + "', NAME='" + name + "', HEIGHT='" + height
					+ "', INITIAL_WEIGHT='" + initialWeight + "', CURRENT_WEIGHT='" + currentWeight + "', USERNAME='"
					+ username + "', PASSWORD='" + password + "', DESIRED_WEIGHT='" + desiredWeight + "',WANTED_DAYS='"
					+ wantedDays + "' WHERE ID='" + id + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addUser(String name, String username, String password, String birth, String initialWeight,
			String currentWeight, String height, String desiredWeight, String wantedDays, String type) {
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			statement.executeUpdate(
					"INSERT INTO `osen`.`user` (`TYPE`, `NAME`, `HEIGHT`, `INITIAL_WEIGHT`, `USERNAME`, `PASSWORD`, `BIRTH`, `DESIRED_WEIGHT`, `WANTED_DAYS`) VALUES ('"
							+ type + "', '" + name + "', '" + height + "', '" + initialWeight + "', '" + username
							+ "', '" + password + "', '" + birth + "', '" + desiredWeight + "', '" + wantedDays
							+ "');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
