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
import java.sql.ResultSet;
import java.sql.SQLException;

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

import db.DBManager;
import dialogos.DialogoUsuario;
import estados.GeneradorPanelesMuestra;
import estados.GestorEstadosAnadirMuestra;
import idiomas.ControladorIdioma;
import lineaSerie.LineaSeriePrincipal;
import modelos.UsuarioVO;
import muestras.Localizacion;
import muestras.Muestra;
import muestras.MuestraCo2;
import notificaciones.NotificationManager;


@SuppressWarnings("serial")
public class Principal extends JFrame implements ActionListener, PropertyChangeListener{

	File file = new File("ficheros/TeoriaCo2.pdf");
	
	final static String dbuser="Admin";
	final static String dbpass="Osen!1234";
	final static String dbname="osen";
	final static String dbip="68.183.211.91";

	JMenuBar barra;
	JMenu	menuAgregaciones, menuSalir;
	JMenuItem opcionMenu;
	JPanel panelComboBox2,panelinfo;
	MiAccion anadirCampo, anadirMuestra, ayuda, recargar,perfil, salir;
	JComboBox<String> comboLocalizacion1, comboMeteo1, comboFecha1, comboLocalizacion2, comboMeteo2, comboFecha2;
	boolean compararActivado=false;
	LineaSeriePrincipal lsP;
	DBManager manager;
	UsuarioVO usuario;
	ControladorIdioma controladorIdioma;
	JLabel labelMuestraID, labelFecha, labelMeteo, labelUsuario, labelTemp, labelHumedad, labelCo2, labelVoc, labelLugar, labelHabitantes, labelArea, labelDensidad;
	Font fuenteTituloInfoGeneral;
	String seleccionIdioma="Castellano";
	GeneradorPanelesMuestra generadorPan;
	Muestra muestra1,muestra2;
	Component combo;
	NotificationManager notiManager;
	public Principal(){
		super("OSEN");
		this.setLocation (340,100);
		this.setSize(1000,800);
		manager=DBManager.getInstance(dbuser,dbpass ,dbname,dbip);
		fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
		usuario = new UsuarioVO(1, "Mikel", "imanol.rubio@alumni.mondragon.edu","kaixo", 1, 0);
		usuario.setTipo(2);
		//iniciarNotis();
		controladorIdioma=new ControladorIdioma(usuario.getIdiomaSeleccionado());
		controladorIdioma.addPropertyChangeListener(this);
		controladorIdioma.cargarIdioma();
		generadorPan=new GeneradorPanelesMuestra(controladorIdioma);
		this.crearAcciones();
		this.crearComboBox1();
		this.crearComboBox2();
		this.setJMenuBar(crearMenu());
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//lsP = new LineaSeriePrincipal();
		//lsP.accion();
	}
	

	private void iniciarNotis() {
		if(usuario.getTipo()>0) {
			notiManager=new NotificationManager("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", manager, usuario);
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
	}
	private void crearComboBox1() {
		//coger los datos de la BD
		comboLocalizacion1=new JComboBox<>();
		comboMeteo1=new JComboBox<>();
		comboFecha1=new JComboBox<>();
		cargarDatosComboBox(comboLocalizacion1, comboMeteo1, comboFecha1);
		comboLocalizacion1.addActionListener(this);
		comboLocalizacion1.setActionCommand("localizacion");
		comboMeteo1.addActionListener(this);
		comboMeteo1.setActionCommand("meteo");
	}
	
	private void cargarDatosComboBox(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo, JComboBox<String> comboFecha) {
		this.cargarDatosLocalizacionMuestra(comboLocalizacion);
		this.cargarDatosMeteo(comboLocalizacion,comboMeteo);
		this.cargarDatosFecha(comboLocalizacion,comboMeteo,comboFecha);
	}

	private void crearComboBox2() {
		//coger los datos de la BD
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
		boton =(JButton) toolBar.add(anadirCampo);
		toolBar.add(Box.createGlue());
		boton =(JButton) toolBar.add(recargar);
		boton =(JButton) toolBar.add(anadirMuestra);
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
		JMenuItem op;
		JMenu menuSalir = new JMenu (controladorIdioma.getListaPalabras().get(28));
		menuSalir.setMnemonic(new Integer(KeyEvent.VK_S));
		op=menuSalir.add(salir);
		
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
				GestorEstadosAnadirMuestra gestorAnadirMuestra= new GestorEstadosAnadirMuestra(1, Principal.this,null, controladorIdioma.getListaPalabras(), manager);

			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(4))){//recargar
				cargarDatosComboBox(comboLocalizacion1, comboMeteo1, comboFecha1);
				cargarDatosComboBox(comboLocalizacion2, comboMeteo2, comboFecha2);
			}
			if(texto.equals(controladorIdioma.getListaPalabras().get(6))) {
				
				System.out.println("Ayuda");
				Desktop desktop = Desktop.getDesktop();
		        
		        if(file.exists())
					try {
						desktop.open(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

			}
			if (texto.equals(controladorIdioma.getListaPalabras().get(30))){//perfil
				DialogoUsuario dialogoUsuario = new DialogoUsuario(Principal.this, "Perfil", true, controladorIdioma.getListaPalabras(), manager,usuario);
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
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		//NotificationSender noti=new NotificationSender();
		//PaymentsTest payments=new PaymentsTest("sk_test_dZGN1z9nd2Bx0WHAMfRmomsJ00wCLPBWmC");
		Principal programa = new Principal();
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



	private Muestra realizarBusquedaSinComparar(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo, JComboBox<String> comboFecha) {
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		String meteo=comboMeteo.getSelectedItem().toString();
		String fecha=comboFecha.getSelectedItem().toString();
		Muestra muestra=null;
		String condicion=(" WHERE Localizaciones.nombre = '"+ pueblo+"' AND Meteos.descripcion='"+meteo+"' AND fecha='"+fecha+"'");
		
		ResultSet resultados = manager.executeQuery("SELECT Muestras.muestraID, Meteos.descripcion, Muestras.fecha, Usuarios.nombre, Muestras.temperatura, Muestras.humedad, Muestras.co2eq, Muestras.voc, Localizaciones.nombre AS lugar, Localizaciones.habitantes, Localizaciones.areakm2, Localizaciones.habitantes/Localizaciones.areakm2 AS 'densidad (habitantes/km2)'\r\n" + 
				"FROM ((Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID)JOIN Usuarios ON Muestras.usuario=Usuarios.usuarioID\r\n" + 
				condicion+";");
		try {
			resultados.next();
			float duracion=(float) 10.5;
			muestra=new MuestraCo2(resultados.getInt(1), resultados.getString(3), duracion, resultados.getInt(7), resultados.getFloat(6), resultados.getFloat(5), resultados.getFloat(8), resultados.getString(2), new Localizacion(resultados.getString(9),resultados.getInt(10),resultados.getFloat(11)), resultados.getString(4));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		return muestra;
	}
	public void cargarDatosLocalizacionMuestra(JComboBox<String> comboLocalizacion) {
		ResultSet resultados = manager.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Muestras JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				"GROUP BY Localizaciones.localizacionID;");
		try {
			comboLocalizacion.removeAllItems();
			while(resultados.next()) {
				String result=resultados.getString("nombre");
				comboLocalizacion.addItem(result);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		manager.conClose();		
	}
	
	
	public void cargarDatosFecha(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo, JComboBox<String> comboFecha) {
		comboFecha.removeAllItems();
		
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		String meteo=comboMeteo.getSelectedItem().toString();
		
		ResultSet resultados = manager.executeQuery("SELECT DISTINCT Muestras.fecha\r\n" + 
				"FROM (Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				"WHERE Localizaciones.nombre='"+pueblo+"' AND Meteos.descripcion='"+meteo+"';");
		
		try {
			while(resultados.next()) {
				comboFecha.addItem(resultados.getString("fecha"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		manager.conClose();	
		if(muestra1!=null)generadorPan.setState(GeneradorPanelesMuestra.ESTADO_SIN_COMPARAR);
	}
	
	public void cargarDatosMeteo(JComboBox<String> comboLocalizacion, JComboBox<String> comboMeteo) {
		comboMeteo.removeAllItems();
				
		String pueblo=comboLocalizacion.getSelectedItem().toString();
		String condicion=(" WHERE nombre = '"+ pueblo+"' ");
		
		ResultSet resultados = manager.executeQuery("SELECT DISTINCT Meteos.descripcion \r\n" + 
				"FROM (Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				condicion+";");
		
		try {
			while(resultados.next()) {
				comboMeteo.addItem(resultados.getString("descripcion"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Principal.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		manager.conClose();			
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
