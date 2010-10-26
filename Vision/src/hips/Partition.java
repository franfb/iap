package hips;

import hips.images.ImagePartitionable;
import hips.pixel.PixelValue;
import hips.region.NewRegionEvent;
import hips.region.NewRegionListener;
import hips.region.Region;
import javax.swing.event.EventListenerList;

/**
 * Esta clase almacena la partición de una imagen creada a partir del
 * algoritmo de segmentación utilizado en la libreria HIPS. Este algoritmo
 * está basado en la conectividad reestringida por parámetros. Los parámetros
 * utilizados son: el parámetro de rango local, el parámetro de rango global, y
 * el índice de conectividad.
 */
public abstract class Partition<Img extends ImagePartitionable, PValue extends PixelValue> {

    protected Img image;
    protected int[] references;
    protected int[] pixels;
    protected int[] lbl;
    protected EventListenerList listenerList;
    private PValue alpha;
    private PValue omega;
    private float cindex;

    /**
     * Método que ejecuta el algoritmo de segmentación y crea las regiones
     * correspondientes a la partición.
     */
    public abstract void makeRegions();

    /**
     * Inicializa una instancia de la clase, indicando la imagen de entrada y
     * los parámetros que se van a utilizar para la segmentación.
     * @param input Imagen que va a ser segmentada.
     * @param alpha Parámetro de rango local.
     * @param omega Parámetro de rango global.
     * @param ci Índice de conectividad.
     */
    public Partition(Img input, PValue alpha, PValue omega, float ci) {
        super();
        this.image = input;
        this.alpha = alpha;
        this.omega = omega;
        this.cindex = ci;
        references = null;
        pixels = null;
        lbl = null;
        listenerList = new EventListenerList();
    }

    /**
     * Inicializa una instancia de la clase, indicando la imagen de entrada.
     * A la hora de segmentar la imagen mediante la llamada a la función
     * makeRegions, se utilizarán unos parámetros predefinidos.
     * @param input Imagen que va a ser segmentada.
     */
    public Partition(Img input) {
        super();
        this.image = input;
        alpha = (PValue) input.getMaxRange();
        omega = (PValue) input.getMaxRange();
        alpha.div(3);
        omega.div(2);
        cindex = 0.0f;
        references = null;
        pixels = null;
        lbl = null;
        listenerList = new EventListenerList();
    }

    /**
     * Obtiene la región a la que pertenece el pixel.
     * @param pixel Posición del pixel dentro de la imagen. Se calcula como:
     * <i>x * ancho + y</i>.
     */
    public Region getRegionByPixel(int pixel) {
        int regionIndex = lbl[pixel];
        int base = references[regionIndex];
        int size = 0;
        if (regionIndex < getRegionSize() - 1) {
            size = references[regionIndex + 1] - references[regionIndex];
        } else {
            size = getImage().getSize() - references[regionIndex];
        }
        return new Region(pixels, base, size, regionIndex);
    }

    /**
     * Obtiene la región a partir de su etiqueta.
     * @param label Etiqueta que le corresponde a la región en la partición.
     */
    public Region getRegionByLabel(int label) {
        int base = references[label];
        int size;
        if (label < references.length - 1) {
            size = references[label + 1] - references[label];
        } else {
            size = image.getSize() - references[label];
        }
        return new Region(pixels, base, size, label);
    }

    /**
     * Comprueba si la partición es jerárquicamente inferior a la partición que
     * se le pasa por parámetro. Si esto ocurre, cada una de las regiones
     * que la conforma están contenidas dentro de una región de la otra
     * partición. La partición será jerárquicamente inferior si se cumplen
     * las siguientes condiciones: <p>
     * - el parámetro de rango local es menor o igual en todos los canales de
     * la imagen. <br>
     * - el parámetro de rango global es menor o igual en todos los canales de
     * la imagen. <br>
     * - el índice de conectividad es mayor o igual.
     * @return Devuelve <i>true</i> si la partición es jerárquicamente inferior,
     * y <i>false</i> en caso contrario.
     */
    public boolean hierarchycallyLowerThan(Partition other) {
        if (!alpha.isLowerOrEqual(other.alpha)) {
            return false;
        }
        if (!omega.isLowerOrEqual(other.omega)) {
            return false;
        }
        if (cindex < other.cindex) {
            return false;
        }
        if (alpha.isEqual(other.alpha) && omega.isEqual(other.omega) && cindex == other.cindex) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si la partición es jerárquicamente superior a la partición que
     * se le pasa por parámetro. La partición será jerárquicamente superior si
     * se cumplen las siguientes condiciones: <p>
     * - el parámetro de rango local es mayor o igual en todos los canales de
     * la imagen. <br>
     * - el parámetro de rango global es mayor o igual en todos los canales de
     * la imagen. <br>
     * - el índice de conectividad es menor o igual.
     * @return Devuelve <i>true</i> si la partición es jerárquicamente superior,
     * y <i>false</i> en caso contrario.
     */
    public boolean hierarchycallyGreaterThan(Partition other) {
        if (!alpha.isGreaterOrEqual(other.alpha)) {
            return false;
        }
        if (!omega.isGreaterOrEqual(other.omega)) {
            return false;
        }
        if (cindex > other.cindex) {
            return false;
        }
        if (alpha.isEqual(other.alpha) && omega.isEqual(other.omega) && cindex == other.cindex) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si la partición es jerárquicamente del mismo orden que la
     * partición que se le pasa por parámetro. Las particines serán
     * jerárquicamente iguales si se cumplen las siguientes condiciones: <p>
     * - los parámetros de rango local son iguales en todos los canales. <br>
     * - los parámetros de rango global son iguales en todos los canales. <br>
     * - los índices de conectividad son iguales.
     * @return Devuelve <i>true</i> si las particiones son jerárquicamente
     * iguales, y <i>false</i> en caso contrario.
     */
    public boolean hierarchycallyEqualTo(Partition other) {
        if (alpha.isEqual(other.alpha) && omega.isEqual(other.omega) && cindex == other.cindex) {
            return true;
        }
        return false;
    }

    private ImagePartitionable getWorkingImage(ImagePartitionable img, float[] transforming) {
        ImagePartitionable working = (ImagePartitionable) img.newImage();
        for (int i = 0; i < img.getSize(); i++) {
            PixelValue p = img.getPixelValue(i);
            PixelValue pf = img.newPixelValue(img.getZero());
            for (int j = 0; j < img.getSlices(); j++) {
                pf.setValueAsFloat(p.getValueAsFloat(j) * transforming[j], j);
            }
            working.putPixelValue(i, pf);
        }
        return working;
    }

    /**
     * Transforma la imagen y los parámetros parámetros alpha y
     * omega, cuando el vector alpha no contiene los mismos valores en todos
     * los canales.
     * @return Devuelve los objetos transformados.
     */
    protected Object[] getWorkingData() {
        Object[] returned = new Object[3];
        if (alpha.maxValue().compareTo(alpha.minValue()) == 0){
            returned[0] = alpha.maxValue();
            returned[1] = image;
            returned[2] = omega;
            return returned;
        }
        float workingAlpha;
        Comparable maxValue = alpha.maxValue();
        if (maxValue instanceof Integer){
            workingAlpha = ((Integer)maxValue).floatValue();
        }
        else{
            workingAlpha = (Float)maxValue;
        }
        returned[0] = maxValue;
        float[] transforming = new float[image.getSlices()];
        PixelValue omg = image.newPixelValue();
        for (int i = 0; i < image.getSlices(); i++) {
            transforming[i] = workingAlpha / getAlpha().getValueAsFloat(i);
            omg.setValueAsFloat(getOmega().getValueAsFloat(i) * transforming[i], i);
        }
        Img working = (Img) getWorkingImage(image, transforming);
        returned[1] = working;
        returned[2] = omg;
        return returned;
    }

    /**
     * Obtiene la imagen particionada.
     */
    public Img getImage() {
        return image;
    }

    /**
     * Obtiene el parámetro de rango local.
     */
    public PValue getAlpha() {
        return alpha;
    }

    /**
     * Obtiene el parámetro de rango global.
     */
    public PValue getOmega() {
        return omega;
    }

    /**
     * Obtiene el índice de conectividad.
     */
    public float getCindex() {
        return cindex;
    }

    /**
     * Asigna el parámetro de rango local.
     */
    public void setAlpha(PValue alpha) {
        if (lbl == null){
            this.alpha = alpha;
        }
    }

    /**
     * Asigna el parámetro de rango global.
     */
    public void setOmega(PValue omega) {
        if (lbl == null){
            this.omega = omega;
        }
    }

    /**
     * Asigna el índice de conectividad.
     */
    public void setCindex(float cindex) {
        if (lbl == null){
            this.cindex = cindex;
        }
    }

    /**
     * Obtiene el número de regiones de la partcición.
     */
    public int getRegionSize() {
        return references.length;
    }


    /**
     * añade un listener que escuchará los eventos relacionados con las
     * nuevas regiones creadas, cuando se llame al método makeRegions.
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

    protected void fireRegionEvent(NewRegionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == NewRegionListener.class) {
                ((NewRegionListener) listeners[i + 1]).newRegionCreated(evt);
            }
        }
    }
}
