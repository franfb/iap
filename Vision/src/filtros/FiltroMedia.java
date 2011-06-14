package filtros;

import vision.Image;

public class FiltroMedia extends Filtro {

	public int evaluar(Image im, int x, int y, int w, int h) {
		double acumR = 0;
		double acumG = 0;
		double acumB = 0;
		int value;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(src.x + i, src.y + j);
				acumR += Image.red(value);
				acumG += Image.green(value);
				acumB += Image.blue(value);
			}
		}
		int nPix = w * h;
		acumR /= nPix;
		acumG /= nPix;
		acumB /= nPix;
		return Image.rgb((int)Math.rint(acumR), (int)Math.rint(acumG), (int)Math.rint(acumB));
	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		return 0;
	}

}
