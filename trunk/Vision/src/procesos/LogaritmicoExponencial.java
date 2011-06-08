package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		generarLut(dialog, im, info, lut, points);
		final DisplayHistogram grafica = new DisplayHistogram(points, "Gráfica");
		grafica.setHeight(256);
		dialog.panelGrafica.add(grafica);
		im.img = Lut.aplicarLut(oldBufIm, newBufIm, lut);
		im.panel.img = im.img;
		im.resetInfo();
		im.panel.repaint();
		
		ChangeListener changeTipo = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (dialog.rbTipo2.isSelected() || dialog.rbTipo3.isSelected()) {
					dialog.spValorK.setEnabled(true);
				}
				else {
					dialog.spValorK.setEnabled(false);
				}
				generarLut(dialog, im, info, lut, points);
				grafica.setHistogram(points);
				grafica.repaint();
				im.img = Lut.aplicarLut(oldBufIm, newBufIm, lut);
				im.panel.img = im.img;
				im.resetInfo();
				im.panel.repaint();
			}
		};
		
		dialog.rbTipo1.addChangeListener(changeTipo);
		dialog.rbTipo2.addChangeListener(changeTipo);
		dialog.rbTipo3.addChangeListener(changeTipo);
		dialog.spValorK.addChangeListener(changeTipo);
		
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
	}
}
