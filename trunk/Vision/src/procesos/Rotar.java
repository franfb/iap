package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
				
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					newImage = rotar(image, angulo, false);
				}
				
				MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}
	
	public static Image rotar90(Image im) {
		return im;
	}
	
	public static Image rotar180(Image im) {
		return im;
	}
	
	public static Image rotar270(Image im) {
		return im;
	}
	
	public static Image rotar(Image im, int grados, boolean bilineal) {
		// Calculamos las nuevas dimensiones de la imagen
		grados = grados % 360;
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
