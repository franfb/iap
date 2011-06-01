package vision;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {
	
	public File file;
	public BufferedImage img;
	public ImagePanel panel;
	public String format;
	public boolean saved;
	
	public Image(File file, BufferedImage img, boolean saved) {
		this.file = file;
		this.img = img;
		this.format = ImageFilter.getExtension(file);
		this.saved = saved;
		panel = new ImagePanel(this);
	}

	public void cambiarFile(File file){
		this.file = file;
		this.format = ImageFilter.getExtension(file);
	}
	
	public Point topLeftRoi(){
		if (!panel.validRoi()){
			return new Point(0, 0);
		}
		else{
			Point topLeft = panel.getTopLeftRoi();
			return panel.getCoordinate(topLeft.x, topLeft.y);
		}
	}
	
	public int widthRoi(){
		if (!panel.validRoi()){
			return img.getWidth();
		}
		else{
			return panel.getBottomRightRoi().x - panel.getTopLeftRoi().x + 1;
		}
	}
	
	public int heightRoi(){
		if (!panel.validRoi()){
			return img.getHeight();
		}
		else{
			return panel.getBottomRightRoi().y - panel.getTopLeftRoi().y + 1;
		}
	}
	
	public static Image crearImagen(int width, int height, File filename){
		return new Image(filename, new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), false);
	}
	
	public static String getString(int value) {
		return "R=" + red(value) + ", G=" + green(value) + ", B="
				+ blue(value);
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
	
	public static float grey(int value) {
		return 0.299f * red(value) + 0.587f * green(value) + 0.114f * blue(value);
	}
	
	public static int rgb(int r, int g, int b) {
		return (((0x0FF & r) << 16) | ((0x0FF & g) << 8) | (0x0FF & b));
	}
	
	public static int rgb(int grey) {
		return (((0x0FF & grey) << 16) | ((0x0FF & grey) << 8) | (0x0FF & grey));
	}
	
}
