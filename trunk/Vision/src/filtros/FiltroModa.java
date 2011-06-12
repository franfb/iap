package filtros;

import java.util.Arrays;

import vision.Image;
import vision.ImageInfo;

public class FiltroModa implements Filtro {

	@Override
	public int evaluar(Image im, int x, int y, int w, int h) {
		int nPix = w * h;
		int[] listR = new int[ImageInfo.NIVELES];
		int[] listG = new int[ImageInfo.NIVELES];
		int[] listB = new int[ImageInfo.NIVELES];
		for (int i = 0; i < listR.length; i++) {
			listR[i] = 0;
			listG[i] = 0;
			listB[i] = 0;
		}
		
		int value;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(i, j);
				listR[Image.red(value)]++;
				listG[Image.green(value)]++;
				listB[Image.blue(value)]++;
			}
		}
		return Image.rgb(moda(listR), moda(listG), moda(listB));
	}

	public int moda(int[] list) {
		int max = 0;
		int moda = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] > max) {
				max = list[i];
				moda = i;
			}
		}
		return moda;
	}
}
