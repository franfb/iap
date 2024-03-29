package vision;

import hips.Partition;
import hips.images.Image;
import hips.images.gray8.ImageGray8;
import hips.images.rgb.ImageRGB;
import hips.region.NewRegionEvent;
import hips.region.NewRegionListener;
import ij.ImagePlus;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;

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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

public class MainWindow {

	private JFrame frame;
	private JFileChooser chooser = null;
	public JTabbedPane tabbedPane;
	public JLabel infoLabel;
	private MyMouseListener listener;

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
		frame.setTitle("Visi�n por ordenador.  Curso 2010 / 2011.");
		frame.setBounds(100, 100, 635, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		listener = new MyMouseListener(this);
		
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
		
				JMenu mnFile = new JMenu("Archivo");
				menuBar_1.add(mnFile);
				
						JMenuItem mntmOpenImageFrom = new JMenuItem("Abrir desde un archivo");
						mntmOpenImageFrom.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								int returnValue = getFileChooser().showOpenDialog(frame);
								if (returnValue == JFileChooser.APPROVE_OPTION) {
									File file = getFileChooser().getSelectedFile();
									Image img = Image.getImage(new ImagePlus(file.getAbsolutePath()));
									insertAndListenImage(img, false);
								}
							}
						});
						mnFile.add(mntmOpenImageFrom);
						
								JMenuItem mntmOpenImageFrom_1 = new JMenuItem("Abrir desde una URL");
								mnFile.add(mntmOpenImageFrom_1);
								
								JMenuItem mntmExit = new JMenuItem("Salir");
								mntmExit.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.exit(0);
									}
								});
								mnFile.add(mntmExit);
								
								JMenu mnDigitalizacin = new JMenu("Digitalizaci\u00F3n");
								menuBar_1.add(mnDigitalizacin);
								
								JMenuItem mntmMuestrear = new JMenuItem("Muestrear");
								mnDigitalizacin.add(mntmMuestrear);
								
								JMenuItem mntmCuantizar = new JMenuItem("Cuantizar");
								mnDigitalizacin.add(mntmCuantizar);
								
								JMenu mnSegmentacin = new JMenu("Segmentaci\u00F3n");
								menuBar_1.add(mnSegmentacin);
								
								JMenuItem mntmSimplifyImage = new JMenuItem("Segmentar mediante HIPS");
								mnSegmentacin.add(mntmSimplifyImage);
								mntmSimplifyImage.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (tabbedPane.getTabCount() == 0){
											return;
										}
										simplifyImage(getImage(tabbedPane.getSelectedIndex()));
									}
								});
	}

	private void simplifyImage(final Image img){
		final Partition p = img.newPartition();
		final Image result = img.newImageRGB();
		result.setTitle(img.getTitle() + " - simplified");
		result.panel.setPartition(p);
		insertImage(result, true);
		p.addNewRegionEventListener(new NewRegionListener() {
			public void newRegionCreated(NewRegionEvent evt) {
				result.paintRegion(evt.getRegion(), img.toRGB(img.getMeanValue(evt.getRegion())));
			}
		});
		new Thread(new Runnable() {
			public void run() {
				p.makeRegions();
				listenImage(result, true);
				System.out.println("Regiones1: " + p.getRegionSize());
			}
		}).start();
	}
	
	private void insertAndListenImage(Image img, boolean simplified){
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.insertTab(img.getTitle(), null, scrollPane, null, 0);
		scrollPane.setViewportView(img.panel);
		tabbedPane.setSelectedIndex(0);
		if (simplified){
			MyMouseListenerForSimplified listener = new MyMouseListenerForSimplified(this, img.panel);
			img.panel.addMouseListener(listener);
			img.panel.addMouseMotionListener(listener);
		}
		else{
			img.panel.addMouseListener(listener);
			img.panel.addMouseMotionListener(listener);
		}
	}
	
	private void insertImage(Image img, boolean simplified){
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.insertTab(img.getTitle(), null, scrollPane, null, 0);
		scrollPane.setViewportView(img.panel);
		tabbedPane.setSelectedIndex(0);
	}
	
	private void listenImage(Image img, boolean simplified){
		if (simplified){
			MyMouseListenerForSimplified listener = new MyMouseListenerForSimplified(this, img.panel);
			img.panel.addMouseListener(listener);
			img.panel.addMouseMotionListener(listener);
		}
		else{
			img.panel.addMouseListener(listener);
			img.panel.addMouseMotionListener(listener);
		}
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
}
