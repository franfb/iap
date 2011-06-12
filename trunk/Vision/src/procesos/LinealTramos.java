package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import dialogs.LinealTramosDialog;
import vision.DisplayTramos;
import vision.Image;
import vision.ImageInfo;
import vision.MainWindow;

public class LinealTramos {
	public static void run() {
		Copiar.run("Trans. Lineal por Tramos de ");
		final Image image = MainWindow.getCurrentImage();
//		final ImageInfo info = image.getInfo();
		final BufferedImage oldBufIm = image.img;
		final BufferedImage newBufIm = new BufferedImage(image.widthRoi(), image.heightRoi(), BufferedImage.TYPE_INT_RGB);
		
		int[] tramosLineal = new int[ImageInfo.NIVELES];
		// Construimos un tramo lineal
		for (int i = 0; i < tramosLineal.length; i++) {
			tramosLineal[i] = i;
		}
		final DisplayTramos display = new DisplayTramos(tramosLineal, "Tramos");
		final LinealTramosDialog dialog = new LinealTramosDialog();
		dialog.contentPanel.add(display);
		// TODO arreglar el keyListener del DisplayTramos
		dialog.addKeyListener(display);
		
		display.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				image.img = Lut.aplicarLut(oldBufIm, newBufIm, display.getLut());
				image.panel.img = image.img;
				image.resetInfo();
				image.panel.repaint();
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				MainWindow.removeCurrentImage();
			}
		});
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
}
