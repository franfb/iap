package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dialogs.CuantizarDialog;

import vision.Image;
import vision.MainWindow;

public class Cuantizar {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		final CuantizarDialog dialog = new CuantizarDialog();
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);

				int niveles = dialog.getNiveles();
				boolean rgb = dialog.isRGB();
				
				Image newImage = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Cuantizacion de ");
				
				Point src = image.topLeftRoi();
				for (int x = 0; x < image.widthRoi(); x++){
					for (int y = 0; y < image.heightRoi(); y++){
						int pixelColor = image.img.getRGB(src.x + x, src.y + y);
						int value = 0;
						if (rgb){
							float red = Image.red(pixelColor);
							float green = Image.green(pixelColor);
							float blue = Image.blue(pixelColor);
							red /= 256;
							green /= 256;
							blue /= 256;
							int nivelRed = (int) (red * niveles);
							int nivelGreen = (int) (green * niveles);
							int nivelBlue = (int) (blue * niveles);
							red = Math.round(nivelRed * 255.0 / (niveles - 1));
							green = Math.round(nivelGreen * 255.0 / (niveles - 1));
							blue = Math.round(nivelBlue * 255.0 / (niveles - 1));
							value = Image.rgb((int)red, (int)green, (int)blue);
						}
						else{
							double grey = Image.grey(pixelColor);
							grey /= 256;
							int nivel = (int) (grey * niveles);
							value = Image.rgb((int) Math.round(nivel * 255.0 / (niveles - 1)));
						}
						newImage.img.setRGB(x, y, value);
					}
				}
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}

}
