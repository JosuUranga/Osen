package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

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
	JFormattedTextField tarjeta, fecha, CVC;
	Font fuenteTituloInfoGeneral=new Font("Tahoma",Font.BOLD,14);
	boolean editando=false;
	UsuarioVO user;
	JButton botonOK,botonCancelar;
	MaskFormatter cardNumberFormatter, fechaNumberFormatter, cvcNumberFormatter;
	
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
		try {
			panel.add(crearPanelDatos(),BorderLayout.CENTER);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
	private Component crearPanelDatos()  throws ParseException{
		JPanel panel = new JPanel (new GridLayout(2,1,0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
	
		cardNumberFormatter = new MaskFormatter("####-####-####-####");
		cardNumberFormatter.setValueClass(String.class);
		cardNumberFormatter.setPlaceholderCharacter('_');  
		
		panel.add(crearTextField(tarjeta=new JFormattedTextField(), listaPalabras.getListaPalabras().get(56)+" "+cardNumberFormatter));

		
		
		DefaultFormatterFactory ssnFormatterFactory = new DefaultFormatterFactory(cardNumberFormatter);
		tarjeta.setFormatterFactory(ssnFormatterFactory);

		//tarjeta.setText("4242424242424242");
		panel.add(crearPanelDatos2());

		return panel;
	}
	

	private Component crearPanelDatos2() throws ParseException {
		JPanel panel = new JPanel(new GridLayout(1,2,20,0));

		
		fechaNumberFormatter = new MaskFormatter("##/##");
		fechaNumberFormatter.setValueClass(String.class);
		fechaNumberFormatter.setPlaceholderCharacter('_'); 
		panel.add(crearTextField(fecha=new JFormattedTextField(), listaPalabras.getListaPalabras().get(57)));
		
		cvcNumberFormatter = new MaskFormatter("###");
		cvcNumberFormatter.setValueClass(String.class);
		cvcNumberFormatter.setPlaceholderCharacter('_'); 
		panel.add(crearTextField(CVC=new JFormattedTextField(), listaPalabras.getListaPalabras().get(58)));


		
		DefaultFormatterFactory ssnFechaFactory = new DefaultFormatterFactory(fechaNumberFormatter);
		fecha.setFormatterFactory(ssnFechaFactory);
	
		DefaultFormatterFactory ssnCvcFactory = new DefaultFormatterFactory(cvcNumberFormatter);
		CVC.setFormatterFactory(ssnCvcFactory);

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
				
				YearMonth ym;
				int ano;
				int mes;
					try {
						Suscripciones sus=Suscripciones.getInstance();
						sus.createCustomer(user.getNombre(), user.getEmail());
						 if (fecha.getText().contains("/")) {
							ym = YearMonth.parse(fecha.getText(), DateTimeFormatter.ofPattern("MM/yy"));
							ano= ym.getYear();
							mes = ym.getMonthValue();
							sus.createCard(user.getEmail(), tarjeta.getText(), String.valueOf(ano), String.valueOf(mes), CVC.getText());
							sus.activateSub(user.getEmail());
							if(sus.checkSubActive(user.getEmail())) {
								user.setTipo(UsuarioVO.PRO);
								UsuarioDAO.getInstance(user.calcularTipoUsuario(), Principal.dbpass, Principal.dbname, Principal.dbip).updateUser(user.getNombre(), user.getPass(), user.getEmail(), 1, user.getIdiomaSeleccionado(), user.getTipo(), user.getUsuarioID());
								DialogoTarjeta.this.dispose();
							}
						}
						else {
							JOptionPane.showMessageDialog(DialogoTarjeta.this, "Formato de fecha no valido", listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
						} 
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(DialogoTarjeta.this, e1.getMessage(), listaPalabras.getListaPalabras().get(41)+e1.getErrorCode(), JOptionPane.WARNING_MESSAGE);
					} catch (StripeException e2) {
						JOptionPane.showMessageDialog(DialogoTarjeta.this, e2.getLocalizedMessage(), listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
					} catch (DateTimeParseException e3) {
						JOptionPane.showMessageDialog(DialogoTarjeta.this, e3.getLocalizedMessage(), listaPalabras.getListaPalabras().get(43), JOptionPane.WARNING_MESSAGE);
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

