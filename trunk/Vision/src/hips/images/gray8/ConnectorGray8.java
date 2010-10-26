package hips.images.gray8;

class ConnectorGray8 implements Connector{
    private byte[][] pixels;
    
    protected ConnectorGray8(byte[][] pixels){
        this.pixels = pixels;
    }

    public int getPixel(int slice, int pixel) {
        return 0x0FF & pixels[slice][pixel];
    }

    public void putPixel(int slice, int pixel, int value) {
        pixels[slice][pixel] = (byte) value;
    }
}