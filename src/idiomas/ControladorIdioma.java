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
			System.out.println("castellano");
			ficheroIdioma=new FicheroIdioma(ficheroCastellano); 
		break; 
		case 1: 
			System.out.println("euskara");
			ficheroIdioma=null;
			ficheroIdioma=new FicheroIdioma(ficheroEuskara); 
		break; 
		case 2: 
			ficheroIdioma=null;
			System.out.println("ingles");
			ficheroIdioma=new FicheroIdioma(ficheroIngles); 
		break; 
		} 
	} 
} 
