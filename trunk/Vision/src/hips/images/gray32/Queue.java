package hips.images.gray32;


class Queue {
    private ListFloat vector[];
    private float width;
    private int size;
    private int top;
    private int bottom;

    protected Queue(int n, float range){
        vector = new ListFloat[n + 1]; // IMPORTANTE ESE +1 ?
        width = range / n;
        for (int i = 0; i < n; i++)
            vector[i] = new ListFloat(5000);
        size = 0;
        top = Integer.MIN_VALUE;
        bottom = Integer.MAX_VALUE;
    }

    protected boolean isEmpty(){
        return size == 0;
    }

    protected void add(int pixel, float fprio){
        int prio = (int) (fprio / width);
        vector[prio].add(pixel, fprio);
        size++;
        if (prio > top) top = prio;
        if (prio < bottom) bottom = prio;
    }

    protected PixelPriority remove(float fprio){
        int prio = (int) (fprio / width);
        size--;
        PixelPriority p = null;
        if (prio == bottom){
            p = vector[prio].removePrioOrMin(fprio);
        }
        else if (prio < bottom){
            p = vector[bottom].removeMin();
        }
        else if (prio > top){
            p = vector[top].removeMax();
        }
        else{
            if (! vector[prio].isEmpty()){
                p = vector[prio].removePrio(fprio);
            }
            if (p == null){
                while (vector[--prio].isEmpty()){}
                p = vector[prio].removeMax();
            }
        }
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
        PixelPriority p = vector[bottom].removeDontCare();
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