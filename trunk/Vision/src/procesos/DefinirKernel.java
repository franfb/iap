package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import vision.Image;
import vision.KernelFileFilter;
import vision.MainWindow;

import dialogs.DefinirKernelDialog;
import filtros.Filtro;
import filtros.Kernel;

public class DefinirKernel {

	public static void run() {
		final DefinirKernelDialog dialog = new DefinirKernelDialog();
		
		dialog.spTam.setModel(new SpinnerNumberModel(3, 3, 51, 2));
				
		crearTabla(dialog.tKernel, 3);
		
		dialog.spTam.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				crearTabla(dialog.tKernel, (Integer)dialog.spTam.getValue());
			}
		});
		
		dialog.btnCargarKernel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrir(dialog.tKernel, dialog);
			}
		});
		
		dialog.btnGuardarKernel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guardar(dialog.tKernel, dialog.tfNormalizacion.getText(), (Integer)dialog.spTam.getValue());
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
			}
		});
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Kernel h = tableToKernel(dialog.tKernel,
						(Integer) dialog.spTam.getValue(),
						parseFactor(dialog.tfNormalizacion.getText()));
				Image im = MainWindow.getCurrentImage();
				Copiar.run("Convolución de ");
				Image newIm = MainWindow.getCurrentImage();
				Filtro f = new Filtro() {
					@Override
					public int evaluar(Image im, int x, int y, int w, int h, int k, int v) { return 0; }
					@Override
					public int evaluar(Image im, int x, int y, int w, int h) { return 0; }
				};
				f.convolucion(im, newIm, h);
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
	
	public static double parseFactor(String factorText) {
		String[] numeros = factorText.split("/");
		double res = Double.parseDouble(numeros[0]);
		for (int i = 1; i < numeros.length; i++) {
			res /= Double.parseDouble(numeros[i]);
		}
		return res;
	}
	
	public static void abrir(JTable table, DefinirKernelDialog dialog) {
		MainWindow.chooser.setFileFilter(new KernelFileFilter());
		int returnValue = MainWindow.chooser.showOpenDialog(MainWindow.frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = MainWindow.chooser.getSelectedFile();
			fileToTable(table, file, dialog);
		}
	}
	
	public static void guardar(JTable table, String factor, Integer tam) {
		MainWindow.chooser.setFileFilter(new KernelFileFilter());
		int returnValue = MainWindow.chooser.showSaveDialog(MainWindow.frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = MainWindow.chooser.getSelectedFile();
			tableToFile(table, tam, factor, file);
		}
	}
	
	public static void tableToFile(JTable table, int tam, String factor, File file) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(file));
		    out.write(factor);
		    out.newLine();
		    out.write(Integer.toString(tam));
		    out.newLine();
		    
		    for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					out.write(Integer.toString((Integer)table.getValueAt(i, j)));
					out.newLine();
				}
			}
		    
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void fileToTable(JTable table, File file, DefinirKernelDialog dialog) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			dialog.tfNormalizacion.setText(line);
			line = in.readLine();
			Integer tam = Integer.parseInt(line);
			dialog.spTam.setValue(tam);
			crearTabla(table, tam);
			for (int i = 0; i < tam; i++) {
				for (int j = 0; j < tam; j++) {
					line = in.readLine();
					table.setValueAt(Integer.parseInt(line), i, j);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Kernel tableToKernel(JTable table, int tam, double factor) {
		int offset = -tam/2;
		Kernel h = new Kernel(tam, tam, offset, offset);

		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				h.set(i + offset, j + offset, factor * (Integer)table.getValueAt(i, j));
				System.out.println(h.get(i + offset, j + offset));
			}
		}
		
		return h;
	}
	
	public static void crearTabla(JTable table, int tam) {
		Object[][] contenido = new Object[tam][tam];
		String[] nombres = new String[tam];
		final Class[] clases = new Class[tam];
		for (Integer i = 1; i <= nombres.length; i++) {
			nombres[i - 1] = new String(i.toString());
			clases[i - 1] = Integer.class;
		}
		
		DefaultTableModel modelo = new DefaultTableModel(contenido, nombres) {
					Class[] columnTypes = clases;
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				};
		
		table.setModel(modelo);
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(35);
		}
	}
}
