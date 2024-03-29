/*
 * Created on Jun 10, 2005
 * @author Rafael Santos (rafael.santos@lac.inpe.br)
 *
 * Part of the Java Advanced Imaging Stuff site
 * (http://www.lac.inpe.br/~rafael.santos/Java/JAI)
 *
 * STATUS: Complete, but could be improved, for example, with:
 *   - Plotting more than one band of the histogram.
 *   - Considering the minimum number of pixels in a bin.
 *   - Customization as a JavaBean.
 *
 * Redistribution and usage conditions must be done under the
 * Creative Commons license:
 * English: http://creativecommons.org/licenses/by-nc-sa/2.0/br/deed.en
 * Portuguese: http://creativecommons.org/licenses/by-nc-sa/2.0/br/deed.pt
 * More information on design and applications are on the projects' page
 * (http://www.lac.inpe.br/~rafael.santos/Java/JAI).
 */

package vision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//import javax.media.jai.Histogram;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;

/**
 * This class displays a histogram (instance of Histogram) as a component.
 * Only the first histogram band ins considered for plotting.
 * The component has a tooltip which displays the bin index and bin count for the
 * bin under the mouse cursor.
 */
public class DisplayHistogram extends JComponent implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	// The histogram and its title.
    protected int histogram[][];//Histogram histogram;
    protected String title;
    // Some data and hints for the histogram plot.
    protected int[][] counts;
    protected double maxCount, minCount;
    protected int indexMultiplier = 1;
    protected int skipIndexes = 8;
    // The components' dimensions.
    protected int width=250,height=100;
    // Some constants for this component.
    protected int verticalTicks = 10;
    protected Insets border = new Insets(40,50,40,30);
    protected int binWidth = 1;
//    private Color backgroundColor = new Color(236,233,216);
    protected Color backgroundColor = new Color(240,240,240);
    protected Color barColor[] = { Color.DARK_GRAY };//new Color(255,255,200);
//    private Color marksColor = Color.BLUE;//new Color(100,180,255);
    protected Color marksColor = Color.BLACK;//new Color(100,180,255);
    protected Font fontSmall = new Font("monospaced",0,10);
    protected Font fontLarge = new Font("default",0,20);
    // Attributes for drawing the selected bin info
    protected Color selectedColor = Color.RED;
    protected Color selectedInfoColor = Color.BLACK;
    protected Color backgroundInfoColor = Color.WHITE;
    protected Font fontInfo = new Font("default", 0, 12);
    protected int leftGap = 4;
    protected int bottomGap = 4;
    protected int binTip = 0;
    protected int xTip = 0;
    protected int yTip = 0;
    protected boolean showTip = false;
    protected String[] names;
    // Attributes for painting multiple histograms
    protected int initHist;
    protected int endHist;
    
    /**
     * The constructor for this class, which will set its fields' values and get some information
     * about the histogram.
     * @param histogram the histogram to be plotted.
     * @param title the title of the plot.
     */
    public DisplayHistogram(int histogram[][], String title, String[] names) {
        ToolTipManager.sharedInstance().setInitialDelay(100);
    	this.histogram = histogram;
        this.title = title;
        this.names = names;
        setHistRange(0, this.histogram.length);
        
        scale();
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public DisplayHistogram(int histogram[], String title, String name) {
        ToolTipManager.sharedInstance().setInitialDelay(100);
    	this.histogram = new int[1][];
    	this.histogram[0] = histogram;
        this.title = title;
        this.names = new String[1]; 
        this.names[0] = name;
        setHistRange(0, this.histogram.length);
        
        scale();
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public void setHistRange(int init, int end) {
    	this.initHist = init; // inclusive
    	this.endHist = end; // exclusive
    }
    
    /**
     * Overrided the default methods
     */
    
    public void changeHistogram(int newhist[][]) {
        setHistogram(newhist);
    }
    
    public void scale() {
        // Calculate the components dimensions.
        width = histogram[0].length*binWidth;
        // Get the histogram data.
        counts = histogram;
        // Get the max and min counts.
        maxCount = Integer.MIN_VALUE;
        minCount = 0;
        for (int i = initHist; i < endHist; i++) {
			for (int c = 0; c < counts[i].length; c++) {
				maxCount = Math.max(maxCount, counts[i][c]);
				minCount = Math.min(minCount, counts[i][c]);
			}
        }
    }
    
    public int[][] getHistogram() {
        return histogram;
    }
    
    public void setHistogram(int[][] histogram) {
        this.histogram = histogram;
        setHistRange(0, histogram.length);
    }
    
    public int[] getHistogram(int index) {
        return histogram[index];
    }
    
    public void setHistogram(int[] histogram, int index) {
        this.histogram[index] = histogram;
        setHistRange(index, index + 1);
    }
    
    public void setHistogram(int[][] histogram, Color[] barColor) {
        this.histogram = histogram;
        setBarColor(barColor);
        setHistRange(0, histogram.length);
    }
    
    public void setHistogram(int[] histogram, int index, Color barColor) {
        this.histogram[index] = histogram;
        setBarColor(barColor, index);
        setHistRange(index, index + 1);
    }
    
    public void setBinWidth(int newWidth) {
        binWidth = newWidth;
        width = histogram[0].length*binWidth;
    }
    
    /**
     * Override the default height for the plot.
     * @param h the new height.
     */
    public void setHeight(int h) {
        height = h;
    }
    
    /**
     * Override the index multiplying factor (for bins with width != 1)
     */
    public void setIndexMultiplier(int i) {
        indexMultiplier = i;
    }
    
    /**
     * Override the index skipping factor (determines how many labels will be
     * printed on the index axis).
     */
    public void setSkipIndexes(int i) {
        skipIndexes = i;
    }
    
    /**
     * Set the maximum value (used to scale the histogram y-axis). The default value
     * is defined in the constructor and can be overriden with this method.
     */
    public void setMaxCount(int m) {
        maxCount = m;
    }
    
    /**
     * This method informs the maximum size of this component, which will be the same as the preferred size.
     */
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    /**
     * This method informs the minimum size of this component, which will be the same as the preferred size.
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    
    /**
     * This method informs the preferred size of this component, which will be constant.
     */
    public Dimension getPreferredSize() {
        return new Dimension(width+border.left+border.right,height+border.top+border.bottom);
    }
    
    /**
     * This method will paint the component.
     */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        scale();
        // Draw the background.
        g2d.setColor(backgroundColor);
        g2d.fillRect(0,0,getSize().width,getSize().height);
        // Draw some marks.
        g2d.setColor(marksColor);
        g2d.drawRect(border.left,border.top,width,height);
        // Draw the histogram bars.
        for (int i = initHist; i < endHist; i++) {
			g2d.setColor(barColor[i]);
			for (int bin = 0; bin < histogram[i].length; bin++) {
				int x = border.left + bin * binWidth;
				double offset = counts[i][bin];
				if (offset < 0) {
					offset = 0;
				}
				double barStarts = border.top + height
						* (maxCount - Math.abs(offset))
						/ (1. * (maxCount - minCount));// //////
				// if (counts[bin] < -30)
				// System.out.println("hullla");
				double barEnds = Math.ceil(height * Math.abs(counts[i][bin])
						/ (1. * (maxCount - minCount)));// ////////
				g2d.drawRect(x, (int) barStarts, binWidth - 1, (int) barEnds);
				// Get the selected X and Y
				if ((showTip) && (bin == binTip)) {
					xTip = x;
					yTip = (int) barStarts;
				}
			}
        }
        
//        for(int bin=0;bin<histogram.length; bin++) {
//            int x = border.left+bin*binWidth;
//            double barStarts = border.top+height*(maxCount-counts[bin])/(1.*maxCount);
//            double barEnds = Math.ceil(height*counts[bin]/(1.*maxCount));
//            g2d.drawRect(x,(int)barStarts,binWidth-1,(int)barEnds);
//        }
        
        // Draw the values on the horizontal axis. We will plot only 1/8th of them.
        g2d.setColor(marksColor);
        g2d.setFont(fontSmall);
        FontMetrics metrics = g2d.getFontMetrics();
        int halfFontHeight = metrics.getHeight()/2;
        for(int bin=0;bin<=histogram[0].length;bin++) {
            if (bin % skipIndexes == 0) {
                String label = ""+(indexMultiplier*bin);
                int textHeight = metrics.stringWidth(label); // remember it will be rotated!
                int x = border.left+bin*binWidth+binWidth/2;
                g2d.translate(border.left+bin*binWidth+halfFontHeight,border.top+height+textHeight+2);
                g2d.rotate(-Math.PI/2);
                g2d.drawString(label,0,0);
                g2d.rotate(Math.PI/2);
                g2d.translate(-(border.left+bin*binWidth+halfFontHeight),-(border.top+height+textHeight+2));
            }
        }
        // Draw the values on the vertical axis. Let's draw only some of them.
        double step = (int)((maxCount-minCount)/verticalTicks);
        for(int l=0;l<=(verticalTicks);l++) // last will be done separately ////// minCounts antes 0
        {
            String label;
            if (l == verticalTicks) label = ""+maxCount;
            else label = ""+(minCount + l*step);
            int textWidth = metrics.stringWidth(label);
            g2d.drawString(label,border.left-2-textWidth,border.top+height-l*(height/verticalTicks));
        }
        // Draw the title.
        g2d.setFont(fontLarge);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(title);
        g2d.drawString(title,(border.left+width+border.right-textWidth)/2,28);
        
        // Draw the information of the selected X
        if (showTip) {
        	g2d.setColor(selectedColor);
        	g2d.drawRect(xTip - 2, yTip - 2, 4, 4);
        	g2d.setFont(fontInfo);
        	g2d.setColor(backgroundInfoColor);
        	metrics = g2d.getFontMetrics();
        	int fontHeight = metrics.getHeight();
//        	String text = (indexMultiplier*binTip)+": "+counts[binTip];
            String text = indexMultiplier * binTip + ": ";
            for (int i = initHist; i < endHist; i++) {
            	text += names[i] + "=" + counts[i][binTip] + " ";
            }
        	textWidth = metrics.stringWidth(text);
        	g2d.fillRect(xTip - textWidth/2 - leftGap, yTip - fontHeight - 5, textWidth + (leftGap << 1), fontHeight);
        	g2d.setColor(Color.BLACK);
        	g2d.drawRect(xTip - textWidth/2 - leftGap, yTip - fontHeight - 5, textWidth + (leftGap << 1), fontHeight);
        	g2d.setColor(selectedInfoColor);
        	g2d.drawString(text, xTip - textWidth/2, yTip - 5 - bottomGap);
        }
    }
    
    /**
     * This method does not do anything, it is here to keep the MouseMotionListener interface happy.
     */
    public void mouseDragged(MouseEvent e) {
    }
    
    /**
     * This method will be called when the mouse is moved over the component. It will
     * set the tooltip text on the component to show the histogram data.
     */
    public void mouseMoved(MouseEvent e) {
        int x = e.getX(); int y = e.getY();
        // Don't show anything out of the plot region.
        if ((x >= border.left) && (x < border.left+width) && (y > border.top) && (y < border.top+height)) {
            // Convert the X to an index on the histogram.
            x = (x-border.left)/binWidth;
//            y = counts[x];
            String msg = indexMultiplier * x + ": ";
            for (int i = initHist; i < endHist; i++) {
            	msg += names[i] + "=" + counts[i][x] + " ";
            }
//            setToolTipText((indexMultiplier*x)+": "+y);
            setToolTipText(msg);
        } else {
            setToolTipText(null);
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() > 1) {
			setToolTipText(null);
			showTip = false;
			repaint();
		}
		else {
			int x = e.getX(); int y = e.getY();
	        // Don't show anything out of the plot region.
	        if ((x >= border.left) && (x < border.left+width) && (y > border.top) && (y < border.top+height)) {
	            // Convert the X to an index on the histogram.
	            x = (x-border.left)/binWidth;
//	            y = counts[x];
	            showTip = true;
	            binTip = x;
	        } else {
	            setToolTipText(null);
	            showTip = false;
	        }
	        repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param barColor the barColor to set
	 */
	public void setBarColor(Color[] barColor) {
		this.barColor = barColor;
	}
	
	public void setBarColor(Color barColor, int index) {
		this.barColor[index] = barColor;
	}
} // end class