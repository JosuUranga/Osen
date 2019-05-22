package dialogos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DialogoProgressBar extends JDialog{
	
	JFrame ventana;
	
	public DialogoProgressBar (JFrame ventana, String titulo, boolean modo) {
		super(ventana,titulo,modo);
		this.ventana=ventana;
		this.setSize(600,500);
		this.setLocation (500,200);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		this.setVisible(true);
		
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelProgressBar());
		return panel;
	}

	private Component crearPanelProgressBar() {
		JPanel panel = new JPanel(new BorderLayout());
		

		return panel;
	}
	
	
	


}
