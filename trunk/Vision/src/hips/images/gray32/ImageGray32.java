package hips.images.gray32;

import hips.images.Image;
import hips.images.ImagePartitionable;
import hips.region.Region;
import ij.ImagePlus;
import ij.process.FloatProcessor;

public class ImageGray32 extends Image<PixelValue, Float>{
    private float pixels[][];

    public ImageGray32(ImagePlus impl){
        height = impl.getHeight();
        width = impl.getWidth();
        size = height * width;
        slices = impl.getStackSize();
        pixels = new float[slices][];
        for (int i = 0; i < slices; i++){
            pixels[i] = (float[])impl.getStack().getProcessor(i + 1).getPixels();
        }
        initialize(impl);
    }

    public ImageGray32(int width, int height, int n){
    	this(createImagePlus(width, height, n));
    }
    
	private static ImagePlus createImagePlus(int width, int height, int n) {
		ij.ImageStack stack = new ij.ImageStack(width, height);
		for (int i = 0; i < n; i++) {
			stack.addSlice("" + i, new FloatProcessor(width, height));
		}
		return new ImagePlus("", stack);
	}
    
    public PixelValue getPixelValue(int index){
        PixelValue p = new PixelValue(slices);
        for (int i = 0; i < slices; i++)
            p.setValueFloat(pixels[i][index], i);
        return p;
    }

    public void putPixelValue(int index, PixelValue pixel){
        for (int i = 0; i < slices; i++)
            pixels[i][index] = pixel.getValueFloat(i);
    }

    public PixelValue newPixelValue(float value){
        PixelValue p = new PixelValue(slices);
        for (int i = 0; i < slices; i++){
            p.setValueFloat(value, i);
        }
        return p;
    }

    public PixelValue newPixelValue() {
        return new PixelValue(slices);
    }

    public hips.images.Image newImage() {
        return new ImageGray32(width, height, slices);
    }

    public hips.images.rgb.ImageRGB newImageRGB() {
        return new hips.images.rgb.ImageRGB(width, height, slices);
    }

    public Partition newPartition(PixelValue alpha, PixelValue omega, float ci){
        return new Partition(this, alpha, omega, ci);
    }

    public hips.Partition newPartition() {
        return new Partition(this);
    }

    public PixelValue newPixelValue(Float value){
        return newPixelValue(value.floatValue());
    }

    public Float getZero(){
        return 0f;
    }

    public PixelValue getMeanValue(Region r) {
        float[] sum = new float[slices];
        for (int i = 0; i < slices; i++) {
            sum[i] = 0f;
        }
        for (int i = 0; i < r.getSize(); i++) {
            PixelValue p = getPixelValue(r.getPixelPosition(i));
            for (int j = 0; j < slices; j++) {
                sum[j] += p.getValueFloat(j);
            }
        }
        PixelValue color = newPixelValue();
        for (int j = 0; j < slices; j++) {
            color.setValueFloat(sum[j] / r.getSize(), j);
        }
        return color;
    }

    public hips.images.rgb.PixelValue toRGB(PixelValue color) {
        hips.images.rgb.PixelValue p = new hips.images.rgb.PixelValue(slices);
        for (int i = 0; i < slices; i++){
            int value = (int)
                (((color.getValueFloat(i) - getMinValue().getValueFloat(i)) * 255)
                /
                (getMaxValue().getValueFloat(i) - getMinValue().getValueFloat(i)));
            p.setValue(i, value, value, value);
        }
        return p;
    }
}