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

public class ImagePanel extends JComponent /*
											 * implements MouseMotionListener,
											 * MouseListener
											 */{
	private static final long serialVersionUID = 1L;
	Image image;
	public BufferedImage img;
	Dimension imgSize;
	Point offset;

	int border = 9;

	public static Listener listener;
	
	private enum Listener{
		ROI(), PERFIL();
	}
	
	public static void setRoiListener(){
		listener = Listener.ROI;
	}
	
	public static void setPerfilListener(){
		listener = Listener.PERFIL;
	}
	
	public ImagePanel(Image image) {
		this.image = image;
		this.img = image.img;
		imgSize = new Dimension(img.getWidth(null) + border * 2 + 1, img.getHeight(null) + border * 2 + 1);
		setPreferredSize(imgSize);
		setMinimumSize(imgSize);
		setMaximumSize(imgSize);
		setSize(imgSize);
		offset = new Point(0, 0);
		resetMouseListener();
	}

	public void paint(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		offset.x = (size.width - imgSize.width) / 2;
		offset.y = (size.height - imgSize.height) / 2;
		g.drawImage(img, offset.x + border + 1, offset.y + border + 1, null);
		g.drawRect(offset.x, offset.y, imgSize.width, imgSize.height);
		if (listener == Listener.ROI && validRoi()) {
			roiPainted = true;
			Point topLeft = getTopLeftRoi();
			Point bottomRight = getBottomRightRoi();
			g.setColor(new Color(0xCCCCCC));
			g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
			g.setColor(new Color(0x66000000, true));
			g.fillRect(topLeft.x + 1, topLeft.y + 1, bottomRight.x - topLeft.x - 1, bottomRight.y - topLeft.y - 1);
		}
		
		if (listener == Listener.PERFIL && beginLine != null && endLine != null) {
			g.setColor(Color.WHITE);
			g.drawLine(beginLine.x, beginLine.y, endLine.x, endLine.y);
			/*roiPainted = true;
			Point topLeft = getTopLeftRoi();
			Point bottomRight = getBottomRightRoi();
			g.setColor(new Color(0xCCCCCC));
			g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
			g.setColor(new Color(0x66000000, true));
			g.fillRect(topLeft.x + 1, topLeft.y + 1, bottomRight.x - topLeft.x - 1, bottomRight.y - topLeft.y - 1);*/
		}
		
		MainWindow.showInfo();
	}

	public Point getCoordinate(int x, int y) {
		Point coord = new Point(x - offset.x - border - 1, y - offset.y - border - 1);
		if (coord.x < 0 || coord.y < 0 || coord.x >= img.getWidth() || coord.y >= img.getHeight()) {
			return null;
		}
		return coord;
	}

	public Point bringCloser(Point p) {
		Point coord = new Point(p.x - offset.x - border - 1, p.y - offset.y - border - 1);
		if (coord.x < 0) {
			p.x -= coord.x;
		}
		if (coord.y < 0) {
			p.y -= coord.y;
		}
		if (coord.x >= img.getWidth()) {
			p.x -= coord.x - img.getWidth() + 1;
		}
		if (coord.y >= img.getHeight()) {
			p.y -= coord.y - img.getHeight() + 1;
		}
		return p;
	}

	/*public Point bringCloserPerfil(Point p) {
		Point coord = new Point(p.x - offset.x - border - 1, p.y - offset.y - border - 1);
		if (coord.x < 0) {
			p.x -= coord.x;
		}
		if (coord.y < 0) {
			p.y -= coord.y;
		}
		if (coord.x >= img.getWidth()) {
			p.x -= coord.x - img.getWidth() + 1;
		}
		if (coord.y >= img.getHeight()) {
			p.y -= coord.y - img.getHeight() + 1;
		}
		return p;
	}*/
	
	Point begin = null;
	Point end = null;
	boolean movement;
	boolean roiPainted = false;

	public Point getTopLeftRoi() {
		if (begin == null) {
			return null;
		}
		Point topLeft = new Point(begin);
		if (end.x < begin.x) {
			topLeft.x = end.x;
		}
		if (end.y < begin.y) {
			topLeft.y = end.y;
		}
		return topLeft;
	}

	public Point getBottomRightRoi() {
		if (end == null) {
			return null;
		}
		Point bottomRight = new Point(end);
		if (end.x < begin.x) {
			bottomRight.x = begin.x;
		}
		if (end.y < begin.y) {
			bottomRight.y = begin.y;
		}
		return bottomRight;
	}

	public boolean validRoi() {
		if (begin != null && begin.x != end.x && begin.y != end.y) {
			return true;
		}
		return false;
	}

	public void resetMouseListener() {
		for (MouseMotionListener m: getMouseMotionListeners()){
			removeMouseMotionListener(m);
		}
		for (MouseListener m: getMouseListeners()){
			removeMouseListener(m);
		}
		if (listener == Listener.ROI) {
			addMouseListener(roiListener);
			addMouseMotionListener(roiMotionListener);
			resetRoiListener();
		}
		if (listener == Listener.PERFIL) {
			addMouseListener(perfilListener);
			addMouseMotionListener(perfilMotionListener);
			resetPerfilListener();
		}
	}

	private void resetRoiListener(){	
	}
	
	MouseMotionListener roiMotionListener = new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent e) {
			movement = true;
			end = bringCloser(e.getPoint());
			if (validRoi() || roiPainted) {
				repaint();
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}
	};

	MouseListener roiListener = new MouseListener() {

		@Override
		public void mousePressed(MouseEvent e) {
			movement = false;
			begin = bringCloser(e.getPoint());
			end = begin;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!movement) {
				begin = null;
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	};
	
	boolean creatingLine;
	Point beginLine;
	Point endLine;
	
	private void resetPerfilListener(){	
		creatingLine = false;
		beginLine = null;
		endLine = null;
	}
	
	MouseMotionListener perfilMotionListener = new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent e) {
			/*movement = true;
			end = bringCloser(e.getPoint());
			if (validRoi() || roiPainted) {
				repaint();
			}*/
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (getCoordinate(e.getX(), e.getY()) != null && creatingLine){
				endLine = e.getPoint();
				repaint();
			}
		}
	};

	MouseListener perfilListener = new MouseListener() {

		@Override
		public void mousePressed(MouseEvent e) {
			/*movement = false;
			begin = bringCloser(e.getPoint());
			end = begin;*/
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			/*if (!movement) {
				begin = null;
				repaint();
			}*/
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (getCoordinate(e.getX(), e.getY()) == null){
				return;
			}
			/*creatingLine = !creatingLine;
			if (creatingLine){
				beginLine = e.getPoint();
				endLine = null;
			}*/
			
			if (creatingLine){
				creatingLine = false;
			}
			else{
				if (beginLine == null){
					beginLine = e.getPoint();
					endLine = null;
					creatingLine = true;
				}
				else{
					beginLine = null;
					repaint();
				}
			}
		}
		

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	};

}
