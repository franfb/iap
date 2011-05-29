package hips.images.gray8;

import vision.ImagePanel;
import hips.images.Image;
import hips.images.rgb.ImageRGB;
import hips.region.Region;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;

public class ImageGray8 extends Image<PixelValue, Integer> {

	private byte pixels[][];

	public ImageGray8(ImagePlus impl) {
		height = impl.getHeight();
		width = impl.getWidth();
		size = height * width;
		slices = impl.getStackSize();
		channels = 1;
		pixels = new byte[slices][];
		for (int i = 0; i < slices; i++) {
			pixels[i] = (byte[]) impl.getStack().getProcessor(i + 1).getPixels();
		}
		initialize(impl);
	}

	private ImageGray8(int width, int height, int n) {
		this(createImagePlus(width, height, n));
	}

	private static ImagePlus createImagePlus(int width, int height, int n) {
		ij.ImageStack stack = new ij.ImageStack(width, height);
		for (int i = 0; i < n; i++) {
			stack.addSlice("" + i, new ByteProcessor(width, height));
		}
		return new ImagePlus("", stack);
	}

	public PixelValue getPixelValue(int index) {
		PixelValue p = new PixelValue(slices);
		for (int i = 0; i < slices; i++) {
			p.setValueInt(0x0FF & pixels[i][index], i);
		}
		return p;
	}

	public PixelValue newPixelValue(int value) {
		PixelValue p = new PixelValue(slices);
		for (int i = 0; i < slices; i++) {
			p.setValueInt(value, i);
		}
		return p;
	}

	public PixelValue newPixelValue() {
		return new PixelValue(slices);
	}

	public hips.images.Image newImage() {
		return new ImageGray8(width, height, slices);
	}

	public hips.images.rgb.ImageRGB newImageRGB() {
		return new hips.images.rgb.ImageRGB(width, height, slices);
	}

	public Partition newPartition(PixelValue alpha, PixelValue omega, float ci) {
		return new Partition(this, alpha, omega, ci);
	}

	public hips.Partition newPartition() {
		return new Partition(this);
	}

	public PixelValue newPixelValue(Integer value) {
		return newPixelValue(value.intValue());
	}

	public Integer getZero() {
		return 0;
	}

	public void putPixelValue(int index, PixelValue pixel) {
		for (int i = 0; i < slices; i++) {
			pixels[i][index] = (byte) pixel.getValueInt(i);
		}
	}

	public PixelValue getMeanValue(Region r) {
		long[] sum = new long[slices];
		for (int i = 0; i < slices; i++) {
			sum[i] = 0;
		}
		for (int i = 0; i < r.getSize(); i++) {
			PixelValue p = getPixelValue(r.getPixelPosition(i));
			for (int j = 0; j < slices; j++) {
				sum[j] += p.getValueInt(j);
			}
		}
		PixelValue color = newPixelValue();
		for (int j = 0; j < slices; j++) {
			color.setValueInt((int) (sum[j] / r.getSize()), j);
		}
		return color;
	}

	public hips.images.rgb.PixelValue toRGB(PixelValue color) {
		hips.images.rgb.PixelValue pixel = new hips.images.rgb.PixelValue(
				slices);
		for (int i = 0; i < slices; i++) {
			pixel.setValue(i, color.getValueInt(i), color.getValueInt(i),
					color.getValueInt(i));
		}
		return pixel;
	}
}
