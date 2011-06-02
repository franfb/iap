package procesos;

import java.awt.Point;
import java.io.File;

import vision.Image;
import vision.MainWindow;

public class Copiar {
	
	public static void run(){
		final Image image = MainWindow.getImage();
		File newFile = new File(image.file.getParent(), "Copia de " + image.file.getName());
		Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), newFile);
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				newImage.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
