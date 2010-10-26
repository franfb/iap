package hips.images.rgb;

import hips.pixel.PixelValue;


public class PixelValueRGB extends PixelValue {

    private int[] rgb;

    public PixelValueRGB(int n) {
        rgb = new int[n];
    }

    public void setValueInt(int value, int slice) {
        rgb[slice] = value;
    }

    public void setValue(int slice, int r, int g, int b) {
        rgb[slice] = hips.images.rgb.ImageRGB.toRGB(r, g, b);
    }

    public int getValueInt(int slice) {
        return rgb[slice];
    }

    public int getSlices() {
        return rgb.length;
    }

    public PixelValueRGB copy() {
        PixelValueRGB p = new PixelValueRGB(rgb.length);
        System.arraycopy(rgb, 0, p.rgb, 0, rgb.length);
        return p;
    }

    public String getString() {
        String returned = "[";
        for (int i = 0; i < rgb.length - 1; i++) {
            returned += "(" + getString(i) + "), ";
        }
        return returned + getString(rgb.length - 1) + "]";

    }

    public String getString(int index) {
        return  "R=" + ImageRGB.getRed(rgb[index]) +
                ", G=" + ImageRGB.getGreen(rgb[index]) +
                ", B=" + ImageRGB.getBlue(rgb[index]);
    }

    @Override
    public void setValueAsFloat(float value, int slice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getValueAsFloat(int slice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLower(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setGreater(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLowerOrEqual(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isGreaterOrEqual(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEqual(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public hips.pixel.PixelValue range(hips.pixel.PixelValue pixel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(hips.pixel.PixelValue p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(float value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sub(hips.pixel.PixelValue p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sub(float value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void div(float value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mult(float value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Integer value, int slice) {
        setValueInt(value, slice);
    }

    public Integer getValue(int slice) {
        return getValueInt(slice);
    }

    @Override
    public void setValue(Comparable value, int slice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Comparable minValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Comparable maxValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
