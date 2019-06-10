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
	private static final int MINUTOS = 00;
	private static final int SEGUNDOS = 30;
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
			System.out.println(user.getLocalizacion());
			muestra=MuestrasDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getUltimaMuestra(user.getLocalizacion());
			System.out.println("user loca: "+user.getLocalizacion());
			System.out.println("muestra loca:"+muestra.getLocalizacion());
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
					System.out.println("Localizacion user:"+user.getLocalizacion());
					System.out.println("ultimamuestra int:"+ultimaMuestra);
					System.out.println("MuestraActual int:"+muestraActual);

					if(ultimaMuestra<muestraActual) {
						if(muestra2.getCo2eq()>420) body = "Buenas "+user.getNombre()+",\n\n�Soy Carlos de OSEN, y vengo a informarte de que hay una nueva muestra en tu ubicaci�n!\nEstos son los niveles que se han recogido en tu ubicaci�n.\n El nivel de CO2eq es de: "+muestra2.getCo2eq()+"\n Precauci�n! Los niveles CO2eq en el aire son bastante altos.\n Los niveles de VOC son: "+muestra2.getVoc()+"\nEstas muestras han sido tomadas en estas condiciones:\nCondici�n meteorol�gica: "+muestra2.getMetorologia()+"\nHumedad: "+muestra2.getHumedad()+"% HR\nTemperatura: "+muestra2.getTemperatura()+"\n\nPara mas detalles abre la aplicaci�n de OSEN. ^^\n Gracias por suscribirte,\nCarlos";
						else body= "Buenas "+user.getNombre()+",\n\n�Soy Carlos de OSEN, y vengo a informarte de que hay una nueva muestra en tu ubicaci�n!\nEstos son los niveles que se han recogido en tu ubicaci�n.\n El nivel de CO2eq es de: "+muestra2.getCo2eq()+"\n Los niveles de CO2 est�n estables.\n Los niveles de VOC son: "+muestra2.getVoc()+"\n Estas muestras han sido tomadas en estas condiciones:\nCondici�n meteorol�gica: "+muestra2.getMetorologia()+"\nHumedad: "+muestra2.getHumedad()+"% HR\nTemperatura: "+muestra2.getTemperatura()+"\n\nPara mas detalles abre la aplicaci�n de OSEN. ^^\n Gracias por suscribirte,\nCarlos";
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
	public void parar() {
		timer.stop();
	}
	
}
