package muestras;



public class Muestra {
	String fecha;
	float duracion;
	int co2eq;
	float humedad;
	float temperatura;
	float voc;
	String metorologia;
	Localizacion localizacion;
	String usuario;
	
	public Muestra(String fecha, float duracion, int co2eq, float humedad, float temperatura, float voc,
			String metorologia, Localizacion localizacion, String usuario) {
		super();
		this.fecha = fecha;
		this.duracion = duracion;
		this.co2eq = co2eq;
		this.humedad = humedad;
		this.temperatura = temperatura;
		this.voc = voc;
		this.metorologia = metorologia;
		this.localizacion = localizacion;
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public float getDuracion() {
		return duracion;
	}

	public int getCo2eq() {
		return co2eq;
	}

	public float getHumedad() {
		return humedad;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public float getVoc() {
		return voc;
	}

	public String getMetorologia() {
		return metorologia;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public String getUsuario() {
		return usuario;
	}

	

	
	
}

