package procesos;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;
import dialogs.Diferencias2Dialog;
import dialogs.DiferenciasDialog;

public class Diferencias {
	
	static DiferenciasDialog dialog;
	static Image image1;
	static Image image2;
	
	public static void run(){
		dialog = new DiferenciasDialog();
		dialog.okButton.setText("Continuar");
		dialog.titulo.setText("Selecciona la primera imagen:");
		dialog.nombreImagen.setText(MainWindow.getCurrentImage().getFileCompleto().getName());
		dialog.scrollBar.setValues(MainWindow.getCurrentImageIndex(), 1, 0, MainWindow.getImageCount());
		image1 = MainWindow.getCurrentImage();
		setNombreImagen1();
		
		final AdjustmentListener adjustment1 = new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				MainWindow.tabbedPane.setSelectedIndex(dialog.scrollBar.getValue());
				image1 = MainWindow.getCurrentImage();
				setNombreImagen1();
			}
		};
		
		dialog.scrollBar.addAdjustmentListener(adjustment1);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);				
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.okButton.removeActionListener(this);
				
				dialog.okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
						siguienteDialogo();
					}
				});
				
				dialog.scrollBar.removeAdjustmentListener(adjustment1);
				dialog.titulo.setText("Selecciona la segunda imagen:");
				dialog.nombreImagen.setText(MainWindow.getCurrentImage().getFileCompleto().getName());
				dialog.scrollBar.setValues(MainWindow.getCurrentImageIndex(), 1, 0, MainWindow.getImageCount());
				image2 = MainWindow.getCurrentImage();
				setNombreImagen2();
				dialog.scrollBar.addAdjustmentListener(new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent arg0) {
						MainWindow.tabbedPane.setSelectedIndex(dialog.scrollBar.getValue());
						image2 = MainWindow.getCurrentImage();
						setNombreImagen2();
					}
				});
			}
		});
		dialog.setVisible(true);
	}
	
	private static void siguienteDialogo(){
		final Diferencias2Dialog dialog = new Diferencias2Dialog();
		ButtonGroup group = new ButtonGroup();
		group.add(dialog.valorAbsoluto); 
		group.add(dialog.ajustePosterior);
		dialog.n.setModel(new SpinnerNumberModel(1, 0, 1000, 0.5));
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				Image imagenDiferencia = Image.crearImagenSinPrefijo(image1.widthRoi(), image1.heightRoi(), image1, "Imagen diferencia");
				Point src1 = image1.topLeftRoi();
				Point src2 = image2.topLeftRoi();
				int[] pixel1 = new int[3];
				int[] pixel2 = new int[3];
				int[] resultado = new int[3];
				for (int x = 0; x < imagenDiferencia.widthRoi(); x++){
					for (int y = 0; y < imagenDiferencia.heightRoi(); y++){
						Image.rgb2array(image1.img.getRGB(src1.x + x, src1.y + y), pixel1);
						Image.rgb2array(image2.img.getRGB(src2.x + x, src2.y + y), pixel2);
						for (int i = 0; i < 3; i++){
							resultado[i] = Math.abs(pixel1[i] - pixel2[i]);
						}
						imagenDiferencia.img.setRGB(x, y, Image.array2rgb(resultado));
					}
				}
				
				Image imagenCambio = Image.crearImagenSinPrefijo(image1.widthRoi(), image1.heightRoi(), image1, "Mapa de cambios");
				ImageInfo info = imagenDiferencia.getInfo();
				
				Double n = (Double) dialog.n.getValue();
				int limiteSuperior = (int) (info.brillo + n * info.contraste);
				
				for (int x = 0; x < imagenCambio.widthRoi(); x++){
					for (int y = 0; y < imagenCambio.heightRoi(); y++){
						int greyDif = (int) Image.grey(imagenDiferencia.img.getRGB(x, y));
						double grey1 = Image.grey(image1.img.getRGB(src1.x + x, src1.y + y));
						double grey2 = Image.grey(image2.img.getRGB(src2.x + x, src2.y + y));
						if (greyDif > limiteSuperior){
							if (grey1 > grey2){
								imagenCambio.img.setRGB(x, y, Color.RED.getRGB());
							}
							else{
								imagenCambio.img.setRGB(x, y, Color.GREEN.getRGB());
							}
						}
						else{
							imagenCambio.img.setRGB(x, y, Image.rgb((int)grey1));
						}
					}
				}
				MainWindow.insertAndListenImage(imagenCambio);
			}
		});
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		dialog.setVisible(true);
	}
	
	private static void setNombreImagen1(){
		dialog.nombreImagen.setText(image1.getFileCompleto().getName());
		dialog.dimensiones.setText("" + image1.widthRoi() + "x" + image1.heightRoi());
		if (image1.validRoi()){
			dialog.dimensiones.setText(dialog.dimensiones.getText() + "  (Región de interés seleccionada)");
		}
		else{
			dialog.dimensiones.setText(dialog.dimensiones.getText() + "  (Imagen completa)");
		}
		dialog.error.setVisible(false);
	}
	
	private static void setNombreImagen2(){
		dialog.nombreImagen.setText(image2.getFileCompleto().getName());
		dialog.dimensiones.setText("" + image2.widthRoi() + "x" + image2.heightRoi());
		if (image2.validRoi()){
			dialog.dimensiones.setText(dialog.dimensiones.getText() + "  (Región de interés seleccionada)");
		}
		else{
			dialog.dimensiones.setText(dialog.dimensiones.getText() + "  (Imagen completa)");
		}
		if (image1.widthRoi() == image2.widthRoi() && image1.heightRoi() == image2.heightRoi()){
			dialog.okButton.setEnabled(true);
			if (image1 == image2){
				dialog.error.setText("Esta imagen es la misma que la anterior");
				dialog.error.setForeground(new Color(0xFF7700));
				dialog.error.setVisible(true);
			}
			else{
				dialog.error.setVisible(false);
			}
		}
		else{
			dialog.error.setText("El tamaño de la imagen seleccionada tiene que ser " + image1.widthRoi() + "x" + image1.heightRoi());
			dialog.error.setForeground(Color.red);
			dialog.error.setVisible(true);
			dialog.okButton.setEnabled(false);
		}
	}
	
}
