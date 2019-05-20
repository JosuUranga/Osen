package osen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import db.DBManager;
import dialogos.DialogoInsertarMuestra;
import graficos.Anillo;
import lineaSerie.LineaSeriePrincipal;
import muestras.Muestra;


public class Principal extends JFrame implements ActionListener{
	
	final static String ficheroCastellano="ficheros/Castellano.txt";
	final static String ficheroEuskara="ficheros/Euskara.txt";
	final static String ficheroIngles="ficheros/Ingles.txt";
	File file = new File("ficheros/TeoriaCo2.pdf");
	
	final static String dbuser="Admin";
	final static String dbpass="Osen!1234";
	final static String dbname="osen";
	final static String dbip="68.183.211.91";

	JMenuBar barra;
	JMenu	menuAgregaciones, menuSalir;
	JMenuItem opcionMenu;
	JPanel panelComboBox2;
	MiAccion anadirCampo, anadirMuestra, ayuda, recargar, salir;
	JComboBox<String> comboLocalizacion1, comboMeteo1, comboFecha1, comboLocalizacion2, comboMeteo2, comboFecha2;
	boolean compararActivado=false;
	LineaSeriePrincipal lsP;
	DBManager manager;
	JLabel labelMuestraID, labelFecha, labelMeteo, labelUsuario, labelTemp, labelHumedad, labelCo2, labelVoc, labelLugar, labelHabitantes, labelArea, labelDensidad;
	Font fuenteTituloInfoGeneral;
	FicheroIdioma ficheroIdioma;
	String seleccionIdioma="Castellano";
	Muestra muestra;
	public Principal(){
		super("OSEN");
		this.setLocation (340,100);
		this.setSize(1000,800);
		
		manager = new DBManager(dbuser,dbpass,dbname,dbip);
		fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
		this.inicializarFicheros();
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
	
	

	private void inicializarFicheros() {
		switch(seleccionIdioma) {
		case "Castellano":
			ficheroIdioma=new FicheroIdioma(ficheroCastellano);
		break;
		case "Euskara":
			ficheroIdioma=new FicheroIdioma(ficheroEuskara);
		break;
		case "Ingles":
			ficheroIdioma=new FicheroIdioma(ficheroIngles);
		break;
		}
	}



	private void crearAcciones() {
		anadirCampo = new MiAccion (ficheroIdioma.getListaPalabras().get(0),new ImageIcon("iconos/edit_add.png"),ficheroIdioma.getListaPalabras().get(1),
				new Integer(KeyEvent.VK_C));
		anadirMuestra = new MiAccion (ficheroIdioma.getListaPalabras().get(2),new ImageIcon("iconos/amigo.png"),ficheroIdioma.getListaPalabras().get(3),
				new Integer(KeyEvent.VK_A));
		recargar = new MiAccion (ficheroIdioma.getListaPalabras().get(4),new ImageIcon("iconos/recargar.png"),ficheroIdioma.getListaPalabras().get(5),
				new Integer(KeyEvent.VK_R));
		ayuda = new MiAccion (ficheroIdioma.getListaPalabras().get(6),new ImageIcon("iconos/edit.png"),ficheroIdioma.getListaPalabras().get(7),
				new Integer(KeyEvent.VK_H));
		salir = new MiAccion (ficheroIdioma.getListaPalabras().get(8),new ImageIcon("iconos/exit.png"),ficheroIdioma.getListaPalabras().get(9),
				new Integer(KeyEvent.VK_S));
	}
	private void crearComboBox1() {
		//coger los datos de la BD
		comboLocalizacion1=new JComboBox<>();
		comboMeteo1=new JComboBox<>();
		comboFecha1=new JComboBox<>();
		cargarDatosComboBox(comboLocalizacion1,comboMeteo1,comboFecha1);
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
		comboLocalizacion2.addItem("Orio");
		comboMeteo2=new JComboBox<>();
		comboFecha2=new JComboBox<>();
		comboFecha2.addItem("01/03/1999");		
	}
	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout(0,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(crearPanelNorte(),BorderLayout.NORTH);
		panel.add(crearPanelPestanas(),BorderLayout.CENTER);
		return panel;
	}
	private Component crearPanelPestanas() {
		JTabbedPane panel = new JTabbedPane();
		panel.addTab(ficheroIdioma.getListaPalabras().get(10), crearPanelMapa());
		panel.addTab(ficheroIdioma.getListaPalabras().get(11), crearPanelInfoGeneral());
		panel.addTab(ficheroIdioma.getListaPalabras().get(12), crearPanelGraficos());
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
	private Component crearPanelInfoGeneral() {
		JPanel panel = new JPanel (new GridLayout(3,1));
		panel.add(crearPanelInfoNorte());
		panel.add(crearPanelInfoCentro());
		panel.add(crearPanelInfoSur());

		return panel;
	}
	private Component crearPanelInfoSur() {
		JPanel panel = new JPanel (new GridLayout(2,2));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(13))));
		panel.add(crearPanelJLabel(labelLugar=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(14))));
		panel.add(crearPanelJLabel(labelArea=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(15))));
		panel.add(crearPanelJLabel(labelHabitantes=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(16))));
		panel.add(crearPanelJLabel(labelDensidad=new JLabel(" ")));
		
		return panel;
	}



	private Component crearPanelJLabelTitulo(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setFont(fuenteTituloInfoGeneral);
		panel.add(label);

		return panel;
	}



	private Component crearPanelInfoCentro() {
		JPanel panel = new JPanel (new GridLayout(2,2));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), ficheroIdioma.getListaPalabras().get(17)));
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(18))));
		panel.add(crearPanelJLabel(labelTemp=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(19))));
		panel.add(crearPanelJLabel(labelCo2=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(20))));
		panel.add(crearPanelJLabel(labelHumedad=new JLabel(" ")));
		
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(21))));
		panel.add(crearPanelJLabel(labelVoc=new JLabel(" ")));
		
		return panel;
	}



	private Component crearPanelInfoNorte() {
		JPanel panel = new JPanel (new GridLayout(2,4));
		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(22))));
		panel.add(crearPanelJLabel(labelMuestraID=new JLabel(" ")));

		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(23))));
		panel.add(crearPanelJLabel(labelFecha=new JLabel(" ")));

		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(24))));
		panel.add(crearPanelJLabel(labelMeteo=new JLabel(" ")));

		panel.add(crearPanelJLabelTitulo(new JLabel(ficheroIdioma.getListaPalabras().get(25))));
		panel.add(crearPanelJLabel(labelUsuario=new JLabel(" ")));
		
		return panel;
	}



	private Component crearPanelJLabel(JLabel label) {
		JPanel panel = new JPanel (new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(label);

		return panel;
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
		
		JButton boton;
		boton =(JButton) toolBar.add(anadirCampo);
		toolBar.add(Box.createGlue());
		boton =(JButton) toolBar.add(recargar);
		boton =(JButton) toolBar.add(anadirMuestra);
		toolBar.add(Box.createHorizontalGlue());
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
		JMenu menudAyuda = new JMenu (ficheroIdioma.getListaPalabras().get(26));
		menudAyuda.setMnemonic(new Integer(KeyEvent.VK_A));
		JMenuItem opcionMenu = new JMenuItem (ayuda);
		menudAyuda.add(opcionMenu);
		return menudAyuda;
	}
	private JMenu crearMenuEditar() {
		JMenu menuEditar = new JMenu (ficheroIdioma.getListaPalabras().get(27));
		menuEditar.setMnemonic(new Integer(KeyEvent.VK_E));
		JMenuItem opcionMenu = new JMenuItem (anadirCampo);
		menuEditar.add(opcionMenu);
		opcionMenu = new JMenuItem (recargar);
		menuEditar.add(opcionMenu);
		return menuEditar;
	}
	private JMenu crearMenuSalir() {
		JMenuItem op;
		JMenu menuSalir = new JMenu (ficheroIdioma.getListaPalabras().get(28));
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
			
			if (texto.equals(ficheroIdioma.getListaPalabras().get(0))){
				compararActivado=!compararActivado;
				if(compararActivado) {
					panelComboBox2.add(comboLocalizacion2,0);
					panelComboBox2.add(comboMeteo2,1);
					panelComboBox2.add(comboFecha2,2);
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
			if (texto.equals(ficheroIdioma.getListaPalabras().get(2))){//añadir muestra
				DialogoInsertarMuestra dialogoInsertarMuestra= new DialogoInsertarMuestra(Principal.this, ficheroIdioma.getListaPalabras().get(2), true, ficheroIdioma.getListaPalabras(),manager);

			}
			if (texto.equals(ficheroIdioma.getListaPalabras().get(4))){//recargar
				cargarDatosComboBox(comboLocalizacion1, comboMeteo1, comboFecha1);
				cargarDatosComboBox(comboLocalizacion2, comboMeteo2, comboFecha2);
			}
			if(texto.equals(ficheroIdioma.getListaPalabras().get(6))) {
				
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
			if (texto.equals(ficheroIdioma.getListaPalabras().get(8))){
				
				Principal.this.dispose();
			}
		}

		

			
	}
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
		//NotificationSender noti=new NotificationSender();
		//PaymentsTest payments=new PaymentsTest("sk_test_dZGN1z9nd2Bx0WHAMfRmomsJ00wCLPBWmC");
		Principal programa = new Principal();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
				
		switch(e.getActionCommand()) {
		
		case "Buscar":
			if(!compararActivado) {
				realizarBusquedaSinComparar();
			}
			break;
		case "localizacion":		
			if(comboLocalizacion1.getItemCount()!=0)this.cargarDatosMeteo(comboLocalizacion1, comboMeteo1);
			break;
			
		case "meteo":		
			if(comboMeteo1.getItemCount()!=0)this.cargarDatosFecha(comboLocalizacion1, comboMeteo1, comboFecha1);
			break;
		}
	}



	private void realizarBusquedaSinComparar() {
		String pueblo=comboLocalizacion1.getSelectedItem().toString();
		String meteo=comboMeteo1.getSelectedItem().toString();
		String fecha=comboFecha1.getSelectedItem().toString();
		String condicion=(" WHERE Localizaciones.nombre = '"+ pueblo+"' AND Meteos.descripcion='"+meteo+"' AND fecha='"+fecha+"'");
		
		ResultSet resultados = manager.executeQuery("SELECT Muestras.muestraID, Meteos.descripcion, Muestras.fecha, Usuarioa.nombre, Muestras.temperatura, Muestras.humedad, Muestras.co2eq, Muestras.voc, Localizaciones.nombre AS lugar, Localizaciones.habitantes, Localizaciones.areakm2, Localizaciones.habitantes/Localizaciones.areakm2 AS 'densidad (habitantes/km2)'\r\n" + 
				"FROM ((Muestras JOIN Meteos ON Muestras.meteorologia=Meteos.meteoID) JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID)JOIN Usuarioa ON Muestras.usuario=Usuarioa.usuarioID\r\n" + 
				condicion+";");
		try {
			resultados.next();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		actualizarcampos(resultados);
	}

	private void actualizarcampos(ResultSet resultados) {
		actualizarPestanaTexto(resultados);
	}



	private void actualizarPestanaTexto(ResultSet resultados) {
		try {
			labelMuestraID.setText((Integer.toString(resultados.getInt("muestraID"))));
			labelMeteo.setText(resultados.getString("descripcion"));
			labelFecha.setText(String.valueOf(resultados.getDate("fecha")));
			labelUsuario.setText(resultados.getString("nombre"));
			labelTemp.setText(Float.toString(resultados.getFloat("temperatura"))+" ï¿½C");
			labelHumedad.setText(Float.toString(resultados.getFloat("humedad"))+" %");
			labelCo2.setText(Integer.toString(resultados.getInt("Co2eq"))+" PPP");
			labelVoc.setText(Float.toString(resultados.getFloat("VOC"))+" l/min");
			labelLugar.setText((resultados.getString("lugar")));
			labelHabitantes.setText(Integer.toString(resultados.getInt("habitantes")));
			labelArea.setText(Integer.toString(resultados.getInt("areakm2"))+" km2");
			labelDensidad.setText(Integer.toString(resultados.getInt("densidad (habitantes/km2)"))+ficheroIdioma.getListaPalabras().get(29));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void cargarDatosLocalizacionMuestra(JComboBox<String> comboLocalizacion) {
		ResultSet resultados = manager.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Muestras JOIN Localizaciones ON Muestras.localizacion=Localizaciones.localizacionID\r\n" + 
				"GROUP BY Localizaciones.localizacionID;");
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
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		manager.conClose();			
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
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		manager.conClose();			
	}
}
