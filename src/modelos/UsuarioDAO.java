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
				user = new UsuarioVO(resultSet.getInt("usuarioID"), resultSet.getString("nombre"),
						resultSet.getString("email"),resultSet.getString("pass"), resultSet.getInt("localizacion"),
						resultSet.getInt("idioma"));
				user.setTipo(resultSet.getInt("tipo"));
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public void updateUser(String name, String password,String email,int localizacion,int idioma,int type,int id) {
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			statement.executeUpdate("UPDATE Usuaios SET nombre='"+name+"', email='"+email+"', localizacion='"+localizacion+"', idioma='"+idioma+ "' WHERE usuarioID='" + id + "';");
			statement.executeUpdate("UPDATE RelacionTipoUsuarios SET tipo='"+type+ "' WHERE usuario='" + id + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addUser(String name, String password,String email,int localizacion,int idioma,int type,int id) {
		Connection con = this.getConnection();
		try {
			statement = con.createStatement();
			statement.executeUpdate(
					"INSERT INTO Usuarios (nombre, email, pass, localizacion, idioma) VALUES ('"
							+ name + "', '" + email + "', '" + password + "', '" + localizacion + "', '" + idioma
							+"');");
			statement.executeUpdate("INSERT INTO RelacionTipoUsuarios (fecini, fecfinal, tipo, usuario)VALUES (CURDATE(), NULL, '"+type+"', '"+id+");");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
