package procesos;

import vision.ImageInfo;
import dialogs.LogaritmicoExponencialDialog;

public class LogaritmicoExponencial {

	public static void run() {
		
	}
	
	public static byte[] generarLut(LogaritmicoExponencialDialog dialog) {
		byte[] lut = new byte[ImageInfo.NIVELES];
		
		if (dialog.rbTipo1.isSelected()) {
			for (int i = 0; i < lut.length; i++) {
				double t = (lut.length - 1) / Math.log(lut.length);
				t *= Math.log(1 + i);
				lut[i] = (byte)Math.rint(t);
			}
		}
		else if (dialog.rbTipo2.isSelected()) {
			
		}
		else { // rbTipo3.isSelected
			
		}
		
		return lut;
	}
}
