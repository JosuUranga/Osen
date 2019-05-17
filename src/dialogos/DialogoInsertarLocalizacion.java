package dialogos;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import muestras.Localizacion;
import muestras.Muestra;




@SuppressWarnings("serial")
public class DialogoInsertarLocalizacion extends JDialog{
	
	DialogoInsertarMuestra ventana;
	Muestra muestra;
	JComboBox<String> comboLocalizacion;
	JComboBox<String> comboMeteorologia;
	JTextField nombre,habitantes,area;
	List<String>listaPalabras;
	Localizacion localizacion;
	//Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
	final static String [] meteorologias= {"Despejado", "Nublado", "Lluvioso", "Nevado", "Niebla"};
	boolean anadirLocalizacionSeleccionado=false;
	public void setErrorIgual(boolean errorIgual) {
		this.errorIgual = errorIgual;
	}

	int numVariables;
	boolean errorRellenar=false;
	boolean errorIgual=false;
	
	
	public DialogoInsertarLocalizacion (DialogoInsertarMuestra dialogoInsertarMuestra,String titulo, boolean modo, JComboBox<String> comboLocalizacion, List<String> list) {
		super(dialogoInsertarMuestra,titulo,modo);
		this.listaPalabras=list;
		this.comboLocalizacion=comboLocalizacion;
		this.ventana=dialogoInsertarMuestra;
		this.setSize(600,400);
		this.setLocation (100,100);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
		
	}
	
	public Muestra getMuestra() {
		return muestra;
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelCentro(),BorderLayout.CENTER);
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}

	
	
	private Component crearPanelCentro() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		

		return panel;
	}

	
	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(3,1));
		
		panel.add(crearTextField(nombre=new JTextField(), "Nombre: "));
		panel.add(crearTextField(habitantes=new JTextField(), "Habitantes: "));
		panel.add(crearTextField(area=new JTextField(), "Area (m2): "));

		

		return panel;
	}
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(titulo);	
		panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
		panel.add(label);
		text.setHorizontalAlignment(0);
		panel.add(text);
		return panel;
	}
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JButton boton1 = new JButton ("OK");
		
	
		boton1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				localizacion = new Localizacion(nombre.getText(),Integer.valueOf(habitantes.getText()),Float.valueOf(area.getText()));
				anadirLocalizacionSeleccionado=true;
				DialogoInsertarLocalizacion.this.dispose();

			}
			
		});
	
		JButton boton2 = new JButton ("Cancel");
		boton2.addActionListener(new ActionListener(){
		
			public void actionPerformed(ActionEvent arg0) {
				DialogoInsertarLocalizacion.this.dispose();
			}
		});
		panel.add(boton1);
		this.getRootPane().setDefaultButton(boton1);

		panel.add(boton2);
		return panel;
	}
	
	
	
	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public String getText() {
		return comboMeteorologia.getSelectedItem().toString();
	}

	public boolean isAnadirLocalizacionSeleccionado() {
		return anadirLocalizacionSeleccionado;
	}


}
