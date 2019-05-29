package estados;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import dialogos.DialogoInsertarLocalizacion;
import dialogos.DialogoInsertarMuestra;
import idiomas.ControladorIdioma;
import lineaSerie.LineaSeriePrincipal;
import objetos.Localizacion;
import objetos.UsuarioVO;

public class GestorEstadosAnadirMuestra {

	int state;
	Localizacion localizacion;
	DialogoInsertarMuestra dialogoInsertarMuestra;
	LineaSeriePrincipal lsP;
	JFrame ventana;
	ControladorIdioma listaPalabras;
	Thread hiloProgressBar;
	JProgressBar progressBar;
	UsuarioVO usuario;
	public GestorEstadosAnadirMuestra(int state, JFrame ventana, DialogoInsertarMuestra dialogoInsertarMuestra, ControladorIdioma controladorIdioma, UsuarioVO usuario) {
		super();
		this.usuario=usuario;
		lsP = new LineaSeriePrincipal();
		this.state = state;
		System.out.println("Nuevo objeto, estado: "+this.state);
		this.ventana = ventana;
		this.listaPalabras = controladorIdioma;
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
			DialogoInsertarMuestra(ventana, listaPalabras.getListaPalabras().get(2), true, listaPalabras, this, lsP, usuario);
			localizacion=dialogoInsertarMuestra.getLocalizacion();
			break;
			
		case 2://a�adir localizacion
			@SuppressWarnings("unused") 
			DialogoInsertarLocalizacion dialogoInsertarLocalizacion= 
			new DialogoInsertarLocalizacion(this,dialogoInsertarMuestra, listaPalabras.getListaPalabras().get(2), true, listaPalabras, usuario);
			break;
			
		case 3://sensor
			dialogoInsertarMuestra.gestionarStart();
			break;
			
		default:
			break;
		}
	}
	
}
