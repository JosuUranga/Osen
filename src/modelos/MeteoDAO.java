package modelos;

import java.sql.*;

import db.DBManager;

public class MeteoDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callStatement;
	static MeteoDAO instance;
	private final static String getMeteo="{CALL get_meteo(?)}";
	
	protected MeteoDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static MeteoDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new MeteoDAO(u, p, db, ip);
		}
		return instance;
	}
	public void getMeteo(String nombre) {
		
	}

}
