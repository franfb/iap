package vision;

import hips.region.Region;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListenerForSimplified implements MouseListener, MouseMotionListener {
	MainWindow window;
	ImagePanel panel;
    private boolean highlightedPinned;
    private Region highlighted;
	
	public MyMouseListenerForSimplified(MainWindow window, ImagePanel panel){
		this.window = window;
		this.panel = panel;
		highlightedPinned = false;
        highlighted = null;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!(e.getComponent() instanceof ImagePanel)){
			return;
		}
		ImagePanel panel = (ImagePanel) e.getComponent();
		Point p = panel.getCoordinate(e.getX(), e.getY());
		if (p == null){
			exit(panel);
			window.infoLabel.setText("");
			return;
		}
		window.infoLabel.setText("Pixel: (x=" + p.x + ", y=" + p.y + "),  Value: " + panel.img.getPixelValue(p.x, p.y).getString());
		
        int pos = panel.img.getWidth() * p.y + p.x;
        Region r = panel.p.getRegionByPixel(pos);
        if (highlighted == null && !highlightedPinned) {
            highlighted = r;
            panel.img.paintRegion(r, panel.img.newPixelValue(Color.YELLOW.getRGB()));
        } else if (highlighted != null && !r.sameAs(highlighted) && !highlightedPinned) {
            panel.img.paintRegion(highlighted, panel.p.getImage().toRGB(panel.p.getImage().getMeanValue(highlighted)));
            panel.img.paintRegion(r, panel.img.newPixelValue(Color.YELLOW.getRGB()));
            highlighted = r;
        }
	}

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        if (!(e.getComponent() instanceof ImagePanel)){
			return;
		}
		ImagePanel panel = (ImagePanel) e.getComponent();
		Point p = panel.getCoordinate(e.getX(), e.getY());
		if (p == null){
			return;
		}
		int pos = panel.img.getWidth() * p.y + p.x;
        Region r = panel.p.getRegionByPixel(pos);
        highlightedPinned = !highlightedPinned;
        if (!highlightedPinned && !r.sameAs(highlighted)){
        	panel.img.paintRegion(highlighted, panel.p.getImage().toRGB(panel.p.getImage().getMeanValue(highlighted)));
            panel.img.paintRegion(r, panel.img.newPixelValue(Color.YELLOW.getRGB()));
            highlighted = r;
        }
    }

    private void exit(ImagePanel panel){
    	if (highlightedPinned){
    		return;
    	}
    	if (highlighted != null) {
            panel.img.paintRegion(highlighted, panel.p.getImage().toRGB(panel.p.getImage().getMeanValue(highlighted)));
        }
    	highlighted = null;
    }
    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    	if (!(e.getComponent() instanceof ImagePanel)){
			return;
		}
		ImagePanel panel = (ImagePanel) e.getComponent();
    	exit(panel);
    }

    public void mouseDragged(MouseEvent e) {
    }
}
