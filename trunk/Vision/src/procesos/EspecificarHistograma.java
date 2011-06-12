package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import dialogs.EspecificarHistogramaDialog;
import vision.DisplayHistogram;
import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;

public class EspecificarHistograma {
	static Image image1;
	static Image image2;
	static EspecificarHistogramaDialog dialog;
	
	public static void run() {
		image1 = MainWindow.getCurrentImage();
		final ImageInfo info = image1.getInfo();
		final int currentIndex = MainWindow.getCurrentImageIndex();
		
		dialog = new EspecificarHistogramaDialog();
		dialog.lbNombreIm1.setText(image1.getFileCompleto().getName());
		dialog.scrollBar.setValues(currentIndex, 1, 0, MainWindow.getImageCount());
		
		final DisplayHistogram hist1 = new DisplayHistogram(info.hist, "Histograma Actual");
		final DisplayHistogram hist2 = new DisplayHistogram(info.hist, "Histograma Deseado");
		dialog.panelHistAnterior.add(hist1);
		dialog.panelHistDeseado.add(hist2);
		
		dialog.scrollBar.addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				MainWindow.tabbedPane.setSelectedIndex(dialog.scrollBar.getValue());
				image2 = MainWindow.getCurrentImage();
				ImageInfo info2 = image2.getInfo();
				hist2.setHistogram(info2.hist);
				hist2.repaint();
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.tabbedPane.setSelectedIndex(currentIndex);
				dialog.setVisible(false);
			}
		});
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.tabbedPane.setSelectedIndex(currentIndex);
				if (currentIndex != dialog.scrollBar.getValue()) {
					Copiar.run("Ajuste del histograma de ");
					Image newIm = MainWindow.getCurrentImage();
					cambiarHistograma(image1, image2, newIm);
				}
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
	
	public static void cambiarHistograma(Image imIn, Image imOut, Image newIm) {
		ImageInfo infoIn = imIn.getInfo();
		ImageInfo infoOut = imOut.getInfo();
		
		// Construimos la LUT para cambiar el histograma
		double nPixIn = imIn.widthRoi() * imIn.heightRoi();
		double nPixOut = imOut.widthRoi() * imOut.heightRoi();
		byte[] lut = new byte[infoIn.histAc.length];
		for (int i = 0; i < infoOut.histAc.length; i++) {
			int nivelIzq = -1;
			int nivelDer = -1;
			double c1;
			double c0;
			for (int j = 0; j < infoIn.histAc.length; j++) {
				c1 = (double)infoOut.histAc[i] / nPixOut;
				c0 = (double)infoIn.histAc[j] / nPixIn;
				if (c0 < c1) {
					nivelIzq = j;
				}
				else if (c0 > c1) {
					nivelDer = j;
					break;
				}
			}
			if (nivelIzq == -1) { // No hay extremo izquierdo
				lut[i] = (byte)nivelDer;
			}
			else if (nivelDer == -1) {  // No hay extremo derecho
				lut[i] = (byte)nivelIzq;
			}
			else {
				double c1i, c0i;
				c1i = infoOut.histAc[nivelIzq] / nPixOut;
				c0i = infoIn.histAc[nivelIzq] / nPixIn;
				c1 = infoOut.histAc[nivelDer] / nPixOut;
				c0 = infoIn.histAc[nivelDer] / nPixIn;
				if (Math.abs(c1 - c0) < Math.abs(c1i - c0i)) {
					lut[i] = (byte)nivelDer;
				}
				else {
					lut[i] = (byte)nivelIzq;
				}
			}
		}
		Lut.aplicarLut(imIn.img, newIm.img, lut);
	}
}
