package procesos;

import java.awt.Point;

import vision.Image;
import vision.MainWindow;

public class EspejoVertical {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		Image newImage = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Espejo vertical de ");
		Point src = image.topLeftRoi();
		for (int x = 0; x < newImage.widthRoi(); x++){
			for (int y = 0; y < newImage.heightRoi(); y++){
				newImage.img.setRGB(x, newImage.heightRoi() - y - 1, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
