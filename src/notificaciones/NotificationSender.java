package notificaciones;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class NotificationSender {
	public NotificationSender() {
	}
    public void enviarNotificacion(String notificacion){
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
}