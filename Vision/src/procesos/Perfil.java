package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import dialogs.PerfilDialog;

import vision.DisplayHistogram;
import vision.Image;
import vision.ImagePanel;
import vision.MainWindow;
import vision.Menu;

public class Perfil {
	
	public static DisplayHistogram perfil;
	public static DisplayHistogram perfilSuvizado;
	public static DisplayHistogram derivadaPerfil;
	public static DisplayHistogram derivadaPerfilSuavizada;
	
	public static PerfilDialog dialog;
	
	public static void run(){
		dialog = new PerfilDialog();
		
		int[] valores = new int[200];
		
		perfil = new DisplayHistogram(valores, "Perfil", "");
		perfilSuvizado = new DisplayHistogram(valores, "P. suavizado", "");
		derivadaPerfil = new DisplayHistogram(valores, "Derivada del perfil", "");
		derivadaPerfilSuavizada = new DisplayHistogram(valores, "Derivada del P. suavizado", "");
		
		dialog.panel.add(perfil);
		dialog.panel.add(perfilSuvizado);
		dialog.panel.add(derivadaPerfil);
		dialog.panel.add(derivadaPerfilSuavizada);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (MainWindow.getImageCount() > 0){
					Menu.activaOpcionesMenu();
				}
				ImagePanel.setRoiListener();
				dialog.setVisible(false);
			}
		});
		Menu.desactivaOpcionesMenu();
		
		ImagePanel.setPerfilListener();
		
		dialog.setVisible(true);
	}

	
	public static void nuevaRecta(Image img, Point begin, Point end){
		
			int x0 = begin.x;
			int x1 = end.x;
			
			int y0 = begin.y;
			int y1 = end.y;
	        
	        ArrayList<Integer> niveles = new ArrayList<Integer>();
	        
	        // Comprobamos que la distancia vertical sea mayor que la horizontal
	        boolean condicion = Math.abs(y1 - y0) > Math.abs(x1-x0);
	        if (condicion) {
	            // Intercambiamos x0 e y0
	            int t = x0;
	            x0 = y0;
	            y0 = t;
	            // Intercambiamos x1 e y1
	            t = x1;
	            x1 = y1;
	            y1 = t;
	        }
	        if (x0 > x1) {
	            // Intercambiamos x0 y x1
	            int t = x0;
	            x0 = x1;
	            x1 = t;
	            // Intercambiamos y0 e y1
	            t = y0;
	            y0 = y1;
	            y1 = t;
	        }
	        
	        int deltax = x1 - x0;
	        int deltay = Math.abs(y1 - y0);
	        int error = -deltax / 2;
	        int ystep;
	        int y = y0;
	        
	        if (y0 < y1)
	            ystep = 1;
	        else
	            ystep = -1;
	        
	        
	        for (int x = x0; x <= x1; x++) {
	            if (condicion) {
	                niveles.add(img.img.getRGB(y, x));
	            } else {
	            	niveles.add(img.img.getRGB(x, y));
	            }
	            error = error + deltay;
	            if (error > 0) {
	                y = y + ystep;
	                error = error - deltax;
	            }
	        }
	        
	        int[] arrayPerfil = new int[niveles.size()];
	        int[] arraySuavizado = new int[niveles.size()];
	        int[] arrayDerivadaPerfil = new int[niveles.size()];
	        int[] arrayDerivadaSuavizado = new int[niveles.size()];
	        for (int i = 0; i < arrayPerfil.length; i++){
	        	arrayPerfil[i] = (int) Image.grey(niveles.get(i));
	        }
	        
	        int ventana = 2;
	        for (int i = ventana; i < arraySuavizado.length - ventana; i++){
	        	arraySuavizado[i] = arrayPerfil[i];
	        	for (int j = i - ventana; j < i; j++){
	        		arraySuavizado[i] += arrayPerfil[j];
	        	}
	        	for (int j = i + 1; j <= i + ventana; j++){
	        		arraySuavizado[i] += arrayPerfil[j];
	        	}
	        	arraySuavizado[i] = arraySuavizado[i] / (ventana*2 + 1);
	        }
	        for (int i = 0; i < ventana; i++){
	        	arraySuavizado[i] = arrayPerfil[i];
	        }
	        for (int i = arraySuavizado.length - ventana; i < arraySuavizado.length; i++){
	        	arraySuavizado[i] = arrayPerfil[i];
	        }
	        
	        arrayDerivadaPerfil[0] = 0;
	        for (int i = 1; i < arrayDerivadaPerfil.length; i++){
	        	arrayDerivadaPerfil[i] = arrayPerfil[i] - arrayPerfil[i-1];
	        }
	        
	        arrayDerivadaSuavizado[0] = 0;
	        for (int i = 1; i < arrayDerivadaSuavizado.length; i++){
	        	arrayDerivadaSuavizado[i] = arraySuavizado[i] - arraySuavizado[i-1];
	        }
	        
	        perfil.setHistogram(arrayPerfil, 0);
	        perfilSuvizado.setHistogram(arraySuavizado, 0); 
	        derivadaPerfil.setHistogram(arrayDerivadaPerfil, 0); 
	        derivadaPerfilSuavizada.setHistogram(arrayDerivadaSuavizado, 0);

	       dialog.setSize(dialog.getWidth()+1, dialog.getHeight());
	}
	
	
}
