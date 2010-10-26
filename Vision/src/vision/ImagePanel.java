package vision;

import hips.images.Image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	Image img;
	Dimension imgSize;
	
	public ImagePanel(Image img) {
		this.img = img;
		imgSize = new Dimension(img.getAwtImage().getWidth(null),
				img.getAwtImage().getHeight(null));
		setPreferredSize(imgSize);
		setMinimumSize(imgSize);
		setMaximumSize(imgSize);
		setSize(imgSize);
	}

	public void paint(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		int x = (size.width - imgSize.width) / 2;
		int y = (size.height - imgSize.height) / 2;
		g.drawImage(img.getAwtImage(), x, y, null);
		g.drawRect(x - 9, y - 9, imgSize.width + 18, imgSize.height + 18);
	}
}
