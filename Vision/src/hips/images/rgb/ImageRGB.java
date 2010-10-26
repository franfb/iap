package hips.images.rgb;

import hips.images.Image;
import hips.region.Region;
import ij.ImagePlus;
import ij.process.ColorProcessor;

public class ImageRGB extends hips.images.Image<PixelValueRGB, Integer> {
	public int[][] pixels;

	public ImageRGB(ImagePlus impl) {
		height = impl.getHeight();
		width = impl.getWidth();
		size = height * width;
		slices = impl.getStackSize();
		pixels = new int[slices][];
		for (int i = 0; i < slices; i++) {
			pixels[i] = (int[]) impl.getStack().getProcessor(i + 1).getPixels();
		}
		initialize(impl);
	}

	public ImageRGB(int width, int height, int n) {
		this(createImagePlus(width, height, n));
	}

	private static ImagePlus createImagePlus(int width, int height, int n) {
		ij.ImageStack stack = new ij.ImageStack(width, height);
		for (int i = 0; i < n; i++) {
			stack.addSlice("" + i, new ColorProcessor(width, height));
		}
		return new ImagePlus("", stack);
	}

	public static int toRGB(int r, int g, int b) {
		return (((0x0FF & r) << 16) | ((0x0FF & g) << 8) | (0x0FF & b));
	}

	public static int getRed(int value) {
		return ((0x0FF0000 & value) >> 16);
	}

	public static int getGreen(int value) {
		return ((0x00FF00 & value) >> 8);
	}

	public static int getBlue(int value) {
		return (0x0FF & value);
	}

	public void putPixelValue(int index, PixelValueRGB pixel) {
		for (int i = 0; i < slices; i++) {
			pixels[i][index] = pixel.getValueInt(i);
		}
	}

	public PixelValueRGB newPixelValue(Integer value) {
		PixelValueRGB p = new PixelValueRGB(slices);
		for (int i = 0; i < slices; i++) {
			p.setValueInt(value, i);
		}
		return p;
	}

	public PixelValueRGB newPixelValue() {
		return new PixelValueRGB(slices);
	}

	public PixelValueRGB getPixelValue(int index) {
		PixelValueRGB pixel = new PixelValueRGB(slices);
		for (int i = 0; i < slices; i++) {
			pixel.setValueInt(pixels[i][index], i);
		}
		return pixel;
	}

	public Integer getZero() {
		return 0;
	}

	public Image newImage() {
		return new ImageRGB(width, height, slices);
	}

	public ImageRGB newImageRGB() {
		return new ImageRGB(width, height, slices);
	}

	public PixelValueRGB getMeanValue(Region r) {
		long[] sum = new long[slices];
		for (int i = 0; i < slices; i++) {
			sum[i] = 0;
		}
		for (int i = 0; i < r.getSize(); i++) {
			PixelValueRGB p = getPixelValue(r.getPixelPosition(i));
			for (int j = 0; j < slices; j++) {
				sum[j] += p.getValueInt(j);
			}
		}
		PixelValueRGB color = newPixelValue();
		for (int j = 0; j < slices; j++) {
			color.setValueInt((int) (sum[j] / r.getSize()), j);
		}
		return color;
	}

	public PixelValueRGB toRGB(PixelValueRGB color) {
		return color;
	}
}