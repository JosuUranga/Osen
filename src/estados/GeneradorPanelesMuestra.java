package estados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import graficos.Anillo;
import idiomas.ControladorIdioma;
import muestras.Muestra;

public class GeneradorPanelesMuestra {
	public final static int ESTADO_SIN_COMPARAR=1;
	public final static int ESTADO_COMPARANDO=2;
	public final static int ESTADO_NO_MOSTRAR=0;
	JLabel labelMuestraID, labelFecha, labelMeteo, labelUsuario, labelTemp, labelHumedad, labelCo2, labelVoc, labelLugar, labelHabitantes, labelArea, labelDensidad;
	ControladorIdioma controladorIdioma;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	Muestra muestra1,muestra2;
	int state;
	public GeneradorPanelesMuestra(ControladorIdioma controlador) {
		this.controladorIdioma=controlador;
		this.state=ESTADO_NO_MOSTRAR;
	}
	public void setState(int estado) {
		this.state=estado;
	}
	public int getState() {
		return state;
	}
	public Component getPanel(Muestra muestra1,Muestra muestra2) {
		this.muestra1=muestra1;
		this.muestra2=muestra2;
		JTabbedPane panel = new JTabbedPane();
		switch(state) {
		case ESTADO_SIN_COMPARAR:
			panel=getPanelSinComparar();
			break;
		case ESTADO_COMPARANDO:
			panel=getPanelComparando();
			break;
		default:
			panel=getPanelNoMostrar();
			break;
		}
		return panel;
	}
	private JTabbedPane getPanelSinComparar() {
		JTabbedPane panel = new JTabbedPane();
		
		panel.addTab(controladorIdioma.getListaPalabras().get(10), crearPanelMapa());
		panel.addTab(controladorIdioma.getListaPalabras().get(11), crearPanelInfoGeneral(muestra1,null));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), crearPanelGraficos());
		return panel;
		
	}
	private JTabbedPane getPanelComparando() {
		JTabbedPane panel = new JTabbedPane();
		
		panel.addTab(controladorIdioma.getListaPalabras().get(10), crearPanelMapa());
		panel.addTab(controladorIdioma.getListaPalabras().get(11), crearPanelInfoGeneral(muestra1,muestra2));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), crearPanelGraficos());
		return panel;
		
	}
	private JTabbedPane getPanelNoMostrar() {
		JTabbedPane panel = new JTabbedPane();
		panel.addTab(controladorIdioma.getListaPalabras().get(10), new JPanel (new BorderLayout(0,10)));
		panel.addTab(controladorIdioma.getListaPalabras().get(11), new JPanel (new BorderLayout(0,10)));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), new JPanel (new BorderLayout(0,10)));
		return panel;
		
	}
	private Component crearPanelGraficos() {
		JPanel panel=new Anillo().getPanel();
		return panel;
	}
	
	private Component crearPanelMapa() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		
		return panel;
	}
	private Component crearPanelInfoGeneral(Muestra muestra1,Muestra muestra2) {
		JPanel panel = new JPanel (new GridLayout(3,1));
		panel.add(crearPanelInfoNorte(muestra1,muestra2));
		panel.add(crearPanelInfoCentro(muestra1,muestra2));
		panel.add(crearPanelInfoSur(muestra1,muestra2));
		return panel;
	}
	private Component crearPanelInfoSur(Muestra muestra,Muestra muestra2) {
		
		int columnas;
		if(this.getState()==ESTADO_COMPARANDO)columnas=6;
		else columnas=4;
		
		JPanel panel = new JPanel (new GridLayout(2,columnas));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(13))));
		panel.add(crearPanelJLabel(labelLugar=new JLabel(muestra.ensenarTexto()[0])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[0])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(14))));
		panel.add(crearPanelJLabel(labelArea=new JLabel(muestra.ensenarTexto()[1])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[1])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(15))));
		panel.add(crearPanelJLabel(labelHabitantes=new JLabel(muestra.ensenarTexto()[2])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[2])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(16))));
		panel.add(crearPanelJLabel(labelDensidad=new JLabel(muestra.ensenarTexto()[3])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[3])));
		
		return panel;
	}



	private Component crearPanelJLabelTitulo(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setHorizontalAlignment(0);
		label.setFont(fuenteTituloInfoGeneral);
		panel.add(label);

		return panel;
	}



	private Component crearPanelInfoCentro(Muestra muestra,Muestra muestra2) {
		int columnas;
		if(this.getState()==ESTADO_COMPARANDO)columnas=6;
		else columnas=4;
		
		JPanel panel = new JPanel (new GridLayout(2,columnas));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), controladorIdioma.getListaPalabras().get(17)));
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(18))));
		panel.add(crearPanelJLabel(labelTemp=new JLabel(muestra.ensenarTexto()[4])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[4])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(19))));
		panel.add(crearPanelJLabel(labelCo2=new JLabel(muestra.ensenarTexto()[5])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[5])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(20))));
		panel.add(crearPanelJLabel(labelHumedad=new JLabel(muestra.ensenarTexto()[6])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[6])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(21))));
		panel.add(crearPanelJLabel(labelVoc=new JLabel(muestra.ensenarTexto()[7])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[7])));

		return panel;
	}



	private Component crearPanelInfoNorte(Muestra muestra,Muestra muestra2) {
		
		int columnas;
		if(this.getState()==ESTADO_COMPARANDO)columnas=6;
		else columnas=4;
		
		JPanel panel = new JPanel (new GridLayout(3,columnas));
		
		
		panel.add(crearPanelJLabel(new JLabel(" ")));
		panel.add(crearPanelJLabel(new JLabel("Muestra 1 ")));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(panel.add(crearPanelJLabel(new JLabel("Muestra 2 "))));
		
		panel.add(crearPanelJLabel(new JLabel(" ")));
		panel.add(crearPanelJLabel(new JLabel("Muestra 1 ")));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(panel.add(crearPanelJLabel(new JLabel("Muestra 2 "))));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(22))));
		panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra.ensenarTexto()[8])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[8])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(23))));
		panel.add(crearPanelJLabel(labelFecha=new JLabel(muestra.ensenarTexto()[9])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[9])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(24))));
		panel.add(crearPanelJLabel(labelMeteo=new JLabel(muestra.ensenarTexto()[10])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[10])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(25))));
		panel.add(crearPanelJLabel(labelUsuario=new JLabel(muestra.ensenarTexto()[11])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra2.ensenarTexto()[11])));
		
		return panel;
	}



	private Component crearPanelJLabel(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setHorizontalAlignment(0);
		panel.add(label);

		return panel;
	}



	

}
