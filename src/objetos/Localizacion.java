package objetos;

public class Localizacion {
	String nombre;
	int habitantes;
	float area;
	int id;
	public Localizacion(int id,String nombre, int habitantes, float area) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.area = area;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
