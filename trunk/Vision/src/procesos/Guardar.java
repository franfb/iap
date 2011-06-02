package procesos;

import java.io.IOException;

import javax.imageio.ImageIO;

import vision.Image;
import vision.MainWindow;

public class Guardar {
	
	public static void run() {
		Image image = MainWindow.getImage();
		try {
			ImageIO.write(image.img, image.format, image.file);
			image.saved = true;
			MainWindow.changeImageTitle(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
