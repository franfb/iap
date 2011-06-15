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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
	
	public static JLabel lblBrillo;
	public static JLabel lblFormato;

	public static JLabel lblPixelValue;

	public static JLabel lblDimensiones;

	public static JProgressBar progressBar;

	public static JPanel panelHistograma;
	
	public static DisplayHistogram hist;
	public static DisplayHistogram histAc;
	
	private static int[][] histCero = new int[4][256];
	private static int[][] histCero2 = new int[4][256];

	public static JComboBox cbTipoHist;


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
					ImagePanel.setRoiListener();
					Menu.desactivaOpcionesMenu();
					InfoLabels.desactivaEtiquetas();
					inicializaHistogramas();
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
		frame.setBounds(100, 100, 845, 642);
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
					hist.setHistogram(histCero);
					histAc.setHistogram(histCero);
					panelHistograma.repaint();
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
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 635, Short.MAX_VALUE)
					.addGap(11))
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
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
		
		lblPixelPos = new JLabel("Posicion");
//		lblPixelPos.setHorizontalAlignment(SwingConstants.CENTER);
		InfoLabels.listaEtiquetas.add(lblPixelPos);
		
		lblPixelValue = new JLabel("Valor");
//		lblPixelValue.setHorizontalAlignment(SwingConstants.CENTER);
		InfoLabels.listaEtiquetas.add(lblPixelValue);
		
		lblDimensiones = new JLabel("Dimensiones");
		InfoLabels.listaEtiquetas.add(lblDimensiones);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		panelHistograma = new JPanel();
		
		cbTipoHist = new JComboBox();
		cbTipoHist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				showInfo();
			}
		});
		cbTipoHist.setModel(new DefaultComboBoxModel(new String[] {"Escala de Grises", "Rojo", "Verde", "Azul", "RGB"}));
		cbTipoHist.setSelectedIndex(0);
		InfoLabels.listaEtiquetas.add(cbTipoHist);
		
		JLabel lblHistogramaDe = new JLabel("Histograma de");
		InfoLabels.listaEtiquetas.add(lblHistogramaDe);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRangoDinamico)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblG)
										.addComponent(lblR)
										.addComponent(lblB)))
								.addComponent(lblHistogramaDe))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblFormato, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
										.addComponent(lblBrillo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblContraste, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblEntropa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(10)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblPixelValue)
										.addComponent(lblPixelPos, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
										.addComponent(lblDimensiones, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addComponent(cbTipoHist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(5))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panelHistograma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRangoDinamico)
						.addComponent(lblFormato)
						.addComponent(lblDimensiones))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblR)
						.addComponent(lblBrillo)
						.addComponent(lblPixelPos))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblG, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblContraste, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblPixelValue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblB)
						.addComponent(lblEntropa))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbTipoHist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHistogramaDe))
					.addGap(49)
					.addComponent(panelHistograma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(60))
		);
		panelHistograma.setLayout(new GridLayout(2, 1, 0, 0));
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar_1 = new JMenuBar();
		frame.setJMenuBar(menuBar_1);

		JMenu mnFile = new JMenu("Archivo");
		menuBar_1.add(mnFile);
		
		//Menu.opcionesMenu.add(mnFile);

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
		
		JMenuItem mntmGrises = new JMenuItem("A escala de grises");
		mnEditar.add(mntmGrises);
		mntmGrises.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Grises.run();
			}
		});
		Menu.opcionesMenu.add(mntmGrises);

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
		
		JMenu mnImagen = new JMenu("Imagen");
		Menu.opcionesMenu.add(mnImagen);
		menuBar_1.add(mnImagen);
		
		JMenuItem mntmBrilloContraste = new JMenuItem("Brillo y Contraste");
		mntmBrilloContraste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BrilloContraste.run();
			}
		});
		mnImagen.add(mntmBrilloContraste);
		Menu.opcionesMenu.add(mntmBrilloContraste);
		
		JMenuItem mntmTransfLinealPor = new JMenuItem("Transf. Lineal por Tramos");
		mntmTransfLinealPor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LinealTramos.run();
			}
		});
		mnImagen.add(mntmTransfLinealPor);
		Menu.opcionesMenu.add(mntmTransfLinealPor);
		
		JMenuItem mntmEcualizarHistograma = new JMenuItem("Ecualizar Histograma");
		mnImagen.add(mntmEcualizarHistograma);
		mntmEcualizarHistograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ecualizar.run();
			}
		});
		Menu.opcionesMenu.add(mntmEcualizarHistograma);
		
		JMenuItem mntmEspecificarHistograma = new JMenuItem("Especificar Histograma");
		mntmEspecificarHistograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EspecificarHistograma.run();
			}
		});
		mnImagen.add(mntmEspecificarHistograma);
		Menu.opcionesMenu.add(mntmEspecificarHistograma);
		
		JMenuItem mntmTransfLogartmicaY = new JMenuItem("Transf. Logar\u00EDtmica y Exponencial");
		mnImagen.add(mntmTransfLogartmicaY);
		mntmTransfLogartmicaY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogaritmicoExponencial.run();
			}
		});
		Menu.opcionesMenu.add(mntmTransfLogartmicaY);

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
		
		JMenuItem mntmVerPerfil = new JMenuItem("Ver perfil");
		mnMiscelnea.add(mntmVerPerfil);
		mntmVerPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Perfil.run();
			}
		});
		Menu.opcionesMenu.add(mntmVerPerfil);
		
		JMenu mnFiltros = new JMenu("Filtros");
		menuBar_1.add(mnFiltros);
		Menu.opcionesMenu.add(mnFiltros);
		
		JMenuItem mntmFiltrosDeSuavizado = new JMenuItem("Filtros de suavizado");
		mntmFiltrosDeSuavizado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Filtrado.run();
			}
		});
		
		JMenuItem mntmRuido = new JMenuItem("Ruido");
		mnFiltros.add(mntmRuido);
		mntmRuido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ruido.run();
			}
		});
		Menu.opcionesMenu.add(mntmRuido);
		
		JMenuItem mntmDefinirKernel = new JMenuItem("Definir Kernel");
		mntmDefinirKernel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefinirKernel.run();
			}
		});
		mnFiltros.add(mntmDefinirKernel);
		mnFiltros.add(mntmFiltrosDeSuavizado);
		Menu.opcionesMenu.add(mntmDefinirKernel);
		Menu.opcionesMenu.add(mntmFiltrosDeSuavizado);

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
	
	private static void inicializaHistogramas() {
		for (int h = 0; h < histCero.length; h++) {
			for (int i = 0; i < histCero[h].length; i++) {
				histCero[h][i] = 0;
				histCero2[h][i] = 0;
			}
		}
		String[] names = { "Gris", "R", "G", "B" };
		Color[] color1 = new Color[4];
		Color[] color2 = new Color[4];
		hist = new DisplayHistogram(histCero, "Histograma", names);
		hist.setHistRange(0, 1);
		hist.setBarColor(color1);
		histAc = new DisplayHistogram(histCero2, "Histograma Acumulado", names);
		histAc.setHistRange(0, 1);
		histAc.setBarColor(color2);
		panelHistograma.add(hist);
		panelHistograma.add(histAc);
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
		//panelHistograma.removeAll();
		//panelHistograma.add(image.getHistogram());
		if (cbTipoHist.getSelectedIndex() == 0) { // Escala de grises
			hist.setHistogram(info.hist, 0, Color.DARK_GRAY);
			histAc.setHistogram(info.histAc, 0, Color.DARK_GRAY);
		}
		else if (cbTipoHist.getSelectedIndex() == 1) { // Rojo
			hist.setHistogram(info.histR, 1, new Color(192, 0, 0, 128));
			histAc.setHistogram(info.histAcR, 1, new Color(192, 0, 0, 128));
		}
		else if (cbTipoHist.getSelectedIndex() == 2) { // Verde
			hist.setHistogram(info.histG, 2, new Color(0, 192, 0, 128));
			histAc.setHistogram(info.histAcG, 2, new Color(0, 192, 0, 128));
		}
		else if (cbTipoHist.getSelectedIndex() == 3) { // Azul
			hist.setHistogram(info.histB, 3, new Color(0, 0, 255, 128));
			histAc.setHistogram(info.histAcB, 3, new Color(0, 0, 255, 128));
		}
		else if (cbTipoHist.getSelectedIndex() == 4) {
			hist.setHistogram(info.histR, 1, new Color(192, 0, 0, 128));
			histAc.setHistogram(info.histAcR, 1, new Color(192, 0, 0, 128));
			hist.setHistogram(info.histG, 2, new Color(0, 192, 0, 128));
			histAc.setHistogram(info.histAcG, 2, new Color(0, 192, 0, 128));
			hist.setHistogram(info.histB, 3, new Color(0, 0, 192, 128));
			histAc.setHistogram(info.histAcB, 3, new Color(0, 0, 192, 128));
			hist.setHistRange(1, 4);
			histAc.setHistRange(1, 4);
		}
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

	public static Image getImage(int tabIndex) {
		return ((ImagePanel) ((JScrollPane) tabbedPane.getComponentAt(tabIndex)).getViewport().getView()).image;
	}
	
	public static void removeCurrentImage() {
		tabbedPane.remove(tabbedPane.getSelectedIndex());
	}
}
