package osen;

public class Usuario { 
	int usuarioID;
	String nombre;
	String email;
	int localizacion;
	String idiomaSeleccionado; 
	int tipo;
	 
	public Usuario(int ID, String nombre, String email, int localizacion, String idioma) { 
		this.idiomaSeleccionado=idioma; 
		this.usuarioID=ID;
		this.nombre=nombre;
		this.email=email;
		this.localizacion=localizacion;
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
		return localizacion;
	}

	public void setLocalizacion(int localizacion) {
		this.localizacion = localizacion;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		if(tipo>0 && tipo<3)
			this.tipo = tipo;
	}

	public String getIdiomaSeleccionado() { 
		return idiomaSeleccionado; 
	} 
 
	public void setIdiomaSeleccionado(String idiomaSeleccionado) { 
		this.idiomaSeleccionado = idiomaSeleccionado; 
	} 
	 
} 
