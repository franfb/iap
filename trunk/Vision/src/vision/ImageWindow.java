package vision;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;

public class ImageWindow extends JFrame implements ImageObserver{
	private static final long serialVersionUID = 1L;

	class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image img;

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(this), img.getHeight(this));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }
		  
		  public void paintComponent(Graphics g) {
			  g.drawImage(img, 0, 0, null);
		  }
	}
	
	private JPanel contentPane;

	public ImageWindow(Image img) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new ImagePanel(img);
		setContentPane(contentPane);
		this.pack();
		this.setVisible(true);
	}
}
