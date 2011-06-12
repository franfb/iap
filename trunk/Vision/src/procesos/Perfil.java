package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dialogs.PerfilDialog;

import vision.Image;
import vision.ImagePanel;
import vision.MainWindow;
import vision.Menu;

public class Perfil {
	
	public static void run(){
		final PerfilDialog dialog = new PerfilDialog();
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MainWindow.getImageCount() > 0){
					Menu.activaOpcionesMenu();
				}
				dialog.setVisible(false);
			}
		});
		Menu.desactivaOpcionesMenu();
		
		ImagePanel.setPerfilListener();
		for (int i = 0; i < MainWindow.getImageCount(); i++){
			MainWindow.getImage(i).panel.resetMouseListener();
		}
		
		dialog.setVisible(true);
	}

}
