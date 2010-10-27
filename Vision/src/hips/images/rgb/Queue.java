package hips.images.rgb;

import hips.tools.ListPixels;

class Queue {
    private ListPixels vector[];
    private int size;
    private int top;
    private int bottom;

    protected Queue(int n){
        vector = new ListPixels[n];
        for (int i = 0; i < n; i++)
            vector[i] = new ListPixels(5000);
        size = 0;
        top = Integer.MIN_VALUE;
        bottom = Integer.MAX_VALUE;
    }

    protected void initialize(){
        
    }

    protected boolean isEmpty(){
        return size == 0;
    }

    protected void add(int pixel, int prio){
        vector[prio].add(pixel);
        size++;
        if (prio > top) top = prio;
        if (prio < bottom) bottom = prio;
    }

    protected PixelPriority remove(int prio){
        size--;
        if (prio >= top)
            prio = top;
        else if (prio > bottom)
            while (vector[--prio].isEmpty()){}
        else
            prio = bottom;
        PixelPriority p = new PixelPriority(vector[prio].remove(), prio);
        if (vector[top].isEmpty()){
            if (size == 0)
                top = Integer.MIN_VALUE;
            else
                while (vector[--top].isEmpty()){}
        }
        if (vector[bottom].isEmpty()){
            if (size == 0)
                bottom = Integer.MAX_VALUE;
            else
                while (vector[++bottom].isEmpty()){}
        }
        return p;
    }

    protected PixelPriority remove(){
        size--;
        PixelPriority p = new PixelPriority(vector[bottom].remove(), bottom);
        if (vector[bottom].isEmpty()){
            if (size == 0){
                bottom = Integer.MAX_VALUE;
                top = Integer.MIN_VALUE;
            }
            else
                while (vector[++bottom].isEmpty()){}
        }
        return p;
    }
}