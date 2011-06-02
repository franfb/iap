package vision;

import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class Menu {

	public static JFileChooser chooser =  new JFileChooser();
	public static ArrayList<JComponent> opcionesMenu = new ArrayList<JComponent>();
	
	public static void activaOpcionesMenu(){
		for (JComponent item: opcionesMenu){
			item.setEnabled(true);
		}
	}
	
	public static void desactivaOpcionesMenu(){
		for (JComponent item: opcionesMenu){
			item.setEnabled(false);
		}
	}
	
}
