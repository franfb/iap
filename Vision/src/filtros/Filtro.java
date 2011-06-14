package filtros;

import vision.Image;

public abstract class Filtro {

	public abstract int evaluar(Image im, int x, int y, int w, int h);
	
	public abstract int evaluar(Image im, int x, int y, int w, int h, int k, int v);
	
	public void ventanaMovil(Image im, Image newIm, int tam) {
		ventanaMovil(im, newIm, tam, -1);
	}
	
	public void ventanaMovil(Image im, Image newIm, int tam, int k) {
		int inc = tam / 2;
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
				
				if (x == 636 && y == 657) {
					System.out.println("Aqui");
				}
				
				if (k == -1) {
					newIm.img.setRGB(x, y, evaluar(im, startX, startY, width, height));
				}
				else {
					newIm.img.setRGB(x, y, evaluar(im, startX, startY, width, height, k, im.img.getRGB(x, y)));
				}
			}
		}
	}
}
