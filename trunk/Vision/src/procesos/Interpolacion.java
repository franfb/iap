package procesos;

import java.awt.Point;

import vision.Image;

public class Interpolacion {
	
	public static void bilineal(Image im, Image newIm, float xf, float yf, int xn, int yn) {
		Point src = im.topLeftRoi();
		int x0 = src.x + (int)xf;
        int x1 = src.x + (int)Math.ceil(xf);
        int y0 = src.y + (int)yf;
        int y1 = src.y + (int)Math.ceil(yf);
        float p = (xf + src.x) - x0;
        float q = (yf + src.y) - y0;
		
		int[] a = Image.rgb2array(im.img.getRGB(x0, y0));
		int[] b = Image.rgb2array(im.img.getRGB(x1, y0));
		int[] c = Image.rgb2array(im.img.getRGB(x0, y1));
		int[] d = Image.rgb2array(im.img.getRGB(x1, y1));

		float[] nivel = new float[3];
		for (int i = 0; i < 3; i++) {
			nivel[i] = a[i] + (b[i] - a[i]) * p + (c[i] - a[i])	* q + (a[i] + d[i] - b[i] - c[i]) * p * q;
		}
		newIm.img.setRGB(xn, yn, Image.array2rgb(nivel));
	}
	
	public static void vmp(Image im, Image newIm, float xf, float yf, int xn, int yn) {
		Point src = im.topLeftRoi();
		int masProximoX = Math.round(xf);
		int masProximoY = Math.round(yf);
		newIm.img.setRGB(xn, yn, im.img.getRGB(src.x + masProximoX, src.y + masProximoY));
	}
}
