package osen;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dialogos.DialogoUsuario;
import dialogos.Login;
import estados.GeneradorPanelesMuestra;
import estados.GestorEstadosAnadirMuestra;
import idiomas.ControladorIdioma;
import lineaSerie.LineaSeriePrincipal;
import modelos.FechaDAO;
import modelos.IdiomaDAO;
import modelos.LocalizacionDAO;
import modelos.MeteoDAO;
import modelos.MuestrasDAO;
import modelos.UsuarioVO;
import muestras.Localizacion;
import muestras.Meteorologia;
import muestras.Muestra;
import notificaciones.NotificationManager;


@SuppressWarnings("serial")
public class Principal extends JFrame implements ActionListener, PropertyChangeListener{

	File file = new File("ficheros/TeoriaCo2.pdf");
	
	public final static String dbpass="Osen!1234";
	public final static String dbname="osen";
	public final static String dbip="68.183.211.91";

	JMenuBar barra;
	JMenu	menuAgregaciones, menuSalir;
	JMenuItem opcionMenu;
	JPanel panelComboBox2,panelinfo;
	MiAccion anadirCampo, anadirMuestra, ayuda, recargar,perfil, salir;
	JComboBox<String>comboFecha1,comboFecha2;
	JComboBox<Localizacion>comboLocalizacion1,comboLocalizacion2;
	JComboBox<Meteorologia>comboMeteo1,comboMeteo2;
	boolean compararActivado=false;
	LineaSeriePrincipal lsP;
	UsuarioVO usuario;
	ControladorIdioma controladorIdioma;
	JLabel labelMuestraID, labelFecha, labelMeteo, labelUsuario, labelTemp, labelHumedad, labelCo2, labelVoc, labelLugar, labelHabitantes, labelArea, labelDensidad;
	Font fuenteTituloInfoGeneral;
	String seleccionIdioma;
	GeneradorPanelesMuestra generadorPan;
	Muestra muestra1,muestra2;
	Component combo;
	NotificationManager notiManager;
	Login login;
	public Principal(){
		super("OSEN");
		System.setProperty("javax.net.ssl.trustStore","client-certs/cacerts"); 
		System.setProperty("javax.net.ssl.trustStorePassword","Osen!1234");
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.keyStore","client-certs/keycerts"); 
		System.setProperty("javax.net.ssl.keyStorePassword","Osen!1234");
		System.setProperty("javax.net.ssl.keyStoreType", "JKS");
		this.loguear();
		this.setLocation (340,100);
		this.setSize(1000,800);
		fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
		
		//iniciarNotis();
		seleccionIdioma=seleccionarIdioma();
		generadorPan=new GeneradorPanelesMuestra(controladorIdioma);
		this.crearAcciones();
		this.crearComboBox1();
		this.crearComboBox2();
		this.setJMenuBar(crearMenu());
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	private String seleccionarIdioma() {
		try {
			return IdiomaDAO.getInstance(usuario.calcularTipoUsuario(), dbpass, dbname, dbip).getIdiomaUser(usuario.getUsuarioID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);

		}
		return "Castellano";
	}

	
	private void loguear() {
		login = new Login(this);
		controladorIdioma=login.getControladorIdioma();
		System.out.println(login.esCorrecto());
		if(login.esCorrecto()) {
			usuario=login.getUser();
		}else {
			this.dispose();
			System.exit(1);
		}		
	}


	private void iniciarNotis() {
		if(usuario.getTipo()>0) {
			notiManager=new NotificationManager("keyaqui", usuario);
			notiManager.start();
			}		
	}


	private void crearAcciones() {
		anadirCampo = new MiAccion (controladorIdioma.getListaPalabras().get(0),new ImageIcon("iconos/edit_add.png"),controladorIdioma.getListaPalabras().get(1),
				new Integer(KeyEvent.VK_C));
		anadirMuestra = new MiAccion (controladorIdioma.getListaPalabras().get(2),new ImageIcon("iconos/amigo.png"),controladorIdioma.getListaPalabras().get(3),
				new Integer(KeyEvent.VK_A));
		recargar = new MiAccion (controladorIdioma.getListaPalabras().get(4),new ImageIcon("iconos/recargar.png"),controladorIdioma.getListaPalabras().get(5),
				new Integer(KeyEvent.VK_R));
		ayuda = new MiAccion (controladorIdioma.getListaPalabras().get(6),new ImageIcon("iconos/edit.png"),controladorIdioma.getListaPalabras().get(7),
				new Integer(KeyEvent.VK_H));
		perfil = new MiAccion (controladorIdioma.getListaPalabras().get(30),new ImageIcon("iconos/perfil.png"),controladorIdioma.getListaPalabras().get(31),
				new Integer(KeyEvent.VK_P));
		salir = new MiAccion (controladorIdioma.getListaPalabras().get(8),new ImageIcon("iconos/exit.png"),controladorIdioma.getListaPalabras().get(9),
				new Integer(KeyEvent.VK_S));
		if(usuario.getTipo()==0) {
			anadirCampo.setEnabled(false);
			anadirMuestra.setEnabled(false);

		}
		if(usuario.getTipo()==1) {
			anadirMuestra.setEnabled(false);
		}
		
	}
	private void crearComboBox1() {
		comboLocalizacion1=new JComboBox<>();
		comboMeteo1=new JComboBox<>();
		comboFecha1=new JComboBox<>();
		cargarDatosComboBox(comboLocalizacion1, comboMeteo1, comboFecha1);
		comboLocalizacion1.addActionListener(this);
		comboLocalizacion1.setActionCommand("localizacion");
		comboMeteo1.addActionListener(this);
		comboMeteo1.setActionCommand("meteo");
	}
	
	private void cargarDatosComboBox(JComboBox<Localizacion> comboLocalizacion, JComboBox<Meteorologia> comboMeteo, JComboBox<String> comboFecha) {
		this.cargarDatosLocalizacionMuestra(comboLocalizacion);
		this.cargarDatosMeteo(comboLocalizacion,comboMeteo);
		this.cargarDatosFecha(comboLocalizacion,comboMeteo,comboFecha);
	}

	private void crearComboBox2() {
		comboLocalizacion2=new JComboBox<>();
		comboMeteo2=new JComboBox<>();
		comboFecha2=new JComboBox<>();	
		comboLocalizacion2.addActionListener(this);
		comboLocalizacion2.setActionCommand("localizacion2");
		comboMeteo2.addActionListener(this);
		comboMeteo2.setActionCommand("meteo2");
	}
	private Container crearPanelVentana() {
		panelinfo = new JPanel(new BorderLayout(0,0));
		panelinfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		combo=crearPanelNorte();
		panelinfo.add(combo,BorderLayout.NORTH);
		panelinfo.add(generadorPan.getPanel(muestra1, muestra2),BorderLayout.CENTER);
		return panelinfo;
	}
	
	private Component crearPanelNorte() {
		JPanel panel = new JPanel (new GridLayout(2,1));
		panel.add(panelBarraBotones());
		panel.add(crearPanelJComboBoxesyBusqueda());
		return panel;
	}
	
	
	private Component crearPanelJComboBoxesyBusqueda() {
		JPanel panel = new JPanel (new BorderLayout(0,0));
		panel.add(crearPanelJComboBoxes(),BorderLayout.CENTER);
		panel.add(crearPanelBotonBusqueda(),BorderLayout.EAST);
		return panel;
	}



	private Component crearPanelBotonBusqueda() {
		JPanel panel = new JPanel (new BorderLayout(0,0));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
		JButton boton=new JButton(new ImageIcon("iconos/searchicon.png"));
		boton.setActionCommand("Buscar");
		boton.addActionListener(this);
		panel.add(boton);
		return panel;
	}



	private Component crearPanelJComboBoxes() {
		JPanel panel = new JPanel (new GridLayout(2,1));
		panel.add(crearPanelJComboBox1());
		panel.add(crearPanelJComboBox2());
		return panel;
	}


	private Component crearPanelJComboBox1() {
		JPanel panel = new JPanel (new GridLayout(1,3,5,5));

		panel.add(comboLocalizacion1,0);
		panel.add(comboMeteo1,1);
		panel.add(comboFecha1,2);
		
		return panel;
	}
	private Component crearPanelJComboBox2() {
		panelComboBox2 = new JPanel (new GridLayout(1,3,5,5));
				
		panelComboBox2.add(comboLocalizacion2,0);
		panelComboBox2.add(comboMeteo2,1);
		panelComboBox2.add(comboFecha2,2);
		
		panelComboBox2.remove(0);
		panelComboBox2.remove(0);
		panelComboBox2.remove(0);

		return panelComboBox2;
	}
	private Component panelBarraBotones() {
		JToolBar toolBar = new JToolBar();
		
		@SuppressWarnings("unused")
		JButton boton;
		toolBar.add(anadirCampo);
		toolBar.add(Box.createGlue());
		boton =(JButton) toolBar.add(recargar);
		toolBar.add(anadirMuestra);
		toolBar.add(Box.createHorizontalGlue());
		boton =(JButton) toolBar.add(perfil);
		boton =(JButton) toolBar.add(salir);
		return toolBar;
	}
	
	

	private JMenuBar crearMenu() {
		JMenuBar barra = new JMenuBar();
		barra.add (crearMenuEditar());
		barra.add (crearMenuAyuda());
		barra.add (crearMenuSalir());
		
		return barra;
	}
	private JMenu crearMenuAyuda() {
		JMenu menudAyuda = new JMenu (controladorIdioma.getListaPalabras().get(26));
		menudAyuda.setMnemonic(new Integer(KeyEvent.VK_A));
		JMenuItem opcionMenu = new JMenuItem (ayuda);
		menudAyuda.add(opcionMenu);
		return menudAyuda;
	}
	private JMenu crearMenuEditar() {
		JMenu menuEditar = new JMenu (controladorIdioma.getListaPalabras().get(27));
		menuEditar.setMnemonic(new Integer(KeyEvent.VK_E));
		JMenuItem opcionMenu = new JMenuItem (anadirCampo);
		menuEditar.add(opcionMenu);
		opcionMenu = new JMenuItem (recargar);
		menuEditar.add(opcionMenu);
		opcionMenu = new JMenuItem (perfil);
		menuEditar.add(opcionMenu);
		return menuEditar;
	}
	private JMenu crearMenuSalir() {
		JMenu menuSalir = new JMenu (controladorIdioma.getListaPalabras().get(28));
		menuSalir.setMnemonic(new Integer(KeyEvent.VK_S));
		menuSalir.add(salir);
		
		return menuSalir;
	}
	private class MiAccion extends AbstractAction {
		String texto;
		public MiAccion (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue(Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (texto.equals(controladorIdioma.getListaPalabras().get(0))){
				compararActivado=!compararActivado;
				if(generadorPan.getState()!=2)generadorPan.setState(2);
				else generadorPan.setState(0);
				if(compararActivado) {
					panelComboBox2.add(comboLocalizacion2,0);
					panelComboBox2.add(comboMeteo2,1);
					panelComboBox2.add(comboFecha2,2);
					cargarDatosComboBox(comboLocalizacion2, comboMeteo2, comboFecha2);
				}
				else {
				panelComboBox2.remove(0);
				panelComboBox2.remove(0);
				panelComboBox2.remove(0);
				}
				//hay q dinamizar esto
				Principal.this.revalidate();
				Principal.this.repaint();
				
			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(2))){//anadir muestra
				new GestorEstadosAnadirMuestra(1, Principal.this,null, controladorIdioma.getListaPalabras(), usuario);

			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(4))){//recargar
				cargarDatosComboBox(comboLocalizacion1, comboMeteo1, comboFecha1);
				cargarDatosComboBox(comboLocalizacion2, comboMeteo2, comboFecha2);
			}
			if(texto.equals(controladorIdioma.getListaPalabras().get(6))) {//ayuda
				
				Desktop desktop = Desktop.getDesktop();
		        
		        if(file.exists())
					try {
						desktop.open(file);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Principal.this, e1.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);					
						}

			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(30))){//perfil
				new DialogoUsuario(Principal.this, controladorIdioma.getListaPalabras().get(30), true, controladorIdioma.getListaPalabras(), usuario);
			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(8))){//salir
				Principal.this.dispose();
			}
		}

		

			
	}
	@SuppressWarnings("unused")
	private void iniciarSerialComm() {
		lsP = new LineaSeriePrincipal();
		lsP.accion();
	}
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new Principal();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
				
		switch(e.getActionCommand()) {
		
		case "Buscar":
			if(!compararActivado)generadorPan.setState(GeneradorPanelesMuestra.ESTADO_SIN_COMPARAR);
			else {
				muestra2=realizarBusquedaSinComparar(comboLocalizacion2,comboMeteo2,comboFecha2);
				System.out.println(muestra2);
			}
			muestra1=realizarBusquedaSinComparar(comboLocalizacion1,comboMeteo1,comboFecha1);
			panelinfo.removeAll();
			panelinfo.add(combo,BorderLayout.NORTH);
			panelinfo.add(generadorPan.getPanel(muestra1, muestra2),BorderLayout.CENTER);
			panelinfo.revalidate();
			panelinfo.repaint();
			break;
		case "localizacion":		
			if(comboLocalizacion1.getItemCount()!=0)this.cargarDatosMeteo(comboLocalizacion1, comboMeteo1);
			break;
		case "localizacion2":
			if(comboLocalizacion2.getItemCount()!=0)this.cargarDatosMeteo(comboLocalizacion2, comboMeteo2);
			break;
		case "meteo2":
			if(comboMeteo2.getItemCount()!=0)this.cargarDatosFecha(comboLocalizacion2, comboMeteo2, comboFecha2);
			break;
		case "meteo":		
			if(comboMeteo1.getItemCount()!=0)this.cargarDatosFecha(comboLocalizacion1, comboMeteo1, comboFecha1);
			break;
		}
	}



	private Muestra realizarBusquedaSinComparar(JComboBox<Localizacion> comboLocalizacion, JComboBox<Meteorologia> comboMeteo, JComboBox<String> comboFecha) {
		Localizacion localiz=(Localizacion) comboLocalizacion.getSelectedItem();
		String pueblo=localiz.getNombre();
		Meteorologia meteo=(Meteorologia) comboMeteo.getSelectedItem();
		String fecha=comboFecha.getSelectedItem().toString();
		Muestra muestra=null;
		try {
			muestra=MuestrasDAO.getInstance(usuario.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getMuestra(meteo.getId(),pueblo, fecha);
			} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
			}
		return muestra;
	}
	public void cargarDatosLocalizacionMuestra(JComboBox<Localizacion> comboLocalizacion) {
		try {
			List<Localizacion>listaLoca=LocalizacionDAO.getInstance(usuario.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getLocalizacionesMuestra();
			comboLocalizacion.removeAllItems();
			listaLoca.forEach(loca->comboLocalizacion.addItem(loca));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public void cargarDatosFecha(JComboBox<Localizacion> comboLocalizacion, JComboBox<Meteorologia> comboMeteo, JComboBox<String> comboFecha) {
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		Meteorologia meteo=(Meteorologia) comboMeteo.getSelectedItem();
		try {
			List<String>listaFec=FechaDAO.getInstance(usuario.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getFechas(pueblo, meteo.getId());
			comboFecha.removeAllItems();
			listaFec.forEach(fecha->comboFecha.addItem(fecha));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void cargarDatosMeteo(JComboBox<Localizacion> comboLocalizacion, JComboBox<Meteorologia> comboMeteo) {
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		try {
			List<Meteorologia>listaMeteo=MeteoDAO.getInstance(usuario.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getMeteo(pueblo);
			comboMeteo.removeAllItems();
			listaMeteo.forEach(meteo->comboMeteo.addItem(meteo));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), controladorIdioma.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()) {
		case "idioma":
				this.revalidate();
				this.repaint();
			break;
		}
	}
}
