package vision;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {

	private File file;
	private String prefijo;
	
	public BufferedImage img;
	public ImagePanel panel;
	public boolean saved;
	// Atributos para guardar la información de la imagen
	public String format;
	private ImageInfo info;
	private long pixelsOut;
	
	public Image(File file, String prefijo, BufferedImage img, boolean saved) {
		this.file = file;
		this.prefijo = prefijo;
		this.img = img;
		this.format = ImageFilter.getExtension(file);
		this.saved = saved;
		panel = new ImagePanel(this);
		this.pixelsOut = 0;
	}

	public ImageInfo getInfo() {
		if ((info == null) || (panel.validRoi())) {
			ImageInfo inf = new ImageInfo();

			long numPixels = widthRoi() * heightRoi();
			if (!panel.validRoi()) {
				numPixels -= pixelsOut; 
			}
			
			Point src = topLeftRoi();
			for (int x = 0; x < widthRoi(); x++) {
				for (int y = 0; y < heightRoi(); y++) {
					int rgbLevel = img.getRGB(src.x + x, src.y + y);
					int r = red(rgbLevel);
					int g = green(rgbLevel);
					int b = blue(rgbLevel);
					int greyLevel = (int)Math.round(grey(rgbLevel));
					// Rango dinámico para R
					if (r > inf.maxR) inf.maxR = r;
					if (r < inf.minR) inf.minR = r;
					// Rango dinámico para G
					if (g > inf.maxG) inf.maxG = g;
					if (g < inf.minG) inf.minG = g;
					// Rango dinámico para B
					if (b > inf.maxB) inf.maxB = b;
					if (b < inf.minB) inf.minB = b;
					// Histograma
					inf.hist[greyLevel]++;
					inf.histR[r]++;
					inf.histG[g]++;
					inf.histB[b]++;
//					inf.histAc[greyLevel]++;
//					inf.histAcR[r]++;
//					inf.histAcG[g]++;
//					inf.histAcB[b]++;
					// Brillo
					inf.brillo += greyLevel;
					// Contraste
					// Entropía
				}
			}
			inf.hist[0] -= pixelsOut;
			inf.histR[0] -= pixelsOut;
			inf.histG[0] -= pixelsOut;
			inf.histB[0] -= pixelsOut;
			
			// Hallamos los histogramas acumulados
			for (int i = 0; i < inf.hist.length - 1; i++) {
				inf.histAc[i] += inf.hist[i];
				inf.histAc[i+1] += inf.histAc[i];
				inf.histAcR[i] += inf.histR[i];
				inf.histAcR[i+1] += inf.histAcR[i];
				inf.histAcG[i] += inf.histG[i];
				inf.histAcG[i+1] += inf.histAcG[i];
				inf.histAcB[i] += inf.histB[i];
				inf.histAcB[i+1] += inf.histAcB[i];
			}
			inf.histAc[255] += inf.hist[255];
			inf.histAcR[255] += inf.histR[255];
			inf.histAcG[255] += inf.histG[255];
			inf.histAcB[255] += inf.histB[255];
			
			// Hallamos la media de los valores de gris (brillo)
			inf.brillo /= numPixels;

			// Hallamos la desviación típica de la imagen (contraste) a partir del histograma
	        for (int i = 0; i < inf.hist.length; i++) {
	            inf.contraste += inf.hist[i] * Math.pow((double)(i - inf.brillo), 2);
	        }
	        inf.contraste = Math.round((float)Math.sqrt((1/(double)numPixels) * inf.contraste));
	        
	        // Calculamos la entropía de la imagen a partir del histograma
	        inf.entropia = 0;
	        for(int i = 0; i < inf.hist.length; i++) {
	            if (inf.hist[i] > 0) {
	                double prob = ((double)inf.hist[i] / (double)numPixels);
	                double logProb = Math.log10(prob)/Math.log10(2);
	                inf.entropia = inf.entropia - (prob*logProb);
	            }
	        }
			
	        if (info == null) info = inf;
	        
			return inf;
		}
		else {
			return info;
		}
	}
	
	public void resetInfo() {
		this.info = null;
		this.getInfo();
	}

	public File getFileCompleto(){
		return new File(file.getParent(), prefijo + file.getName());
	}
	
	public File getFileOriginal(){
		return file;
	}
	
	public void cambiarFile(File file) {
		if (file.getAbsolutePath().compareTo(getFileCompleto().getAbsolutePath()) == 0){
			return;
		}
		this.file = file;
		this.prefijo = "";
		this.format = ImageFilter.getExtension(file);
	}

	public Point topLeftRoi() {
		if (!panel.validRoi()) {
			return new Point(0, 0);
		} else {
			Point topLeft = panel.getTopLeftRoi();
			return panel.getCoordinate(topLeft.x, topLeft.y);
		}
	}

	public boolean validRoi(){
		return panel.validRoi();
	}
	
	public int widthRoi() {
		if (!panel.validRoi()) {
			return img.getWidth();
		} else {
			return panel.getBottomRightRoi().x - panel.getTopLeftRoi().x + 1;
		}
	}

	public int heightRoi() {
		if (!panel.validRoi()) {
			return img.getHeight();
		} else {
			return panel.getBottomRightRoi().y - panel.getTopLeftRoi().y + 1;
		}
	}
	
	public void setPixelsOut(long pixOut) {
		this.pixelsOut = pixOut;
	}
	
	/*public static Image crearImagen(int width, int height, File filename) {
		return new Image(filename, new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB), false);
	}*/
	
	public static Image crearImagenConPrefijo(int width, int height, Image img, String prefijo) {
		if (img.prefijo.compareTo("") != 0){
			return new Image(img.getFileOriginal(), "Edición de " , new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB), false);
		}
		else{
			return new Image(img.getFileOriginal(), prefijo, new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB), false);
		}
	}
	
	public static Image crearImagenSinPrefijo(int width, int height, Image img, String nombre) {
		File file = new File(img.file.getParent(), nombre + "." + img.format);
		return new Image(file, "", new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB), false);
	}

	public static String getString(int value) {
		return "R=" + red(value) + ", G=" + green(value) + ", B=" + blue(value);
	}

	public static int red(int value) {
		return ((0x0FF0000 & value) >> 16);
	}

	public static int green(int value) {
		return ((0x00FF00 & value) >> 8);
	}

	public static int blue(int value) {
		return (0x0FF & value);
	}

	public static double grey(int value) { // Modelo NTSC
		return (double)0.299 * red(value) + (double)0.587 * green(value) + (double)0.114 * blue(value);
	}

	public static int rgb(int r, int g, int b) {
		return (((0x0FF & r) << 16) | ((0x0FF & g) << 8) | (0x0FF & b));
	}

	public static int rgb(int grey) {
		return (((0x0FF & grey) << 16) | ((0x0FF & grey) << 8) | (0x0FF & grey));
	}
	
	public static int[] rgb2array(int value, int[] array){
		array[0] = red(value);
		array[1] = green(value);
		array[2] = blue(value);
		return array;
	}
	
	public static int array2rgb(int[] values){
		return rgb(values[0], values[1], values[2]);
	}
	
	public static int array2rgb(float[] values){
		return rgb(Math.round(values[0]), Math.round(values[1]), Math.round(values[2]));
	}

}
