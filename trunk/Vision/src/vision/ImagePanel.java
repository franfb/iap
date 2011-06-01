package vision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class ImagePanel extends JComponent implements MouseMotionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	Image image;
	BufferedImage img;
	Dimension imgSize;
	Point offset;
	
	int border = 9;
	
	public ImagePanel(Image image) {
		this.image = image;
		this.img = image.img;
		imgSize = new Dimension(img.getWidth(null) + border * 2 + 1,
				img.getHeight(null) + border * 2 + 1);
		setPreferredSize(imgSize);
		setMinimumSize(imgSize);
		setMaximumSize(imgSize);
		setSize(imgSize);
		offset = new Point(0, 0);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paint(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		offset.x = (size.width - imgSize.width) / 2;
		offset.y = (size.height - imgSize.height) / 2;
		g.drawImage(img, offset.x + border + 1, offset.y + border + 1, null);
		g.drawRect(offset.x, offset.y, imgSize.width, imgSize.height);
		if (validRoi()){
			roiPainted = true;
			Point topLeft = getTopLeftRoi();
			Point bottomRight = getBottomRightRoi();
			g.setColor(new Color(0xCCCCCC));
			g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
			g.setColor(new Color(0x66000000, true));
			g.fillRect(topLeft.x + 1, topLeft.y + 1, bottomRight.x - topLeft.x - 1, bottomRight.y - topLeft.y - 1);
		}
		
	}
	
	public Point getCoordinate(int x, int y){
		Point coord = new Point(x - offset.x - border - 1, y - offset.y - border - 1);
		if (coord.x < 0 || coord.y < 0 || coord.x >= img.getWidth() || coord.y >= img.getHeight()){
			return null;
		}
		return coord;
	}
	
	public Point bringCloser(Point p){
		Point coord = new Point(p.x - offset.x - border - 1, p.y - offset.y - border - 1);
		if (coord.x < 0){
			p.x -= coord.x;
		}
		if (coord.y < 0){
			p.y -= coord.y;
		}
		if (coord.x >= img.getWidth()){
			p.x -= coord.x - img.getWidth() + 1;
		}
		if (coord.y >= img.getHeight()){
			p.y -= coord.y - img.getHeight() + 1;
		}
		return p;
	}

	Point begin = null;
	Point end = null;
	boolean movement;
	boolean roiPainted = false;
	
	public Point getTopLeftRoi(){
		if (begin == null){
			return null;
		}
		Point topLeft = new Point(begin);
		if (end.x < begin.x){
			topLeft.x = end.x;
		}
		if (end.y < begin.y){
			topLeft.y = end.y;
		}
		return topLeft;
	}
	
	public Point getBottomRightRoi(){
		if (end == null){
			return null;
		}
		Point bottomRight = new Point(end);
		if (end.x < begin.x){
			bottomRight.x = begin.x;
		}
		if (end.y < begin.y){
			bottomRight.y = begin.y;
		}
		return bottomRight;
	}
	
	public boolean validRoi(){
		if (begin != null && begin.x != end.x && begin.y != end.y){
			return true;
		}
		return false;
	}
		
	@Override
	public void mousePressed(MouseEvent e) {
		movement = false;
		begin = bringCloser(e.getPoint());
		end = begin;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (! movement){
			begin = null;
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		movement = true;
		end = bringCloser(e.getPoint());
		if (validRoi() || roiPainted){
			repaint();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
