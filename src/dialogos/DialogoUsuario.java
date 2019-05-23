package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBManager;
import modelos.UsuarioVO;

@SuppressWarnings("serial")
public class DialogoUsuario extends JDialog{
	
	JFrame ventana;
	List<String>listaPalabras;
	DBManager manager;
	JTextField nombre, email;
	JComboBox <String> localizacion, idioma;
	JPasswordField pass;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	boolean editando=false;
	UsuarioVO user;
	JButton botonOK,botonEditar, botonSalir;
	
	public DialogoUsuario (JFrame ventana, String titulo, boolean modo, List<String> list, DBManager manager,UsuarioVO user) {
		super(ventana,titulo,modo);
		this.listaPalabras=list;
		this.ventana=ventana;
		this.setSize(600,500);
		this.setLocation (500,200);
		this.user=user;
		this.manager=manager;
		this.cargarLocalizacionesIdioma();
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
		
	}
	
	private void cargarLocalizacionesIdioma() {
		this.cargarDatosIdioma(idioma=new JComboBox<>());
		this.idioma.setSelectedIndex(user.getIdiomaSeleccionado());
		
		this.cargarDatosLocalizaciones(localizacion=new JComboBox<>());
		this.localizacion.setSelectedIndex(user.getLocalizacion());		
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelDobleBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
	private Component crearPanelDobleBotones() {
		JPanel panel = new JPanel (new GridLayout(2,1));
		panel.add(crearPanelBotones());
		panel.add(crearPanelBoton(botonSalir = new JButton(listaPalabras.get(28))));
		return panel;
	}

	private Component crearPanelBoton(JButton boton) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogoUsuario.this.dispose();
			}
			
		});
		panel.add(boton);
		return panel;
	}

	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(7,1));
		
		panel.add(crearJLabelCombo(new JLabel(String.valueOf(user.getUsuarioID())), listaPalabras.get(32)));
		
		panel.add(crearJLabelCombo(new JLabel(calcularTipoUsuario()), listaPalabras.get(33)));

		panel.add(crearTextField(nombre=new JTextField(user.getNombre()), listaPalabras.get(34)));
		
		panel.add(crearTextField(pass= new JPasswordField(user.getPass()), listaPalabras.get(35)));
		
		panel.add(crearTextField(email=new JTextField(user.getEmail()), listaPalabras.get(36)));
		
		panel.add(crearComboBox(localizacion, listaPalabras.get(37)));

		panel.add(crearComboBox(idioma, listaPalabras.get(38)));

		return panel;
	}
	private String calcularTipoUsuario() {
		String tipo;
		
		if(user.getTipo()==0)tipo="Admin";
		else if(user.getTipo()==1)
		{
			tipo="Basic";
		
		}
		else tipo="Pro";
		return tipo;
	}

	private Component crearComboBox(JComboBox<String> text, String string) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(string);	
		label.setFont(fuenteTituloInfoGeneral);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		panel.add(label);
		text.setEnabled(editando);
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
		text.setEnabled(editando);
		panel.add(text);
		return panel;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		botonOK = new JButton ("OK");
		botonOK.setEnabled(false);	

		botonOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleStatusEditando();
				//UsuarioDAO.getInstance("Admin","Osen!1234","osen","68.183.211.91").updateUser(nombre.getText(), pass.getPassword().toString(), email.getText(), localizacion.getText(), idioma.getText(), type, id);
				
			}
			
		});
		panel.add(botonOK);
		
		botonEditar = new JButton ("Editar");
	
		botonEditar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleStatusEditando();

				
				
			}
			
		});
		panel.add(botonEditar);
		return panel;
	}
	
	private void toggleStatusEditando() {
		editando=!editando;
		botonOK.setEnabled(editando);
		botonEditar.setEnabled(!editando);	
		botonSalir.setEnabled(!editando);
		
		nombre.setEnabled(editando);
		pass.setEnabled(editando);
		email.setEnabled(editando);
		localizacion.setEnabled(editando);
		idioma.setEnabled(editando);

	}
	
	public void cargarDatosIdioma(JComboBox<String> combo) {
		ResultSet resultados = manager.executeQuery("SELECT Idiomas.descripcion\r\n" + 
				"FROM Idiomas\r\n" + 
				"GROUP BY Idiomas.idiomaID;");
		try {
			combo.removeAllItems();
			while(resultados.next()) {
				String result=resultados.getString("descripcion");
				combo.addItem(result);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoUsuario.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		manager.conClose();		
	}
	public void cargarDatosLocalizaciones(JComboBox<String> combo) {
		ResultSet resultados = manager.executeQuery("SELECT Localizaciones.nombre\r\n" + 
				"FROM Localizaciones\r\n;");
		try {
			combo.removeAllItems();
			while(resultados.next()) {
				combo.addItem(resultados.getString("nombre"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoUsuario.this, e.getMessage(), "Codigo de error SQL: "+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		manager.conClose();		
	}
	


}
