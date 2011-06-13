package procesos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.SpinnerNumberModel;

import dialogs.FiltradoDialog;
import filtros.FiltroKVec;
import filtros.FiltroMedia;
import filtros.FiltroMediana;
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
		dialog.spK.setModel(new SpinnerNumberModel(4, 1, 8, 1));
		
		ButtonGroup groupTipo = new ButtonGroup();
		groupTipo.add(dialog.rbMedia);
		groupTipo.add(dialog.rbMediana);
		groupTipo.add(dialog.rbModa);
		groupTipo.add(dialog.rbKvecinos);
		
		dialog.rbMedia.setSelected(true);
		
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
				else if (dialog.rbModa.isSelected()) {
					new FiltroModa().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue());
				}
				else if (dialog.rbKvecinos.isSelected()) {
					new FiltroKVec().ventanaMovil(im, newIm, (Integer)dialog.spTam.getValue(), (Integer)dialog.spK.getValue());
				}
				newIm.resetInfo();
				newIm.panel.repaint();
				dialog.setVisible(false);
			}
		});
		
		dialog.setVisible(true);
	}
}
