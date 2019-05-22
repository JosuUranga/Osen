package modelos;

public class UsuarioVO { 
	int usuarioID;
	String nombre;
	String email;
	int localizacionId;
	int idiomaSeleccionado; 
	int tipo;
	public final static int NORMAL=0;
	public final static int PRO=1;
	public final static int ADMIN=2;
	 
	public UsuarioVO(int ID, String nombre, String email, int localizacion, int idioma) { 
		this.idiomaSeleccionado=idioma; 
		this.usuarioID=ID;
		this.nombre=nombre;
		this.email=email;
		this.localizacionId=localizacion;
		tipo=0;
	} 
 
	public int getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLocalizacion() {
		return localizacionId;
	}

	public void setLocalizacion(int localizacion) {
		this.localizacionId = localizacion;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		if(tipo>=0 && tipo<3)
			this.tipo = tipo;
	}

	public int getIdiomaSeleccionado() { 
		return idiomaSeleccionado; 
	} 
 
	public void setIdiomaSeleccionado(int idiomaSeleccionado) { 
		this.idiomaSeleccionado = idiomaSeleccionado; 
	} 
	 
} 
