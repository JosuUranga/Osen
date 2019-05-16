package osen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FicheroIdioma {
	File texto;
	List<String> listaPalabras;
	public FicheroIdioma(String filePath) {
		this.texto = new File(filePath);
		listaPalabras=leerPalabrasIdioma();
	}

	public List<String> leerPalabrasIdioma(){
		   
			String linea;
			listaPalabras=new ArrayList<>();
			try (BufferedReader in = new BufferedReader (new FileReader(texto))) {
				
				while ((linea = in.readLine())!=null){
					if (linea.length()>0){
						listaPalabras.add(linea);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return listaPalabras;
	   }

	public List<String> getListaPalabras() {
		return listaPalabras;
	}
	
}
