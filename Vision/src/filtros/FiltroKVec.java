package filtros;

import java.util.Arrays;

import vision.Image;

public class FiltroKVec implements Filtro {

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k) {
		int nPix = w * h;
		int[] listR = new int[nPix];
		int[] listG = new int[nPix];
		int[] listB = new int[nPix];
		int value;
		int count = 0;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(i, j);
				listR[count] = Image.red(value);
				listG[count] = Image.green(value);
				listB[count] = Image.blue(value);
				count++;
			}
		}
		Arrays.sort(listR);
		Arrays.sort(listG);
		Arrays.sort(listB);
//		return Image.rgb(mediana(listR), mediana(listG), mediana(listB));
		return 0;
	}

}
