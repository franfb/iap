package procesos;

import dialogs.LinealTramosDialog;
import vision.DisplayTramos;
import vision.ImageInfo;

public class LinealTramos {
	public static void run() {
		int[] tramosLineal = new int[ImageInfo.NIVELES];
		// Construimos un tramo lineal
		for (int i = 0; i < tramosLineal.length; i++) {
			tramosLineal[i] = i;
		}
		final DisplayTramos display = new DisplayTramos(tramosLineal, "Tramos");
		final LinealTramosDialog dialog = new LinealTramosDialog();
		dialog.contentPanel.add(display);
		dialog.setVisible(true);
	}
}
