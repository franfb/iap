package procesos;

import java.awt.Point;
import java.io.File;

import vision.Image;
import vision.MainWindow;

public class EspejoVertical {
	
	public static void run(){
		final Image image = MainWindow.getImage();
		File newFile = new File(image.file.getParent(), "Espejo vertical de " + image.file.getName());
		Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), newFile);
		Point src = image.topLeftRoi();
		for (int x = 0; x < newImage.widthRoi(); x++){
			for (int y = 0; y < newImage.heightRoi(); y++){
				newImage.img.setRGB(x, newImage.heightRoi() - y - 1, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		MainWindow.insertAndListenImage(newImage);
	}

}
