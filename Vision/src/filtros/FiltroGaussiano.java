package filtros;

import java.awt.Point;

import vision.Image;

public class FiltroGaussiano extends Filtro {

	@Override
	public int evaluar(Image im, int x, int y, int w, int h) {
		return 0;
	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		return 0;
	}
	
	public void convolucion(Image im, Image newIm, double sigma) {
		Kernel h = new Kernel(1, (int)Math.round(sigma * 3) * 2 + 1, 0, -(int)Math.round(sigma * 3));
		double value;
		for (int i = h.getColIni(); i < h.getColMax(); i++) {
			value = (1 / (Math.sqrt(2 * Math.PI) * sigma)) * Math.exp(-(i * i) / (2 * sigma * sigma));
			h.set(0, i, value);
		}
		Image imTemp = Image.crearImagenSinPrefijo(im.widthRoi(), im.heightRoi(), im, "Imagen temporal");
		src = im.topLeftRoi();
		for (int x = 0; x < im.widthRoi(); x++) {
			for (int y = 0; y < im.heightRoi(); y++) {
				imTemp.img.setRGB(x, y, im.img.getRGB(src.x + x, src.y + y));
			}
		}
		convolucion(im, imTemp, h);
		h.trasponer();
		convolucion(imTemp, newIm, h);
	}
}
