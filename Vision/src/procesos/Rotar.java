package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vision.Image;
import vision.MainWindow;
import vision.RotateDialog;

public class Rotar {
	
	public static void run(){
		final Image image = MainWindow.getImage();
		final RotateDialog dialog = new RotateDialog();

		dialog.rbRotar90.setSelected(false);
		dialog.rbRotar180.setSelected(false);
		dialog.rbRotar270.setSelected(false);
		
		ButtonGroup groupMultiplos = new ButtonGroup();
		groupMultiplos.add(dialog.rbRotar90);
		groupMultiplos.add(dialog.rbRotar180);
		groupMultiplos.add(dialog.rbRotar270);
		
		final ChangeListener changeAngulo = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				comprobarAngulo(dialog);
			}
		};
		
		final ChangeListener changeMultiplo = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (dialog.rbRotar90.isSelected()){
					dialog.spRotarAngulo.setValue(90);
				}
				else if (dialog.rbRotar180.isSelected()){
					dialog.spRotarAngulo.setValue(180);
				}
				else if (dialog.rbRotar270.isSelected()){
					dialog.spRotarAngulo.setValue(270);
				}
			}
		};
		
		dialog.spRotarAngulo.addChangeListener(changeAngulo);
		//dialog.rbRotar90.addChangeListener(changeMultiplo);
		//dialog.rbRotar180.addChangeListener(changeMultiplo);
		//dialog.rbRotar270.addChangeListener(changeMultiplo);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comprobarAngulo(dialog);
				dialog.setVisible(false);
				// Cálculo de las dimensiones de la nueva imagen
				Image newImage;
				int angulo = 0;
				if (dialog.rbRotar90.isSelected()){
					newImage = rotar90(image);
				}
				else if (dialog.rbRotar180.isSelected()){
					newImage = rotar180(image);
				}
				else if (dialog.rbRotar270.isSelected()){
					newImage = rotar270(image);
				}
				else{
					angulo = (Integer) dialog.spRotarAngulo.getValue();
					newImage = rotar(image, angulo, true);
				}
				
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}
	
	private static void comprobarAngulo(RotateDialog dialog) {
		if ((Integer)dialog.spRotarAngulo.getValue() == 90) {
			dialog.rbRotar90.setSelected(true);
		}
		else if ((Integer)dialog.spRotarAngulo.getValue() == 180) {
			dialog.rbRotar180.setSelected(true);
		}
		else if ((Integer)dialog.spRotarAngulo.getValue() == 270) {
			dialog.rbRotar270.setSelected(true);
		}
		else {
			dialog.rbRotar90.setSelected(false);
			dialog.rbRotar180.setSelected(false);
			dialog.rbRotar270.setSelected(false);
		}
	}
	
	public static Image rotar90(Image im) {
		int width = im.heightRoi();
		int height = im.widthRoi();
		File newFile = new File(im.file.getParent(), "Rotación de " + im.file.getName());
        Image newIm = Image.crearImagen(width, height, newFile);
        Point src = im.topLeftRoi();
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
        		newIm.img.setRGB(x, y, im.img.getRGB(src.x + y, src.y + im.heightRoi() - x - 1));
        	}
        }
        
		return newIm;
	}
	
	public static Image rotar180(Image im) {
		int width = im.widthRoi();
		int height = im.heightRoi();
		File newFile = new File(im.file.getParent(), "Rotación de " + im.file.getName());
        Image newIm = Image.crearImagen(width, height, newFile);
        Point src = im.topLeftRoi();
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
        		newIm.img.setRGB(x, y, im.img.getRGB(src.x + im.widthRoi() - x - 1, src.y + im.heightRoi() - y - 1));
        	}
        }
        
		return newIm;
	}
	
	public static Image rotar270(Image im) {
		int width = im.heightRoi();
		int height = im.widthRoi();
		File newFile = new File(im.file.getParent(), "Rotación de " + im.file.getName());
        Image newIm = Image.crearImagen(width, height, newFile);
        Point src = im.topLeftRoi();
        for (int x = 0; x < width; x++) {
        	for (int y = 0; y < height; y++) {
        		newIm.img.setRGB(x, y, im.img.getRGB(src.x + im.widthRoi() - y - 1, src.y + x));
        	}
        }
        
		return newIm;
	}
	
	public static Image rotar(Image im, int grados, boolean bilineal) {
		// Calculamos las nuevas dimensiones de la imagen
		grados = grados % 360;
		if (grados < 0)
			grados = 360 + grados;
        double radianes = Math.toRadians((double)grados);
        int width = (int)(Math.ceil(im.widthRoi() * Math.abs(Math.cos(radianes))) + Math.ceil(im.heightRoi() * Math.abs(Math.sin(radianes))));
        int height  = (int)(Math.ceil(im.widthRoi() * Math.abs(Math.sin(radianes))) + Math.ceil(im.heightRoi() * Math.abs(Math.cos(radianes))));
        File newFile = new File(im.file.getParent(), "Rotación de " + im.file.getName());
        Image newIm = Image.crearImagen(width, height, newFile);
        
        
        double offX, offY;
        if ((grados >= 0) && (grados < 90)) {
            offX = - (im.heightRoi() * Math.abs(Math.sin(radianes)));
            offY = 0;
        } else if ((grados >= 90) && (grados < 180)) {
            offX = - (im.heightRoi() * Math.abs(Math.sin(radianes)) + (im.widthRoi() * Math.abs(Math.cos(radianes))));
            offY = - (im.heightRoi() * Math.abs(Math.cos(radianes)));
        } else if ((grados >= 180) && (grados < 270)) {
            offX = - (im.widthRoi() * Math.abs(Math.cos(radianes)));
            offY = - (im.widthRoi() * Math.abs(Math.sin(radianes)) + (im.heightRoi() * Math.abs(Math.cos(radianes))));
        } else { //if ((grados >= 270) && (grados < 360))
            offX = 0;
            offY = - (im.widthRoi() * Math.abs(Math.sin(radianes)));
        }
        
        long pixelsOut = 0;
        
        for (int x = 0; x < newIm.widthRoi(); x++) {
            for (int y = 0; y < newIm.heightRoi(); y++) {
                double i0 = x + offX;
                double j0 = y + offY;
                
                float xf = (float) (i0 * Math.cos(radianes) + j0 * Math.sin(radianes));
                float yf = (float) (j0 * Math.cos(radianes) - i0 * Math.sin(radianes));
                
                if ((xf > (im.widthRoi() - 1)) || (xf < 0) || 
                		(yf > (im.heightRoi() - 1)) || (yf < 0)) {
                    newIm.img.setRGB(x, y, 0);
                    pixelsOut++;
                } 
                else {
                	if (bilineal) {
                        Interpolacion.bilineal(im, newIm, xf, yf, x, y);
                	}
                	else { // VMP
                		Interpolacion.vmp(im, newIm, xf, yf, x, y);
                	}
                }
            }
        }
        //calculaInfoRotaciones(newIm, pixelsOut);
        
        
        return newIm;
	}
}
