package hips;

import hips.images.Image;
import hips.pixel.PixelValue;
import hips.region.NewRegionEvent;
import hips.region.NewRegionListener;
import hips.region.Region;
import javax.swing.event.EventListenerList;

/**
 * Esta clase almacena la particiÃ³n de una imagen creada a partir del
 * algoritmo de segmentaciÃ³n utilizado en la libreria HIPS. Este algoritmo
 * estÃ¡ basado en la conectividad reestringida por parÃ¡metros. Los parÃ¡metros
 * utilizados son: el parÃ¡metro de rango local, el parÃ¡metro de rango global, y
 * el Ã­ndice de conectividad.
 */
public abstract class Partition<Img extends Image, PValue extends PixelValue> {

    protected Img image;
    protected int[] references;
    protected int[] pixels;
    protected int[] lbl;
    protected EventListenerList listenerList;
    private PValue alpha;
    private PValue omega;
    private float cindex;

    /**
     * MÃ©todo que ejecuta el algoritmo de segmentaciÃ³n y crea las regiones
     * correspondientes a la particiÃ³n.
     */
    public abstract void makeRegions();

    /**
     * Inicializa una instancia de la clase, indicando la imagen de entrada y
     * los parÃ¡metros que se van a utilizar para la segmentaciÃ³n.
     * @param input Imagen que va a ser segmentada.
     * @param alpha ParÃ¡metro de rango local.
     * @param omega ParÃ¡metro de rango global.
     * @param ci Ã�ndice de conectividad.
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
     * A la hora de segmentar la imagen mediante la llamada a la funciÃ³n
     * makeRegions, se utilizarÃ¡n unos parÃ¡metros predefinidos.
     * @param input Imagen que va a ser segmentada.
     */
    public Partition(Img input) {
        super();
        this.image = input;
        alpha = (PValue) input.getMaxRange();
        omega = (PValue) input.getMaxRange();
        alpha.div(2);
        omega.div(1.5f);
        cindex = 0.0f;
        references = null;
        pixels = null;
        lbl = null;
        listenerList = new EventListenerList();
    }

    /**
     * Obtiene la regiÃ³n a la que pertenece el pixel.
     * @param pixel PosiciÃ³n del pixel dentro de la imagen. Se calcula como:
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
     * Obtiene la regiÃ³n a partir de su etiqueta.
     * @param label Etiqueta que le corresponde a la regiÃ³n en la particiÃ³n.
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
     * Comprueba si la particiÃ³n es jerÃ¡rquicamente inferior a la particiÃ³n que
     * se le pasa por parÃ¡metro. Si esto ocurre, cada una de las regiones
     * que la conforma estÃ¡n contenidas dentro de una regiÃ³n de la otra
     * particiÃ³n. La particiÃ³n serÃ¡ jerÃ¡rquicamente inferior si se cumplen
     * las siguientes condiciones: <p>
     * - el parÃ¡metro de rango local es menor o igual en todos los canales de
     * la imagen. <br>
     * - el parÃ¡metro de rango global es menor o igual en todos los canales de
     * la imagen. <br>
     * - el Ã­ndice de conectividad es mayor o igual.
     * @return Devuelve <i>true</i> si la particiÃ³n es jerÃ¡rquicamente inferior,
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
     * Comprueba si la particiÃ³n es jerÃ¡rquicamente superior a la particiÃ³n que
     * se le pasa por parÃ¡metro. La particiÃ³n serÃ¡ jerÃ¡rquicamente superior si
     * se cumplen las siguientes condiciones: <p>
     * - el parÃ¡metro de rango local es mayor o igual en todos los canales de
     * la imagen. <br>
     * - el parÃ¡metro de rango global es mayor o igual en todos los canales de
     * la imagen. <br>
     * - el Ã­ndice de conectividad es menor o igual.
     * @return Devuelve <i>true</i> si la particiÃ³n es jerÃ¡rquicamente superior,
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
     * Comprueba si la particiÃ³n es jerÃ¡rquicamente del mismo orden que la
     * particiÃ³n que se le pasa por parÃ¡metro. Las particines serÃ¡n
     * jerÃ¡rquicamente iguales si se cumplen las siguientes condiciones: <p>
     * - los parÃ¡metros de rango local son iguales en todos los canales. <br>
     * - los parÃ¡metros de rango global son iguales en todos los canales. <br>
     * - los Ã­ndices de conectividad son iguales.
     * @return Devuelve <i>true</i> si las particiones son jerÃ¡rquicamente
     * iguales, y <i>false</i> en caso contrario.
     */
    public boolean hierarchycallyEqualTo(Partition other) {
        if (alpha.isEqual(other.alpha) && omega.isEqual(other.omega) && cindex == other.cindex) {
            return true;
        }
        return false;
    }

    private Image getWorkingImage(Image img, float[] transforming) {
        Image working = img.newImage();
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
     * Transforma la imagen y los parÃ¡metros parÃ¡metros alpha y
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
     * Obtiene el parÃ¡metro de rango local.
     */
    public PValue getAlpha() {
        return alpha;
    }

    /**
     * Obtiene el parÃ¡metro de rango global.
     */
    public PValue getOmega() {
        return omega;
    }

    /**
     * Obtiene el Ã­ndice de conectividad.
     */
    public float getCindex() {
        return cindex;
    }

    /**
     * Asigna el parÃ¡metro de rango local.
     */
    public void setAlpha(PValue alpha) {
        if (lbl == null){
            this.alpha = alpha;
        }
    }

    /**
     * Asigna el parÃ¡metro de rango global.
     */
    public void setOmega(PValue omega) {
        if (lbl == null){
            this.omega = omega;
        }
    }

    /**
     * Asigna el Ã­ndice de conectividad.
     */
    public void setCindex(float cindex) {
        if (lbl == null){
            this.cindex = cindex;
        }
    }

    /**
     * Obtiene el nÃºmero de regiones de la partciciÃ³n.
     */
    public int getRegionSize() {
        return references.length;
    }


    /**
     * aÃ±ade un listener que escucharÃ¡ los eventos relacionados con las
     * nuevas regiones creadas, cuando se llame al mÃ©todo makeRegions.
     */
    public void addNewRegionEventListener(NewRegionListener listener) {
        listenerList.add(NewRegionListener.class, listener);
    }

    /**
     * borra el listener si fuÃ© aÃ±adido anteriormente.
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
