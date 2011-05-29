package hips.tools;

public class Neighborhood{
    public static int NEIGHBORS = 4;
    private int n;
    private int width;

    public Neighborhood(int width, int height){
        this.n = height * width;
        this.width = width;
    }

    public int getNeighbor(int p, int pos){
          switch (pos){
               case 0:{
                    if (p % width > 0)
                         return p - 1;
                    else
                         return -1;
               }
               case 1:{
                    if (p % width < width - 1)
                         return p + 1;
                    else
                         return -1;
               }
               case 2:{
                    if (p >= width)
                         return p - width;
                    else
                         return -1;
               }
               case 3:{
                    if (p + width  < n)
                         return p + width;
                    else
                         return -1;
               }
          }
          return -1;
     }
}
