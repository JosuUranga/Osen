package lineaSerie;
import gnu.io.CommPortIdentifier;

public class LineaSeriePrincipal {
	SerialComm lineaSerie;
	Lector hiloLectura;
	CommPortIdentifier puerto;

	public LineaSeriePrincipal() {
		lineaSerie = new SerialComm(); //crea un serialComm
		puerto = lineaSerie.encontrarPuerto();	//busca un peurto en el pc
		
		if (puerto == null) {
			System.out.println("No se ha encontrado una linea serie");
			System.exit(0);
		}else {
			try {
				lineaSerie.conectar(puerto); //si ha encontrado puerto lo conecta
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			System.out.println("Linea serie encontrada en: "+ puerto.getName()); 
			hiloLectura = new Lector(lineaSerie, puerto);		//una vez todo funcione se crea un hilo lector
		}
	}
	public void accion() { 		
		hiloLectura.start(); 		//empieza el hilo lector ya creado
		System.out.println("iniciando lectura");

	}
	public void escribir(String msg) { //funcion para mandar el msg llamando al escribir de la linea serie
		lineaSerie.escribir(msg);
	}
	public void cogerMuestra() {
		lineaSerie.escribir("A");
	}
	public String[] cogerDatos() {
		return hiloLectura.getDatos();
	}
}

