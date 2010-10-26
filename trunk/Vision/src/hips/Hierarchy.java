package hips;

import hips.images.ImagePartitionable;
import hips.region.NewRegionListener;
import java.util.ArrayList;
import hips.pixel.PixelValue;
import hips.region.NewRegionEvent;
import hips.region.Region;
import javax.swing.event.EventListenerList;

/**
 * Esta clase proporciona métodos para la construcción de una jerarquía de
 * particiones para la imagen que se especifique. En una jerarquía de
 * particiones, la imagen particionada estará dividida en regiones de pixeles.
 * Las regiones puede pertenecer a cualquiera de las particiones
 * que pertenecen a la jerarquía y pueden ser sustituidas por el cojunto de
 * regiones que la forman en la partición del nivel jerarquico anterior, o por
 * la región que la contiene en el nivel jerarquico siguiente.
 */
public class Hierarchy{
    private ArrayList<Partition> partitions;
    private ImagePartitionable input;
    private int[] level;
    private int[] region;
    private int regionSize;
    private EventListenerList listenerList;

    /**
     * Inicializa una jerarquía de particiones.
     * @param input Imagen a partir de la cual se va a inicializar la jerarquía
     * de particiones.
     */
    public Hierarchy(ImagePartitionable input) {
        this.input = input;
        partitions = new ArrayList<Partition>();
        level = new int[input.getSize()];
        region = new int[input.getSize()];
    }

    /**
     * Añade una nueva partición a la jerarquía a partir de los parámetros que
     * se especifican.
     * @param alpha Parámetro de rango local.
     * @param omega Parámetro de rango global.
     * @param cindex Índice de conectividad.
     * @return Devuelve la posición que ocupa la partición creada dentro de la
     * jerarquía, o NULL en caso de que la partición no se haya podido añadir
     * debido a que los parámetros especificados no permitan situar la partición
     * dentro del orden jerárquico actual.
     */
    public Integer addPartition(PixelValue alpha, PixelValue omega, float cindex) {
        return addPartition(alpha, omega, cindex, null);
    }

    /**
     * Añade una nueva partición a la jerarquía a partir de los parámetros que
     * se especifican.
     * @param alpha Parámetro de rango local.
     * @param omega Parámetro de rango global.
     * @param cindex Índice de conectividad.
     * @param listener listener que escuchará los eventos relacionados con las
     * nuevas regiones creadas, cuando se llame al método makeRegions.
     * @return Devuelve la posición que ocupa la partición creada dentro de la
     * jerarquía, o NULL en caso de que la partición no se haya podido añadir
     * debido a que los parámetros especificados no permitan situar la partición
     * dentro del orden jerárquico actual.
     */
    public Integer addPartition(PixelValue alpha, PixelValue omega, float cindex, NewRegionListener listener) {
        Integer place = getPlaceForPartition(alpha, omega, cindex);
        if (place != null) {
            partitions.add(place, input.newPartition(alpha, omega, cindex));
            if (listener != null) {
                partitions.get(place).addNewRegionEventListener(listener);
            }
            partitions.get(place).makeRegions();
            if (partitions.size() == 1) {
                setPartition(0);
            } else {
                for (int i = 0; i < input.getSize(); i++) {
                    if (level[i] >= place) {
                        level[i]++;
                    }
                }
            }
        }
        return place;
    }

    /**
     * Examina la jerarquía actual para buscar que posición dentro de la misma
     * ocuparía una partición caracterizada por los parámetros especificados.
     * @param alpha Parámetro de rango local.
     * @param omega Parámetro de rango global.
     * @param cindex Índice de conectividad.
     * @return Devuelve la posición que ocuparía la partición dentro de la
     * jerarquía, o NULL en caso de que los parámetros especificados no permitan
     * situar una partición dentro del orden jerárquico actual.
     */
    public Integer getPlaceForPartition(PixelValue alpha, PixelValue omega, float cindex) {
        Partition p = input.newPartition(alpha, omega, cindex);
        for (int i = 0; i < partitions.size(); i++) {
            if (partitions.get(i).hierarchycallyGreaterThan(p)) {
                return i;
            }
            if (!partitions.get(i).hierarchycallyLowerThan(p)) {
                return null;
            }
        }
        return partitions.size();
    }

    /**
     * Obtiene la región a la que pertenece <i>pixel</i> en la jerarquía actual.
     */
    public Region getRegion(int pixel) {
        return partitions.get(level[pixel]).getRegionByPixel(pixel);
    }

    /**
     * Fuerza a que todos los píxeles de la imagen pertenezcan a las regiones
     * que conforma la partición del nivel <i>level</i> de la jerarquía.
     */
    public void setPartition(int level) {
        for (int i = 0; i < input.getSize(); i++) {
            this.level[i] = level;
            region[i] = partitions.get(level).lbl[i];
        }
        regionSize = partitions.get(level).references.length;
    }

    /**
     * Cambia la región a la que pertenece <i>pixel</i> por la que la contiene
     * en el nivel superior.
     */
    public void increaseLevel(int pixel) {
        if (level[pixel] == partitions.size() - 1) {
            return;
        }
        int newLevel = level[pixel] + 1;
        int newRegion = partitions.get(newLevel).lbl[pixel];
        Region newC = partitions.get(newLevel).getRegionByPixel(pixel);
        fireRegionEvent(new NewRegionEvent(this, newC));
        int comps = 0;
        for (int i = 0; i < newC.getSize(); i++) {
            int px = newC.getPixelPosition(i);
            if (level[px] != newLevel) {
                comps++;
                Region c = getRegion(px);
                for (int j = 0; j < c.getSize(); j++) {
                    level[c.getPixelPosition(j)] = newLevel;
                    region[c.getPixelPosition(j)] = newRegion;
                }
            }
        }
        regionSize -= (comps - 1);
    }

    /**
     * Cambia la región a la que pertenece <i>pixel</i> por la que la contiene
     * en el nivel inferior.
     */
    public void decreaseLevel(int pixel) {
        if (level[pixel] == 0) {
            return;
        }
        int newLevel = level[pixel] - 1;
        Region c = getRegion(pixel);
        int comps = 0;
        for (int i = 0; i < c.getSize(); i++) {
            int px = c.getPixelPosition(i);
            if (level[px] != newLevel) {
                comps++;
                int newRegion = partitions.get(newLevel).lbl[px];
                Region newC = partitions.get(newLevel).getRegionByPixel(px);
                fireRegionEvent(new NewRegionEvent(this, newC));
                for (int j = 0; j < newC.getSize(); j++) {
                    level[newC.getPixelPosition(j)] = newLevel;
                    region[newC.getPixelPosition(j)] = newRegion;
                }
            }
        }
        regionSize += (comps - 1);
    }

    /**
     * Obtiene la partición que está en nivel <i>level</i> de la jerarquía.
     */
    public Partition getPartition(int level) {
        return partitions.get(level);
    }

    /**
     * Obtiene el número de particiones de la jerarquía.
     */
    public int getSize() {
        return partitions.size();
    }

    /**
     * Obtiene la imagen de entrada.
     */
    public ImagePartitionable getInput() {
        return input;
    }

    /**
     * Obtiene el nivel al que pertenece el pixel especificado.
     */
    public int getLevelId(int pixel) {
        return level[pixel];
    }

    /**
     * Obtiene el número de regiones de la jerarquía.
     */
    public int getRegionsSize() {
        return regionSize;
    }

    /**
     * añade un listener que escuchará los eventos relacionados con los cambios
     * producidos en las regiones, debidos a la llamada a las funciones
     * <i>increaseLevel</i> y <i>decreaseLevel</i>.
     */
    public void addNewRegionEventListener(NewRegionListener listener) {
        listenerList.add(NewRegionListener.class, listener);
    }

    /**
     * borra el listener si fué añadido anteriormente.
     */
    public void removeNewRegionEventListener(NewRegionListener listener) {
        listenerList.remove(NewRegionListener.class, listener);
    }

    private void fireRegionEvent(NewRegionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == NewRegionListener.class) {
                ((NewRegionListener) listeners[i + 1]).newRegionCreated(evt);
            }
        }
    }
}
