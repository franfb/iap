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
		MainWindow.lblPixelPos.setText("");
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
			MainWindow.lblPixelPos.setText("");
			MainWindow.lblPixelValue.setText("");
			return;
		}
		MainWindow.lblPixelPos.setText("Pixel: (x=" + p.x + ", y=" + p.y + ")");
		MainWindow.lblPixelValue.setText("Value: " + vision.Image.getString(panel.img.getRGB(p.x, p.y)));
	}
}
