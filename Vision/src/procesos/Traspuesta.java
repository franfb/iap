package procesos;

import java.awt.Point;
import java.io.File;

import vision.Image;
import vision.MainWindow;

public class Traspuesta {
	
	public static void run(){
		final Image image = MainWindow.getImage();
		File newFile = new File(image.file.getParent(), "Traspuesta de " + image.file.getName());
		Image newImage = Image.crearImagen(image.heightRoi(), image.widthRoi(), newFile);
		Point src = image.topLeftRoi();
		for (int x = 0; x < newImage.widthRoi(); x++){
			for (int y = 0; y < newImage.heightRoi(); y++){
				newImage.img.setRGB(x, y, image.img.getRGB(src.x + y, src.y + x));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
