package modelos;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DBManager;
import muestras.Localizacion;
import muestras.Muestra;
import muestras.MuestraCo2;

public class MuestrasDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callStatement;
	static MuestrasDAO instance;
	public final static String getMuestra="{CALL get_muestra(?)}";

	protected MuestrasDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static MuestrasDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new MuestrasDAO(u, p, db, ip);
		}
		return instance;
	}	
	public Muestra getMuestra(int id, String fecha) {
		Muestra muestra=null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = sdf1.parse(fecha);
			callStatement=executeCall(getMuestra);
			callStatement.setInt(0, id);
			callStatement.setDate(1, new Date(date.getTime()));
			resultSet=callStatement.executeQuery();
			resultSet.next();
			float duracion=(float) 10.5;
			muestra=new MuestraCo2(resultSet.getInt(1), resultSet.getString(2), duracion, resultSet.getInt(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getFloat(6), resultSet.getString(7), new Localizacion(resultSet.getString(8),resultSet.getInt(9),resultSet.getFloat(10)), resultSet.getString(11));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return muestra;
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

	public void addMuestra(String name, String password,String email,int localizacion,int idioma,int type,int id) {
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