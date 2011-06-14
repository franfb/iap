package filtros;

import java.util.Arrays;
import vision.Image;

public class FiltroKVec extends Filtro {

	public int evaluar(Image im, int x, int y, int w, int h, int k, int v) {
		int nPix = w * h;
		ValueDifPair[] listR = new ValueDifPair[nPix];
		ValueDifPair[] listG = new ValueDifPair[nPix];
		ValueDifPair[] listB = new ValueDifPair[nPix];
		
		int value;
		int count = 0;
		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				value = im.img.getRGB(i, j);
				listR[count] = new ValueDifPair(Image.red(value), Math.abs(Image.red(value) - Image.red(v)));
				listG[count] = new ValueDifPair(Image.green(value), Math.abs(Image.green(value) - Image.green(v)));
				listB[count] = new ValueDifPair(Image.blue(value), Math.abs(Image.blue(value) - Image.blue(v)));
				count++;
			}
		}
		Arrays.sort(listR);
		Arrays.sort(listG);
		Arrays.sort(listB);
		return Image.rgb(mediaKVec(listR, k), mediaKVec(listG, k), mediaKVec(listB, k));
	}
	
	public static int mediaKVec(ValueDifPair[] list, int k) {
		double acum = 0;
		for (int i = 0; i < (k < list.length ? k + 1 : list.length); i++) {
			acum += list[i].getValue();
		}
		return (int)Math.rint(acum / (k < list.length ? k + 1 : list.length));
	}

//	public void ventanaMovil(Image im, Image newIm, int tam, int k) {
//		int inc = tam / 2;
//		int width;
//		int height;
//		int startX, startY;
//		for (int x = 0; x < im.widthRoi(); x++) {
//			for (int y = 0; y < im.heightRoi(); y++) {
//				width = tam;
//				startX = x - inc;
//				if (startX < 0) {
//					width += startX;
//					startX = 0;
//				}
//				if (startX + width > im.widthRoi())
//					width -= (startX + width) - im.widthRoi();
//				
//				height = tam;
//				startY = y - inc;
//				if (startY < 0) {
//					height += startY;
//					startY = 0;
//				}
//				if (startY + height > im.heightRoi())
//					height -= (startY + height) - im.heightRoi();
//				
//				newIm.img.setRGB(x, y, evaluar(im, startX, startY, width, height, k, im.img.getRGB(x, y)));
//			}
//		}
//	}

	@Override
	public int evaluar(Image im, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		return 0;
	}
}
