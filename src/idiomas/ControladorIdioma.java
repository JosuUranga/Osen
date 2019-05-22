package idiomas; 
 
import java.beans.PropertyChangeListener; 
import java.beans.PropertyChangeSupport; 
import java.util.List; 
 
public class ControladorIdioma { 
	 
	final static String ficheroCastellano="ficheros/Castellano.txt"; 
	final static String ficheroEuskara="ficheros/Euskara.txt"; 
	final static String ficheroIngles="ficheros/Ingles.txt"; 
	 
	PropertyChangeSupport soporte; 
	FicheroIdioma ficheroIdioma; 
	int idioma; 
	 
	public ControladorIdioma(int idioma) { 
		soporte=new PropertyChangeSupport(this); 
		this.idioma=idioma; 
	} 
	 
	public void addPropertyChangeListener(PropertyChangeListener listener) { 
		soporte.addPropertyChangeListener(listener); 
	} 
	public void removePropertyChangeListener(PropertyChangeListener listener) { 
		soporte.removePropertyChangeListener(listener); 
	} 
	 
	public void cambiarIdioma(int idioma) { 
		this.idioma=idioma; 
	} 
	 
	public List<String> getListaPalabras() { 
		return ficheroIdioma.getListaPalabras(); 
	} 
 
	public void cargarIdioma() { 
		switch(idioma) { 
		case 0: 
			ficheroIdioma=new FicheroIdioma(ficheroCastellano); 
		break; 
		case 1: 
			ficheroIdioma=new FicheroIdioma(ficheroEuskara); 
		break; 
		case 2: 
			ficheroIdioma=new FicheroIdioma(ficheroIngles); 
		break; 
		} 
	soporte.firePropertyChange("idioma", null, null); 
	} 
} 
