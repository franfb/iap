package hips.tools;

public class ListPixels {
    private int[] pixels;
    private int size;
    private int visited;

    public ListPixels(int n){
        pixels = new int[n];
        size = 0;
        visited = 0;
    }

    public int getSize(){
        return size;
    }

    public int getPixel(int index){
        return pixels[index];
    }

    public void add(int pixel){
        try{
            pixels[size++] = pixel;
        }
        catch(ArrayIndexOutOfBoundsException e){
            int[] newpixels = new int[size + size/2];
            System.arraycopy(pixels, 0, newpixels, 0, pixels.length);
            newpixels[size - 1] = pixel;
            pixels = newpixels;
        }
    }

    public boolean isEmpty(){
        if (size == 0) return true;
        return false;
    }

    public int remove(){
        return pixels[--size];
    }
    
    public boolean hasNew(){
        if (visited < size) return true;
        return false;
    }

    public int getNew(){
        return pixels[visited++];
    }

    public void clear(){
        size = 0;
        visited = 0;
    }

    public void clearIterator(){
        visited = 0;
    }
}
