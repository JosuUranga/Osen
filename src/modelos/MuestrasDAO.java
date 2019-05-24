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
	private final static String getMuestra="{CALL get_muestra(?,?,?)}";

	protected MuestrasDAO(String u, String p, String db, String ip) {
		super(u, p, db, ip);
	}
	public static MuestrasDAO getInstance(String u,String p,String db, String ip) {
		if (instance == null) {
			instance = new MuestrasDAO(u, p, db, ip);
		}
		return instance;
	}	
	public Muestra getMuestra(int id,String localizacion, String fecha) throws SQLException, ParseException{
		Muestra muestra=null;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(fecha);
		callStatement=executeCall(getMuestra);
		callStatement.setInt(1, id);
		callStatement.setString(2, localizacion);
		callStatement.setDate(3, new Date(date.getTime()));
		resultSet=callStatement.executeQuery();
		resultSet.next();
		float duracion=(float) 10.5;
		muestra=new MuestraCo2(resultSet.getInt(1), resultSet.getString(2), duracion, resultSet.getInt(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getFloat(6), resultSet.getString(7), new Localizacion(resultSet.getInt(11),resultSet.getString(8),resultSet.getInt(9),resultSet.getFloat(10)), resultSet.getString(12));
		
		conClose();
		return muestra;
	}

	public void delMuestra(int id)throws SQLException{
		
		execute("DELETE FROM Muestras WHERE muestraID="+id);
		
	}

	public void addMuestra(Float duracion, int co2eq,int humedad,int temp,int voc,int meteorologia,int localizacion,int usuario) throws SQLException{
		
		execute("INSERT INTO Muestras (fecha, duracion, co2eq,humedad,temperatura,voc,meteorologia,localizacion,usuario) "
					+ "VALUES (curdate(), "+duracion+", "+co2eq+", "+humedad+", "+temp+", "+voc+", "+meteorologia+", "+ localizacion+", "+usuario+")");
		
	}

}
