package dialogos;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBManager;
import estados.GestorEstadosAnadirMuestra;
import muestras.Localizacion;
import muestras.MuestraCo2;




@SuppressWarnings("serial")
public class DialogoInsertarLocalizacion extends JDialog{
	
	DialogoInsertarMuestra ventana;
	MuestraCo2 muestra;
	
	JTextField nombre,habitantes,area;
	List<String>listaPalabras;
	Localizacion localizacion;
	DBManager manager;
	GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra;
	//Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
	final static String [] meteorologias= {"Despejado", "Nublado", "Lluvioso", "Nevado", "Niebla"};
	boolean anadirLocalizacionSeleccionado=false;
	public void setErrorIgual(boolean errorIgual) {
		this.errorIgual = errorIgual;
	}

	int numVariables;
	boolean errorRellenar=false;
	boolean errorIgual=false;
	
	
	public DialogoInsertarLocalizacion (GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra, DialogoInsertarMuestra dialogoInsertarMuestra,String titulo, boolean modo, List<String> list, DBManager manager) {
		super(dialogoInsertarMuestra,titulo,modo);
		this.gestorEstadosAnadirMuestra=gestorEstadosAnadirMuestra;
		this.listaPalabras=list;
		this.ventana=dialogoInsertarMuestra;
		this.setSize(600,400);
		this.setLocation (100,100);
		this.manager=manager;
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
		
	}
	
	public MuestraCo2 getMuestra() {
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

				try {
					localizacion = new Localizacion(nombre.getText(),Integer.valueOf(habitantes.getText()),Float.valueOf(area.getText()));
					manager.execute("INSERT INTO Localizaciones (nombre, habitantes, areakm2) VALUES ('"+localizacion.getNombre()+"', "+localizacion.getHabitantes()+", "+localizacion.getArea()+");");
					anadirLocalizacionSeleccionado=true;
					DialogoInsertarLocalizacion.this.dispose();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(DialogoInsertarLocalizacion.this, e1.getMessage(), "Codigo de error SQL: "+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoInsertarLocalizacion.this, "Formato no válido", "Aviso", JOptionPane.WARNING_MESSAGE);
				}	
				
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

	

	public boolean isAnadirLocalizacionSeleccionado() {
		return anadirLocalizacionSeleccionado;
	}


}
