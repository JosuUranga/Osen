package objetos;

public abstract class MuestraVO {
	int id;
	String fecha;
	float duracion;
	String metorologia;
	LocalizacionVO localizacion;
	String usuario;

	public MuestraVO(int id, String fecha, float duracion, String metorologia, LocalizacionVO localizacion,
			String usuario) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.duracion = duracion;
		this.metorologia = metorologia;
		this.localizacion = localizacion;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
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

	public LocalizacionVO getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(LocalizacionVO localizacion) {
		this.localizacion = localizacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public abstract String[] ensenarTexto();
	
	
}
