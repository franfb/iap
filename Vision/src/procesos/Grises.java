package procesos;

import java.awt.Point;

import vision.Image;
import vision.MainWindow;

public class Grises {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		Image newImage = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Escala de grises de ");
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				newImage.img.setRGB(x, y, Image.rgb((int) Image.grey(image.img.getRGB(src.x + x, src.y + y))));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
