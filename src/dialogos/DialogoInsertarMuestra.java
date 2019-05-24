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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import estados.GestorEstadosAnadirMuestra;
import lineaSerie.LineaSeriePrincipal;
import modelos.LocalizacionDAO;
import modelos.MuestrasDAO;
import modelos.UsuarioVO;
import muestras.Localizacion;
import muestras.MuestraCo2;




@SuppressWarnings("serial")
public class DialogoInsertarMuestra extends JDialog{
	
	JFrame ventana;
	JComboBox<Localizacion> comboLocalizacion;
	JComboBox<String> comboMeteorologia;
	JProgressBar progressBar;
	Thread hiloProgressBar;
	List<String>listaPalabras;
	Localizacion localizacion;
	MuestraCo2 muestra;
	boolean anadirLocalizacionSeleccionado=false;
	JButton botonOK;
	GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra;
	LineaSeriePrincipal lsp;
	String[] datos;
	UsuarioVO usuario;
	final static String [] meteorologias= {"Despejado", "Nublado", "Lluvioso", "Nevado", "Niebla"};
	
	public final static String dbuser="Admin";
	public final static String dbpass="Osen!1234";
	public final static String dbname="osen";
	public final static String dbip="68.183.211.91";
	
	
	public JComboBox<Localizacion> getComboLocalizacion() {
		return comboLocalizacion;
	}
	public DialogoInsertarMuestra (JFrame ventana,String titulo, boolean modo, List<String> list, GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra, LineaSeriePrincipal lsp, UsuarioVO usuario) {
		super(ventana,titulo,modo);
		this.usuario=usuario;
		this.gestorEstadosAnadirMuestra=gestorEstadosAnadirMuestra;
		this.listaPalabras=list;
		this.ventana=ventana;
		this.setSize(600,400);
		this.setLocation (100,100);
		this.comboLocalizacion=new JComboBox<>();
		this.cargarDatosLocalizaciones();
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	
		this.lsp=lsp;
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
		JButton boton3 = new JButton (listaPalabras.get(44));
		
		
		boton3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				gestionarStart();
			}			
		});
		panel.add(boton3);
		
		return panel;
	}
	public void gestionarStart() {
		//lsp.accion();
		//lsp.cogerMuestra();
		hiloProgressBar = new Thread(new Runnable() {
			@Override
			public void run(){
				while(progressBar.getValue()<100) {
					progressBar.setValue(progressBar.getValue()+10);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e.getMessage(), listaPalabras.get(43), JOptionPane.PLAIN_MESSAGE);
					}
				}
				JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, listaPalabras.get(45), listaPalabras.get(43), JOptionPane.PLAIN_MESSAGE);
				botonOK.setEnabled(true);
				datos=lsp.cogerDatos();
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

		panel.add(crearPanelDatosFila(listaPalabras.get(24),comboMeteorologia));
		return panel;
	}
	private Component crearPanelDatosFila1() {
		JPanel panel = new JPanel(new BorderLayout(30,10));
		panel.setBorder(BorderFactory.createEmptyBorder(45, 10, 40, 20));
		JButton boton4 = new JButton (listaPalabras.get(46));
		
		
		boton4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				gestorEstadosAnadirMuestra.setState(2);
				gestorEstadosAnadirMuestra.estados();
				cargarDatosLocalizaciones();

			}
		});
		
		panel.add(crearPanelDatosFilaLoca(listaPalabras.get(13),comboLocalizacion),BorderLayout.CENTER);
		panel.add(boton4,BorderLayout.EAST);

		
		return panel;
	}
	private Component crearPanelDatosFila(String tituloCombo, JComboBox<String> comboBox) {
		JPanel panel = new JPanel(new GridLayout(1,2));

		panel.add(new JLabel (tituloCombo));
		panel.add(comboBox);
		
		return panel;
	}
	private Component crearPanelDatosFilaLoca(String tituloCombo, JComboBox<Localizacion> comboBox) {
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
				
				int numeroMeteorologia=comboMeteorologia.getSelectedIndex()+1;
				Localizacion loca = (Localizacion) comboLocalizacion.getSelectedItem();
				int numeroLocalizacion=loca.getId();
			
				if(hiloProgressBar!=null)hiloProgressBar.interrupt();
				
				try {
					MuestrasDAO.getInstance(dbuser, dbpass, dbname, dbip).addMuestra(Float.valueOf(10), Integer.parseInt(datos[0]), Integer.parseInt(datos[2]), Integer.parseInt(datos[1]), Integer.parseInt(datos[3]), numeroMeteorologia, numeroLocalizacion, usuario.getUsuarioID());
					System.out.println(datos[0]+datos[1]+datos[2]+datos[3]);
					DialogoInsertarMuestra.this.dispose();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e1.getMessage(), listaPalabras.get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, listaPalabras.get(42)+e2.getLocalizedMessage()+")", listaPalabras.get(43), JOptionPane.WARNING_MESSAGE);
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
		try {
			List<Localizacion>listaLoca=LocalizacionDAO.getInstance(DialogoInsertarMuestra.dbuser, DialogoInsertarMuestra.dbpass, DialogoInsertarMuestra.dbname, DialogoInsertarMuestra.dbip)
					.getAllLocalizaciones();
			comboLocalizacion.removeAllItems();
			listaLoca.forEach(loca->comboLocalizacion.addItem(loca));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoInsertarMuestra.this, e.getMessage(), listaPalabras.get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
			
	}
	
		
	public String getText() {
		return comboMeteorologia.getSelectedItem().toString();
	}
	public Localizacion getLocalizacion() {
		return localizacion;
	}
	

	

}
