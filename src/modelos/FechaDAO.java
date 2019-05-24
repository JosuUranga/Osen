package modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;

public class FechaDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callStatement;
	static FechaDAO instance;
	List<String>listaFechas;
	private final static String getFechas="{CALL get_fechas(?,?)}";
	
	protected FechaDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static FechaDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new FechaDAO(u, p, db, ip);
		}
		return instance;
	}
	public List<String> getFechas(String nombre, int id)throws SQLException{
		callStatement=executeCall(getFechas);
		callStatement.setString(1, nombre);
		callStatement.setInt(2, id);
		resultSet=callStatement.executeQuery();
		listaFechas=new ArrayList<>();
		while(resultSet.next()) {
			String fecha=resultSet.getString(1);
			listaFechas.add(fecha);
		}
		conClose();
		return listaFechas;
	}
}
