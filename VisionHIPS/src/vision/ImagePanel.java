package vision;

import hips.Partition;
import hips.images.Image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class ImagePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	Image img;
	Partition p;
	Dimension imgSize;
	Point offset;
	
	public ImagePanel(Image img) {
		this.img = img;
		p = null;
		imgSize = new Dimension(img.getAwtImage().getWidth(null),
				img.getAwtImage().getHeight(null));
		setPreferredSize(imgSize);
		setMinimumSize(imgSize);
		setMaximumSize(imgSize);
		setSize(imgSize);
		offset = new Point(0, 0);
	}
	
	public void setPartition(Partition p){
		this.p = p;
	}
	
	public void paint(Graphics g) {
		Dimension size = getSize();
		
		
		g.clearRect(0, 0, size.width, size.height);
		offset.x = (size.width - imgSize.width) / 2;
		offset.y = (size.height - imgSize.height) / 2;
		g.drawImage(img.getAwtImage(), offset.x, offset.y, null);
		g.drawRect(offset.x - 9, offset.y - 9, imgSize.width + 18, imgSize.height + 18);
	}
	
	public Point getCoordinate(int x, int y){
		Point coord = new Point(x - offset.x, y - offset.y);
		if (coord.x < 0 || coord.y < 0 || coord.x >= img.getWidth() || coord.y >= img.getHeight()){
			return null;
		}
		return coord;
	}
}
