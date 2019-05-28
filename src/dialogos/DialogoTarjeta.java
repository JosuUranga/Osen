package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stripe.exception.StripeException;

import idiomas.ControladorIdioma;
import modelos.UsuarioDAO;
import modelos.UsuarioVO;
import osen.Principal;
import otros.TextPrompt;
import stripe.Suscripciones;

@SuppressWarnings("serial")
public class DialogoTarjeta extends JDialog{
	
	DialogoUsuario ventana;
	ControladorIdioma listaPalabras;
	JTextField tarjeta, fecha, CVC;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	boolean editando=false;
	UsuarioVO user;
	JButton botonOK,botonCancelar;
	
	public DialogoTarjeta (DialogoUsuario dialogoUsuario, String titulo, boolean modo, ControladorIdioma listaPalabras2,UsuarioVO user) {
		super(dialogoUsuario,titulo,modo);
		this.listaPalabras=listaPalabras2;
		this.ventana=dialogoUsuario;
		this.setSize(600,275);
		this.setLocation (600,200);
		this.user=user;
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
	}	
	

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
	private Component crearPanelDatos() {
		JPanel panel = new JPanel (new GridLayout(2,1,0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		panel.add(crearTextField(tarjeta=new JTextField(), listaPalabras.getListaPalabras().get(56)));
		tarjeta.setText("4242424242424242");
		panel.add(crearPanelDatos2());

		return panel;
	}
	

	private Component crearPanelDatos2() {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));

		panel.add(crearTextField(fecha=new JTextField(), listaPalabras.getListaPalabras().get(57)));
		panel.add(crearTextField(CVC=new JTextField(), listaPalabras.getListaPalabras().get(58)));
		
		return panel;
	}
	
	
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		TextPrompt placeholder=new TextPrompt(titulo,text);
		placeholder.changeAlpha(0.75f);
		placeholder.changeStyle(Font.ITALIC);
		panel.add(text);
		return panel;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		botonOK = new JButton ("OK");

		botonOK.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						Suscripciones sus=new Suscripciones("sk_test_dZGN1z9nd2Bx0WHAMfRmomsJ00wCLPBWmC");
						sus.createCustomer(user.getNombre(), user.getEmail());
						String cad[]=fecha.getText().split("/");
						sus.createCard(user.getEmail(), tarjeta.getText(), cad[1], cad[0], CVC.getText());
						sus.activateSub(user.getEmail());
						if(sus.checkSubActive(user.getEmail())) {
							user.setTipo(UsuarioVO.PRO);
							UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(user.getNombre(), user.getPass(), user.getEmail(), 1, user.getIdiomaSeleccionado(), user.getTipo(), user.getUsuarioID());
							DialogoTarjeta.this.dispose();
						}
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(DialogoTarjeta.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					} catch (StripeException e2) {
						JOptionPane.showMessageDialog(DialogoTarjeta.this, e2.getLocalizedMessage(), listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
					}
			}
		
		});
		panel.add(botonOK);
		botonCancelar = new JButton (listaPalabras.getListaPalabras().get(39));
		botonCancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

					DialogoTarjeta.this.dispose();
			}
			
		});
		panel.add(botonCancelar);
		return panel;
	}	

}

