package vision;

import java.util.ArrayList;

import javax.swing.JComponent;

public class InfoLabels {
	
	public static ArrayList<JComponent> listaEtiquetas = new ArrayList<JComponent>();
	
	public static void activaEtiquetas(){
		for (JComponent item: listaEtiquetas){
			item.setEnabled(true);
		}
	}
	
	public static void desactivaEtiquetas(){
		for (JComponent item: listaEtiquetas){
			item.setEnabled(false);
		}
	}
}
