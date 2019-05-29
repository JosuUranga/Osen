package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

import com.stripe.exception.StripeException;

import idiomas.ControladorIdioma;
import modelos.IdiomaDAO;
import modelos.LocalizacionDAO;
import modelos.UsuarioDAO;
import modelos.UsuarioVO;
import muestras.Localizacion;
import osen.Principal;
import stripe.Suscripciones;

@SuppressWarnings("serial")
public class DialogoUsuario extends JDialog{
	
	JFrame ventana;
	ControladorIdioma listaPalabras;
	JTextField nombre, email;
	JComboBox <String>  idioma;
	JComboBox <Localizacion>  localizacion;

	JPasswordField pass;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	boolean editando=false;
	UsuarioVO user;
	JButton botonOK,botonEditar, botonSalir;
	JButton upgrade;
	JLabel tipo;
	PropertyChangeSupport soporte;
	JPanel panelPrincipal;
	JLabel labelID;
	
	public DialogoUsuario (JFrame ventana, String titulo, boolean modo, ControladorIdioma controladorIdioma,UsuarioVO user, PropertyChangeListener listener) {
		super(ventana,titulo,modo);
		soporte = new PropertyChangeSupport(this);
		soporte.addPropertyChangeListener(listener);
		this.listaPalabras=controladorIdioma;
		this.ventana=ventana;
		this.setSize(600,500);
		this.setLocation (500,200);
		this.user=user;
		this.cargarLocalizacionesIdioma();
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
	}	
	private void cargarLocalizacionesIdioma() {
		this.cargarDatosIdioma(idioma=new JComboBox<>());
		this.idioma.setSelectedIndex(user.getIdiomaSeleccionado()-1);
		
		this.cargarDatosLocalizaciones(localizacion=new JComboBox<>());
		if(user.getTipo()==0)this.localizacion.setSelectedIndex(-1);	
		else this.localizacion.setSelectedIndex(user.getLocalizacion());		

	}

	private Container crearPanelDialogo() {
		panelPrincipal = new JPanel (new BorderLayout(0,20));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
		panelPrincipal.add(crearPanelDatos(),BorderLayout.CENTER);
		panelPrincipal.add(crearPanelDobleBotones(),BorderLayout.SOUTH);
		return panelPrincipal;
	}
	
	private Component crearPanelDobleBotones() {
		JPanel panel = new JPanel (new GridLayout(2,1));
		panel.add(crearPanelBotones());
		panel.add(crearPanelBoton(botonSalir = new JButton(listaPalabras.getListaPalabras().get(28))));
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
		
		panel.add(crearJLabelCombo(labelID=new JLabel(String.valueOf(user.getUsuarioID())), listaPalabras.getListaPalabras().get(32)));
		
		panel.add(crearJLabelCombo2(crearPanelTipoUsuarioUpgrade(), listaPalabras.getListaPalabras().get(33)));

		panel.add(crearTextField(nombre=new JTextField(user.getNombre()), listaPalabras.getListaPalabras().get(34)));
		
		panel.add(crearTextField(pass= new JPasswordField(user.getPass()), listaPalabras.getListaPalabras().get(35)));
		
		panel.add(crearTextField(email=new JTextField(user.getEmail()), listaPalabras.getListaPalabras().get(36)));
		
		panel.add(crearComboBoxLoca(localizacion, listaPalabras.getListaPalabras().get(37)));

		panel.add(crearComboBox(idioma, listaPalabras.getListaPalabras().get(38)));

		return panel;
	}
	private Component crearJLabelCombo2(JPanel panel2, String string) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(string);	
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		label.setFont(fuenteTituloInfoGeneral);
		panel.add(label);
		panel.add(panel2);
		return panel;
	}

	private JPanel crearPanelTipoUsuarioUpgrade() {
		JPanel panel = new JPanel(new GridLayout(1,2));
		tipo = new JLabel(user.calcularTipoUsuario());		
		tipo.setHorizontalAlignment(0);
		panel.add(tipo);
		
		upgrade = new JButton (listaPalabras.getListaPalabras().get(53));
		upgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("upgrade")) {
				new DialogoTarjeta(DialogoUsuario.this, "Payment", true, listaPalabras, user);
				upgradeText();
				}
				if(e.getActionCommand().equals("downgrade")) {
					
					try {
						Suscripciones.getInstance(listaPalabras).cancelSub(user.getEmail());
						user.setTipo(0);
						UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(user.getNombre(), user.getPass(), user.getEmail(), -1, user.getLocalizacion(), user.getTipo(), user.getUsuarioID());
						upgradeText();
					}  catch (StripeException e2) {
						JOptionPane.showMessageDialog(DialogoUsuario.this, e2.getLocalizedMessage(), listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(DialogoUsuario.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		
		upgradeText();
		panel.add(upgrade);
		return panel;
		
		
	}

	private void upgradeText() {
		tipo.setText(user.calcularTipoUsuario());
		if(user.getTipo()==0) {
			upgrade.setText(listaPalabras.getListaPalabras().get(53));
			upgrade.setActionCommand("upgrade");

		}
		else if(user.getTipo()==1) {
			cargarDatosLocalizaciones(localizacion);
			upgrade.setText(listaPalabras.getListaPalabras().get(55));
			upgrade.setActionCommand("downgrade");

		}
		else {
			upgrade.setText("ADMIN");
			upgrade.setEnabled(false);

		}
	}


	private Component crearComboBoxLoca(JComboBox<Localizacion> text, String string) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(string);	
		label.setFont(fuenteTituloInfoGeneral);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		panel.add(label);
		text.setEnabled(editando);
		panel.add(text);
		return panel;
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
				
				if(email.getText().matches(Principal.EMAIL_PATTERN)) {
					toggleStatusEditando();
					try {
						updateUser();
						updatePaneles();
		
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(DialogoUsuario.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					};
				}
				else {
					JOptionPane.showMessageDialog(DialogoUsuario.this, "Email no valido", listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
				}
				
			}

					
		});
		panel.add(botonOK);
		
		botonEditar = new JButton (listaPalabras.getListaPalabras().get(27));
	
		botonEditar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleStatusEditando();
				
			}
			
		});
		panel.add(botonEditar);
		return panel;
	}
	private void updatePaneles() {
		panelPrincipal.removeAll();
		panelPrincipal.add(crearPanelDatos(),BorderLayout.CENTER);
		panelPrincipal.add(crearPanelDobleBotones(),BorderLayout.SOUTH);
		panelPrincipal.revalidate();
		panelPrincipal.repaint();				
	}
	private void updateUser() throws SQLException{
		Localizacion loca = (Localizacion) localizacion.getSelectedItem();
		user.setIdiomaSeleccionado(idioma.getSelectedIndex()+1);
		
		if(user.getTipo()!=0)UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(nombre.getText(), String.valueOf(pass.getPassword()), email.getText(), loca.getId(), idioma.getSelectedIndex()+1, user.getTipo(), user.getUsuarioID());
		else UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(nombre.getText(), String.valueOf(pass.getPassword()), email.getText(), -1, idioma.getSelectedIndex()+1, user.getTipo(), user.getUsuarioID());
		user.setNombre(nombre.getText());
		user.setEmail(email.getText());
		user.setPass( String.valueOf(pass.getPassword()));
		if(user.getTipo()!=0) {
			user.setLocalizacion(loca.getId());
		}
		soporte.firePropertyChange("idioma", null, idioma.getSelectedIndex());
	}
	private void toggleStatusEditando() {
		editando=!editando;
		botonOK.setEnabled(editando);
		botonEditar.setEnabled(!editando);	
		botonSalir.setEnabled(!editando);
		
		nombre.setEnabled(editando);
		pass.setEnabled(editando);
		email.setEnabled(editando);
		if(user.getTipo()!=0)localizacion.setEnabled(editando);
		idioma.setEnabled(editando);

	}
	
	public void cargarDatosIdioma(JComboBox<String> combo) {
		
		try {
			System.out.println(user.calcularTipoUsuario());
			List<String> listaIdiomas = IdiomaDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).getIdiomas();
			combo.removeAllItems();
			listaIdiomas.forEach(idioma->combo.addItem(idioma));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoUsuario.this, e.getMessage(), listaPalabras.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
	}
	

	public void cargarDatosLocalizaciones(JComboBox<Localizacion> combo) {
	
		try {
			List<Localizacion>listaLoca=LocalizacionDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip)
					.getAllLocalizaciones();
			localizacion.removeAllItems();
			listaLoca.forEach(loca->localizacion.addItem(loca));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DialogoUsuario.this, e.getMessage(), listaPalabras.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
}
