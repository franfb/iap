package hips.images.gray8;

interface Connector {
    public int getPixel(int slice, int pixel);
    public void putPixel(int slice, int pixel, int value);
}
