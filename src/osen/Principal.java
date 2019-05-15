package osen;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lineaSerie.LineaSeriePrincipal;


public class Principal extends JFrame implements ActionListener{
	
	JScrollPane sPanel;
	JMenuBar barra;
	JMenu	menuAgregaciones, menuSalir;
	JMenuItem opcionMenu;
	JPanel panelComboBox2;
	MiAccion anadirCampo, anadirMuestra, ayuda, recargar, salir;
	JComboBox<String> comboLocalizacion1, comboMeteo1, comboFecha1, comboLocalizacion2, comboMeteo2, comboFecha2;
	boolean compararActivado=false;
	LineaSeriePrincipal lsP;
	
	public Principal(){
		super("OSEN");
		this.setLocation (340,100);
		this.setSize(1000,800);
		this.crearAcciones();
		this.crearComboBox1();
		this.crearComboBox2();
		this.setJMenuBar(crearMenu());
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		lsP = new LineaSeriePrincipal();
		lsP.accion();
	}
	
	

	private void crearAcciones() {
		anadirCampo = new MiAccion ("AnadirCampo",new ImageIcon("iconos/edit_add.png"),"Anade un nuevo campo",
				new Integer(KeyEvent.VK_C));
		anadirMuestra = new MiAccion ("AnadirMuestra",new ImageIcon("iconos/amigo.png"),"Anade una nueva muestra",
				new Integer(KeyEvent.VK_A));
		recargar = new MiAccion ("Recargar",new ImageIcon("iconos/recargar.png"),"Refresca la informacinn de la base de datos",
				new Integer(KeyEvent.VK_R));
		ayuda = new MiAccion ("Ayuda",new ImageIcon("iconos/edit.png"),"Revisar informacinn de las variables meterologica.",
				new Integer(KeyEvent.VK_H));
		salir = new MiAccion ("Salir",new ImageIcon("iconos/exit.png"),"Cierra la aplicacion",
				new Integer(KeyEvent.VK_S));
	}
	private void crearComboBox1() {
		//coger los datos de la BD
		comboLocalizacion1=new JComboBox<>();
		comboLocalizacion1.addItem("Orio");
		comboMeteo1=new JComboBox<>();
		comboMeteo1.addItem("Soleado");
		comboMeteo1.addItem("Lluvioso");
		comboFecha1=new JComboBox<>();
		comboFecha1.addItem("01/03/1999");
	}
	private void crearComboBox2() {
		//coger los datos de la BD
		comboLocalizacion2=new JComboBox<>();
		comboLocalizacion2.addItem("Orio");
		comboMeteo2=new JComboBox<>();
		comboMeteo2.addItem("Soleado");
		comboMeteo2.addItem("Lluvioso");
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
		panel.addTab("Mapa", crearPanelMapa());
		panel.addTab("Informacinn general", crearPanelInfoGeneral());
		panel.addTab("Grnficos", crearPanelGraficos());
		return panel;
	}
	private Component crearPanelGraficos() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		
		return panel;
	}
	private Component crearPanelMapa() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		
		return panel;
	}
	private Component crearPanelInfoGeneral() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		
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
		JMenu menudAyuda = new JMenu ("Ayuda");
		menudAyuda.setMnemonic(new Integer(KeyEvent.VK_A));
		JMenuItem opcionMenu = new JMenuItem (ayuda);
		menudAyuda.add(opcionMenu);
		return menudAyuda;
	}
	private JMenu crearMenuEditar() {
		JMenu menuEditar = new JMenu ("Editar");
		menuEditar.setMnemonic(new Integer(KeyEvent.VK_E));
		JMenuItem opcionMenu = new JMenuItem (anadirCampo);
		menuEditar.add(opcionMenu);
		opcionMenu = new JMenuItem (recargar);
		menuEditar.add(opcionMenu);
		return menuEditar;
	}
	private JMenu crearMenuSalir() {
		JMenuItem op;
		JMenu menuSalir = new JMenu ("Salir");
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
			
			if (texto.equals("AnadirCampo")){
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
			if (texto.equals("AnadirMuestra")){
				
			}
			if (texto.equals("Recargar")){
				
			}
			if(texto.equals("Ayuda")) {
				
			}
			if (texto.equals("Salir")){
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
		
		
		Principal programa = new Principal();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		case "Buscar":
			System.out.println("Buscar");
			if(!compararActivado) {
				String pueblo=comboLocalizacion1.getSelectedItem().toString();
				String meteo=comboMeteo1.getSelectedItem().toString();
				String fecha=comboFecha1.getSelectedItem().toString();
				String condicion=("WHERE nombre = '\"+ pueblo+\"' AND");
				if(!pueblo.equals("Cualquiera1")) condicion+=(" nombre = '"+ pueblo+"' AND" );
				
				
				condicion+=";";
				System.out.println(condicion);

			}
			break;
		}
	}
	
}
