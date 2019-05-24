package notificaciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Timer;

import modelos.MuestrasDAO;
import modelos.UsuarioVO;
import muestras.Muestra;
import muestras.MuestraCo2;
import osen.Principal;

public class NotificationManager extends Thread {
	private static final int MINUTOS = 60;
	private static final int SEGUNDOS = 00;
	private static final int UN_SEGUNDO = 1000;

	String key, body;
	Notificacion notificacion;
	Timer timer;
	Tiempo tiempo;
	UsuarioVO user;
	ResultSet resultado;
	Muestra muestra;
	int ultimaMuestra, muestraActual, co2eq;
	public NotificationManager(String key, UsuarioVO usuario) {
		this.key=key;
		this.user=usuario;
		notificacion=new Notificacion(key, user.getEmail());
		tiempo = new Tiempo();
		tiempo.setMinutos(MINUTOS);
		tiempo.setSegundos(SEGUNDOS);
		timer = new Timer(UN_SEGUNDO,new MiTimer());
	}
	@Override
	public void run() {
		try {
			muestra=MuestrasDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getUltimaMuestra(user.getLocalizacion());
			ultimaMuestra=muestra.getId();
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
				try {
					muestra=MuestrasDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
							.getUltimaMuestra(user.getLocalizacion());
					muestraActual=muestra.getId();
					MuestraCo2 muestra2=(MuestraCo2) muestra;
					co2eq=muestra2.getCo2eq();
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
