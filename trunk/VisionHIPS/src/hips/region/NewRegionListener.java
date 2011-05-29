package hips.region;

import java.util.EventListener;

/**
 * Interface que se ha de implementar si se quieren escuchar eventos del tipo
 * <i>NewRegion</i>.
 */
public interface NewRegionListener extends EventListener {

    /**
     * Este método se ejecuta cada vez que una nueva región ha sido creada
     * en el proceso de segmentación de una imagen, en la llamada al método
     * <i>makeRegions</i> de la clase <i>hips.Partition</i>.
     */
    public void newRegionCreated(NewRegionEvent evt);
}
