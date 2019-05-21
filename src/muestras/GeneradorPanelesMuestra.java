package muestras;

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
		System.out.println(muestra1);
		panel.addTab(controladorIdioma.getListaPalabras().get(10), crearPanelMapa());
		panel.addTab(controladorIdioma.getListaPalabras().get(11), crearPanelInfoGeneral(muestra1));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), crearPanelGraficosSinCompa());
		return panel;
		
	}
	private JTabbedPane getPanelComparando() {
		JTabbedPane panel = new JTabbedPane();
		System.out.println(muestra2+"muestra2");
		panel.addTab(controladorIdioma.getListaPalabras().get(10), crearPanelMapa());
		panel.addTab(controladorIdioma.getListaPalabras().get(11), crearPanelInfoGeneral(muestra1));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), crearPanelGraficosCompa());
		return panel;
		
	}
	private JTabbedPane getPanelNoMostrar() {
		JTabbedPane panel = new JTabbedPane();
		panel.addTab(controladorIdioma.getListaPalabras().get(10), new JPanel (new BorderLayout(0,10)));
		panel.addTab(controladorIdioma.getListaPalabras().get(11), new JPanel (new BorderLayout(0,10)));
		panel.addTab(controladorIdioma.getListaPalabras().get(12), new JPanel (new BorderLayout(0,10)));
		return panel;
		
	}
	private Component crearPanelGraficosSinCompa() {
		JPanel panel=new Anillo().getPanel();
		return panel;
	}
	private Component crearPanelGraficosCompa() {
		JPanel panel=new Anillo().getPanel();
		return panel;
	}
	private Component crearPanelMapa() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		
		return panel;
	}
	private Component crearPanelInfoGeneral(Muestra muestra) {
		JPanel panel = new JPanel (new GridLayout(3,1));
		panel.add(crearPanelInfoNorte(muestra));
		panel.add(crearPanelInfoCentro(muestra));
		panel.add(crearPanelInfoSur(muestra));
		return panel;
	}
	private Component crearPanelInfoSur(Muestra muestra) {
		JPanel panel = new JPanel (new GridLayout(2,2));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(13))));
		panel.add(crearPanelJLabel(labelLugar=new JLabel(muestra.enseñarTexto()[0])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(14))));
		panel.add(crearPanelJLabel(labelArea=new JLabel(muestra.enseñarTexto()[1])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(15))));
		panel.add(crearPanelJLabel(labelHabitantes=new JLabel(muestra.enseñarTexto()[2])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(16))));
		panel.add(crearPanelJLabel(labelDensidad=new JLabel(muestra.enseñarTexto()[3])));
		
		return panel;
	}



	private Component crearPanelJLabelTitulo(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setFont(fuenteTituloInfoGeneral);
		panel.add(label);

		return panel;
	}



	private Component crearPanelInfoCentro(Muestra muestra) {
		JPanel panel = new JPanel (new GridLayout(2,2));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), controladorIdioma.getListaPalabras().get(17)));
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(18))));
		panel.add(crearPanelJLabel(labelTemp=new JLabel(muestra.enseñarTexto()[4])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(19))));
		panel.add(crearPanelJLabel(labelCo2=new JLabel(muestra.enseñarTexto()[5])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(20))));
		panel.add(crearPanelJLabel(labelHumedad=new JLabel(muestra.enseñarTexto()[6])));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(21))));
		panel.add(crearPanelJLabel(labelVoc=new JLabel(muestra.enseñarTexto()[7])));
		
		return panel;
	}



	private Component crearPanelInfoNorte(Muestra muestra) {
		
		int columnas;
		if(this.getState()==ESTADO_COMPARANDO)columnas=6;
		else columnas=4;
		
		JPanel panel = new JPanel (new GridLayout(2,columnas));
		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(22))));
		panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra.enseñarTexto()[8])));
		if(this.getState()==ESTADO_COMPARANDO)panel.add(crearPanelJLabel(labelMuestraID=new JLabel(muestra.enseñarTexto()[8])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(23))));
		panel.add(crearPanelJLabel(labelFecha=new JLabel(muestra.enseñarTexto()[9])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(24))));
		panel.add(crearPanelJLabel(labelMeteo=new JLabel(muestra.enseñarTexto()[10])));

		panel.add(crearPanelJLabelTitulo(new JLabel(controladorIdioma.getListaPalabras().get(25))));
		panel.add(crearPanelJLabel(labelUsuario=new JLabel(muestra.enseñarTexto()[11])));
		
		
		return panel;
	}



	private Component crearPanelJLabel(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(label);

		return panel;
	}



	

}
