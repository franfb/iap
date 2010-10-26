package hips.region;

/**
 * Clase que contiene la lista de píxeles que forman parte de una región
 * perteneciente a la partición de una imagen, resultado de la segmentación
 * realizada por la clase <i>hips.Partition</i>.
 */
public class Region {

    private int base;
    private int size;
    private int[] pixels;
    private int label;

    /**
     * Construye una instancia de la clase <i>Region</i> a partir de los
     * parámetros especificados.
     * @param pixels Vector de pixels en el cual una porción del mismo almacena
     * todos los píxeles que pertenecen a la región, de manera consecutiva.
     * @param base Índice al vector que marca el comienzo de la parte en la que
     * se encuentran los píxeles de esta región.
     * @param size Número de píxeles de esta región.
     * @param label Etiqueta que se le asigna a esta región.
     */
    public Region(int[] pixels, int base, int size, int label) {
        this.base = base;
        this.size = size;
        this.pixels = pixels;
        this.label = label;
    }

    /**
     * Devuelve <i>true</i> si la región <i>other</i> es idéntica, y
     * <i>false</i> en caso contrario.
     */
    public boolean sameAs(Region other) {
        if (pixels == other.pixels && base == other.base) {
            return true;
        }
        return false;
    }

    /**
     * Obtiene la posición del píxel cuyo índice es <i>index</i> en la región.
     */
    public int getPixelPosition(int index) {
        return pixels[base + index];
    }

    /**
     * Obtiene el número de píxeles de la región.
     */
    public int getSize() {
        return size;
    }

    /**
     * Obtiene la etiqueta de la región.
     */
    public int getLabel() {
        return label;
    }
}