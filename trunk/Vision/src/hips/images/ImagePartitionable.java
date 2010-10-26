package hips.images;

import hips.tools.Neighborhood;
import hips.Partition;
import ij.ImagePlus;

/**
 * Clase abstracta que hereda de la clase <i>hips.images.Image</i>, y proporciona una
 * serie de mÃ©todos adicionales necesarios para que la imagen pueda ser
 * utilizada por la clase <i>hips.Partition</i> para posibilitar su segmentación.
 */
public abstract class ImagePartitionable<PixelValue extends hips.pixel.PixelValue, C extends Comparable> extends Image<PixelValue, C> {

    protected PixelValue maxValue = null;
    protected PixelValue minValue = null;
    protected PixelValue maxRange = null;

    /**
     * Devuelve una nueva instancia de la clase <i>Partition</i> mediante la
     * cual la imagen podrÃ¡ ser segmentada. La particiÃ³n que se cree estarÃ¡
     * caracterizada por los parÃ¡metros que se especifican en la llamada.
     * @param alpha ParÃ¡metro de rango local.
     * @param omega ParÃ¡metro de rango global.
     * @param cindex Ã�ndice de conectividad.
     */
    public abstract Partition newPartition(PixelValue alpha, PixelValue omega, float ci);

    /**
     * Devuelve una nueva instancia de la clase <i>Partition</i> mediante la
     * cual la imagen podrÃ¡ ser segmentada. La particiÃ³n que se cree estarÃ¡
     * caracterizada por unos parÃ¡metros predefinidos.
     */
    public abstract Partition newPartition();

    /**
     * MÃ©todo estÃ¡tico que identifica el tipo de imagen almacenada en el objeto
     * <i>ImagePlus</i> suministrado, y si corresponde a una imagen que pueda
     * ser segmentada por la clase <i>hips.Partition</i>, devuelve la instancia
     * de la clase <i>ImagePartitionable</i> que corresponda.
     * @return Devuelve una instancia de alguna clase hija de
     * <i>ImagePartitionable</i>, o <i>NULL</i> si la imagen en <i>imp</i> no
     * puede ser segmentada.
     */
    public static ImagePartitionable getImage(ImagePlus imp){
        if (imp == null){
            return null;
        }
        if (!imp.isProcessor()){
            return null;
        }
        if (imp.getStackSize() == 1){
            if (imp.getType() == ImagePlus.COLOR_RGB) return new hips.images.gray8.ImageGray8(imp);
            else if (imp.getType() == ImagePlus.GRAY8) return new hips.images.gray8.ImageGray8(imp);
            else if (imp.getType() == ImagePlus.GRAY32) return new hips.images.gray32.ImageGray32(imp);
            return null;
        }
        if (imp.getStackSize() > 1){
            if (imp.getType() == ImagePlus.GRAY8) return new hips.images.gray8.ImageGray8(imp);
            else if (imp.getType() == ImagePlus.GRAY32) return new hips.images.gray32.ImageGray32(imp);
            return null;
        }
        return null;
    }

    /**
     * Obtiene el rango mÃ¡ximo entre pÃ­xeles consecutivos en toda la imagen.
     */
    public PixelValue getMaxRange() {
        if (maxRange == null){
            calculateMaxRange();
        }
        return (PixelValue) maxRange.copy();
    }

    /**
     * Obtiene el valor mÃ¡ximo de pixel que hay en la imagen.
     */
    public PixelValue getMaxValue() {
        if (maxValue == null){
            calculateBounds();
        }
        return (PixelValue) maxValue.copy();
    }

    /**
     * Obtiene el valor mÃ­nimo de pixel que hay en la imagen.
     */
    public PixelValue getMinValue() {
        if (minValue == null){
            calculateBounds();
        }
        return (PixelValue) minValue.copy();
    }

    private void calculateBounds() {
        int n = width * height;
        maxValue = getPixelValue(0);
        minValue = getPixelValue(0);
        for (int i = 0; i < n; i++) {
            PixelValue p = getPixelValue(i);
            maxValue.setGreater(p);
            minValue.setLower(p);
        }
    }

    private void calculateMaxRange() {
        int n = width * height;
        Neighborhood nbh = new Neighborhood(width, height);
        maxRange = newPixelValue(getZero());
        int q;
        for (int i = 0; i < n; i++) {
            PixelValue p = getPixelValue(i);
            q = nbh.getNeighbor(i, 1);
            if (q != -1) {
                maxRange.setGreater(p.range(getPixelValue(q)));
            }

            q = nbh.getNeighbor(i, 3);
            if (q != -1) {
                maxRange.setGreater(p.range(getPixelValue(q)));
            }
        }
    }
};
