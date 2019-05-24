package modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;

public class IdiomaDAO extends DBManager{
	Statement statement, tempStatement;
	ResultSet resultSet, tempResultSet;
	CallableStatement callStatement;
	static IdiomaDAO instance;
	List<String>listaIdiomas;
	private final static String get_idiomas="{CALL get_idiomas()}";
	private final static String get_idiomaUsuario="{CALL get_idiomaUsuario(?)}";
	
	protected IdiomaDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static IdiomaDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new IdiomaDAO(u, p, db, ip);
		}
		return instance;
	}
	public String getIdiomaUser(int id) throws SQLException{
		callStatement=executeCall(get_idiomaUsuario);
		callStatement.setInt(1, id);
		resultSet=callStatement.executeQuery();
		resultSet.next();
		String idioma=resultSet.getString(1);
		conClose();
		return idioma;
	}
	public List<String> getIdiomas() throws SQLException {
		callStatement=executeCall(get_idiomas);
		resultSet=callStatement.executeQuery();
		listaIdiomas=new ArrayList<>();
		while(resultSet.next()) {
			String idioma=resultSet.getString(1);
			listaIdiomas.add(idioma);
		}
		conClose();
		return listaIdiomas;
	}
}
