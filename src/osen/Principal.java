package osen;

import lineaSerie.LineaSeriePrincipal;

public class Principal {
	LineaSeriePrincipal lsP;
	
	public Principal() {
		iniciarSerialComm();
	}
	private void iniciarSerialComm() {
		lsP = new LineaSeriePrincipal();
	}
	public static void main(String[] args) {
		Principal programa = new Principal();

	}

}
