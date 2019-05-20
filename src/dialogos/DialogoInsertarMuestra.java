package dialogos;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import db.DBManager;
import muestras.Localizacion;
import muestras.Muestra;




@SuppressWarnings("serial")
public class DialogoInsertarMuestra extends JDialog{
	
	JFrame ventana;
	JComboBox<String> comboLocalizacion;
	JComboBox<String> comboMeteorologia;
	JProgressBar progressBar;
	Thread hiloProgressBar;
	List<String>listaPalabras;
	Localizacion localizacion;
	Muestra muestra;
	boolean anadirLocalizacionSeleccionado=false;
	String numeroLocalizacion;
	DBManager manager;
	JButton botonOK;
	final static String [] meteorologias= {"Despejado", "Nublado", "Lluvioso", "Nevado", "Niebla"};
	
	
	
	
	public DialogoInsertarMuestra (JFrame ventana,String titulo, boolean modo, List<String> list, DBManager manager) {
		super(ventana,titulo,modo);
		this.listaPalabras=list;
		this.ventana=ventana;
		this.setSize(600,400);
		this.setLocation (100,100);
		this.manager=manager;
		this.cargarDatosLocalizaciones(comboLocalizacion=new JComboBox<>());
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
		panel.add(crearPanelBarrayBotones(),BorderLayout.SOUTH);
		return panel;
	}

	private Component crearPanelBarrayBotones() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(crearPanelProgressBar(),BorderLayout.CENTER);
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);

		return panel;
	}
	private Component crearPanelProgressBar() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0,10));

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		panel.add(progressBar);
		return panel;
	}
	private Component crearPanelCentro() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelBotonStart(),BorderLayout.SOUTH);

		return panel;
	}

	private Component crearPanelBotonStart() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 220, 0,220));
		JButton boton3 = new JButton ("Start");
		
		
		boton3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				hiloProgressBar = new Thread(new Runnable() {
					@Override
					public void run() {
						while(progressBar.getValue()<100) {
							progressBar.setValue(progressBar.getValue()+10);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, "La muestra se ha tomado correctamente", "Aviso", JOptionPane.PLAIN_MESSAGE);
						botonOK.setEnabled(true);

					}		
				});
				hiloProgressBar.start();

			}
		});
		panel.add(boton3);
		
		return panel;
	}
	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		
		panel.add(crearPanelDatosFila1());
		
		comboMeteorologia=new JComboBox<>();
		for(int i=0; i<meteorologias.length; i++)comboMeteorologia.addItem(meteorologias[i]);
		
		
		panel.add(crearPanelDatosFila2());
	
		return panel;
	}
	private Component crearPanelDatosFila2() {
		JPanel panel = new JPanel(new BorderLayout(30,10));
		panel.setBorder(BorderFactory.createEmptyBorder(35, 10, 50, 10));

		panel.add(crearPanelDatosFila("Meteorologia: ",comboMeteorologia));
		return panel;
	}
	private Component crearPanelDatosFila1() {
		JPanel panel = new JPanel(new BorderLayout(30,10));
		panel.setBorder(BorderFactory.createEmptyBorder(45, 10, 40, 20));
		JButton boton4 = new JButton ("Anadir localizacion");
		
		
		boton4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				DialogoInsertarLocalizacion dialogoInsertarLocalizacion= new DialogoInsertarLocalizacion(DialogoInsertarMuestra.this, listaPalabras.get(2), true, comboLocalizacion, listaPalabras, manager);
				if(dialogoInsertarLocalizacion.isAnadirLocalizacionSeleccionado()) {
					localizacion=dialogoInsertarLocalizacion.getLocalizacion();
					comboLocalizacion.addItem(localizacion.getNombre());
				}
			}
		});
		
		panel.add(crearPanelDatosFila("Lugar: ",comboLocalizacion),BorderLayout.CENTER);
		panel.add(boton4,BorderLayout.EAST);

		
		return panel;
	}
	private Component crearPanelDatosFila(String tituloCombo, JComboBox comboBox) {
		JPanel panel = new JPanel(new GridLayout(1,2));

		panel.add(new JLabel (tituloCombo));
		panel.add(comboBox);
		
		return panel;
	}
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		botonOK = new JButton ("OK");
		botonOK.setEnabled(false);	
		botonOK.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				
				String numeroMeteorologia=String.valueOf(comboMeteorologia.getSelectedIndex());
				numeroLocalizacion=String.valueOf(comboLocalizacion.getSelectedIndex());;
				if(hiloProgressBar!=null)hiloProgressBar.stop();	
				try {
					muestra=new Muestra("'2019-05-01'", (float)10.00, 15, (float)50.59, (float)18.64, (float)65.95, numeroMeteorologia, localizacion, "1"); 
					manager.execute("INSERT INTO Muestras (fecha, duracion, co2eq,humedad,temperatura,voc,meteorologia,localizacion,usuario) VALUES (curdate(), "+muestra.getDuracion()+", "+muestra.getCo2eq()+", "+muestra.getHumedad()+", "+muestra.getTemperatura()+", "+muestra.getVoc()+", "+muestra.getMetorologia()+", "+ muestra.getLocalizacion()+", "+muestra.getUsuario()+");");
					DialogoInsertarMuestra.this.dispose();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e1.getMessage(), "Codigo de error SQL: "+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, "Formato no válido", "Aviso", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
	
		JButton boton2 = new JButton ("Cancel");
		boton2.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if(hiloProgressBar!=null)hiloProgressBar.stop();		
				DialogoInsertarMuestra.this.dispose();
			}
		});
		panel.add(botonOK);
		this.getRootPane().setDefaultButton(botonOK);

		panel.add(boton2);
		return panel;
	}
	
	public void cargarDatosLocalizaciones(JComboBox<String> comboLocalizacion) {
		ResultSet resultados = manager.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Localizaciones\r\n;");
		try {
			comboLocalizacion.removeAllItems();
			while(resultados.next()) {
				comboLocalizacion.addItem(resultados.getString("nombre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		manager.conClose();		
	}
	
	
	
	public String getText() {
		return comboMeteorologia.getSelectedItem().toString();
	}
	public Localizacion getLocalizacion() {
		return localizacion;
	}
	public String getNumeroLocalizacion() {
		return numeroLocalizacion;
	}

	

}
