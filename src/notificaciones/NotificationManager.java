package notificaciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Timer;

import db.DBManager;
import osen.Usuario;

public class NotificationManager extends Thread {
	private static final int MINUTOS = 60;
	private static final int SEGUNDOS = 00;
	private static final int UN_SEGUNDO = 1000;

	String key, body;
	DBManager manager;
	Notificacion notificacion;
	Timer timer;
	Tiempo tiempo;
	Usuario user;
	ResultSet resultado;
	int ultimaMuestra, muestraActual, co2eq;
	public NotificationManager(String key, DBManager manager, Usuario usuario) {
		this.key=key;
		this.user=usuario;
		this.manager=manager;
		notificacion=new Notificacion(key, user.getEmail());
		tiempo = new Tiempo();
		tiempo.setMinutos(MINUTOS);
		tiempo.setSegundos(SEGUNDOS);
		timer = new Timer(UN_SEGUNDO,new MiTimer());
	}
	@Override
	public void run() {
		resultado = manager.executeQuery("SELECT muestraID FROM Muestras WHERE localizacion="+user.getLocalizacion()+" ORDER BY muestraID DESC LIMIT 1");
		try {
			resultado.next();
			ultimaMuestra=resultado.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		timer.start();
		super.run();
	}
	public class MiTimer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			tiempo.decrementar();
			if (tiempo.isACero()) {
				System.out.println("timer a 0, notificacion ");
				tiempo.setMinutos(MINUTOS);
				tiempo.setSegundos(SEGUNDOS);
				resultado = manager.executeQuery("SELECT muestraID, co2eq FROM Muestras WHERE localizacion="+user.getLocalizacion()+" ORDER BY muestraID DESC LIMIT 1");
				try {
					resultado.next();
					muestraActual=resultado.getInt(1);
					co2eq=resultado.getInt(2);
					if(ultimaMuestra<muestraActual) {
						if(co2eq>2500) body = "El CO2eq es de: "+co2eq+"\n Ponte una mascarilla que te mueres";
						else body= "El CO2eq es de: "+co2eq+"\n Tranqui que no pasa na";
						notificacion.enviarNotificacionMail("Nueva muestra disponible!", body);
						notificacion.enviarNotificacionWindows("Hay una nueva muestra disponible \n"+body);
						ultimaMuestra=muestraActual;
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				timer.restart();
			}
		}
	}
}
