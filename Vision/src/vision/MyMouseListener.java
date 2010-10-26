package vision;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {
	MainWindow window;
	
	public MyMouseListener(MainWindow window){
		this.window = window;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		window.infoLabel.setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!(e.getComponent() instanceof ImagePanel)){
			return;
		}
		ImagePanel panel = (ImagePanel) e.getComponent();
		Point p = panel.getCoordinate(e.getX(), e.getY());
		if (p == null){
			window.infoLabel.setText("");
			return;
		}
		window.infoLabel.setText("Pixel: (x=" + p.x + ", y=" + p.y + "),  Value: " + panel.img.getPixelValue(p.x, p.y).getString());
	}
}
