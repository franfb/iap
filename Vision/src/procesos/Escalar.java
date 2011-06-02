package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.Image;
import vision.MainWindow;
import vision.ZoomDialog;

public class Escalar {
	
	public static void run(){
		final Image image = MainWindow.getImage();
		final ZoomDialog dialog = new ZoomDialog();
		dialog.porcentaje.setSelected(false);
		dialog.dimensiones.setSelected(false);
		
		ButtonGroup group = new ButtonGroup();
		group.add(dialog.porcentaje); 
		group.add(dialog.dimensiones); 
		
		dialog.vecino.setSelected(true);
		ButtonGroup groupInterpolacion = new ButtonGroup();
		groupInterpolacion.add(dialog.bilineal);
		groupInterpolacion.add(dialog.vecino);
		
		final ChangeListener changePorcentaje = new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				float porcentajeX = (Integer) dialog.porcentajeX.getValue() / 100.0f;
				float porcentajeY = (Integer) dialog.porcentajeY.getValue() / 100.0f;
				dialog.dimensionesX.setValue((int)(porcentajeX * image.widthRoi()));
				dialog.dimensionesY.setValue((int)(porcentajeY * image.heightRoi()));
			}
		};
		final ChangeListener changeDimensiones = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int dimensionesX = (Integer) dialog.dimensionesX.getValue();
				int dimensionesY = (Integer) dialog.dimensionesY.getValue();
				int porcentajeX = (int) ((dimensionesX / (float)image.widthRoi()) * 100.0);
				int porcentajeY = (int) ((dimensionesY / (float)image.heightRoi()) * 100.0);
				dialog.porcentajeX.setValue(porcentajeX);
				dialog.porcentajeY.setValue(porcentajeY);
			}
		};
		
		ChangeListener changeButtons = new ChangeListener() {
			JRadioButton selected = null;
			public void stateChanged(ChangeEvent e) {
				
				if (dialog.porcentaje.isSelected() && (selected == null || selected != dialog.porcentaje)){
					selected = dialog.porcentaje;
					dialog.dimensionesX.removeChangeListener(changeDimensiones);
					dialog.dimensionesY.removeChangeListener(changeDimensiones);
					dialog.porcentajeX.setEnabled(true);
					dialog.porcentajeY.setEnabled(true);
					dialog.dimensionesX.setEnabled(false);
					dialog.dimensionesY.setEnabled(false);
					dialog.porcentajeX.addChangeListener(changePorcentaje);
					dialog.porcentajeY.addChangeListener(changePorcentaje);
				}
				else if(dialog.dimensiones.isSelected() && (selected == null || selected != dialog.dimensiones)){
					selected = dialog.dimensiones;
					dialog.porcentajeX.removeChangeListener(changePorcentaje);
					dialog.porcentajeY.removeChangeListener(changePorcentaje);
					dialog.porcentajeX.setEnabled(false);
					dialog.porcentajeY.setEnabled(false);
					dialog.dimensionesX.setEnabled(true);
					dialog.dimensionesY.setEnabled(true);
					dialog.dimensionesX.addChangeListener(changeDimensiones);
					dialog.dimensionesY.addChangeListener(changeDimensiones);
				}
			}
		};
		
		dialog.porcentajeX.setModel(new SpinnerNumberModel(200, 1, 10000, 10));
		dialog.porcentajeY.setModel(new SpinnerNumberModel(200, 1, 10000, 10));
		
		int dimensionesX = image.widthRoi() * 2;
		int dimensionesY = image.heightRoi() * 2;
		
		dialog.dimensionesX.setModel(new SpinnerNumberModel(dimensionesX, 1, 1000000000, 100));
		dialog.dimensionesY.setModel(new SpinnerNumberModel(dimensionesY, 1, 1000000000, 100));
		
		dialog.dimensionesX.setEnabled(false);
		dialog.dimensionesY.setEnabled(false);
		
		dialog.porcentaje.addChangeListener(changeButtons);
		dialog.dimensiones.addChangeListener(changeButtons);
		dialog.porcentaje.setSelected(true);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				File newFile = new File(image.file.getParent(), "Escalado de " + image.file.getName());
				int width = (Integer) dialog.dimensionesX.getValue();
				int height = (Integer) dialog.dimensionesY.getValue();
				
				boolean bilineal = dialog.bilineal.isSelected();
				
				float escalaX = (width - 1) / ((float)image.widthRoi() - 1);
				float escalaY = (height - 1) / ((float)image.heightRoi() - 1);
				Image newImage = Image.crearImagen(width, height, newFile);
				Point src = image.topLeftRoi();
				for (int x = 0; x < newImage.widthRoi(); x++){
					for (int y = 0; y < newImage.heightRoi(); y++){
						if (bilineal){
							float xf = x / escalaX;
			                float yf = y / escalaY;
			                int x0 = src.x + (int)xf;
			                int x1 = src.x + (int)Math.ceil(xf);
			                int y0 = src.y + (int)yf;
			                int y1 = src.y + (int)Math.ceil(yf);
			                float p = xf - x0;
			                float q = yf - y0;
			                if (x1 >= image.widthRoi()) {
		                    	x1 = x0;
		                	}
		                	if (y1 >= image.heightRoi()) {
		                    	y1 = y0;
		                	}
			                int[] a = Image.rgb2array(image.img.getRGB(x0, y0));
			                int[] b = Image.rgb2array(image.img.getRGB(x1, y0));
			                int[] c = Image.rgb2array(image.img.getRGB(x0, y1));
			                int[] d = Image.rgb2array(image.img.getRGB(x1, y1));
			                
			                float[] nivel = new float[3];
			                for (int i = 0; i < 3; i++){
			                	nivel[i] = a[i] + (b[i] - a[i])*p + (c[i] - a[i])*q + (a[i] + d[i] - b[i] - c[i])*p*q;
			                }
			                newImage.img.setRGB(x, y, Image.array2rgb(nivel));
						}
						else{							
							int masProximoX = Math.round(x / escalaX);
							int masProximoY = Math.round(y / escalaY);
							newImage.img.setRGB(x, y, image.img.getRGB(src.x + masProximoX, src.y + masProximoY));
						}
					}
				}
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}

}
