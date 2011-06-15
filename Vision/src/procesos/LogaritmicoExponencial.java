package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.DisplayHistogram;
import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;
import dialogs.LogaritmicoExponencialDialog;

public class LogaritmicoExponencial {

	public static void run() {
		Copiar.run("Log/Exp de ");
		final Image im = MainWindow.getCurrentImage();
		final ImageInfo info = im.getInfo();
		final BufferedImage oldBufIm = im.img;
		final BufferedImage newBufIm = new BufferedImage(im.widthRoi(), im.heightRoi(), BufferedImage.TYPE_INT_RGB);
		
		final LogaritmicoExponencialDialog dialog = new LogaritmicoExponencialDialog();
		final byte[] lut = new byte[ImageInfo.NIVELES];
		final int[] points = new int[lut.length];
		final int[][] ppoints = new int[1][];
		ppoints[0] = points;
		final byte[] lutNegarImagen = new byte[lut.length];
		for (int i = 0; i < lutNegarImagen.length; i++) {
			lutNegarImagen[i] = (byte)(lutNegarImagen.length - 1 - i);
		}
		generarLut(dialog, im, info, lut, points);
		String[] names = { "V" };
		final DisplayHistogram grafica = new DisplayHistogram(ppoints, "Gráfica", names);
		grafica.setHeight(256);
		dialog.panelGrafica.add(grafica);
		im.img = Lut.aplicarLut(oldBufIm, newBufIm, lut);
		im.panel.img = im.img;
		im.resetInfo();
		im.panel.repaint();
		
		ChangeListener changeK = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				aplicarTransformacion(dialog, im, info, lut, ppoints, grafica, oldBufIm, newBufIm, lutNegarImagen);
			}
		};
		
		MouseListener mouseList = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				aplicarTransformacion(dialog, im, info, lut, ppoints, grafica, oldBufIm, newBufIm, lutNegarImagen);
			}
		};
		
		dialog.rbLogaritmico.addMouseListener(mouseList);
		dialog.rbExponencial.addMouseListener(mouseList);
		dialog.rbTipo1.addMouseListener(mouseList);
		dialog.rbTipo2.addMouseListener(mouseList);
		dialog.rbTipo3.addMouseListener(mouseList);
		dialog.spValorK.addChangeListener(changeK);
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				MainWindow.removeCurrentImage();
			}
		});
		
		dialog.setVisible(true);
	}
	
	private static void aplicarTransformacion(LogaritmicoExponencialDialog dialog, Image im, ImageInfo info, byte[] lut, int[][] points, DisplayHistogram grafica, BufferedImage oldBufIm, BufferedImage newBufIm, byte[] lutNegarImagen) {
		if (dialog.rbTipo2.isSelected() || dialog.rbTipo3.isSelected()) {
			dialog.spValorK.setEnabled(true);
		}
		else {
			dialog.spValorK.setEnabled(false);
		}
		generarLut(dialog, im, info, lut, points[0]);
		grafica.setHistogram(points);
		grafica.repaint();
		if (dialog.rbExponencial.isSelected()) {
			Lut.aplicarLut(oldBufIm, newBufIm, lutNegarImagen);
			Lut.aplicarLut(newBufIm, newBufIm, lut);
			Lut.aplicarLut(newBufIm, newBufIm, lutNegarImagen);
		}
		else {
			Lut.aplicarLut(oldBufIm, newBufIm, lut);
		}
		im.img = newBufIm;
		im.panel.img = im.img;
		im.resetInfo();
		im.panel.repaint();
	}
	
	public static void generarLut(LogaritmicoExponencialDialog dialog, Image im, ImageInfo info, byte[] lut, int[] points) {
		double t = 0;
		if (dialog.rbTipo1.isSelected()) {
			for (int i = 0; i < lut.length; i++) {
				t = (lut.length - 1) / Math.log(lut.length);
				t *= Math.log(1 + i);
				points[i] = (int)Math.round(t);
				lut[i] = (byte)points[i];
			}
		}
		else if (dialog.rbTipo2.isSelected()) {
			for (int i = 0; i < lut.length; i++) {
				t = (lut.length - 1) / (Integer)dialog.spValorK.getValue();
				t *= Math.log(1 + ((Math.exp((Integer)dialog.spValorK.getValue()) - 1) * 
						((double)i / (double)(lut.length - 1))));
				points[i] = (int)Math.round(t);
				lut[i] = (byte)points[i];
			}
		}
		else { // rbTipo3.isSelected
			double nPix = im.widthRoi() * im.heightRoi();
			for (int i = 0; i < lut.length; i++) {
				t = (lut.length - 1) / (Integer)dialog.spValorK.getValue();
				t *= Math.log(1 + ((Math.exp((Integer)dialog.spValorK.getValue()) - 1) * 
						((double)info.histAc[i] / nPix)));
				points[i] = (int)Math.round(t);
				lut[i] = (byte)points[i];
			}
		}
		if (dialog.rbExponencial.isSelected()) {
			int temp;
			for (int i = 0; i < points.length / 2; i++) {
				temp = points[i];
				points[i] = 255 - points[255 - i];
				points[255 - i] = 255 - temp;
			}
		}
	}
}
