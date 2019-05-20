package muestras;

public class Muestra {
	String fecha;
	float duracion;
	String metorologia;
	Localizacion localizacion;
	String usuario;
	
	public Muestra(String fecha, float duracion, String metorologia, Localizacion localizacion, String usuario) {
		super();
		this.fecha = fecha;
		this.duracion = duracion;
		this.metorologia = metorologia;
		this.localizacion = localizacion;
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}

	public String getMetorologia() {
		return metorologia;
	}

	public void setMetorologia(String metorologia) {
		this.metorologia = metorologia;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
