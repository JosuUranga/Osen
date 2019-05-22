package muestras;

public class MuestraOzono extends Muestra{
	int oxigeno;
	float humedad;
	float temperatura;
	float ozono;
	public MuestraOzono(int id, String fecha, float duracion, int oxigeno, float humedad, float temperatura, float ozono, 
			String metorologia, Localizacion localizacion, String usuario) {
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
	public String getFecha() {
		// TODO Auto-generated method stub
		return super.getFecha();
	}
	@Override
	public void setFecha(String fecha) {
		// TODO Auto-generated method stub
		super.setFecha(fecha);
	}
	@Override
	public float getDuracion() {
		// TODO Auto-generated method stub
		return super.getDuracion();
	}
	@Override
	public void setDuracion(float duracion) {
		// TODO Auto-generated method stub
		super.setDuracion(duracion);
	}
	@Override
	public String getMetorologia() {
		// TODO Auto-generated method stub
		return super.getMetorologia();
	}
	@Override
	public void setMetorologia(String metorologia) {
		// TODO Auto-generated method stub
		super.setMetorologia(metorologia);
	}
	@Override
	public Localizacion getLocalizacion() {
		// TODO Auto-generated method stub
		return super.getLocalizacion();
	}
	@Override
	public void setLocalizacion(Localizacion localizacion) {
		// TODO Auto-generated method stub
		super.setLocalizacion(localizacion);
	}
	@Override
	public String getUsuario() {
		// TODO Auto-generated method stub
		return super.getUsuario();
	}
	@Override
	public void setUsuario(String usuario) {
		// TODO Auto-generated method stub
		super.setUsuario(usuario);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
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
