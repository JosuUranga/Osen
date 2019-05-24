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
	public UsuarioVO getUser(String email, String password) throws SQLException{
		UsuarioVO user=null;
		resultSet = executeQuery(
				"select * "
				+ "from Usuarios JOIN RelacionTipoUsuarios ON Usuarios.usuarioID=RelacionTipoUsuarios.usuario "
				+ "where email='" + email + "' and pass='" + password + "';");
		while (resultSet.next()) {
			user = new UsuarioVO(resultSet.getInt("usuarioID"), resultSet.getString("nombre"),
					resultSet.getString("email"),resultSet.getString("pass"), resultSet.getInt("localizacion"),
					resultSet.getInt("idioma"));
			user.setTipo(resultSet.getInt("tipo"));
		}
		conClose();
		return user;
	}

	public void updateUser(String name, String password,String email,int localizacion,int idioma,int type,int id) throws SQLException{
		executeUpdate("UPDATE Usuaios SET nombre='"+name+"', email='"+email+"', localizacion="+localizacion+", idioma="+idioma+ " WHERE usuarioID=" + id + ";");
		executeUpdate("UPDATE RelacionTipoUsuarios SET tipo="+type+ " WHERE usuario=" + id + ";");
	}

	public void addUser(String name, String password,String email,int idioma) throws SQLException{
		String localizacion ="null";
		int type = 0;
		executeUpdate(
				"INSERT INTO Usuarios (nombre, email, pass, localizacion, idioma) VALUES ('"
						+ name + "', '" + email + "', '" + password + "', " + localizacion + ", " + idioma
						+");");
		resultSet=executeQuery("SELECT usuarioID FROM Usuarios WHERE email='"+email+"'");
		resultSet.next();
		int id=resultSet.getInt("usuarioID");
		conClose();
		executeUpdate("INSERT INTO RelacionTipoUsuarios (fecini, fecfinal, tipo, usuario)VALUES (CURDATE(), NULL, "+type+", "+id+");");
	}
}
