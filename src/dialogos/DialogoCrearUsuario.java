package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modelos.IdiomaDAO;
import modelos.UsuarioDAO;
import osen.Principal;

@SuppressWarnings("serial")
public class DialogoCrearUsuario extends JDialog{
	
	Login ventana;
	List<String>listaPalabras;
	JTextField nombre, email;
	JComboBox <String> idioma;
	JPasswordField pass;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	JButton botonOK,botonEditar, botonSalir;
	int numeroLocalizacion;
	
	public final static String dbuser="Basic";
	
	
	public DialogoCrearUsuario (Login login, String titulo, boolean modo, List<String> list) {
		super(login,titulo,modo);
		this.listaPalabras=list;
		this.ventana=login;
		this.setSize(600,500);
		this.setLocation (500,200);
		this.cargarLocalizacionesIdioma();
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
		
	}
	
	private void cargarLocalizacionesIdioma() {
		this.cargarDatosIdioma(idioma=new JComboBox<>());
		this.idioma.setSelectedIndex(-1);
			
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelBoton(botonSalir = new JButton("OK")),BorderLayout.SOUTH);
		return panel;
	}
	
	

	private Component crearPanelBoton(JButton boton) {
		JPanel panel = new JPanel(new GridLayout(1,2,30,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e3) {
				
				try {
					UsuarioDAO.getInstance("Admin",Principal.dbpass, Principal.dbname, Principal.dbip).addUser(nombre.getText(), String.valueOf(pass.getPassword()), email.getText(),(idioma.getSelectedIndex()+1));
					DialogoCrearUsuario.this.dispose();

				} catch (SQLException e1) {
					if(e1.getErrorCode()==1062) JOptionPane.showMessageDialog(DialogoCrearUsuario.this, listaPalabras.get(40), listaPalabras.get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					else JOptionPane.showMessageDialog(DialogoCrearUsuario.this, e1.getMessage(), listaPalabras.get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(DialogoCrearUsuario.this, listaPalabras.get(42)+e2.getLocalizedMessage()+")", listaPalabras.get(43), JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		panel.add(boton);
		JButton boton2= new JButton(listaPalabras.get(39));
		boton2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogoCrearUsuario.this.dispose();
			}
			
		});
		panel.add(boton2);
		return panel;
	}

	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(5,1));
				
		panel.add(crearJLabelCombo(new JLabel("Basic"), listaPalabras.get(33)));

		panel.add(crearTextField(nombre=new JTextField(), listaPalabras.get(34)));
		
		panel.add(crearTextField(pass= new JPasswordField(), listaPalabras.get(35)));
		
		panel.add(crearTextField(email=new JTextField(), listaPalabras.get(36)));
		
		panel.add(crearComboBox(idioma, listaPalabras.get(38)));

		return panel;
	}
	

	private Component crearComboBox(JComboBox<String> text, String string) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(string);	
		label.setFont(fuenteTituloInfoGeneral);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		panel.add(label);
		panel.add(text);
		return panel;
	}

	private Component crearJLabelCombo(JLabel text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(titulo);	
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		label.setFont(fuenteTituloInfoGeneral);
		panel.add(label);
		text.setHorizontalAlignment(0);
		panel.add(text);
		return panel;
	}
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(titulo);	
		label.setFont(fuenteTituloInfoGeneral);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		panel.add(label);
		text.setHorizontalAlignment(0);
		panel.add(text);
		return panel;
	}
	
	
	
	
	
	public void cargarDatosIdioma(JComboBox<String> combo) {
	
		try {
			List<String> listaIdiomas = IdiomaDAO.getInstance(dbuser, Principal.dbpass, Principal.dbname, Principal.dbip).getIdiomas();
			combo.removeAllItems();
			listaIdiomas.forEach(idioma->combo.addItem(idioma));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoCrearUsuario.this, e.getMessage(), listaPalabras.get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
	}
	

}

