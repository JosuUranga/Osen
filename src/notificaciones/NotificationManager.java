package notificaciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Timer;

import modelos.MuestrasDAO;
import objetos.MuestraCo2;
import objetos.MuestraVO;
import objetos.UsuarioVO;
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
	MuestraVO muestra;
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
					if(ultimaMuestra<muestraActual) {
						if(muestra2.getCo2eq()>420) body = "El CO2eq es de: "+muestra2.getCo2eq()+"\n Precaución! Los niveles CO2eq en el aire son bastante altos.";
						else body= "El CO2eq es de: "+muestra2.getCo2eq()+"\n Los niveles de CO2 stán estables.";
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
