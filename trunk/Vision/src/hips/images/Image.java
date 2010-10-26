package hips.images;

import vision.ImagePanel;
import hips.region.Region;
import ij.ImagePlus;

/**
 * Clase que almacena una instancia de la clase <i>ImagePlus</i> de ImageJ y
 * proporciona diversos métodos para el acceso y modificación de la imagen, sin
 * importar su tipo, apoyándose en el uso de la interface <i>PixelValue</i>.
 */
public abstract class Image<PixelValue extends hips.pixel.PixelValue, C extends Comparable> {
	public ImagePanel panel;
	protected java.awt.Image awtImage;
	protected String title;
	
    protected int slices;
    protected int size;
    protected int height;
    protected int width;
    private hips.pixel.PixelValue[] backup;
    private Region regionBackedUp;

    protected void initialize(ImagePlus impl){
        title = impl.getTitle();
        awtImage = impl.getImage();
    	panel = new ImagePanel(this);
    }
    
    public static Image getImage(ImagePlus imp){
        if (imp == null){
            return null;
        }
        if (!imp.isProcessor()){
            return null;
        }
        if (imp.getType() == ImagePlus.COLOR_RGB) return new hips.images.rgb.ImageRGB(imp);
        else if (imp.getType() == ImagePlus.GRAY8) return new hips.images.gray8.ImageGray8(imp);
        else if (imp.getType() == ImagePlus.GRAY32) return new hips.images.gray32.ImageGray32(imp);
        return null;
    }
    
    /**
     * Devuelve una nueva instancia de la clase <i>PixelValue</i>.
     * @param value Valor que será asignado a todos los canales del pixel.
     */
    public abstract PixelValue newPixelValue(C value);

    /**
     * Devuelve una nueva instancia de la clase <i>PixelValue</i>.
     */
    public abstract PixelValue newPixelValue();

    /**
     * Crea una nueva imagen de las mismas dimensiones, con el mismo número de
     * canales y con el mismo tipo de pixel.
     */
    public abstract Image newImage();

    /**
     * Crea una nueva imagen de las mismas dimensiones y con el mismo número de
     * canales, en formato RGB.
     */
    public abstract hips.images.rgb.ImageRGB newImageRGB();

    /**
     * Devuelve una instancia de <i>PixelValueRGB</i> con el valor de
     * <i>pvalue</i> en cada canal, transformado a RGB.
     */
    public abstract hips.images.rgb.PixelValueRGB toRGB(PixelValue pvalue);

    /**
     * Obtiene el valor del pixel situado en <i>index</i>.
     * @param index Posición del pixel dentro de la imagen. Se calcula como:
     * <i>x * ancho + y</i>.
     */
    public abstract PixelValue getPixelValue(int index);

    /**
     * Escribe el valor <i>pvalue</i> en el pixel situado en <i>index</i>.
     * @param index Posición del pixel dentro de la imagen. Se calcula como:
     * <i>x * ancho + y</i>.
     * @param pvalue Valor a asignar.
     */
    public abstract void putPixelValue(int index, PixelValue pvalue);

    /**
     * Obtiene el número <i>cero</i> en el formato correspondiente a la imagen.
     * @return Devuelve una instancia de <i>Integer</i> si la imagen es de tipo
     * entera o una instancia de <i>Float</i> si la imagen es de tipo flotante.
     */
    public abstract C getZero();

    /**
     * Devuelve una instancia de <i>PixelValue</i> con el valor medio que los
     * píxeles contenidos en la región <i>r</i> toman en la imagen.
     */
    public abstract PixelValue getMeanValue(Region r);

    
    public PixelValue getPixelValue(int x, int y){
    	if (x >= width || y >= height){
    		return null;
    	}
    	return getPixelValue(y * width + x);
    }
    
    public void putPixelValue(int x, int y, PixelValue pvalue){
    	if (x >= width || y >= height){
    		return;
    	}
    	putPixelValue(y * width + x, pvalue);
    }

    /**
     * Pinta los píxeles de la región <i>r</i> con el valor que se especifique.
     * Realiza una copia (backup) del valor que tiene cada pixel antes de realizar el
     * pintado, para que posteriormente la región pueda ser restaurada mediante
     * una llamada al método <i>restoreBackup</i>. Sólo se permite el
     * almacenamiento de un backup, por lo que si se llama dos veces seguidas a
     * este método, el primer backup se perderá.
     * @param r Región que contiene los píxeles que van a ser pintados en la
     * imagen.
     * @param pvalue Valor con el que va a ser pintada la región de la imagen.
     */
    public void paintRegionAndBackup(Region r, PixelValue pvalue){
        regionBackedUp = r;
        backup = new hips.pixel.PixelValue[r.getSize()];
        for (int i = 0; i < r.getSize(); i++) {
            backup[i] = getPixelValue(r.getPixelPosition(i));
            putPixelValue(r.getPixelPosition(i), pvalue);
        }
        panel.repaint();
    }

    /**
     * Recupera la copia de los valores de los píxeles de la región guardada
     * en la últimada llamada al método <i>paintRegionAndBackup</i>, y pinta
     * la región de la imagen con dichos valores.
     */
    public void restoreBackup(){
        if (regionBackedUp != null){
            for (int i = 0; i < backup.length; i++) {
                putPixelValue(regionBackedUp.getPixelPosition(i), (PixelValue) backup[i]);
            }
            panel.repaint();
        }
    }

    /**
     * Pinta los píxeles de la región <i>r</i> con el valor que se especifique.
     * @param r Región que contiene los píxeles que van a ser pintados en la
     * imagen.
     * @param pvalue Valor con el que va a ser pintada la región de la imagen.
     */
    public void paintRegion(Region r, PixelValue pvalue) {
        for (int i = 0; i < r.getSize(); i++) {
            putPixelValue(r.getPixelPosition(i), pvalue);
        }
        panel.repaint();
    }

    /**
     * Pinta la imagen completa con el valor cero en cada uno de los píxeles.
     */
    public void clear() {
        PixelValue p = newPixelValue(getZero());
        for (int i = 0; i < size; i++) {
            putPixelValue(i, p);
        }
        panel.repaint();
    }

    /**
     * Devuelve el número total de píxeles de la imagen.
     */
    public int getSize() {
        return size;
    }

    /**
     * Devuelve el ancho de la imagen, en píxeles.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Devuelve el alto de la imagen, en píxeles.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Devuelve la instancia de la clase <i>ImagePlus</i> a partir de la cual
     * se creó la imagen.
     */
    
    public String getTitle(){
    	return title;
    }
    
    public void setTitle(String title){
    	this.title = title;
    }
    
    public java.awt.Image getAwtImage(){
    	return awtImage;
    }

    /**
     * Devuelve el número de canales de la imagen.
     */
    public int getSlices() {
        return slices;
    }
};
