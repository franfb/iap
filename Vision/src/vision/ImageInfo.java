package vision;

public class ImageInfo {
	public int[] hist, histR, histG, histB;
	public int[] histAc, histAcR, histAcG, histAcB;
	public int minR, maxR, minG, maxG, minB, maxB;
	public int brillo, contraste;
	public double entropia;
	
	public static final int NIVELES = 256;
	
	public ImageInfo() {
		hist = new int[NIVELES];
		histR = new int[NIVELES];
		histG = new int[NIVELES];
		histB = new int[NIVELES];
		histAc = new int[NIVELES];
		histAcR = new int[NIVELES];
		histAcG = new int[NIVELES];
		histAcB = new int[NIVELES];
		for (int i = 0; i < NIVELES; i++) {
			hist[i] = 0;
			histR[i] = 0;
			histG[i] = 0;
			histB[i] = 0;
			histAc[i] = 0;
			histAcR[i] = 0;
			histAcG[i] = 0;
			histAcB[i] = 0;
		}
		minR = 255;
		maxR = 0;
		minG = 255;
		maxG = 0;
		minB = 255;
		maxB = 0;
		brillo = 0;
		contraste = 0;
		entropia = 0;
	}
}
