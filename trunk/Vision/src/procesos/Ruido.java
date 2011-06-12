package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

import dialogs.RuidoDialog;


import vision.Image;
import vision.MainWindow;

public class Ruido {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		final RuidoDialog dialog = new RuidoDialog();
		ButtonGroup funcionDist = new ButtonGroup();
		funcionDist.add(dialog.rdbtnGaussiano);
		funcionDist.add(dialog.rdbtnImpulsivo);
		funcionDist.add(dialog.rdbtnUniforme);
		dialog.rdbtnUniforme.setSelected(true);
		
		dialog.contaminacionMin.setModel(new SpinnerNumberModel(-100, -255, 255, 3));
		dialog.contaminacionMax.setModel(new SpinnerNumberModel(100, -255, 255, 3));
		dialog.porcentajeRuido.setModel(new SpinnerNumberModel(20.0, 0.0, 100.0, 1.0));
		
		dialog.rdbtnImpulsivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dialog.rdbtnImpulsivo.isSelected()){
					dialog.contaminacionMin.setValue(-255);
					dialog.contaminacionMax.setValue(255);
					dialog.contaminacionMin.setEnabled(false);
					dialog.contaminacionMax.setEnabled(false);
				}
			}
		});
		
		dialog.rdbtnGaussiano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.contaminacionMin.setEnabled(true);
				dialog.contaminacionMax.setEnabled(true);
			}
		});
		
		dialog.rdbtnUniforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.contaminacionMin.setEnabled(true);
				dialog.contaminacionMax.setEnabled(true);
			}
		});
		
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				
				double p = (Double) dialog.porcentajeRuido.getValue();
				int minVal = (Integer) dialog.contaminacionMin.getValue();
				int maxVal = (Integer) dialog.contaminacionMax.getValue();
				if (minVal > maxVal){
					int temp = maxVal;
					maxVal = minVal;
					minVal = temp;
				}
				
				if (dialog.rdbtnUniforme.isSelected()){
					uniforme(image, p, minVal, maxVal);
				}
				if (dialog.rdbtnImpulsivo.isSelected()){
					impulsivo(image, p);
				}
				if (dialog.rdbtnGaussiano.isSelected()){
					gaussiano(image, p, minVal, maxVal);
				}
			}
		});
		dialog.setVisible(true);
	}

	private static void uniforme(Image image, double p, int minVal, int maxVal){
		int n = (int) ((p / 100.0) * (image.widthRoi() * image.heightRoi()));
		int f = Math.round(n / (maxVal - minVal + 1));
		Image ruidosa = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen ruidosa de ");
		Image ruido = Image.crearImagenSinPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen de ruido");
		
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				ruidosa.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		
		int[] pixelValue = new int[3]; 
		for (int noise = minVal; noise <= maxVal; noise++){
			for (int count = 0; count < f; count++){
				int x;
				int y;
				do{
					x = (int) Math.floor(Math.random() * image.widthRoi());
					y = (int) Math.floor(Math.random() * image.heightRoi());
				} while (Image.red(ruido.img.getRGB(x, y)) != 0 || Image.green(ruido.img.getRGB(x, y)) != 0);
				Image.rgb2array(image.img.getRGB(src.x + x, src.y + y), pixelValue);
				for (int i = 0; i < 3; i++){
					pixelValue[i] += noise;
					if (pixelValue[i] > 255){
						pixelValue[i] = 255;
					}
					if (pixelValue[i] < 0){
						pixelValue[i] = 0;
					}
				}
				ruidosa.img.setRGB(x, y, Image.array2rgb(pixelValue));
				if (noise < 0){
					ruido.img.setRGB(x, y, Image.rgb(-noise, 0, 0));
				}
				if (noise > 0){
					ruido.img.setRGB(x, y, Image.rgb(0, noise, 0));
				}
			}
		}
		MainWindow.insertAndListenImage(ruidosa);
		MainWindow.insertAndListenImage(ruido);
	}
	
	private static void impulsivo(Image image, double p){
		int n = (int) ((p / 100.0) * (image.widthRoi() * image.heightRoi()));
		int f = Math.round(n / 2);
		Image ruidosa = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen ruidosa de ");
		Image ruido = Image.crearImagenSinPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen de ruido");
		
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				ruidosa.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		int black = Image.rgb(0, 0, 0);
		for (int count = 0; count < f; count++){
			int x;
			int y;
			do{
				x = (int) Math.floor(Math.random() * image.widthRoi());
				y = (int) Math.floor(Math.random() * image.heightRoi());
			} while (Image.red(ruido.img.getRGB(x, y)) != 0 || Image.green(ruido.img.getRGB(x, y)) != 0);
			ruidosa.img.setRGB(x, y, black);
			ruido.img.setRGB(x, y, Image.rgb(255, 0, 0));
		}
		int white = Image.rgb(255, 255, 255);
		for (int count = 0; count < f; count++){
			int x;
			int y;
			do{
				x = (int) Math.floor(Math.random() * image.widthRoi());
				y = (int) Math.floor(Math.random() * image.heightRoi());
			} while (Image.red(ruido.img.getRGB(x, y)) != 0 || Image.green(ruido.img.getRGB(x, y)) != 0);
			ruidosa.img.setRGB(x, y, white);
			ruido.img.setRGB(x, y, Image.rgb(0, 255, 0));
		}
		MainWindow.insertAndListenImage(ruidosa);
		MainWindow.insertAndListenImage(ruido);
	}
	
	private static void gaussiano(Image image, double p, int minVal, int maxVal){
		int n = (int) ((p / 100.0) * (image.widthRoi() * image.heightRoi()));
		
		int media = image.getInfo().brillo;
		int desviacion = image.getInfo().contraste;
		
		double s = 0.0;
		for (int noise = minVal; noise <= maxVal; noise++){
			s += fx(noise, media, desviacion);
		}
		
		Image ruidosa = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen ruidosa de ");
		Image ruido = Image.crearImagenSinPrefijo(image.widthRoi(), image.heightRoi(), image, "Imagen de ruido");
		
		Point src = image.topLeftRoi();
		for (int x = 0; x < image.widthRoi(); x++){
			for (int y = 0; y < image.heightRoi(); y++){
				ruidosa.img.setRGB(x, y, image.img.getRGB(src.x + x, src.y + y));
			}
		}
		
		int[] pixelValue = new int[3]; 
		for (int noise = minVal; noise <= maxVal; noise++){
			int f = (int) (fx(noise, media, desviacion) * n / s);
			for (int count = 0; count < f; count++){
				int x;
				int y;
				do{
					x = (int) Math.floor(Math.random() * image.widthRoi());
					y = (int) Math.floor(Math.random() * image.heightRoi());
				} while (Image.red(ruido.img.getRGB(x, y)) != 0 || Image.green(ruido.img.getRGB(x, y)) != 0);
				Image.rgb2array(image.img.getRGB(src.x + x, src.y + y), pixelValue);
				for (int i = 0; i < 3; i++){
					pixelValue[i] += noise;
					if (pixelValue[i] > 255){
						pixelValue[i] = 255;
					}
					if (pixelValue[i] < 0){
						pixelValue[i] = 0;
					}
				}
				ruidosa.img.setRGB(x, y, Image.array2rgb(pixelValue));
				if (noise < 0){
					ruido.img.setRGB(x, y, Image.rgb(-noise, 0, 0));
				}
				if (noise > 0){
					ruido.img.setRGB(x, y, Image.rgb(0, noise, 0));
				}
			}
		}
		MainWindow.insertAndListenImage(ruidosa);
		MainWindow.insertAndListenImage(ruido);
		
	}
	
	private static double fx(int x, int media, int desviacion){
		return Math.exp(-Math.pow(x - media, 2) / Math.pow(2*desviacion, 2)) / (desviacion * Math.sqrt(2 * Math.PI));
	}
	
}
