package vision;

import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ImageWindow extends JFrame implements ImageObserver{
	private static final long serialVersionUID = 1L;
	
	public JPanel imagePanel;
	public JLabel pixelLabel;
	public JLabel valueLabel;
	public Image img;
	

	public ImageWindow(Image img, String title) {
		this.img = img;
		this.setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		
		imagePanel = new ImagePanel(this);
//		imagePanel = new JPanel();
		imagePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
						.addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		pixelLabel = new JLabel("Pixel:");
		valueLabel = new JLabel("Value:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(pixelLabel)
						.addComponent(valueLabel, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(pixelLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
					.addComponent(valueLabel))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		this.pack();
		this.setVisible(true);
	}
}
