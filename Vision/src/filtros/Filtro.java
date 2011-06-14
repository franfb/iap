package filtros;

import java.awt.Point;

import vision.Image;

public abstract class Filtro {
	
	protected Point src;

	public abstract int evaluar(Image im, int x, int y, int w, int h);
	
	public abstract int evaluar(Image im, int x, int y, int w, int h, int k, int v);
	
	public void ventanaMovil(Image im, Image newIm, int tam) {
		ventanaMovil(im, newIm, tam, -1);
	}
	
	public void ventanaMovil(Image im, Image newIm, int tam, int k) {
		int inc = tam / 2;
		src = im.topLeftRoi();
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
				
				if (k == -1) {
					newIm.img.setRGB(x, y, evaluar(im, startX, startY, width, height));
				}
				else {
					newIm.img.setRGB(x, y, evaluar(im, startX, startY, width, height, k, im.img.getRGB(src.x + x, src.y + y)));
				}
			}
		}
	}
	
	public void convolucion(Image im, Image newIm, Kernel h) {
		double acumR, acumG, acumB;
		src = im.topLeftRoi();
		int width = im.widthRoi();
		int height = im.heightRoi();

		int fi = h.getFilIni();
		int fm = h.getFilMax();
		int ci = h.getColIni();
		int cm = h.getColMax();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((y + fi < 0) || (y + fm > height) || (x + ci < 0)
						|| (x + cm > width)) {
					newIm.img.setRGB(x, y, im.img.getRGB(src.x + x, src.y + y));
				} else {
					acumR = 0;
					acumG = 0;
					acumB = 0;
					int pv;
					for (int m = fi; m < fm; m++) {
						for (int n = ci; n < cm; n++) {
							pv = im.img.getRGB(src.x + x - n, src.y + y - m);
							acumR += Image.red(pv) * h.get(m, n);
							acumG += Image.green(pv) * h.get(m, n);
							acumB += Image.blue(pv) * h.get(m, n);
						}
					}
					newIm.img.setRGB(
							x,
							y,
							Image.rgb((int) Math.round(acumR),
									(int) Math.round(acumG),
									(int) Math.round(acumB)));
				}
			}
		}
	}
}
