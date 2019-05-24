package lineaSerie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import gnu.io.CommPortIdentifier;


public class Lector extends Thread {
	SerialComm lineaSerie;
	
	CommPortIdentifier puerto;
	PropertyChangeSupport soporte;
	String datos[];

	public Lector(SerialComm lineaSerie, CommPortIdentifier puerto) {
		this.lineaSerie = lineaSerie;
		this.puerto = puerto;
		soporte= new PropertyChangeSupport(this);
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		soporte.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		soporte.removePropertyChangeListener(listener);
	}
	@Override
	public void run() {
		String mensaje = null;
		try {
			mensaje = lineaSerie.leer();		//espera aqui hasta que llega algo
			System.out.println("Recivido: "+mensaje);
			datos=mensaje.split("[$]");	
		}catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("fin hilo lector");
	}

	public String[] getDatos() {
		return datos;
	}
}
