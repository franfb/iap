package vision;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {
	
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
		MainWindow.infoLabel.setText("");
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
			MainWindow.infoLabel.setText("");
			return;
		}
		MainWindow.infoLabel.setText("Pixel: (x=" + p.x + ", y=" + p.y + "),  Value: " + vision.Image.getString(panel.img.getRGB(p.x, p.y)));
	}
}
