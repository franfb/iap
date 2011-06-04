package vision;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFileChooser;
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

import procesos.*;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.GridLayout;

public class MainWindow {

	public static JFrame frame;

	public static CloseableTabbedPane tabbedPane;

	public static JLabel lblPixelPos;
	private static MyMouseListener listener;
	
	public static JFileChooser chooser;
	

	public static JLabel lblRangoDinamico;
	public static JLabel lblR;
	public static JLabel lblG;
	public static JLabel lblB;
	public static JLabel lblContraste;
	public static JLabel lblEntropa;


	/*public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}*/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						/*setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN, 14));*/
					} catch (Exception ex) {
					}
					initialize();
					Menu.desactivaOpcionesMenu();
					InfoLabels.desactivaEtiquetas();
					ComponentLocation.setLocationTopLeftCorner(0.02f, 0.02f, frame);
					MainWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void initialize() {
		chooser =  new JFileChooser();
		frame = new JFrame();
		frame.setTitle("Visión por ordenador.  Curso 2010 / 2011.");
		frame.setBounds(100, 100, 820, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		listener = new MyMouseListener();

		tabbedPane = new CloseableTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tabbedPane.getTabCount() == 1) {
					Menu.activaOpcionesMenu();
					InfoLabels.activaEtiquetas();
				} else if (tabbedPane.getTabCount() == 0) {
					Menu.desactivaOpcionesMenu();
					InfoLabels.desactivaEtiquetas();
				}
				if (tabbedPane.getTabCount() > 0) {
					changeSize();
					// showInfo();
				}
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n de la imagen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);

		JLabel lblRangoDinamico = new JLabel("Rango Din\u00E1mico");
		InfoLabels.listaEtiquetas.add(lblRangoDinamico);

		lblR = new JLabel("R");
		InfoLabels.listaEtiquetas.add(lblR);

		lblG = new JLabel("G");
		InfoLabels.listaEtiquetas.add(lblG);

		lblB = new JLabel("B");
		InfoLabels.listaEtiquetas.add(lblB);

		lblBrillo = new JLabel("Brillo");
		InfoLabels.listaEtiquetas.add(lblBrillo);

		lblContraste = new JLabel("Contraste");
		InfoLabels.listaEtiquetas.add(lblContraste);

		lblEntropa = new JLabel("Entrop\u00EDa");
		InfoLabels.listaEtiquetas.add(lblEntropa);

		lblFormato = new JLabel("Formato");
		InfoLabels.listaEtiquetas.add(lblFormato);
		
		lblPixelPos = new JLabel("");
		lblPixelPos.setHorizontalAlignment(SwingConstants.CENTER);
		InfoLabels.listaEtiquetas.add(lblPixelPos);
		
		lblPixelValue = new JLabel("");
		lblPixelValue.setHorizontalAlignment(SwingConstants.CENTER);
		InfoLabels.listaEtiquetas.add(lblPixelValue);
		
		lblDimensiones = new JLabel("");
		InfoLabels.listaEtiquetas.add(lblDimensiones);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		panelHistograma = new JPanel();
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelHistograma, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblRangoDinamico)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(10)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblG)
									.addComponent(lblR)
									.addComponent(lblB)))
							.addComponent(lblBrillo)
							.addComponent(lblContraste)
							.addComponent(lblEntropa)
							.addComponent(lblFormato)
							.addComponent(lblDimensiones, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addComponent(lblPixelPos, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addComponent(lblPixelValue, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
						.addComponent(progressBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblRangoDinamico)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblR)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblG)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblB)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblBrillo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblContraste)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEntropa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFormato)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDimensiones, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPixelPos, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPixelValue, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelHistograma, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panelHistograma.setLayout(new GridLayout(1, 0, 0, 0));
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar_1 = new JMenuBar();
		frame.setJMenuBar(menuBar_1);

		JMenu mnFile = new JMenu("Archivo");
		menuBar_1.add(mnFile);

		JMenuItem mntmOpenImageFrom = new JMenuItem("Abrir");
		mntmOpenImageFrom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmOpenImageFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Abrir.run();
			}
		});
		mnFile.add(mntmOpenImageFrom);

		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Guardar.run();
			}
		});
		mnFile.add(mntmGuardar);
		Menu.opcionesMenu.add(mntmGuardar);

		JMenuItem mntmGuardarComo = new JMenuItem("Guardar como");
		mntmGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GuardarComo.run();
			}
		});
		mnFile.add(mntmGuardarComo);
		Menu.opcionesMenu.add(mntmGuardarComo);

		JMenuItem mntmExit = new JMenuItem("Salir");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
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
				Copiar.run();
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
				Muestrear.run();
			}
		});
		mnDigitalizacin.add(mntmMuestrear);
		Menu.opcionesMenu.add(mntmMuestrear);

		JMenuItem mntmCuantizar = new JMenuItem("Cuantizar");
		mntmCuantizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cuantizar.run();
			}
		});
		mnDigitalizacin.add(mntmCuantizar);
		Menu.opcionesMenu.add(mntmCuantizar);

		JMenu mnTransformacion = new JMenu("Geometr\u00EDa");
		Menu.opcionesMenu.add(mnTransformacion);
		menuBar_1.add(mnTransformacion);

		JMenuItem mntmEscalado = new JMenuItem("Escalar");
		mnTransformacion.add(mntmEscalado);
		mntmEscalado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Escalar.run();
			}
		});
		Menu.opcionesMenu.add(mntmEscalado);
		
		JMenuItem mntmTraspuesta = new JMenuItem("Trasponer");
		mnTransformacion.add(mntmTraspuesta);
		mntmTraspuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Traspuesta.run();
			}
		});
		Menu.opcionesMenu.add(mntmTraspuesta);
		
		JMenuItem mntmEspejoVertical = new JMenuItem("Espejo vertical");
		mnTransformacion.add(mntmEspejoVertical);
		mntmEspejoVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EspejoVertical.run();
			}
		});
		Menu.opcionesMenu.add(mntmEspejoVertical);
		
		JMenuItem mntmEspejoHorizontal = new JMenuItem("Espejo horizontal");
		mnTransformacion.add(mntmEspejoHorizontal);
		mntmEspejoHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EspejoHorizontal.run();
			}
		});
		Menu.opcionesMenu.add(mntmEspejoHorizontal);
		
		JMenuItem mntmRotar = new JMenuItem("Rotar");
		mntmRotar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rotar.run();
			}
		});
		mnTransformacion.add(mntmRotar);
		
		JMenu mnMiscelnea = new JMenu("Miscel\u00E1nea");
		Menu.opcionesMenu.add(mnMiscelnea);
		menuBar_1.add(mnMiscelnea);
		
		JMenuItem mntmDiferencia = new JMenuItem("Diferencia");
		mntmDiferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Diferencias.run();
			}
		});
		mnMiscelnea.add(mntmDiferencia);

	}

	public static void insertAndListenImage(Image image) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(image.panel);
		tabbedPane.addTab("", scrollPane);
		image.panel.addMouseListener(listener);
		image.panel.addMouseMotionListener(listener);
		changeImageTitle(image);
	}

	public static void changeSize() {
		Image image = getCurrentImage();
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle maxSize = env.getMaximumWindowBounds();
		if ((maxSize.getWidth() < image.widthRoi() + 200) || (maxSize.getHeight() < image.heightRoi() + 200)) {
			frame.setMaximizedBounds(maxSize);
			frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
		} else {
			if (tabbedPane.getWidth() < image.widthRoi() + 200) {
				frame.setSize(new Dimension(image.widthRoi() + 200 + (frame.getWidth() - tabbedPane.getWidth()), frame.getHeight()));
			}
			if (tabbedPane.getHeight() < image.heightRoi() + 200) {
				frame.setSize(new Dimension(frame.getWidth(), image.heightRoi() + 200 + (frame.getHeight() - tabbedPane.getHeight())));
			}
		}
	}

	static JLabel lblBrillo;
	static JLabel lblFormato;

	public static JLabel lblPixelValue;

	public static JLabel lblDimensiones;

	public static JProgressBar progressBar;

	public static JPanel panelHistograma;
	
	public static void startOperation(int max) {
		progressBar.setMaximum(max);
		progressBar.setVisible(true);
		progressBar.repaint();
	}
	
	public static void incProgressBar() {
		progressBar.setValue(progressBar.getValue() + 1);
	}
	
	public static void endOperation() {
		progressBar.setVisible(false);
		progressBar.setValue(0);
	}
	
	public static void showInfo() {
		Image image = getCurrentImage();
		ImageInfo info = image.getInfo();
		lblR.setText("R: [" + info.minR + ", " + info.maxR + "]");
		lblG.setText("G: [" + info.minG + ", " + info.maxG + "]");
		lblB.setText("B: [" + info.minB + ", " + info.maxB + "]");
		lblBrillo.setText("Brillo: " + info.brillo);
		lblContraste.setText("Contraste: " + info.contraste);
		lblEntropa.setText("Entrop\u00EDa: " + Math.rint(info.entropia * 1000) / 1000);
		lblFormato.setText("Formato: " + image.format);
		lblDimensiones.setText("Ancho=" + image.widthRoi() + ", Alto=" + image.heightRoi());
		panelHistograma.removeAll();
		panelHistograma.add(image.getHistogram());
		panelHistograma.repaint();
	}

	public static Image getCurrentImage() {
		return getImage(tabbedPane.getSelectedIndex());
	}

	public static int getImageCount(){
		return tabbedPane.getTabCount();
	}
	
	public static int getCurrentImageIndex(){
		return tabbedPane.getSelectedIndex();
	}
	
	public static void changeImageTitle(Image image) {
		String title = image.getFileCompleto().getName();
		if (!image.saved) {
			title = "* " + title;
		}
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
	}

	private static Image getImage(int tabIndex) {
		return ((ImagePanel) ((JScrollPane) tabbedPane.getComponentAt(tabIndex)).getViewport().getView()).image;
	}
}
