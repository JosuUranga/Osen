package estados;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import db.DBManager;
import dialogos.DialogoInsertarLocalizacion;
import dialogos.DialogoInsertarMuestra;
import muestras.Localizacion;

public class GestorEstadosAnadirMuestra {

	int state;
	Localizacion localizacion;
	DialogoInsertarMuestra dialogoInsertarMuestra;
	JFrame ventana;
	List<String>listaPalabras;
	DBManager manager;
	Thread hiloProgressBar;
	JProgressBar progressBar;

	public GestorEstadosAnadirMuestra(int state, JFrame ventana, DialogoInsertarMuestra dialogoInsertarMuestra, List<String> list,
			DBManager manager) {
		super();
		this.state = state;
		System.out.println("Nuevo objeto, estado: "+this.state);
		this.ventana = ventana;
		this.listaPalabras = list;
		this.manager = manager;
		this.dialogoInsertarMuestra=dialogoInsertarMuestra;
		this.estados();
	}
	public void setState(int estado) {
		this.state=estado;
	}
	public int getState() {
		return state;
	}
	public void estados() {
		switch(state) {
		case 1://añadir muestra
			dialogoInsertarMuestra= new 
			DialogoInsertarMuestra(ventana, listaPalabras.get(2), true, listaPalabras,manager, this);
			localizacion=dialogoInsertarMuestra.getLocalizacion();
			break;
			
		case 2://añadir localizacion
			@SuppressWarnings("unused") DialogoInsertarLocalizacion dialogoInsertarLocalizacion= new 
			DialogoInsertarLocalizacion(this,dialogoInsertarMuestra, listaPalabras.get(2), true, listaPalabras, manager);
			break;
			
		case 3://sensor
			dialogoInsertarMuestra.gestionarStart();
			break;
			
		default:
			break;
		}
	}
	
}
