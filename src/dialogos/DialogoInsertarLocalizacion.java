package dialogos;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import estados.GestorEstadosAnadirMuestra;
import idiomas.ControladorIdioma;
import modelos.LocalizacionDAO;
import modelos.UsuarioVO;
import muestras.MuestraCo2;
import osen.Principal;




@SuppressWarnings("serial")
public class DialogoInsertarLocalizacion extends JDialog{
	
	DialogoInsertarMuestra ventana;
	MuestraCo2 muestra;
	
	JTextField nombre,habitantes,area;
	ControladorIdioma listaPalabras;
	GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra;
	UsuarioVO usuario;
	boolean anadirLocalizacionSeleccionado=false;
	
	
	public DialogoInsertarLocalizacion (GestorEstadosAnadirMuestra gestorEstadosAnadirMuestra, DialogoInsertarMuestra dialogoInsertarMuestra,String titulo, boolean modo, ControladorIdioma listaPalabras2, UsuarioVO usuario) {
		super(dialogoInsertarMuestra,titulo,modo);
		this.usuario=usuario;
		this.gestorEstadosAnadirMuestra=gestorEstadosAnadirMuestra;
		this.listaPalabras=listaPalabras2;
		this.ventana=dialogoInsertarMuestra;
		this.setSize(600,400);
		this.setLocation (100,100);
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
		
		panel.add(crearTextField(nombre=new JTextField(), listaPalabras.getListaPalabras().get(34)));
		panel.add(crearTextField(habitantes=new JTextField(), listaPalabras.getListaPalabras().get(15)));
		panel.add(crearTextField(area=new JTextField(), listaPalabras.getListaPalabras().get(14)));

		

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
					LocalizacionDAO.getInstance(usuario.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).addLocalizacion(nombre.getText(), Integer.parseInt(habitantes.getText()), Float.parseFloat(area.getText()));
					anadirLocalizacionSeleccionado=true;
					DialogoInsertarLocalizacion.this.dispose();

				} catch (SQLException e1) {
					if(e1.getErrorCode()==1062)	JOptionPane.showMessageDialog(DialogoInsertarLocalizacion.this, listaPalabras.getListaPalabras().get(52)+nombre.getText(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					else JOptionPane.showMessageDialog(DialogoInsertarLocalizacion.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoInsertarLocalizacion.this, listaPalabras.getListaPalabras().get(42)+e2.getLocalizedMessage()+")", listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
					
				}	
				
			}
			
		});
	
		JButton boton2 = new JButton (listaPalabras.getListaPalabras().get(39));
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

	

	public boolean isAnadirLocalizacionSeleccionado() {
		return anadirLocalizacionSeleccionado;
	}


}
