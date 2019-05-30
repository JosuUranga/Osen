package objetos;

public class MuestraCo2 extends MuestraVO{
	
	
	int co2eq;
	float humedad;
	float temperatura;
	float voc;
	
	public MuestraCo2(int id, String fecha, float duracion, int co2eq, float humedad, float temperatura, float voc,String metorologia, LocalizacionVO localizacion, String usuario) {
		super(id, fecha, duracion, metorologia, localizacion, usuario);
		this.co2eq = co2eq;
		this.humedad = humedad;
		this.temperatura = temperatura;
		this.voc = voc;
	}

	public int getCo2eq() {
		return co2eq;
	}

	public void setCo2eq(int co2eq) {
		this.co2eq = co2eq;
	}

	public float getHumedad() {
		return humedad;
	}

	public void setHumedad(float humedad) {
		this.humedad = humedad;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(float temperatura) {
		this.temperatura = temperatura;
	}

	public float getVoc() {
		return voc;
	}

	public void setVoc(float voc) {
		this.voc = voc;
	}

	@Override
	public String[] ensenarTexto() {
		String[] info= {this.getLocalizacion().getNombre(), Float.toString(this.getLocalizacion().getArea()),
				Integer.toString(this.getLocalizacion().getHabitantes()), Float.toString(this.getLocalizacion().getDensidad()),
				Float.toString(this.getTemperatura()), Integer.toString(this.getCo2eq()), Float.toString(this.getHumedad()),
				Float.toString(this.getVoc()), Integer.toString(this.getId()),this.getFecha(),this.getMetorologia(), this.getUsuario()};
		return info;		
	}

	
	
}

