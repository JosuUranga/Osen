package modelos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;
import muestras.Meteorologia;

public class MeteoDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callStatement;
	static MeteoDAO instance;
	List<Meteorologia> listaMeteo;
	Meteorologia meteo;
	private final static String getMeteoLocalizaciones="{CALL get_meteo(?)}";
	private final static String getMeteoAll="{CALL get_meteoAll()}";
	
	protected MeteoDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static MeteoDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new MeteoDAO(u, p, db, ip);
		}
		return instance;
	}
	public List<Meteorologia> getMeteo(String nombre) throws SQLException{
		callStatement=executeCall(getMeteoLocalizaciones);
		callStatement.setString(1, nombre);
		resultSet=callStatement.executeQuery();
		listaMeteo=new ArrayList<>();
		while(resultSet.next()) {
			meteo=new Meteorologia(resultSet.getInt(1),resultSet.getString(2));
			listaMeteo.add(meteo);
		}
		conClose();
		return listaMeteo;
	}
	public List<Meteorologia> getMeteoAll() throws SQLException{
		callStatement=executeCall(getMeteoAll);
		resultSet=callStatement.executeQuery();
		listaMeteo=new ArrayList<>();
		while(resultSet.next()) {
			meteo=new Meteorologia(resultSet.getInt(1),resultSet.getString(2));
			listaMeteo.add(meteo);
		}
		conClose();
		return listaMeteo;
	}

}
