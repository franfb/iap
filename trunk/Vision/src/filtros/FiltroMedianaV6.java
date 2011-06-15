package filtros;

import java.util.Arrays;

import vision.Image;

public class FiltroMedianaV6 extends Filtro {

	public int evaluar(Image im, int x, int y, int w, int h) {
		int nPix = w * h;
		GreyRgbPair[] listRgb = new GreyRgbPair[nPix];
//		GreyRgbPair[] listG = new GreyRgbPair[nPix];
//		GreyRgbPair[] listB = new GreyRgbPair[nPix];
		int value;
		int count = 0;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(src.x + i, src.y + j);
				listRgb[count] = new GreyRgbPair(value, (int)Math.round(Image.grey(value)));
//				listG[count] = Image.green(value);
//				listB[count] = Image.blue(value);
				count++;
			}
		}
		Arrays.sort(listRgb);
//		Arrays.sort(listG);
//		Arrays.sort(listB);
		return mediana(listRgb);
//		return Image.rgb(mediana(listRgb), mediana(listG), mediana(listB));
	}

	public static int mediana(GreyRgbPair[] list) {
		int centro = list.length / 2;
		if (list.length % 2 == 0) {
			int rgb1 = list[centro].getRgb();
			int rgb2 = list[centro - 1].getRgb();
			int r = (int)Math.round((Image.red(rgb1) + Image.red(rgb2)) / 2);
			int g = (int)Math.round((Image.green(rgb1) + Image.green(rgb2)) / 2);
			int b = (int)Math.round((Image.blue(rgb1) + Image.blue(rgb2)) / 2);
			return Image.rgb(r, g, b);
		}
		else {
			return (list[centro].getRgb());
		}
	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		return 0;
	}
}
