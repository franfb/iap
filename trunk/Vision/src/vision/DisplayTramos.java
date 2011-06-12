package vision;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayTramos extends DisplayHistogram implements MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean[] tramos;
	private byte[] lut;
	
//	protected int height = 256;

	public DisplayTramos(int[] histogram, String title) {
		super(histogram, title);
		super.height = 256;
		
		tramos = new boolean[histogram.length];
		for (int i = 0; i < tramos.length; i++) {
			tramos[i] = false;
		}
		tramos[0] = true;
		tramos[tramos.length - 1] = true;
		this.removeMouseListener(this);
		this.addMouseListener(this);
//		this.addKeyListener(this);
	}
	
	public byte[] getLut() {
		if (lut == null) {
			lut = new byte[histogram.length];
		}
		for (int i = 0; i < histogram.length; i++) {
			lut[i] = (byte)histogram[i];
		}
		return lut;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.YELLOW);
		
		for(int bin=0;bin<histogram.length; bin++) {
			if (tramos[bin]) {
				int x = border.left + bin * binWidth;
				double offset = counts[bin];
				if (offset < 0) {
					offset = 0;
				}
				double barStarts = border.top + height
						* (maxCount - Math.abs(offset))
						/ (1. * (maxCount - minCount));
//				if (counts[bin] < -30)
//					System.out.println("hullla");
				double barEnds = Math.ceil(height * Math.abs(counts[bin])
						/ (1. * (maxCount - minCount)));
				g2d.drawRect(x, (int) barStarts, binWidth - 1, (int) barEnds);
			}
//            // Get the selected X and Y
//            if ((showTip) && (bin == binTip)) {
//            	xTip = x;
//            	yTip = (int)barStarts;
//            }
        }
	}
	
	public void mouseClicked(MouseEvent e) {
//		super.getMouseListeners()[0].mouseClicked(e);
		super.mouseClicked(e);
		int x = e.getX(); int y = e.getY();
        // Don't show anything out of the plot region.
        if ((x >= border.left) && (x < border.left+width) && (y > border.top) && (y < border.top+height)) {
            // Convert the X to an index on the histogram.
            x = (x-border.left)/binWidth;
//            y = counts[x];
//            showTip = true;
//            binTip = x;
            y = height-(y-border.top);
            System.out.println("x :" + x);
            System.out.println("y :" + y);
            if ((x != 0) && (x != tramos.length - 1)) {
                tramos[x] = !tramos[x];
            }
            if (tramos[x]) {
                histogram[x] = (int)y;
            }
            int x1 = 0;
            int x2;
            float pendiente;
            for (int i = 1; i < tramos.length; i++) {
                if (tramos[i]) {
                    x2 = i;
                    pendiente = ((float)(counts[x2] - counts[x1]))/((float)(x2 - x1));
                    for (int j = (x1 + 1); j < x2; j++) {
                        counts[j] = (int)((j - x1) * pendiente + counts[x1]);
                    }
                    x1 = x2;
                }
            }
        }
        repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (binTip > 0) {
				for (int i = binTip - 1; i >= 0; i--) {
					if (tramos[i]) {
						binTip = i;
						break;
					}
				}
			}
			showTip = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (binTip < 255) {
				for (int i = binTip + 1; i < 256; i++) {
					if (tramos[i]) {
						binTip = i;
						break;
					}
				}
			}
			showTip = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
