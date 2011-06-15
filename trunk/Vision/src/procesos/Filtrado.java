package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dialogs.FiltradoDialog;
import filtros.FiltroDifEst;
import filtros.FiltroGaussiano;
import filtros.FiltroKVec;
import filtros.FiltroMedia;
import filtros.FiltroMediana;
import filtros.FiltroMedianaV6;
import filtros.FiltroModa;
import filtros.FiltroOp;

import vision.Image;
import vision.MainWindow;

public class Filtrado {

	public static void run() {
		final Image im = MainWindow.getCurrentImage();
		Copiar.run("Filtrado de ");
		final Image newIm = MainWindow.getCurrentImage();
		final FiltradoDialog dialog = new FiltradoDialog();
		
		dialog.spTam.setModel(new SpinnerNumberModel(3, 3, 55, 2));
		final SpinnerNumberModel spKModel = new SpinnerNumberModel(4, 1, 8, 1);
		dialog.spK.setModel(spKModel);
		
		ButtonGroup groupTipo = new ButtonGroup();
		groupTipo.add(dialog.rbMedia);
		groupTipo.add(dialog.rbMediana);
		groupTipo.add(dialog.rbMedianaV);
		groupTipo.add(dialog.rbModa);
		groupTipo.add(dialog.rbKvecinos);
		groupTipo.add(dialog.rbDifEstadstica);
		groupTipo.add(dialog.rbGaussiano);
		
		dialog.rbMedia.setSelected(true);
		dialog.spK.setEnabled(false);
		dialog.spSigma.setEnabled(false);
		
		MouseListener changeTipo = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (dialog.rbKvecinos.isSelected()) {
					dialog.lblKSigma.setText("N\u00BA de vecinos (k)");
					int max = (Integer)dialog.spTam.getValue() * (Integer)dialog.spTam.getValue() - 1;
					spKModel.setMaximum(max);
					spKModel.setValue(max / 2);
					dialog.spK.setEnabled(true);
					dialog.spTam.setEnabled(true);
					dialog.spSigma.setEnabled(false);
				}
				else if (dialog.rbDifEstadstica.isSelected()) {
					dialog.lblKSigma.setText("Contraste nuevo");
					spKModel.setMaximum(255);
					spKModel.setValue(im.getInfo().contraste);
					dialog.spK.setEnabled(true);
					dialog.spTam.setEnabled(true);
					dialog.spSigma.setEnabled(false);
				}
				else if (dialog.rbGaussiano.isSelected()) {
					dialog.spTam.setEnabled(false);
					dialog.spK.setEnabled(false);
					dialog.spSigma.setEnabled(true);
				}
				else {
					dialog.spTam.setEnabled(true);
					dialog.spK.setEnabled(false);
					dialog.spSigma.setEnabled(false);
				}
			}
		};
		
		dialog.rbMedia.addMouseListener(changeTipo);
		dialog.rbMediana.addMouseListener(changeTipo);
		dialog.rbMedianaV.addMouseListener(changeTipo);
		dialog.rbModa.addMouseListener(changeTipo);
		dialog.rbKvecinos.addMouseListener(changeTipo);
		dialog.rbDifEstadstica.addMouseListener(changeTipo);
		dialog.rbGaussiano.addMouseListener(changeTipo);
		
		dialog.spTam.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (dialog.rbKvecinos.isSelected()) {
					int max = (Integer)dialog.spTam.getValue() * (Integer)dialog.spTam.getValue() - 1;
					spKModel.setMaximum(max);
					spKModel.setValue(max / 2);
				}
			}
		});
		
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dialog.setVisible(false);
				MainWindow.removeCurrentImage();
			}
		});
		
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialog.rbMedia.isSelected()) {
					new FiltroMedia().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbMediana.isSelected()) {
					new FiltroMediana().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbMedianaV.isSelected()) {
					new FiltroMedianaV6().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbModa.isSelected()) {
					new FiltroModa().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbKvecinos.isSelected()) {
					new FiltroKVec().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue(), (Integer)dialog.spK.getValue());
				}
				else if (dialog.rbDifEstadstica.isSelected()) {
					new FiltroDifEst().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue(), (Integer)dialog.spK.getValue());
				}
				else if (dialog.rbGaussiano.isSelected()) {
					new FiltroGaussiano().convolucion(im, newIm, (Double)dialog.spSigma.getValue());
				}
				newIm.resetInfo();
				newIm.panel.repaint();
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
}
