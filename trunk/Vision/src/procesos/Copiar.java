package procesos;

import java.awt.Point;

import vision.Image;
import vision.MainWindow;

public class Copiar {
	
	public static void run(){
		run("Copia de ");
	}
	
	public static void run(String prefijo) {
		final Image image = MainWindow.getCurrentImage();
		Image newImage = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, prefijo);
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				newImage.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
