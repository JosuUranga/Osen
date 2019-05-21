package notificaciones;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class Notificacion {
	 static SendGrid sg;
	 String mail;
	public Notificacion(String key, String mail) {
		sg=new SendGrid(key);
		this.mail=mail;
	}
	
	 public void enviarNotificacionWindows(String notificacion){
	        SystemTray tray = SystemTray.getSystemTray();
	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
	        TrayIcon trayIcon = new TrayIcon(image, "Osen noti");
	        trayIcon.setImageAutoSize(true);
	        trayIcon.setToolTip("Notificaciones del sistema de Osen");
	        try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	        trayIcon.displayMessage("Osen", notificacion, MessageType.INFO);
	    }
	 
		public void enviarNotificacionMail(String titulo,String contenido) throws IOException {
	    Email from = new Email("noreply@osen.edu");
	    Email to = new Email(mail);
	    Content content = new Content("text/plain", contenido);
	    Mail mail = new Mail(from, titulo, to, content);
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }
}
