package muestras;

public class Localizacion {
	String nombre;
	int habitantes;
	float area;
	public Localizacion(String nombre, int habitantes, float area) {
		super();
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.area = area;
	}
	public String getNombre() {
		return nombre;
	}
	public int getHabitantes() {
		return habitantes;
	}
	public float getArea() {
		return area;
	}
	public float getDensidad() {
		return (float)habitantes/area;
	}
	public String toString() {
		return this.nombre;
		
	}
	
	
	
}
