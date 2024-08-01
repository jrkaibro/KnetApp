package br.com.knetapp;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import br.com.knetapp.service.ServerPublisher;

public class App {

	public static void main(String[] args) throws Exception {
		
		if (!SystemTray.isSupported()) {			
			System.out.println("SystemTray is not supported");
			return;
		} else {
			
			String code = urlaccess();
			
			if (code.equals("Serviço offline")) {
			  ServerPublisher server = new ServerPublisher();
			  server.main();
			}
			
			SystemTray tray = SystemTray.getSystemTray();
			@SuppressWarnings("unused")
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension trayIconSize = tray.getTrayIconSize();

			URL urlimage = App.class.getClassLoader().getResource("icon.png");
			
			Image image = ImageIO.read(urlimage);
			image = image.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);
			
			PopupMenu menu = new PopupMenu();

			MenuItem messageItem = new MenuItem("Versão");
			messageItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "1.0.0");
				}
			});

			menu.add(messageItem);

			MenuItem closeItem = new MenuItem("Sair");
			closeItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			menu.add(closeItem);

			TrayIcon icon = new TrayIcon(image, "Serviço de impressão ativado!.", menu);
			tray.add(icon);
			
		}
	}
	
	
	public static String urlaccess() {    
		    
		int responseCode = 0;
		String retorno = null;

		try {
			URL url = new URL("http://127.0.0.1:9876/br.com.knetapp.service.ServiceServer");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			responseCode = huc.getResponseCode();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (responseCode == 200) {
			retorno = "Serviço online";
		} else {
			retorno = "Serviço offline";
		}
		    
		return retorno;
	}
}