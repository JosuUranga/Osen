package db;
import java.sql.*;

import javax.swing.JComboBox;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

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
	
	public void cargarDatosLocalizacionMuestra(JComboBox<String> comboLocalizacion) {
		ResultSet resultados = this.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Muestras JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				"GROUP BY Localizaciones.localizacionID;");
		try {
			comboLocalizacion.removeAllItems();
			while(resultados.next()) {
				comboLocalizacion.addItem(resultados.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conClose();		
	}
	public void cargarDatosLocalizaciones(JComboBox<String> comboLocalizacion) {
		ResultSet resultados = this.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Localizaciones\r\n;");
		try {
			comboLocalizacion.removeAllItems();
			while(resultados.next()) {
				comboLocalizacion.addItem(resultados.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conClose();		
	}
	
	public void cargarDatosFecha(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo, JComboBox<String> comboFecha) {
		comboFecha.removeAllItems();
		
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		String meteo=comboMeteo.getSelectedItem().toString();
		
		ResultSet resultados = this.executeQuery("SELECT DISTINCT Muestras.fecha\r\n" + 
				"FROM (Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				"WHERE Localizaciones.nombre='"+pueblo+"' AND Meteos.descripcion='"+meteo+"';");
		
		try {
			while(resultados.next()) {
				comboFecha.addItem(resultados.getString("fecha"));
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		this.conClose();			
	}
	
	public void cargarDatosMeteo(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo) {
		comboMeteo.removeAllItems();
		
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		String condicion=(" WHERE nombre = '"+ pueblo+"' ");
		
		ResultSet resultados = this.executeQuery("SELECT DISTINCT Meteos.descripcion \r\n" + 
				"FROM (Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				condicion+";");
		
		try {
			while(resultados.next()) {
				comboMeteo.addItem(resultados.getString("descripcion"));
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		this.conClose();			
	}
	
}