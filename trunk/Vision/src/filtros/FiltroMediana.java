package filtros;

import java.util.Arrays;

import vision.Image;

public class FiltroMediana extends Filtro {

	private int nPix;
	private int[] listR;
	private int[] listG;
	private int[] listB;
	
	public int evaluar(Image im, int x, int y, int w, int h) {
		nPix = w * h;
//		int[] listR = new int[nPix];
//		int[] listG = new int[nPix];
//		int[] listB = new int[nPix];
		int value;
		int count = 0;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(src.x + i, src.y + j);
				listR[count] = Image.red(value);
				listG[count] = Image.green(value);
				listB[count] = Image.blue(value);
				count++;
			}
		}
		Arrays.sort(listR, 0, nPix);
		Arrays.sort(listG, 0, nPix);
		Arrays.sort(listB, 0, nPix);
		return Image.rgb(mediana(listR, nPix), mediana(listG, nPix), mediana(listB, nPix));
	}

	public static int mediana(int[] list, int tam) {
		int centro = tam / 2;
		if (tam % 2 == 0) {
			return (int)Math.rint((double)(list[centro] + list[centro - 1]) / 2);
		}
		else {
			return (list[centro]);
		}
	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		return 0;
	}
	
	@Override
	public void ventanaMovil(Image im, Image newIm, int tam, int k) {
		nPix = tam * tam;
		listR = new int[nPix];
		listG = new int[nPix];
		listB = new int[nPix];
		super.ventanaMovil(im, newIm, tam, k);
	}
}
