package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dialogs.MuestrearDialog;

import vision.Image;
import vision.MainWindow;

public class Muestrear {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		final MuestrearDialog dialog = new MuestrearDialog();
		ChangeListener change = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int anchoMuestreo = (Integer) dialog.anchoSpinner.getValue();
				int altoMuestreo = (Integer) dialog.altoSpinner.getValue();
				int ancho = (int) Math.ceil(image.widthRoi() / (float)anchoMuestreo);
				int alto = (int) Math.ceil(image.heightRoi() / (float)altoMuestreo);
				dialog.muestras.setText("Dimensiones del muestreo:  " + ancho + " x " + alto);
			}
		};
		int altoMuestreo = image.heightRoi() / 20 + 4;
		int anchoMuestreo = image.widthRoi() / 20 + 4;
		int ancho = (int) Math.ceil(image.widthRoi() / (float)anchoMuestreo);
		int alto = (int) Math.ceil(image.heightRoi() / (float)altoMuestreo);
		dialog.muestras.setText("Dimensiones del muestreo:  " + ancho + " x " + alto);
		dialog.altoSpinner.setModel(new SpinnerNumberModel(altoMuestreo, 1, image.heightRoi(), 1));
		dialog.anchoSpinner.setModel(new SpinnerNumberModel(anchoMuestreo, 1, image.widthRoi(), 1));
		dialog.altoSpinner.addChangeListener(change);
		dialog.anchoSpinner.addChangeListener(change);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);

				Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), image, "Muestreo de ");
				
				int anchoMuestreo = (Integer) dialog.anchoSpinner.getValue();
				int altoMuestreo = (Integer) dialog.altoSpinner.getValue();
				
				Point src = image.topLeftRoi();
				int x = 0;
				while (x < image.widthRoi()){
					int y = 0;
					while (y < image.heightRoi()){
						int red = 0;
						int green = 0;
						int blue = 0;
						int pixels = 0;
						for (int x2 = 0; x2 < anchoMuestreo; x2++){
							for (int y2 = 0; y2 < altoMuestreo; y2++){
								if (x + x2 < image.widthRoi() && y + y2 < image.heightRoi()){
									pixels++;
									int pixelColor = image.img.getRGB(src.x + x + x2, src.y + y + y2);
									red += Image.red(pixelColor);
									green += Image.green(pixelColor);
									blue += Image.blue(pixelColor);
								}
							}
						}
						red = Math.round(red /(float)pixels);
						green = Math.round(green /(float)pixels);
						blue = Math.round(blue /(float)pixels);
						int colorMuestra = Image.rgb(red, green, blue);
						for (int x2 = 0; x2 < anchoMuestreo; x2++){
							for (int y2 = 0; y2 < altoMuestreo; y2++){
								if (x + x2 < image.widthRoi() && y + y2 < image.heightRoi()){
									newImage.img.setRGB(x + x2, y + y2, colorMuestra);
								}
							}
						}
						y += altoMuestreo;
					}
					x += anchoMuestreo;
				}
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}

}
