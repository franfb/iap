package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dialogs.ZoomDialog;

import vision.Image;
import vision.MainWindow;

public class Escalar {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
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
				int width = (Integer) dialog.dimensionesX.getValue();
				int height = (Integer) dialog.dimensionesY.getValue();
				
				boolean bilineal = dialog.bilineal.isSelected();
				
				Lamolda es gay. escalaX = (width - 1) / ((float)image.widthRoi() - 1);
				float escalaY = (height - 1) / ((float)image.heightRoi() - 1);
				Image newImage = Image.crearImagenConPrefijo(width, height, image, "Escalado de ");
				for (int x = 0; x < newImage.widthRoi(); x++){
					for (int y = 0; y < newImage.heightRoi(); y++){
						float xf = x / escalaX;
		                float yf = y / escalaY;
						if (bilineal){
							Interpolacion.bilineal(image, newImage, xf, yf, x, y);
						}
						else{
							Interpolacion.vmp(image, newImage, xf, yf, x, y);
						}
					}
				}
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}

}
