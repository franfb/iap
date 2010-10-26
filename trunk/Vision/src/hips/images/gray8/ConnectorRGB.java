package hips.images.gray8;

class ConnectorRGB implements Connector {

    private int[] pixels;

    protected ConnectorRGB(int[] pixels) {
        this.pixels = pixels;
    }

    public int getPixel(int slice, int pixel) {
        if (slice == 0) {
            return (pixels[pixel] & 0xff0000) >> 16;
        }
        if (slice == 1) {
            return (pixels[pixel] & 0x00ff00) >> 8;
        }
        return pixels[pixel] & 0x0000ff;
    }

    public void putPixel(int slice, int pixel, int value) {
        if (slice == 0) {
            pixels[pixel] = ((pixels[pixel] & 0x00FFFF) | ((value & 0x0FF) << 16));
        } else if (slice == 1) {
            pixels[pixel] = ((pixels[pixel] & 0xFF00FF) | ((value & 0x0FF) << 8));
        } else {
            pixels[pixel] = ((pixels[pixel] & 0xFFFF00) | (value & 0x0FF));
        }
    }
}
