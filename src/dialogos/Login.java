package dialogos;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import modelos.UsuarioDAO;
import objetos.UsuarioVO;
import osen.Principal;
import otros.TextPrompt;
import stripe.Suscripciones;




@SuppressWarnings("serial")
public class Login extends JDialog implements ActionListener, ItemListener{
	JTextField usuario;
	JPasswordField password;
	JButton logear;
	boolean loginCorrecto;
	UsuarioVO user;
	ControladorIdioma listaPalabras;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	JComboBox <String> idioma;
	JPanel panel;
	PropertyChangeSupport soporte;
	
	public JComboBox<String> getIdioma() {
		return idioma;
	}
	public Login(JFrame ventana, PropertyChangeListener listener, ControladorIdioma ci) {
		super(ventana,"Login",true);
		soporte = new PropertyChangeSupport(this);
		soporte.addPropertyChangeListener(listener);
		listaPalabras=ci;
		this.setSize(600,325);
		this.setLocation (600,400);
		loginCorrecto=false;
		this.cargarDatosIdioma(idioma=new JComboBox<>());
		listaPalabras.cargarIdioma();
		this.setContentPane(crearPanelGeneral());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) { 
		soporte.addPropertyChangeListener(listener); 
	} 
	public void removePropertyChangeListener(PropertyChangeListener listener) { 
		soporte.removePropertyChangeListener(listener); 
	} 
	public void cargarDatosIdioma(JComboBox<String> combo) {
		
		try {
			List<String> listaIdiomas = IdiomaDAO.getInstance("Basic", Principal.dbpass, Principal.dbname, Principal.dbip).getIdiomas();
			combo.removeAllItems();
			listaIdiomas.forEach(idioma->combo.addItem(idioma));
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Login.this, e.getMessage(), listaPalabras.getListaPalabras().get(41)+e.getErrorCode(), JOptionPane.WARNING_MESSAGE);
		}
		combo.setSelectedIndex(0);
	}
	private Container crearPanelGeneral() {
		panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(crearPanelCuadro());	
		return panel;
	}
	private void updatePanel() {
		idioma.removeItemListener(this);
		panel.removeAll();
		panel.add(crearPanelCuadro());	
		panel.revalidate();
		panel.repaint();
	}
	public Container crearPanelTitulo() {
		JPanel panel=new JPanel();
		JLabel label= new JLabel("OSEN");
		panel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
		panel.add(label);
		return panel;
	}
	public Container crearPanelCuadro() {
		JPanel panel= new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.add(crearPanelIdioma(idioma, listaPalabras.getListaPalabras().get(38)),BorderLayout.NORTH);
		panel.add(crearPanelLogin(),BorderLayout.CENTER);
		panel.setOpaque(true);
		return panel;
	}
	private Component crearPanelIdioma(JComboBox<String> text, String string) {
		JPanel panel = new JPanel(new GridLayout(1,2));
		JLabel label = new JLabel(string);	
		label.setFont(fuenteTituloInfoGeneral);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
		text.addItemListener(this);
		panel.add(label);
		panel.add(text);
		return panel;
	}
	public Container crearPanelLogin() {
		JPanel panel=new JPanel(new GridLayout(2,2,10,10));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createEmptyBorder(20, 40, 0, 40)));
		panel.setPreferredSize(new Dimension(500,200));
		panel.add(crearPanelTextos());
		panel.add(crearPanelBoton());
		panel.setBackground(Color.white);
		return panel;	
	}	
	public Container crearPanelTextos() {
		JPanel panel=new JPanel(new GridLayout(2,2,10,10));
		JLabel label,label2;
		label=new JLabel(listaPalabras.getListaPalabras().get(36));
		panel.setBackground(Color.white);
		label.setHorizontalAlignment(JLabel.LEFT);
		label2=new JLabel(listaPalabras.getListaPalabras().get(35));
		label2.setHorizontalAlignment(JLabel.LEFT);
		panel.add(label);
		usuario=new JTextField();
		TextPrompt placeholder=new TextPrompt("usuario@dominio.com",usuario);
		placeholder.changeAlpha(0.75f);
		placeholder.changeStyle(Font.ITALIC);
		panel.add(usuario);
		panel.add(label2);
		panel.add(password=new JPasswordField());
		return panel;
	}
	public Container crearPanelBoton() {
		JPanel panel=new JPanel(new GridLayout(1,2,40,10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		panel.setBackground(Color.white);
		logear=new JButton(listaPalabras.getListaPalabras().get(48));
		logear.setActionCommand("logear");
		this.getRootPane().setDefaultButton(logear);
		logear.addActionListener(this);
		panel.add(logear);
		logear=new JButton(listaPalabras.getListaPalabras().get(47));
		logear.setActionCommand("signup");
		logear.addActionListener(this);
		panel.add(logear);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("logear")) {
			try {
				user = UsuarioDAO.getInstance("Basic",Principal.dbpass, Principal.dbname, Principal.dbip).getUser(usuario.getText(), String.valueOf(password.getPassword()));
				Suscripciones sus=Suscripciones.getInstance(listaPalabras);
				
				if(user!=null) {
					if (user.getTipo()==UsuarioVO.PRO && !sus.checkSubActive(user.getEmail())) {
						user.setTipo(UsuarioVO.NORMAL);
						UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(user.getNombre(), user.getPass(), user.getEmail(), -1, user.getLocalizacion(), user.getTipo(), user.getUsuarioID());
					}
					loginCorrecto=true;
					this.dispose();
				}
				else {
					
					JOptionPane.showMessageDialog(Login.this, listaPalabras.getListaPalabras().get(49), listaPalabras.getListaPalabras().get(50), JOptionPane.WARNING_MESSAGE);
				}
			} catch (SQLException e1) {
				if(e1.getErrorCode()==1146)	JOptionPane.showMessageDialog(Login.this, listaPalabras.getListaPalabras().get(51), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				else {
					e1.getStackTrace();

					JOptionPane.showMessageDialog(Login.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(Login.this, listaPalabras.getListaPalabras().get(42)+e2.getLocalizedMessage()+")", listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
			} catch (StripeException e3) {
				JOptionPane.showMessageDialog(Login.this, listaPalabras.getListaPalabras().get(42)+e3.getLocalizedMessage()+")", listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
			}	 
			
		}
		if(e.getActionCommand().equals("signup")) {
			new DialogoCrearUsuario(this, listaPalabras.getListaPalabras().get(47), true, listaPalabras);
		}
	}
	public Boolean esCorrecto() {
		return loginCorrecto;
	}
	public String getUsername() {
		return usuario.getText();
	}
	public String getPassword() {
		return String.valueOf(password.getPassword());
	}
	public ControladorIdioma getControladorIdioma() {
		return listaPalabras;
	}
	public UsuarioVO getUser() {
		return user;
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==1) {
			System.out.println("a");
			soporte.firePropertyChange("idioma", null, idioma.getSelectedIndex());
			updatePanel();
		}
	}

}
