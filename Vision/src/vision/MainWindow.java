package vision;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class MainWindow {

	public static JFrame frame;

	public static CloseableTabbedPane tabbedPane;
	
	public static JLabel infoLabel;
	private static MyMouseListener listener;

	public static JLabel labelRDR;

	public static JLabel labelRDG;

	public static JLabel labelRDB;

	public static JLabel labelBrillo;

	public static JLabel labelContraste;

	public static JLabel labelEntropia;

	public static JLabel labelFormato;
	

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
					//showInfo();
				}
			}
		});

		infoLabel = new JLabel("");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n de la imagen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)))
		);
		
		JLabel lblRangoDinamico = new JLabel("Rango Din\u00E1mico");
		
		JLabel lblR = new JLabel("R");
		
		JLabel lblG = new JLabel("G");
		
		JLabel lblB = new JLabel("B");
		
		labelRDR = new JLabel("");
		
		labelRDG = new JLabel("");
		
		labelRDB = new JLabel("");
		
		JLabel lblBrillo = new JLabel("Brillo");
		
		labelBrillo = new JLabel("");
		
		JLabel lblContraste = new JLabel("Contraste");
		
		labelContraste = new JLabel("");
		
		JLabel lblEntropa = new JLabel("Entrop\u00EDa");
		
		labelEntropia = new JLabel("");
		
		JLabel lblFormato = new JLabel("Formato");
		
		labelFormato = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRangoDinamico)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblG)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(labelRDG, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblR)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(labelRDR, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblB)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(labelRDB, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblBrillo)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelBrillo, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblContraste)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelContraste, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addContainerGap(36, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblEntropa)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelEntropia, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblFormato)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelFormato, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblRangoDinamico)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblR)
						.addComponent(labelRDR, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblG)
						.addComponent(labelRDG, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblB)
						.addComponent(labelRDB, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBrillo)
						.addComponent(labelBrillo, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblContraste)
						.addComponent(labelContraste, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEntropa)
						.addComponent(labelEntropia, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFormato)
						.addComponent(labelFormato, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(174, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
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
		/*
		if (frame.getWidth() < image.widthRoi() + 200){
			frame.setSize(new Dimension(image.widthRoi() + 200, frame.getHeight()));
		}
		if (frame.getHeight() < image.heightRoi() + 200){
			frame.setSize(new Dimension(frame.getWidth(), image.heightRoi() + 200));
		}*/
		//frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle maxSize = env.getMaximumWindowBounds();
		if ((maxSize.getWidth() < image.widthRoi() + 200) || (maxSize.getHeight() < image.heightRoi() + 200)) {
			frame.setMaximizedBounds(maxSize);
			frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
		}
		else {
			if (tabbedPane.getWidth() < image.widthRoi() + 200){
				frame.setSize(new Dimension(image.widthRoi() + 200 + (frame.getWidth() - tabbedPane.getWidth()), frame.getHeight()));
			}
			if (tabbedPane.getHeight() < image.heightRoi() + 200){
				frame.setSize(new Dimension(frame.getWidth(), image.heightRoi() + 200 + (frame.getHeight() - tabbedPane.getHeight())));
			}
		}
	}
	
	public static void showInfo() {
		Image image = getImage();
		ImageInfo info = image.getInfo();
		labelRDR.setText("[" + info.minR + ", " + info.maxR + "]");
		labelRDG.setText("[" + info.minG + ", " + info.maxG + "]");
		labelRDB.setText("[" + info.minB + ", " + info.maxB + "]");
		labelBrillo.setText("" + info.brillo);
		labelContraste.setText("" + info.contraste);
		labelEntropia.setText("" + info.entropia);
		labelFormato.setText("" + image.format);
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
