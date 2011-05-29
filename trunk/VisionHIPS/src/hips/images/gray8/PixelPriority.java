package hips.images.gray8;


class PixelPriority{
    private int pixel;
    private int priority;

    protected PixelPriority(int p, int prio){
        this.pixel = p;
        this.priority = prio;
    }

    protected int getPixel() {
        return pixel;
    }

    protected int getPriority() {
        return priority;
    }
}