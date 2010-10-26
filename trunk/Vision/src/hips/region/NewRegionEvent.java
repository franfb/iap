package hips.region;

import java.util.EventObject;

/**
 * Encapsula la información necesaria al producirse un evento <i>NewRegion</i>.
 */
public class NewRegionEvent extends EventObject {

    private Region region;

    public NewRegionEvent(Object source, Region region) {
        super(source);
        this.region = region;
    }

    /**
     * Obtiene la región que fue creada.
     */
    public Region getRegion() {
        return region;
    }
}
