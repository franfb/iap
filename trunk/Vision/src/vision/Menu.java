package vision;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu {

	public static JFileChooser chooser =  new JFileChooser();
	public static ArrayList<JComponent> opcionesMenu = new ArrayList<JComponent>();
	
	public static void activaOpcionesMenu(){
		for (JComponent item: opcionesMenu){
			item.setEnabled(true);
		}
	}
	
	public static void desactivaOpcionesMenu(){
		for (JComponent item: opcionesMenu){
			item.setEnabled(false);
		}
	}
	
	
	
	public static void abrirDesdeArchivo(){
		
		// QUITAR CUANDO SE HAYAN TERMINADO LAS PRUEBAS
		chooser.setSelectedFile(new File("D:/FACULTAD/Asignaturas/vision/Imagenes/bmw.jpg"));
	    
		chooser.setFileFilter(new ImageFilter());
		int returnValue = chooser.showOpenDialog(MainWindow.frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			try {
				BufferedImage img = ImageIO.read(file);
				Image image = new Image(file, img, true);
				MainWindow.insertAndListenImage(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void guardar(){
		Image image = MainWindow.getImage();
		try {
			ImageIO.write(image.img, image.format, image.file);
			image.saved = true;
			MainWindow.changeImageTitle(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void guardarComo(){
		Image image = MainWindow.getImage();
		chooser.setSelectedFile(image.file);
		int returnValue = chooser.showSaveDialog(MainWindow.frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			image.cambiarFile(chooser.getSelectedFile());
			try {
				ImageIO.write(image.img, image.format, image.file);
				image.saved = true;
				MainWindow.changeImageTitle(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void muestrear(){
		final Image image = MainWindow.getImage();
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

				File newFile = new File(image.file.getParent(), "Muestreo de " + image.file.getName());
				Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), newFile);
				
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
	
	public static void cuantizar(){
		final Image image = MainWindow.getImage();
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
				
				File newFile = new File(image.file.getParent(), "Cuantizacion de " + image.file.getName());
				Image newImage = Image.crearImagen(image.widthRoi(), image.heightRoi(), newFile);
				
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
							float grey = Image.grey(pixelColor);
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
	
	public static void escalar(){
		final Image image = MainWindow.getImage();
		final ZoomDialog dialog = new ZoomDialog();
		dialog.porcentaje.setSelected(false);
		dialog.dimensiones.setSelected(false);
		
		ButtonGroup group = new ButtonGroup();
		group.add(dialog.porcentaje); 
		group.add(dialog.dimensiones); 
		
		final ChangeListener changePorcentaje = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				float porcentajeX = (Integer) dialog.porcentajeX.getValue() / 100.0f;
				float porcentajeY = (Integer) dialog.porcentajeY.getValue() / 100.0f;
				dialog.dimensionesX.setValue((int)(porcentajeX * image.widthRoi()));
				dialog.dimensionesY.setValue((int)(porcentajeY * image.heightRoi()));
			}
		};
		final ChangeListener changeDimensiones = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int dimensionesX = (Integer) dialog.dimensionesX.getValue();
				int dimensionesY = (Integer) dialog.dimensionesY.getValue();
				int porcentajeX = (int) ((dimensionesX / (float)image.widthRoi()) * 100.0);
				int porcentajeY = (int) ((dimensionesY / (float)image.heightRoi()) * 100.0);
				dialog.porcentajeX.setValue(porcentajeX);
				dialog.porcentajeY.setValue(porcentajeY);
			}
		};
		
		ChangeListener changeButtons = new ChangeListener() {
			JRadioButton selected = null;
			public void stateChanged(ChangeEvent e) {
				
				if (dialog.porcentaje.isSelected() && (selected == null || selected != dialog.porcentaje)){
					selected = dialog.porcentaje;
					dialog.dimensionesX.removeChangeListener(changeDimensiones);
					dialog.dimensionesY.removeChangeListener(changeDimensiones);
					dialog.porcentajeX.setEnabled(true);
					dialog.porcentajeY.setEnabled(true);
					dialog.dimensionesX.setEnabled(false);
					dialog.dimensionesY.setEnabled(false);
					dialog.porcentajeX.addChangeListener(changePorcentaje);
					dialog.porcentajeY.addChangeListener(changePorcentaje);
				}
				else if(dialog.dimensiones.isSelected() && (selected == null || selected != dialog.dimensiones)){
					selected = dialog.dimensiones;
					dialog.porcentajeX.removeChangeListener(changePorcentaje);
					dialog.porcentajeY.removeChangeListener(changePorcentaje);
					dialog.porcentajeX.setEnabled(false);
					dialog.porcentajeY.setEnabled(false);
					dialog.dimensionesX.setEnabled(true);
					dialog.dimensionesY.setEnabled(true);
					dialog.dimensionesX.addChangeListener(changeDimensiones);
					dialog.dimensionesY.addChangeListener(changeDimensiones);
				}
			}
		};
		
		dialog.porcentajeX.setModel(new SpinnerNumberModel(200, 1, 10000, 30));
		dialog.porcentajeY.setModel(new SpinnerNumberModel(200, 1, 10000, 30));
		
		int dimensionesX = image.widthRoi() * 2;
		int dimensionesY = image.heightRoi() * 2;
		
		dialog.dimensionesX.setModel(new SpinnerNumberModel(dimensionesX, 1, 1000000000, 100));
		dialog.dimensionesY.setModel(new SpinnerNumberModel(dimensionesY, 1, 1000000000, 100));
		
		dialog.dimensionesX.setEnabled(false);
		dialog.dimensionesY.setEnabled(false);
		
		dialog.porcentaje.addChangeListener(changeButtons);
		dialog.dimensiones.addChangeListener(changeButtons);
		dialog.porcentaje.setSelected(true);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				File newFile = new File(image.file.getParent(), "Escalado de " + image.file.getName());
				int width = (Integer) dialog.dimensionesX.getValue();
				int height = (Integer) dialog.dimensionesY.getValue();
				float escalaX = (width - 1) / ((float)image.widthRoi() - 1);
				float escalaY = (height - 1) / ((float)image.heightRoi() - 1);
				Image newImage = Image.crearImagen(width, height, newFile);
				Point src = image.topLeftRoi();
				for (int x = 0; x < newImage.widthRoi(); x++){
					for (int y = 0; y < newImage.heightRoi(); y++){
						int masProximoX = Math.round(x / escalaX);
						int masProximoY = Math.round(y / escalaY);
						newImage.img.setRGB(x, y, image.img.getRGB(src.x + masProximoX, src.y + masProximoY));
					}
				}
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}
	
	public static void copiar(){
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