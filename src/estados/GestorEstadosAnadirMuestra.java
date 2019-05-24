package estados;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import db.DBManager;
import dialogos.DialogoInsertarLocalizacion;
import dialogos.DialogoInsertarMuestra;
import lineaSerie.LineaSeriePrincipal;
import modelos.UsuarioVO;
import muestras.Localizacion;

public class GestorEstadosAnadirMuestra {

	int state;
	Localizacion localizacion;
	DialogoInsertarMuestra dialogoInsertarMuestra;
	LineaSeriePrincipal lsP;
	JFrame ventana;
	List<String>listaPalabras;
	Thread hiloProgressBar;
	JProgressBar progressBar;
	UsuarioVO usuario;
	public GestorEstadosAnadirMuestra(int state, JFrame ventana, DialogoInsertarMuestra dialogoInsertarMuestra, List<String> list,
			DBManager manager, UsuarioVO usuario) {
		super();
		this.usuario=usuario;
		lsP = new LineaSeriePrincipal();
		this.state = state;
		System.out.println("Nuevo objeto, estado: "+this.state);
		this.ventana = ventana;
		this.listaPalabras = list;
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
		case 1://a�adir muestra
			dialogoInsertarMuestra= new 
			DialogoInsertarMuestra(ventana, listaPalabras.get(2), true, listaPalabras, this, lsP, usuario);
			localizacion=dialogoInsertarMuestra.getLocalizacion();
			break;
			
		case 2://a�adir localizacion
			@SuppressWarnings("unused") 
			DialogoInsertarLocalizacion dialogoInsertarLocalizacion= 
			new DialogoInsertarLocalizacion(this,dialogoInsertarMuestra, listaPalabras.get(2), true, listaPalabras);
			break;
			
		case 3://sensor
			dialogoInsertarMuestra.gestionarStart();
			break;
			
		default:
			break;
		}
	}
	
}
