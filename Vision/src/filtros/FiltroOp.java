package filtros;

import java.awt.image.BufferedImage;

import vision.Image;

public class FiltroOp {

	public static void ventanaMovil(Image im, Image newIm, Filtro f, int tam, int k) {
		int inc = tam / 2;
		int width;
		int height;
		int startX, startY;
		for (int x = 0; x < im.widthRoi(); x++) {
			for (int y = 0; y < im.heightRoi(); y++) {
				width = tam;
				startX = x - inc;
				if (startX < 0) {
					width += startX;
					startX = 0;
				}
				if (startX + width > im.widthRoi())
					width -= (startX + width) - im.widthRoi();
				
				height = tam;
				startY = y - inc;
				if (startY < 0) {
					height += startY;
					startY = 0;
				}
				if (startY + height > im.heightRoi())
					height -= (startY + height) - im.heightRoi();
				
				newIm.img.setRGB(x, y, f.evaluar(im, startX, startY, width, height, k));
			}
		}
	}
}
