package hips.images.gray32;


class PixelPriority{
    private int pixel;
    private float priority;

    public PixelPriority(int p, float prio){
        this.pixel = p;
        this.priority = prio;
    }

    protected int getPixel() {
        return pixel;
    }

    protected float getPriority() {
        return priority;
    }
}