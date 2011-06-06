package procesos;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;

public class Lut {
	public static BufferedImage aplicarLut(BufferedImage im, BufferedImage newIm, byte[] lut) {
		ByteLookupTable blut = new ByteLookupTable(0, lut);
        LookupOp lop = new LookupOp(blut, null);
        newIm = lop.filter(im, newIm);

        return newIm;
	}
}
