package procesos;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dialogs.RotateDialog;

import vision.Image;
import vision.MainWindow;

public class Rotar {
	
	public static void run(){
		final Image image = MainWindow.getCurrentImage();
		final RotateDialog dialog = new RotateDialog();

//		dialog.rbRotar180.setSelected(false);
//		dialog.rbRotar270.setSelected(false);
		
		ButtonGroup groupMultiplos = new ButtonGroup();
		groupMultiplos.add(dialog.rbRotar90);
		groupMultiplos.add(dialog.rbRotar180);
		groupMultiplos.add(dialog.rbRotar270);
		groupMultiplos.add(dialog.rbPersonalizarAngulo);
		
		dialog.rbRotar90.setSelected(true);
		dialog.spRotarAngulo.setEnabled(false);
		
//		dialog.rbVmp.setSelected(false);
		
		ButtonGroup groupInterpolacion = new ButtonGroup();
		groupInterpolacion.add(dialog.rbBilineal);
		groupInterpolacion.add(dialog.rbVmp);
		
		dialog.rbBilineal.setSelected(true);
		
		final MouseListener changeMultiplo = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ((dialog.rbRotar90.isSelected()) 
						|| (dialog.rbRotar180.isSelected()) 
						|| (dialog.rbRotar270.isSelected())) {
					dialog.spRotarAngulo.setEnabled(false);
				}
				else if (dialog.rbPersonalizarAngulo.isSelected()) {
					dialog.spRotarAngulo.setEnabled(true);
				}
			}
		};
		
		dialog.rbRotar90.addMouseListener(changeMultiplo);
		dialog.rbRotar180.addMouseListener(changeMultiplo);
		dialog.rbRotar270.addMouseListener(changeMultiplo);
		dialog.rbPersonalizarAngulo.addMouseListener(changeMultiplo);
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		dialog.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				comprobarAngulo(dialog);
				dialog.setVisible(false);
				// C�lculo de las dimensiones de la nueva imagen
				Image newImage = null;
				int angulo = (Integer) dialog.spRotarAngulo.getValue();
				if ((dialog.rbRotar90.isSelected()) || (angulo == 90)){
					newImage = rotar90(image);
				}
				else if ((dialog.rbRotar180.isSelected()) || (angulo == 180)) {
					newImage = rotar180(image);
				}
				else if ((dialog.rbRotar270.isSelected()) || (angulo == 270)) {
					newImage = rotar270(image);
				}
				else if (angulo != 0){
					newImage = rotar(image, angulo, dialog.rbBilineal.isSelected());
				}
				if (newImage != null)
					MainWindow.insertAndListenImage(newImage);
			}
		});
		dialog.setVisible(true);
	}
	
	/*private static void comprobarAngulo(RotateDialog dialog) {
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
			dialog.rbPersonalizarAngulo.setSelected(true);
		}
	}*/
	
	public static Image rotar90(Image im) {
		int width = im.heightRoi();
		int height = im.widthRoi();
        Image newIm = Image.crearImagen(width, height, im, "Rotaci�n de ");
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
		Image newIm = Image.crearImagen(width, height, im, "Rotaci�n de ");
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
		Image newIm = Image.crearImagen(width, height, im, "Rotaci�n de ");
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
        Image newIm = Image.crearImagen(width, height, im, "Rotaci�n de ");
        
        
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
        
        //MainWindow.startOperation(newIm.widthRoi() * newIm.heightRoi());
        MainWindow.startOperation(100);
//        int paso = newIm.widthRoi() * newIm.heightRoi() / 100;
//        int i = 0;
        for (int x = 0; x < newIm.widthRoi(); x++) {
            for (int y = 0; y < newIm.heightRoi(); y++) {
//            	i++;
//            	if (i == paso) {
//            		i = 0;
//            	MainWindow.incProgressBar();
//            	MainWindow.progressBar.repaint();
//            	}
            	
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
        MainWindow.endOperation();
        
        return newIm;
	}
}
