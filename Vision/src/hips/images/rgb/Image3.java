package hips.images.rgb;

import hips.images.rgb.ImageRGB;
import hips.images.rgb.PixelValueRGB;
import hips.region.Region;
import ij.ImagePlus;
import ij.process.ColorProcessor;

public class Image3 extends hips.images.Image<PixelValueRGB, Integer>{
    int pixels[];

    public Image3(int width, int height, String title){
        this.height = height;
        this.width = width;
        size = height * width;
        slices = 3;
        ImagePlus impl = new ImagePlus(title, new ColorProcessor(width, height));
        pixels = (int[])impl.getProcessor().getPixels();
        initialize(impl);
    }

    public PixelValueRGB newPixelValue(Integer value){
        PixelValueRGB p = new PixelValueRGB(3);
        p.setValueInt(ImageRGB.toRGB(ImageRGB.getRed(value), 0, 0), 0);
        p.setValueInt(ImageRGB.toRGB(ImageRGB.getGreen(value), 0, 0), 1);
        p.setValueInt(ImageRGB.toRGB(ImageRGB.getBlue(value), 0, 0), 2);
        return p;
    }

    public void putPixelValue(int index, PixelValueRGB p) {
        pixels[index] = ImageRGB.toRGB(
                ImageRGB.getRed(p.getValue(0)),
                ImageRGB.getRed(p.getValue(1)),
                ImageRGB.getRed(p.getValue(2)));
    }

//    public static String getStringFrom(hips.pixel.PixelValue p){
//        hips.images.gray8.PixelValue pixel = (hips.images.gray8.PixelValue) p;
//        return  "R=" + pixel.getValueInt(0) +
//                ",G=" + pixel.getValue(1) +
//                ",B=" + pixel.getValueInt(2);
//    }

    public static String getStringFrom(hips.pixel.PixelValue p){
        return  "R=" + p.getValue(0) +
                ",G=" + p.getValue(1) +
                ",B=" + p.getValue(2);
    }

//    public void colourComponent(Component c, segimasat.image.Image colorSource) {
//        segimasat.pixel.PixelSum colorSum = colorSource.newPixelSum();
//        for (int i = 0; i < c.getSize(); i++) {
//            colorSum.add(colorSource.getPixel(c.getPixel(i)));
//        }
//        segimasat.pixel.PixelValue color = colorSum.newPixelValue(c.getSize());
//        for (int i = 0; i < c.getSize(); i++) {
//            putPixel(c.getPixel(i), (PValueStack) color.getRGB(colorSource.minValueVector, colorSource.maxValueVector)); // ESTE CAST HAY QUE KITARLO.
//        }
//        impl.updateAndDraw();
//    }


//    public void colourComponent(Region c, PixelValueRGB color) {
//        for (int i = 0; i < c.getSize(); i++) {
//            putPixelValue(c.getPixelPosition(i), color);
//        }
//        impl.updateAndDraw();
//    }

    @Override
    public PixelValueRGB getPixelValue(int index) {
    	PixelValueRGB pixel = new PixelValueRGB(1);
    	pixel.setValueInt(pixels[index], 0);
    	return pixel;
    }

    @Override
    public Integer getZero() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public hips.images.Image newImage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImageRGB newImageRGB() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PixelValueRGB newPixelValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PixelValueRGB toRGB(PixelValueRGB pv) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PixelValueRGB getMeanValue(Region region) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
