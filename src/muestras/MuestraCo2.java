package muestras;

public class MuestraCo2 extends Muestra{
	
	
	int co2eq;
	float humedad;
	float temperatura;
	float voc;
	
	public MuestraCo2(int id, String fecha, float duracion, int co2eq, float humedad, float temperatura, float voc,String metorologia, Localizacion localizacion, String usuario) {
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
	public String[] ensenarTexto() {
		String[] info= {this.getLocalizacion().getNombre(), Float.toString(this.getLocalizacion().getArea()),
				Integer.toString(this.getLocalizacion().getHabitantes()), Float.toString(this.getLocalizacion().getDensidad()),
				Float.toString(this.getTemperatura()), Integer.toString(this.getCo2eq()), Float.toString(this.getHumedad()),
				Float.toString(this.getVoc()), Integer.toString(this.getId()),this.getFecha(),this.getMetorologia(), this.getUsuario()};
		return info;		
	}

	
	
}

