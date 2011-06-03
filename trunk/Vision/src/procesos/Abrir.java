package procesos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import vision.Image;
import vision.ImageFilter;
import vision.MainWindow;

public class Abrir {
	
	public static void run() {
		// QUITAR CUANDO SE HAYAN TERMINADO LAS PRUEBAS
		MainWindow.chooser.setSelectedFile(new File("D:/FACULTAD/Asignaturas/vision/Imagenes/bmw.jpg"));

		MainWindow.chooser.setFileFilter(new ImageFilter());
		int returnValue = MainWindow.chooser.showOpenDialog(MainWindow.frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = MainWindow.chooser.getSelectedFile();
			try {
				BufferedImage img = ImageIO.read(file);
				Image image = new Image(file, "", img, true);
				MainWindow.insertAndListenImage(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
