package filtros;

import java.awt.Point;

import vision.Image;

public class FiltroDifEst extends Filtro {

	@Override
	public int evaluar(Image im, int x, int y, int w, int h) {
		return 0;
	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		double acumR = 0;
		double acumG = 0;
		double acumB = 0;
		double acum = 0;
		int value;
		// Cálculo de la media local
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(src.x + i, src.y + j);
				acum += Image.grey(value);
				acumR += Image.red(value);
				acumG += Image.green(value);
				acumB += Image.blue(value);
			}
		}
		int nPix = w * h;
		acum /= nPix;
		acumR /= nPix;
		acumG /= nPix;
		acumB /= nPix;
		// Cálculo de la desviación típica local
		double desvR = 0;
		double desvG = 0;
		double desvB = 0;
		double desv = 0;
		// Cálculo de la media local
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(src.x + i, src.y + j);
				desv += Math.pow(Image.grey(value) - acum, 2);
				desvR += Math.pow(Image.red(value) - acumR, 2);
				desvG += Math.pow(Image.green(value) - acumG, 2);
				desvB += Math.pow(Image.blue(value) - acumB, 2);
			}
		}
		desv = Math.sqrt(desv / nPix);
		desvR = Math.sqrt(desvR / nPix);
		desvG = Math.sqrt(desvG / nPix);
		desvB = Math.sqrt(desvB / nPix);
//		int resR = (int)Math.rint(acumR + (Image.red(v) - acumR) * k / desvR);
//		int resG = (int)Math.rint(acumG + (Image.green(v) - acumG) * k / desvG);
//		int resB = (int)Math.rint(acumB + (Image.blue(v) - acumB) * k / desvB);
		if (desvR < 1) {
			desvR = 1;
		}
		if (desvG < 1) {
			desvG = 1;
		}
		if (desvB < 1) {
			desvB = 1;
		}
		int resR, resG, resB;
		if (Image.red(v) - acumR < 0) {
			resR = (int)Math.rint(acumR);
		}
		else {
			resR = (int)Math.rint(acumR + (Image.red(v) - acumR) * k / desvR);
		}
		if (Image.green(v) - acumG < 0) {
			resG = (int)Math.rint(acumG);
		}
		else {
			resG = (int)Math.rint(acumG + (Image.green(v) - acumG) * k / desvG);
		}
		if (Image.blue(v)- acumB < 0) {
			resB = (int)Math.rint(acumB);
		}
		else {
			resB = (int)Math.rint(acumB + (Image.blue(v) - acumB) * k / desvB);
		}
		return Image.rgb(resR, resG, resB);
	}

}
