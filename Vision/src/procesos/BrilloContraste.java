package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;
import dialogs.BrilloContrasteDialog;

public class BrilloContraste {

	public static void run() {
		Copiar.run("Ajuste de Brillo y Contraste de ");
		final Image image = MainWindow.getCurrentImage();
		final ImageInfo info = image.getInfo();
		final BufferedImage oldBufIm = image.img;
		final BufferedImage newBufIm = new BufferedImage(image.widthRoi(), image.heightRoi(), BufferedImage.TYPE_INT_RGB);
		final BrilloContrasteDialog dialog = new BrilloContrasteDialog();
		
		dialog.spBrillo.setValue(info.brillo);
		dialog.sliderBrillo.setValue(info.brillo);
		dialog.spContraste.setValue(info.contraste);
		dialog.sliderContraste.setValue(info.contraste);
		
		dialog.spBrillo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				dialog.sliderBrillo.setValue((Integer)dialog.spBrillo.getValue());
			}
		});
		
		dialog.sliderBrillo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				dialog.spBrillo.setValue(dialog.sliderBrillo.getValue());
				if (!dialog.sliderBrillo.getValueIsAdjusting()) {
					image.img = ajustarBrilloContraste(oldBufIm, newBufIm, 
							info.brillo, 
							info.contraste, 
							dialog.sliderBrillo.getValue(), 
							dialog.sliderContraste.getValue());
					image.panel.img = image.img;
					image.resetInfo();
					image.panel.repaint();
				}
			}
		});
		
		dialog.spContraste.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				dialog.sliderContraste.setValue((Integer)dialog.spContraste.getValue());
			}
		});
		
		dialog.sliderContraste.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				dialog.spContraste.setValue(dialog.sliderContraste.getValue());
				if (!dialog.sliderBrillo.getValueIsAdjusting()) {
					image.img = ajustarBrilloContraste(oldBufIm, newBufIm, 
							info.brillo, 
							info.contraste, 
							dialog.sliderBrillo.getValue(), 
							dialog.sliderContraste.getValue());
					image.panel.img = image.img;
					image.resetInfo();
					image.panel.repaint();
				}				
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
//				image.img = oldBufIm;
//				image.panel.img = oldBufIm;
//				image.resetInfo();
//				image.panel.repaint();
				MainWindow.removeCurrentImage();
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
//				image.img = oldBufIm;
//				image.panel.img = oldBufIm;
//				image.resetInfo();
//				image.panel.repaint();
//				Image newIm = Image.crearImagenConPrefijo(image.widthRoi(), image.heightRoi(), image, "Ajuste Brillo y Contraste de ");
//				newIm.img = newBufIm;
//				MainWindow.insertAndListenImage(newIm);
			}
		});
		dialog.setVisible(true);
	}
	
	public static BufferedImage aplicarLut(BufferedImage im, BufferedImage newIm, byte[] lut) {
		ByteLookupTable blut = new ByteLookupTable(0, lut);
        LookupOp lop = new LookupOp(blut, null);
        newIm = lop.filter(im, newIm);

        return newIm;
	}
	
	public static BufferedImage ajustarBrilloContraste(BufferedImage im, BufferedImage newIm, int brillo, int contraste, int newBrillo, int newContraste) {
//		ImageInfo infoIm = im.getInfo();
		double a = (double)newContraste / (double)contraste;
        double b = (double)newBrillo - (a * (double)brillo);
        byte lut[] = new byte[ImageInfo.NIVELES];
        for(int i = 0; i < lut.length; i++) {
            double nivel = (a * i) + b;
            if (nivel < 0) nivel = 0;
            else if (nivel > ImageInfo.NIVELES - 1) nivel = ImageInfo.NIVELES - 1;
            lut[i] = (byte)Math.round(nivel);
        }
        return aplicarLut(im, newIm, lut);
	}
}
