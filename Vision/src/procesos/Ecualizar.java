package procesos;

import java.awt.image.BufferedImage;

import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;

public class Ecualizar {

	public static void run() {
		Copiar.run("Ecualización de ");
		Image image = MainWindow.getCurrentImage();
		ImageInfo info = image.getInfo();
		final BufferedImage oldBufIm = image.img;
		final BufferedImage newBufIm = new BufferedImage(image.widthRoi(), image.heightRoi(), BufferedImage.TYPE_INT_RGB);
		// Calculamos el inverso de K (inverso del número de pixels / número de niveles)
		double kInv = (double)info.histAc.length / (double)(image.widthRoi() * image.heightRoi());
		byte[] lut = new byte[info.histAc.length];
		// Construimos la LUT con los nuevos niveles
		for (int i = 0; i < info.histAc.length; i++) {
			double vout = Math.rint((kInv * info.histAc[i]) - 1);
			vout = Math.min(255, vout);
			vout = Math.max(0, vout);
			lut[i] = (byte)vout;
		}
		// Aplicamos la nueva LUT
		image.img = Lut.aplicarLut(oldBufIm, newBufIm, lut);
		image.panel.img = image.img;
		image.resetInfo();
		image.panel.repaint();
	}
}
