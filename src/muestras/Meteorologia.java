package muestras;

public class Meteorologia {
	int id;
	String descripcion;
	public Meteorologia(int id,String descripcion) {
		this.id=id;
		this.descripcion=descripcion;
	}
	public String toString() {
		return this.descripcion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
