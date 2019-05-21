package dialogos;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import estados.GestorEstadosAnadirMuestra;
import muestras.Localizacion;
import muestras.MuestraCo2;




@SuppressWarnings("serial")
public class DialogoInsertarMuestra extends JDialog{
	
	JFrame ventana;
	JComboBox<String> comboLocalizacion;
	JComboBox<String> comboMeteorologia;
	JProgressBar progressBar;
	Thread hiloProgressBar;
	List<String>listaPalabras;
	Localizacion localizacion;
	MuestraCo2 muestra;
	boolean anadirLocalizacionSeleccionado=false;
	String numeroLocalizacion;
	DBManager manager;
	JButton botonOK;
	GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra;
	final static String [] meteorologias= {"Despejado", "Nublado", "Lluvioso", "Nevado", "Niebla"};
	
	
	
	
	public JComboBox<String> getComboLocalizacion() {
		return comboLocalizacion;
	}
	public DialogoInsertarMuestra (JFrame ventana,String titulo, boolean modo, List<String> list, DBManager manager, GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra) {
		super(ventana,titulo,modo);
		this.gestorEstadosAnadirMuestra=gestorEstadosAnadirMuestra;
		this.listaPalabras=list;
		this.ventana=ventana;
		this.setSize(600,400);
		this.setLocation (100,100);
		this.manager=manager;
		this.comboLocalizacion=new JComboBox<>();
		this.cargarDatosLocalizaciones();
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
				gestionarStart();
			}			
		});
		panel.add(boton3);
		
		return panel;
	}
	public void gestionarStart() {
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
				gestorEstadosAnadirMuestra.setState(2);
				gestorEstadosAnadirMuestra.estados();
				cargarDatosLocalizaciones();

			}
		});
		
		panel.add(crearPanelDatosFila("Lugar: ",comboLocalizacion),BorderLayout.CENTER);
		panel.add(boton4,BorderLayout.EAST);

		
		return panel;
	}
	private Component crearPanelDatosFila(String tituloCombo, JComboBox<String> comboBox) {
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
			public void actionPerformed(ActionEvent arg0) {
				
				String numeroMeteorologia=String.valueOf(comboMeteorologia.getSelectedIndex()+1);
				ResultSet resultado=manager.executeQuery("SELECT localizacionId from Localizaciones where nombre='"+comboLocalizacion.getSelectedItem().toString()+"'");
				try {
					resultado.next();
					numeroLocalizacion=Integer.toString(resultado.getInt(1));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(hiloProgressBar!=null)hiloProgressBar.interrupt();
				
				try {
					manager.execute("INSERT INTO Muestras (fecha, duracion, co2eq,humedad,temperatura,voc,meteorologia,localizacion,usuario) "
							+ "VALUES (curdate(), 10.00, 15, 50.59, 18.64, 65.95, "+numeroMeteorologia+", "+ numeroLocalizacion+", "+"1)");
					DialogoInsertarMuestra.this.dispose();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e1.getMessage(), "Codigo de error SQL: "+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, "Formato no v�lido: ("+e2.getLocalizedMessage()+")", "Aviso", JOptionPane.WARNING_MESSAGE);
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
	
	public void cargarDatosLocalizaciones() {
		ResultSet resultados = manager.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Localizaciones\r\n;");
		try {
			comboLocalizacion.removeAllItems();
			while(resultados.next()) {
				comboLocalizacion.addItem(resultados.getString("nombre"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
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
