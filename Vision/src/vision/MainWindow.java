package vision;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import org.xnap.commons.gui.CloseableTabbedPane;

public class MainWindow {

	public static JFrame frame;

	public static CloseableTabbedPane tabbedPane;
	
	public static JLabel infoLabel;
	private static MyMouseListener listener;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception ex) {
					}
					initialize();
					Menu.desactivaOpcionesMenu();
					ComponentLocation.setLocationTopLeftCorner(0.02f, 0.02f, frame);
					MainWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void initialize() {
		frame = new JFrame();
		frame.setTitle("Visión por ordenador.  Curso 2010 / 2011.");
		frame.setBounds(100, 100, 635, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		listener = new MyMouseListener();

		tabbedPane = new CloseableTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getTabCount() == 1){
					Menu.activaOpcionesMenu();
				}
				else if (tabbedPane.getTabCount() == 0){
					Menu.desactivaOpcionesMenu();
				}
				if (tabbedPane.getTabCount() > 0){
					changeSize();
				}
			}
		});

		infoLabel = new JLabel("");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE).addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar_1 = new JMenuBar();
		frame.setJMenuBar(menuBar_1);

		JMenu mnFile = new JMenu("Archivo");
		menuBar_1.add(mnFile);

		JMenuItem mntmOpenImageFrom = new JMenuItem("Abrir");
		mntmOpenImageFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.abrirDesdeArchivo();
			}
		});
		mnFile.add(mntmOpenImageFrom);

		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.guardar();
			}
		});
		mnFile.add(mntmGuardar);
		Menu.opcionesMenu.add(mntmGuardar);

		JMenuItem mntmGuardarComo = new JMenuItem("Guardar como");
		mntmGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.guardarComo();
			}
		});
		mnFile.add(mntmGuardarComo);
		Menu.opcionesMenu.add(mntmGuardarComo);
		
				JMenuItem mntmExit = new JMenuItem("Salir");
				mntmExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				mnFile.add(mntmExit);
		
		JMenu mnEditar = new JMenu("Editar");
		Menu.opcionesMenu.add(mnEditar);
		menuBar_1.add(mnEditar);

		JMenuItem mntmCopiar = new JMenuItem("Copiar");
		mntmCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.copiar();
			}
		});
		mnEditar.add(mntmCopiar);
		Menu.opcionesMenu.add(mntmCopiar);


		JMenu mnDigitalizacin = new JMenu("Digitalizaci\u00F3n");
		Menu.opcionesMenu.add(mnDigitalizacin);
		menuBar_1.add(mnDigitalizacin);

		JMenuItem mntmMuestrear = new JMenuItem("Muestrear");
		mntmMuestrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.muestrear();
			}
		});
		mnDigitalizacin.add(mntmMuestrear);
		Menu.opcionesMenu.add(mntmMuestrear);

		JMenuItem mntmCuantizar = new JMenuItem("Cuantizar");
		mntmCuantizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.cuantizar();
			}
		});
		mnDigitalizacin.add(mntmCuantizar);
		Menu.opcionesMenu.add(mntmCuantizar);

		JMenu mnTransformacion = new JMenu("Transformaci\u00F3n");
		Menu.opcionesMenu.add(mnTransformacion);
		menuBar_1.add(mnTransformacion);

		JMenuItem mntmEscalado = new JMenuItem("Escalar");
		mnTransformacion.add(mntmEscalado);
		mntmEscalado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Menu.escalar();
			}
		});
		Menu.opcionesMenu.add(mntmEscalado);
		
	}

	public static void insertAndListenImage(Image image) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(image.panel);
		tabbedPane.addTab("", scrollPane);
		image.panel.addMouseListener(listener);
		image.panel.addMouseMotionListener(listener);
		changeImageTitle(image);
	}
	
	public static void changeSize(){
		Image image = getImage();
		if (frame.getWidth() < image.widthRoi() + 200){
			frame.setSize(new Dimension(image.widthRoi() + 200, frame.getHeight()));
		}
		if (frame.getHeight() < image.heightRoi() + 200){
			frame.setSize(new Dimension(frame.getWidth(), image.heightRoi() + 200));
		}
	}
	
	public static Image getImage() {
		return getImage(tabbedPane.getSelectedIndex());
	}
	
	public static void changeImageTitle(Image image) {
		String title = image.file.getName();
		if (!image.saved){
			title = "* " + title;
		}
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
	}
	
	private static Image getImage(int tabIndex) {
		return ((ImagePanel) ((JScrollPane) tabbedPane.getComponentAt(tabIndex)).getViewport().getView()).image;
	}
}
