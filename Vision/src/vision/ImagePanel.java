package vision;

import hips.images.Image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Image img;
	
	
	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getAwtImage().getWidth(this),
				img.getAwtImage().getHeight(this));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
	}

	public void paint(Graphics g) {
		g.drawImage(img.getAwtImage(), 0, 0, null);
	}
}
