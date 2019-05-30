package objetos;

public class MuestraOzono extends MuestraVO{
	int oxigeno;
	float humedad;
	float temperatura;
	float ozono;
	public MuestraOzono(int id, String fecha, float duracion, int oxigeno, float humedad, float temperatura, float ozono, 
			String metorologia, LocalizacionVO localizacion, String usuario) {
		super(id,fecha, duracion, metorologia, localizacion, usuario);
		this.oxigeno = oxigeno;
		this.humedad = humedad;
		this.temperatura = temperatura;
		this.ozono = ozono;
	}
	public int getOxigeno() {
		return oxigeno;
	}
	public void setOxigeno(int oxigeno) {
		this.oxigeno = oxigeno;
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
	public float getOzono() {
		return ozono;
	}
	public void setOzono(float ozono) {
		this.ozono = ozono;
	}
	@Override
	public String[] ensenarTexto() {
		String[] info= {this.getLocalizacion().getNombre(), Float.toString(this.getLocalizacion().getArea()),
				Integer.toString(this.getLocalizacion().getHabitantes()), Float.toString(this.getLocalizacion().getDensidad()),
				Float.toString(this.getTemperatura()), Integer.toString(this.getOxigeno()), Float.toString(this.getHumedad()),
				Float.toString(this.getOzono()), Integer.toString(this.getId()),this.getFecha(),this.getMetorologia(), this.getUsuario()};
		return info;
	}
	
	
	
}
