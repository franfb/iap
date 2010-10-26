package vision;

import hips.Partition;
import hips.images.Image;
import hips.images.ImagePartitionable;
import hips.images.gray8.ImageGray8;
import hips.images.rgb.Image3;
import hips.images.rgb.ImageRGB;
import hips.region.NewRegionEvent;
import hips.region.NewRegionListener;
import ij.ImagePlus;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Canvas;
import javax.swing.JToolBar;
import javax.swing.JLabel;

public class MainWindow implements MouseListener, MouseMotionListener {

	private JFrame frame;
	private JFileChooser chooser = null;
	JTabbedPane tabbedPane;
	public JLabel infoLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
					} catch (Exception ex) {
					}
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	public JFileChooser getFileChooser(){
		if (chooser == null){
			chooser = new JFileChooser();
		}
		return chooser;
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Visión por ordenador.  Curso 2010 / 2011.");
		frame.setBounds(100, 100, 635, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		infoLabel = new JLabel("");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
				.addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar_1 = new JMenuBar();
		frame.setJMenuBar(menuBar_1);
		
				JMenu mnFile = new JMenu("File");
				menuBar_1.add(mnFile);
				
						JMenuItem mntmOpenImageFrom = new JMenuItem("Open image from file");
						mntmOpenImageFrom.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								int returnValue = getFileChooser().showOpenDialog(frame);
								if (returnValue == JFileChooser.APPROVE_OPTION) {
									File file = getFileChooser().getSelectedFile();
									Image img = Image.getImage(new ImagePlus(file.getAbsolutePath()));
									insertImage(img);
								}
							}
						});
						mnFile.add(mntmOpenImageFrom);
						
								JMenuItem mntmOpenImageFrom_1 = new JMenuItem("Open image from URL");
								mnFile.add(mntmOpenImageFrom_1);
								
								JMenuItem mntmExit = new JMenuItem("Exit");
								mntmExit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.exit(0);
									}
								});
								mnFile.add(mntmExit);
								
								JMenu mnTools = new JMenu("Tools");
								menuBar_1.add(mnTools);
								
								JMenuItem mntmSimplifyImage = new JMenuItem("Simplify image");
								mntmSimplifyImage.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (tabbedPane.getTabCount() == 0){
											return;
										}
										simplifyImage(getImage(tabbedPane.getSelectedIndex()));
									}
								});
								mnTools.add(mntmSimplifyImage);
	}

	private void simplifyImage(Image img){
		boolean isRGB = false;
		if (img instanceof ImageRGB){
			isRGB = true;
			img = new ImageGray8((ImageRGB)img);
		}
		final ImagePartitionable partitionable = (ImagePartitionable) img;
		final Partition p = partitionable.newPartition();
		Image result = null;
		if (isRGB){
			result = new Image3(img.getWidth(), img.getHeight(), "");
		}
		else{
			result = partitionable.newImageRGB();
		}
		result.setTitle(partitionable.getTitle() + " - simplified");
		insertImage(result);
		final Image r = result;
		p.addNewRegionEventListener(new NewRegionListener() {
			public void newRegionCreated(NewRegionEvent evt) {
				r.paintRegion(evt.getRegion(), partitionable.toRGB(partitionable.getMeanValue(evt.getRegion())));
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				p.makeRegions();
				
			}
		}).start();
	}
	
	private void insertImage(Image img){
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.insertTab(img.getTitle(), null, scrollPane, null, 0);
		scrollPane.setViewportView(img.panel);
		tabbedPane.setSelectedIndex(0);
		img.panel.addMouseListener(MainWindow.this);
		img.panel.addMouseMotionListener(MainWindow.this);
	}
	
	private Image getImage(int tabIndex){
//		System.out.println("tab: " + tabIndex);
//		if (tabbedPane == null){
//			System.out.println("UNOddddd");
//		}
//		if (tabbedPane.get == null){
//			System.out.println("UNO");
//		}
//		if (((JScrollPane)tabbedPane.getTabComponentAt(tabIndex)).getViewport() == null){
//			System.out.println("DOS");
//		}
//		if (((ImagePanel)((JScrollPane)tabbedPane.getTabComponentAt(tabIndex)).getViewport().getView()) == null){
//			System.out.println("TRES");
//		}
		return ((ImagePanel)((JScrollPane)tabbedPane.getComponentAt(tabIndex)).getViewport().getView()).img;
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
		infoLabel.setText("");
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
		if (e.getX() >= panel.img.getWidth() || e.getY() >= panel.img.getHeight()){
			infoLabel.setText("");
			return;
		}
		infoLabel.setText("Pixel: (x=" + e.getX() + ", y=" + e.getY()+ "),  Value: " + panel.img.getPixelValue(e.getX(), e.getY()).getString());
	}
}
