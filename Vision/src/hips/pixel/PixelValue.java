package hips.pixel;


/**
 * Clase que almacena el valor de un pÃ­xel de una imagen y proporciona una
 * serie de mÃ©todos necesarios en el algoritmo de segmentaciÃ³n utilizado en
 * la clase <i>hips.Partition</i>.
 */
public abstract class PixelValue<
        PValue extends PixelValue,
        C extends Comparable
    >{

    /**
     * En aquellos canales en los que el valor sea menor en <i>pvalue</i>, copia
     * su valor.
     */
    public abstract void setLower(PValue pvalue);

    /**
     * En aquellos canales en los que el valor sea mayor en <i>pvalue</i>, copia
     * su valor.
     */
    public abstract void setGreater(PValue pixel);

    /**
     * Devuelve <i>true</i> si en todos los canales el valor es menor o igual
     * que el correspondiente en <i>pvalue</i>. En caso contrario devuelve
     * <i>false</i>.
     */
    public abstract boolean isLowerOrEqual(PValue pvalue);

    /**
     * Devuelve <i>true</i> si en todos los canales el valor es mayor o igual
     * que el correspondiente en <i>pvalue</i>. En caso contrario devuelve
     * <i>false</i>.
     */
    public abstract boolean isGreaterOrEqual(PValue pvalue);

    /**
     * Devuelve <i>true</i> si en todos los canales el valor es igual
     * que el correspondiente en <i>pvalue</i>. En caso contrario devuelve
     * <i>false</i>.
     */
    public abstract boolean isEqual(PValue pvalue);

    /**
     * Devuelve una instancia de <i>PixelValue</i> con la diferencia en cada
     * canal entre este pÃ­xel y el almacenado en <i>pvalue</i>.
     */
    public abstract PValue range(PValue pvalue);

    /**
     * Devuelve una representaciÃ³n textual del valor del pÃ­xel en el canal
     * especificado.
     * @param slice Ã�ndice del canal especificado.
     */
    public abstract String getString(int slice);
    
    /**
     * Devuelve una representaciÃ³n textual del valor del pÃ­xel en todos los
     * canales.
     */
    public abstract String getString();

    /**
     * Asigna el valor <i>value</i> en el canal <i>slice</i>.
     */
    public abstract void setValue(C value, int slice);

    /**
     * Asigna el valor flotante <i>value</i> en el canal <i>slice</i>.
     */
    public abstract void setValueAsFloat(float value, int slice);

    /**
     * Obtiene el valor del pÃ­xel en el canal <i>slice</i>.
     */
    public abstract C getValue(int slice);

    /**
     * Obtiene en un flotante el valor del pÃ­xel en el canal <i>slice</i>.
     */
    public abstract float getValueAsFloat(int slice);

    /**
     * Obtiene el valor mÃ­nimo de todos los canales.
     */
    public abstract C minValue();

    /**
     * Obtiene el valor mÃ¡ximo de todos los canales.
     */
    public abstract C maxValue();

    /**
     * Devuelve el nÃºmero total de canales del pÃ­xel.
     */
    public abstract int getSlices();

    /**
     * Suma a cada canal de este pÃ­xel lo almacenado en cada canal de
     * <i>pvalue</i>.
    */
    public abstract void add(PValue pvalue);
    
    /**
     * Suma a cada canal de este pÃ­xel lo almacenado en <i>value</i>.
    */
    public abstract void add(float value);

    /**
     * Resta a cada canal de este pÃ­xel lo almacenado en cada canal de
     * <i>pvalue</i>.
    */
    public abstract void sub(PValue pvalue);

    /**
     * Resta a cada canal de este pÃ­xel lo almacenado en <i>value</i>.
    */
    public abstract void sub(float value);

    /**
     * Divide cada canal de este pÃ­xel por lo almacenado en <i>value</i>.
    */
    public abstract void div(float value);
    
    /**
     * Multiplica cada canal de este pÃ­xel por lo almacenado en <i>value</i>.
    */
    public abstract void mult(float value);

    /**
     * Devuelve una nueva instancia de <i>PixelValue</i> con los mismos valores
     * de pÃ­xel en todos los canales.
     */
    public abstract PValue copy();
}